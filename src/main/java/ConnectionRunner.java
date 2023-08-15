import util.ConnectionManager;

import java.sql.SQLException;

public class ConnectionRunner {
    public static void main(String[] args) {

        getFrequentNames();
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

}
/*
*  Написать класс с методами:

Возвращает имена встречающиеся чаще всего done!!!

Возвращает имена пассажиров и сколько билетов пассажир купил за все время

Обновляет данные в таблице ticket по id

Обновляет данные по flight_id в таблицах flight и ticket в одной транзакции
* (если происходит ошибка, то все операции должны откатиться)
*
*
*
* */