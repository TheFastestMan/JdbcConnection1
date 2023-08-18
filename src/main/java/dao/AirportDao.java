package dao;

import entity.Aircraft;
import entity.Airport;
import exception.DaoException;
import util.ConnectionManager;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AirportDao implements Dao<String, Airport> {
    private static final AirportDao INSTANCE = new AirportDao();

    private static final String FIND_ALL_SQL = """
            SELECT code, country, city FROM airport
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE code = ?;
            """;
    private static final String UPDATE_SQL = """
            UPDATE airport
             SET country = ?, city = ?
            WHERE code = ?
            """;

    private static final String SAVE_SQL = """
            INSERT INTO airport (
            code, country, city
            )
            VALUES (?,?,?);
            """;
    private static final String DELETE_SQL = """
            DELETE FROM airport WHERE
            code = ?;
            """;


    @Override
    public boolean update(Airport airport) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {
            prepareStatement.setString(1, airport.getCountry());
            prepareStatement.setString(2, airport.getCity());
            prepareStatement.setString(3, String.valueOf(airport.getCode()));
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Airport> findAll() {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Airport> airports = new ArrayList<>();

            var result = prepareStatement.executeQuery();
            while (result.next())
                airports.add(new Airport(
                                result.getString("code"),
                                result.getString("country"),
                                result.getString("city")
                        )
                );
            return airports;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Airport> findById(String code) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            Airport airport = null;
            prepareStatement.setString(1, code);

            var result = prepareStatement.executeQuery();
            while (result.next())
                airport = new Airport(result.getString("code"),
                        result.getString("country"),
                        result.getString("city"));
            return Optional.ofNullable(airport);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Airport save(Airport airport) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(SAVE_SQL)) {

            prepareStatement.setString(1, airport.getCode());
            prepareStatement.setString(2, airport.getCity());
            prepareStatement.setString(3, airport.getCountry());

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return airport;
    }

    @Override
    public boolean delete(String code) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(DELETE_SQL)) {
            prepareStatement.setString(1, code);

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return false;
    }

    private AirportDao() {
    }

    public static AirportDao getInstance() {
        return INSTANCE;
    }
}
