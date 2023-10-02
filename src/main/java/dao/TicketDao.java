package dao;

import dto.TicketFilter;
import entity.Flight;
import entity.FlightStatus;
import entity.Ticket;
import exception.DaoException;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TicketDao implements Dao<Long, Ticket> {
    private final static TicketDao INSTANCE = new TicketDao();
    private static SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            configuration.addAnnotatedClass(Ticket.class);
            configuration.addAnnotatedClass(Flight.class);
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Failed to create sessionFactory object." + e);
        }
    }

    private TicketDao() {

    }

    public static TicketDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean update(Ticket ticket) {
        return false;
    }

    @Override
    public List<Ticket> findAll() {
        return null;
    }

    @Override
    public Optional<Ticket> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Ticket save(Ticket ticket) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(ticket);
            transaction.commit();
            return ticket;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }


    public List<Ticket> findAllByFlightId(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Ticket.class);
            criteria.add(Restrictions.eq("flight.id", id));
            return criteria.list();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
