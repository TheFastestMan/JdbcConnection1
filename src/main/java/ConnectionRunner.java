import dao.*;

import entity.*;


public class ConnectionRunner {
    public static void main(String[] args) {
        Aircraft aircraft = new Aircraft(1L, "AAA");
        Seat seat = new Seat(aircraft, "I6");
        SeatDao seatDao = SeatDao.getInstance();

        //seatDao.save(seat);
        //System.out.println(seatDao.findAll());
        //Aircraft searchAircraft = new Aircraft(1L, null);
        //System.out.println(seatDao.findById(searchAircraft));
        seatDao.update(seat);


    }
}
