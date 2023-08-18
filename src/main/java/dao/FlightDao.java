package dao;

import entity.Flight;
import entity.FlightStatus;
import exception.DaoException;
import util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightDao implements Dao<Long, Flight> {
    private static final FlightDao INSTANCE = new FlightDao();

    private static final String FIND_ALL_SQL = """
            SELECT id, flight_no, departure_date, 
            departure_airport_code, arrival_date,
             arrival_airport_code,aircraft_id, status 
            FROM flight;
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

    @Override
    public boolean update(Flight flight) {

        try (var connection = ConnectionManager.open();
            var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {

            prepareStatement.setString(1,flight.getFlightNo());
            prepareStatement.setDate();1,flight.getFlightNo());


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
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
        return Optional.empty();
    }

    @Override
    public Flight save(Flight flight) {
        return null;
    }

    @Override
    public boolean delete(Long id) {

        return false;
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
