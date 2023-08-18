package dao;

import entity.Aircraft;
import entity.Airport;
import entity.Seat;
import exception.DaoException;
import util.ConnectionManager;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SeatDao implements Dao<Aircraft, Seat> {
    private static final SeatDao INSTANCE = new SeatDao();

    private static final String FIND_ALL_SQL = """
            SELECT s.aircraft_id, s.seat_no, a.model FROM seat s
            LEFT JOIN aircraft a ON s.aircraft_id = a.id
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE s.aircraft_id = ?;
            """;
    private static final String UPDATE_SQL = """
            UPDATE seat
             SET seat_no = ?
            WHERE aircraft_id = ?
            """;

    private static final String SAVE_SQL = """
            INSERT INTO seat (
            aircraft_id,
            seat_no
            )
            VALUES (?,?);
            """;
    private static final String DELETE_SQL = """
            DELETE FROM seat WHERE
            aircraft_id = ?;
            """;

    @Override
    public boolean update(Seat seat) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {
            prepareStatement.setString(1, seat.getSeatNo());
            prepareStatement.setLong(2, seat.getAircraft_id().getId());
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Seat> findAll() {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Seat> seats = new ArrayList<>();

            var result = prepareStatement.executeQuery();
            while (result.next())
                seats.add(new Seat(
                                new Aircraft(result.getLong("aircraft_id"),
                                        result.getString("model")),
                                result.getString("seat_no")

                        )
                );
            return seats;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Seat> findById(Aircraft aircraftId) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            Seat seat = null;
            prepareStatement.setLong(1, aircraftId.getId());

            var result = prepareStatement.executeQuery();
            while (result.next())
                seat = new Seat(
                        new Aircraft(result.getLong("aircraft_id"),
                                result.getString("model")),
                        result.getString("seat_no")
                );
            return Optional.ofNullable(seat);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    @Override
    public Seat save(Seat seat) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(SAVE_SQL)) {

            prepareStatement.setLong(1, seat.getAircraft_id().getId());
            prepareStatement.setString(2, seat.getSeatNo());

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return seat;
    }

    @Override
    public boolean delete(Aircraft aircraftId) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(DELETE_SQL)) {
            prepareStatement.setObject(1, aircraftId);

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return false;
    }

    private SeatDao() {
    }

    public static SeatDao getInstance() {
        return INSTANCE;
    }
}
