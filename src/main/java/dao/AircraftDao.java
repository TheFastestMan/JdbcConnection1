package dao;

import entity.Aircraft;
import exception.DaoException;
import util.ConnectionManager;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AircraftDao implements Dao<Long, Aircraft> {

    private static final AircraftDao INSTANCE = new AircraftDao();

    private static final String FIND_ALL_SQL = """
            SELECT id, model FROM aircraft
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?;
            """;
    private static final String UPDATE_SQL = """
            UPDATE aircraft
             SET model = ?
            WHERE id = ?
            """;

    private static final String SAVE_SQL = """
            INSERT INTO aircraft (
            model
            )
            VALUES (?,?);
            """;
    private static final String DELETE_SQL = """
            DELETE FROM aircraft WHERE
            id = ?;
            """;


    @Override
    public boolean update(Aircraft aircraft) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {
            prepareStatement.setString(1, aircraft.getModel());
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Aircraft> findAll() {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Aircraft> aircrafts = new ArrayList<>();

            var result = prepareStatement.executeQuery();
            while (result.next())
                aircrafts.add(new Aircraft(
                                result.getLong("id"),
                                result.getString("model")
                        )
                );
            return aircrafts;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Aircraft> findById(Long id) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {

            Aircraft aircraft = null;
            prepareStatement.setLong(1, id);

            var result = prepareStatement.executeQuery();
            while (result.next())
                aircraft = new Aircraft(result.getLong("id"),
                        result.getString("model"));
            return Optional.ofNullable(aircraft);
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public Aircraft save(Aircraft aircraft) {

        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(UPDATE_SQL,
                     Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, aircraft.getModel());
            prepareStatement.executeUpdate();
            var key = prepareStatement.getGeneratedKeys();
            if (key.next())
                aircraft.setId(key.getLong("id"));

        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return aircraft;
    }

    @Override
    public boolean delete(Long id) {

        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {
            prepareStatement.setLong(1, id);

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return false;
    }

    private AircraftDao() {
    }

    public static AircraftDao getInstance() {
        return INSTANCE;
    }
}
