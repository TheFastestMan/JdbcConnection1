package dao;

import entity.Flight;
import entity.FlightStatus;
import exception.DaoException;
import util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightDao implements Dao<Long, Flight> {
    private static final FlightDao INSTANCE = new FlightDao();

    private static final String FIND_ALL_SQL = """
            SELECT id, flight_no, departure_date, 
            departure_airport_code, arrival_date,
             arrival_airport_code,aircraft_id, status 
            FROM flight
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;
    private static final String UPDATE_SQL = """
            UPDATE flight
             SET flight_no = ?, 
                 departure_date = ?,
                 departure_airport_code = ?, 
                 arrival_date = ?, 
                 arrival_airport_code = ?,
                 aircraft_id = ?,
                 status = ?
            WHERE id = ?
            """;

    private static final String SAVE_SQL = """
            insert into flight (
            flight_no, 
            departure_date,
            departure_airport_code, 
            arrival_date, 
            arrival_airport_code,
            aircraft_id,
            status
            )
            values(?,?,?,?,?,?,?);
            """;
    private static final String DELETE_SQL = """
            DELETE FROM flight WHERE
            id = ?;
            """;

    @Override
    public boolean update(Flight flight) {

        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {

            prepareStatement.setString(1, flight.getFlightNo());
            prepareStatement.setTimestamp(2, Timestamp.valueOf(flight.getDepartureDate()));
            prepareStatement.setString(3, flight.getDepartureAirportCode());
            prepareStatement.setTimestamp(4, Timestamp.valueOf(flight.getArrivalDate()));
            prepareStatement.setString(5, flight.getArrivalAirportCode());
            prepareStatement.setInt(6, flight.getAircraftId());
            prepareStatement.setString(7, String.valueOf(flight.getStatus()));
            prepareStatement.setLong(8, flight.getId());

            return prepareStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public List<Flight> findAll() {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Flight> flights = new ArrayList();

            var result = prepareStatement.executeQuery();
            while (result.next())
                flights.add(
                        buildFlight(result)
                );
            return flights;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Flight> findById(Long id) {

        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            Flight flight = null;
            prepareStatement.setLong(1, id);
            var result = prepareStatement.executeQuery();
            while (result.next()) {
                flight = buildFlight(result);
            }
            return Optional.ofNullable(flight);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Flight save(Flight flight) {

        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(SAVE_SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            prepareStatement.setString(1, flight.getFlightNo());
            prepareStatement.setTimestamp(2, Timestamp.valueOf(flight.getDepartureDate()));
            prepareStatement.setString(3, flight.getDepartureAirportCode());
            prepareStatement.setTimestamp(4, Timestamp.valueOf(flight.getArrivalDate()));
            prepareStatement.setString(5, flight.getArrivalAirportCode());
            prepareStatement.setInt(6, flight.getAircraftId());
            prepareStatement.setString(7, String.valueOf(flight.getStatus()));

            prepareStatement.executeUpdate();

            var key = prepareStatement.getGeneratedKeys();
            if (key.next())
                flight.setId(key.getLong("id"));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return flight;
    }

    @Override
    public boolean delete(Long id) {

        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(DELETE_SQL)) {
            prepareStatement.setLong(1, id);

            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Flight buildFlight(ResultSet result) throws SQLException {
        return new Flight(
                result.getLong("id"),
                result.getString("flight_no"),
                result.getTimestamp("departure_date").toLocalDateTime(),
                result.getString("departure_airport_code"),
                result.getTimestamp("arrival_date").toLocalDateTime(),
                result.getString("arrival_airport_code"),
                result.getInt("aircraft_id"),
                FlightStatus.valueOf(result.getString("status"))
        );
    }

    private FlightDao() {
    }

    public static FlightDao getInstance() {
        return INSTANCE;
    }
}
