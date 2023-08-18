package dao;

import dto.TicketFilter;
import entity.Flight;
import entity.FlightStatus;
import entity.Ticket;
import exception.DaoException;
import util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TicketDao implements Dao<Long, Ticket> {
    private final static TicketDao INSTANCE = new TicketDao();

    private static final String SAVE_SQL = """
            insert into ticket (passport_no, passenger_name, flight_id, seat_no, cost)
            values(?,?,?,?,?);
            """;

    private static final String DELETE_SQL = """
            DELETE FROM ticket WHERE
            id = ?;
            """;

    private static final String FIND_ALL_SQL = """
            SELECT t.id, t.passport_no, t.passenger_name, t.flight_id, t.seat_no, t.cost,
            f.flight_no, f.departure_date, 
            f.departure_airport_code, f.arrival_date,
            f.arrival_airport_code, f.aircraft_id, f.status 
            FROM ticket t 
            JOIN flight f ON f.id = t.flight_id
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE t.id = ?
            """;

    private static final String UPDATE_SQL = """
            UPDATE ticket
             SET seat_no = ?, 
                 cost = ?,
                 passenger_name = ?, 
                 passport_no = ?, 
                 flight_id = ? 
            WHERE id = ?
            """;

    public Ticket save(Ticket ticket) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(SAVE_SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            prepareStatement.setString(1, ticket.getPassportNo());
            prepareStatement.setString(2, ticket.getPassengerName());
            prepareStatement.setObject(3, ticket.getFlight());
            prepareStatement.setString(4, ticket.getSeatNo());
            prepareStatement.setBigDecimal(5, ticket.getCost());

            prepareStatement.executeUpdate();
            var keys = prepareStatement.getGeneratedKeys();
            if (keys.next())
                ticket.setId(keys.getLong("id"));
            return ticket;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    public boolean delete(Long id) {

        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(DELETE_SQL)) {
            prepareStatement.setLong(1, id);

            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Ticket> findAll() {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Ticket> tickets = new ArrayList();

            var result = prepareStatement.executeQuery();
            while (result.next())
                tickets.add(
                        buildTicket(result)
                );
            return tickets;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    public List<Ticket> findAll(TicketFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();

        if (filter.passengerName() != null) {
            parameters.add(filter.passengerName());
            whereSql.add("passenger_name = ?");
        }

        if (filter.seatNo() != null) {
            parameters.add("%" + filter.seatNo() + "%");
            whereSql.add("seat_no like ?");
        }
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        var where = whereSql.stream().collect(Collectors.joining(
                " AND ",
                parameters.size() > 2 ? " WHERE " : "  ",
                " LIMIT ? OFFSET ? "
        ));

        String sql = FIND_ALL_SQL + where;

        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(sql)) {
            List<Ticket> tickets = new ArrayList();

            for (int i = 0; i < parameters.size(); i++) {
                prepareStatement.setObject(i + 1, parameters.get(i));
            }
            System.out.println(prepareStatement);
            var result = prepareStatement.executeQuery();
            while (result.next())
                tickets.add(
                        buildTicket(result)
                );
            return tickets;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    private Ticket buildTicket(ResultSet result) throws SQLException {
        var flight = new Flight(
                result.getLong("flight_id"),
                result.getString("flight_no"),
                result.getTimestamp("departure_date").toLocalDateTime(),
                result.getString("departure_airport_code"),
                result.getTimestamp("arrival_date").toLocalDateTime(),
                result.getString("arrival_airport_code"),
                result.getInt("aircraft_id"),
                FlightStatus.valueOf(result.getString("status"))
        );
        return new Ticket(
                result.getLong("id"),
                result.getString("passport_no"),
                result.getString("passenger_name"),
                flight,
                result.getString("seat_no"),
                result.getBigDecimal("cost")

        );
    }

    public Optional<Ticket> findById(Long id) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            prepareStatement.setLong(1, id);
            Ticket ticket = null;
            var result = prepareStatement.executeQuery();
            while (result.next()) {
                ticket = buildTicket(result);
            }
            return Optional.ofNullable(ticket);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean update(Ticket ticket) {

        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {

            prepareStatement.setString(1, ticket.getSeatNo());
            prepareStatement.setBigDecimal(2, ticket.getCost());
            prepareStatement.setString(3, ticket.getPassengerName());
            prepareStatement.setString(4, ticket.getPassportNo());
            prepareStatement.setObject(5, ticket.getFlight());
            prepareStatement.setLong(6, ticket.getId());

            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    private TicketDao() {
    }

    public static TicketDao getInstance() {
        return INSTANCE;
    }
}
