package dao;

import entity.Flight;
import entity.FlightStatus;
import exception.DaoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightDao implements Dao<Long, Flight> {
    private static final FlightDao INSTANCE = new FlightDao();
    private static SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            configuration.addAnnotatedClass(Flight.class);
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Failed to create sessionFactory object." + e);
        }
    }


    private FlightDao() {
    }

    public static FlightDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean update(Flight flight) {
        return false;
    }

    @Override
    public List<Flight> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Flight", Flight.class).list();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Flight> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Flight save(Flight flight) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(flight);
            transaction.commit();
            return flight;
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
}
