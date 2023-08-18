import dao.FlightDao;
import dao.TicketDao;
import dto.TicketFilter;
import entity.Flight;
import entity.Ticket;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ConnectionRunner {
    public static void main(String[] args) {
        LocalDateTime a = LocalDateTime.parse("2020-02-02");
        Flight flight = new Flight(2L,"xxx",a,
                "MMM","2020-02-02".,"MMM",1,
                "");
        var ticketDao = TicketDao.getInstance();
        var flightDao = FlightDao.getInstance();

        var filter = new TicketFilter(null, null, 5, 0);

        flightDao.update(flight);



    }
}
