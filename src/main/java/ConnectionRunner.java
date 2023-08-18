import dao.FlightDao;
import dao.TicketDao;
import dto.TicketFilter;
import entity.Flight;
import entity.FlightStatus;
import entity.Ticket;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ConnectionRunner {
    public static void main(String[] args) {

        LocalDate departureDate = LocalDate.of(2020, 01, 22);
        LocalDate arrivalDate = LocalDate.of(2020, 01, 22);


        Flight flight = new Flight(2L, "xxx", departureDate.atStartOfDay(),
                "MMM", arrivalDate.atStartOfDay(), "MMM", 1,
                FlightStatus.ARRIVED);
        var ticketDao = TicketDao.getInstance();
        var flightDao = FlightDao.getInstance();

        var filter = new TicketFilter(null, null, 5, 0);

        flightDao.update(flight);


    }
}
