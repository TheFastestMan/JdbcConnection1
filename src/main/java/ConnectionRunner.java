import dao.*;

import entity.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public class ConnectionRunner {
    public static void main(String[] args) {

        /***
         *SeatDaoCheck
         */
        SeatDao seatDao = SeatDao.getInstance();

        // Test Save
        Seat seat = new Seat(new Aircraft(1L, "Боинг 777-300"), "I1");
        //seatDao.save(seat);

        // Test Update
        Seat updatedSeat = new Seat(new Aircraft(1L, "Боинг 777-300"), "I2");
        // seatDao.update(updatedSeat);

        // Test Delete
        // Aircraft aircraftToDelete = new Aircraft(2L, null);
        //seatDao.delete(aircraftToDelete);

        // Test Find All
//        List<Seat> allSeats = seatDao.findAll();
//        System.out.println("All Seats:");
//        for (Seat s : allSeats) {
//            System.out.println(s);
//        }

        // Test Find By ID
        //Aircraft searchAircraft = new Aircraft(2L, null);
//        Optional<Seat> foundSeat = seatDao.findById(searchAircraft);
//        if (foundSeat.isPresent()) {
//            System.out.println("Found Seat by ID: " + foundSeat.get());
//        } else {
//            System.out.println("Seat not found by ID.");
//        }

        /***
         *AircraftDaoCheck
         */

        AircraftDao aircraftDao = AircraftDao.getInstance();


        // Test Save
//        Aircraft aircraft = new Aircraft(8L, "Боинг 666-555");
//        aircraftDao.save(aircraft);

        // Test Update
//        Aircraft aircraft = new Aircraft(8L, "Боинг 666-888");
//        aircraftDao.update(aircraft);

        // Test Delete
        //  aircraftDao.delete(8L);

        // Test Find All
        //System.out.println(aircraftDao.findAll());

        // Test Find By ID
        // System.out.println(aircraftDao.findById(1L));

        /***
         *AirportDaoCheck
         */

        AirportDao airportDao = AirportDao.getInstance();

        // Test Save
        //Airport airport = new Airport("THL", "Thailand", "Chiang-Mai");
        //airportDao.save(airport);

        // Test Update
        //Airport airport = new Airport("THL", "Thailand", "Chiang-MAI");
        //airportDao.update(airport);

        // Test Delete
        //airportDao.delete("THL");

        // Test Find All
        //System.out.println(airportDao.findAll());

        // Test Find By ID
        // System.out.println(airportDao.findById("THL"));

        /***
         *FlightDaoCheck
         */

        FlightDao flightDao = FlightDao.getInstance();

        Long id = 27L;
        String flightNo = "WW0990";
        LocalDateTime departureDate = LocalDateTime.of(2020, 01, 01, 00, 00);
        String departureAirportCode = "LDN";
        LocalDateTime arrivalDate = LocalDateTime.of(2020, 01, 01, 07, 25);
        ;
        String arrivalAirportCode = "THL";
        Integer aircraftId = 2;
        FlightStatus status = FlightStatus.ARRIVED;

        // Test Save
        Flight flight = new Flight(id, flightNo, departureDate, departureAirportCode, arrivalDate,
                arrivalAirportCode, aircraftId, status);
//        flightDao.save(flight);

        // Test Update
//        Flight flight = new Flight(id, flightNo, departureDate, departureAirportCode, arrivalDate,
//                arrivalAirportCode, aircraftId, status);
//        flightDao.update(flight);

        // Test Delete
//        flightDao.delete(21L);

        // Test Find All
//        System.out.println(flightDao.findAll());

        // Test Find By ID
//        System.out.println(flightDao.findById(1L));

        /***
         *TicketDaoCheck
         */

        TicketDao ticketDao = TicketDao.getInstance();

        Long id2 = 64L;
        String passportNo = "NO1111";
        String passengerName = "Mark Zuckerberg";
        String seatNo = "A2";
        BigDecimal cost = new BigDecimal("1234.56");

        // Test Save
//        Ticket ticket = new Ticket(id2, passportNo, passengerName, flight, seatNo, cost);
//        ticketDao.save(ticket);

        // Test Update
//        Ticket ticket = new Ticket(id2, passportNo, passengerName, flight, seatNo, cost);
//        ticketDao.update(ticket);

        // Test Delete
//        ticketDao.delete(2L);

        // Test Find All
//        System.out.println(ticketDao.findAll());

        // Test Find By ID
//        System.out.println(ticketDao.findById(64L));
    }
}