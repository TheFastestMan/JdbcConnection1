package dao;

import entity.Ticket;
import exception.DaoException;
import util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketDao {
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
            SELECT id, passport_no, passenger_name, flight_id, seat_no, cost FROM ticket;
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT id, passport_no, passenger_name, flight_id, seat_no, cost FROM ticket 
            WHERE id = ?;
            """;

    public Ticket save(Ticket ticket) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(SAVE_SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            prepareStatement.setString(1, ticket.getPassportNo());
            prepareStatement.setString(2, ticket.getPassengerName());
            prepareStatement.setLong(3, ticket.getFlightId());
            prepareStatement.setString(4, ticket.getSeatNo());
            prepareStatement.setBigDecimal(5, ticket.getCost());

            prepareStatement.executeUpdate();
            var keys = prepareStatement.getGeneratedKeys();
            if (keys.next())
                ticket.setId(keys.getLong("id"));
            return ticket;
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    public static List<Ticket> findAll() {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Ticket> tickets = new ArrayList();

            var result = prepareStatement.executeQuery();
            while (result.next())
                tickets.add(
                        getTicket(result)
                );
            return tickets;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static Ticket getTicket(ResultSet result) throws SQLException {
        return new Ticket(result.getLong("id"),
                result.getString("passport_no"),
                result.getString("passenger_name"),
                result.getLong("flight_id"),
                result.getString("seat_no"),
                result.getBigDecimal("cost")
        );
    }

    public static Optional<Ticket> findById(Long id) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            prepareStatement.setLong(1, id);
            Ticket ticket = null;
            var result = prepareStatement.executeQuery();
            while (result.next()) {
                ticket = getTicket(result);
            }
            return Optional.ofNullable(ticket);
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
