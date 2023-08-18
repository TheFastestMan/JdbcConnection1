import dao.AircraftDao;
import dao.AirportDao;
import dao.FlightDao;
import dao.TicketDao;
import dto.TicketFilter;
import entity.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ConnectionRunner {
    public static void main(String[] args) {

        AirportDao airportDao = AirportDao.getInstance();
        Airport airport = new Airport("AAA", "IIA","IIA");

       // airportDao.save(airport);
       // airportDao.update(airport);
        //System.out.println(airportDao.findAll());
        //System.out.println(airportDao.findById("MNK"));
        //airportDao.delete("qee");

    }
}
