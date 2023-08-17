import dao.TicketDao;
import dto.TicketFilter;
import entity.Ticket;

import java.math.BigDecimal;

public class ConnectionRunner {
    public static void main(String[] args) {

        var ticketDao = TicketDao.getInstance();

       var filter = new TicketFilter(null, null, 5, 0);

         System.out.println(ticketDao.findAll(filter));


    }
}
