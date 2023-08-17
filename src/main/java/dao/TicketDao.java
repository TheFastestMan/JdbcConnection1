package dao;

public class TicketDao {
    private final static TicketDao INSTANCE = new TicketDao();




    private TicketDao() {
    }

    public static TicketDao getInstance() {
        return INSTANCE;
    }
}
