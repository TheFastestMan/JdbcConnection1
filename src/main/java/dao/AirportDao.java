package dao;

import entity.Airport;
import exception.DaoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Optional;

public class AirportDao implements Dao<String, Airport> {
    private static final AirportDao INSTANCE = new AirportDao();
    private static SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            configuration.addAnnotatedClass(Airport.class);
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Failed to create sessionFactory object." + e);
        }
    }


    private AirportDao() {
    }

    public static AirportDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean update(Airport airport) {
        return false;
    }

    @Override
    public List<Airport> findAll() {
        return null;
    }

    @Override
    public Optional<Airport> findById(String id) {
        return Optional.empty();
    }

    @Override
    public Airport save(Airport airport) {

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(airport);
            transaction.commit();
            return airport;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
