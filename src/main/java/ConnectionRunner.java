import util.ConnectionManager;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ConnectionRunner {
    public static void main(String[] args) throws SQLException {
        Long id = 1L;
        String seat = "A1";
        Long coast = 210L;
        String passengerName = "Антон Чехов";
        String passportNo = "SB666D";
        String flightNo = "MN6666";
        String status = "DENIED666";
        LocalDate departureDate = LocalDate.of(2020, 01, 22);
        LocalDate arrivalDate = LocalDate.of(2020, 01, 22);


        updateFlightAndTicketData(id,
                seat, coast, passengerName, passportNo,
                flightNo, status, departureDate.atStartOfDay(), arrivalDate.atStartOfDay());

        getFrequentNames();

        getAmountOfTicketsPurchase();

        updateTicket(seat, coast, passengerName, passportNo, id);




    }

    public static void getFrequentNames() {
        String sql = """
                SELECT passenger_name
                FROM (SELECT passenger_name
                      FROM ticket
                      GROUP BY passenger_name
                      HAVING COUNT(passenger_name) >= 2) AS SubQuery
                ORDER BY passenger_name;
                 """;

        try (var connection = ConnectionManager.open()) {
            var prepareStatement = connection.prepareStatement(sql);

            try (var result = prepareStatement.executeQuery()) {
                System.out.println("Имена которые повторяются 2 или больше раз:");
                System.out.println("-------------------------");
                while (result.next()) {
                    System.out.println(result.getString("passenger_name"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getAmountOfTicketsPurchase() {

        String sql = """
                SELECT passenger_name, COUNT(*) AS purchase
                FROM ticket
                         INNER JOIN
                     flight ON flight.id = ticket.flight_id
                GROUP BY passenger_name
                ORDER BY purchase DESC;
                """;
        try (var connection = ConnectionManager.open()) {
            var prepareStatement = connection.prepareStatement(sql);
            try (var result = prepareStatement.executeQuery()) {
                while (result.next()) {
                    System.out.println(result.getString("passenger_name"));
                    System.out.println(result.getInt("purchase"));
                    System.out.println("--------------");

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateTicket(String seat, Long cost, String passengerName, String passportNo, Long id) {
        String sql = """
                UPDATE ticket SET seat_no = ?, cost = ?, passenger_name = ?, passport_no = ? WHERE id = ?;
                """;
        try (var connection = ConnectionManager.open()) {
            var prepareStatement = connection.prepareStatement(sql);

            prepareStatement.setString(1, String.valueOf(seat));
            prepareStatement.setLong(2, cost);
            prepareStatement.setString(3, String.valueOf(passengerName));
            prepareStatement.setString(4, String.valueOf(passportNo));
            prepareStatement.setLong(5, id);

            var result = prepareStatement.executeUpdate();
            System.out.println("Person with id " + id + " is updated.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateFlightAndTicketData(Long id,
                                                 String seat, Long cost, String passengerName, String passportNo,
                                                 String flightNo, String status, LocalDateTime departureDate,
                                                 LocalDateTime arrivalDate) throws SQLException {

        String sql = """
                UPDATE ticket SET seat_no = ?, cost = ?, passenger_name = ?, passport_no = ? WHERE flight_id = ?;
                UPDATE flight SET flight_no = ?, departure_date = ?, arrival_date = ?, status = ? WHERE id = ?;
                """;
        try (var connection = ConnectionManager.open()) {
            try {
                var prepareStatement = connection.prepareStatement(sql);
                connection.setAutoCommit(false);

                prepareStatement.setString(1, String.valueOf(seat));
                prepareStatement.setLong(2, cost);
                prepareStatement.setString(3, String.valueOf(passengerName));
                prepareStatement.setString(4, String.valueOf(passportNo));
                prepareStatement.setLong(5, id);

                prepareStatement.setString(6, String.valueOf(flightNo));
                prepareStatement.setTimestamp(7, Timestamp.valueOf((departureDate)));
                prepareStatement.setTimestamp(8, Timestamp.valueOf((arrivalDate)));
                prepareStatement.setString(9, String.valueOf(status));
                prepareStatement.setLong(10, id);

                prepareStatement.executeUpdate();
                connection.commit();

            } catch (SQLException e) {
                System.out.println("An error occurred: " + e.getMessage());
                connection.rollback();
            }
        }
    }
}
