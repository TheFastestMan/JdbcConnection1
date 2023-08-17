import dao.TicketDao;
import entity.Ticket;

import java.math.BigDecimal;

public class ConnectionRunner {
    public static void main(String[] args) {

        var ticketDao = TicketDao.getInstance();

        Ticket ticket = new Ticket(9L, "AN1232","Доливо-Добровольский2",
                9L,"I2",BigDecimal.TEN);

        //ticketDao.save(ticket);
        //ticketDao.delete(60L);
        //ticketDao.findAll();

     //   System.out.println(ticketDao.findById(2L));

        ticketDao.update(ticket);

    }
}
