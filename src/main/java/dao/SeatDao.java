package dao;

import entity.Aircraft;
import entity.Airport;
import entity.Seat;
import exception.DaoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import util.ConnectionManager;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SeatDao implements Dao<Aircraft, Seat> {
    private static final SeatDao INSTANCE = new SeatDao();
    private static SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            configuration.addAnnotatedClass(SeatDao.class);
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Failed to create sessionFactory object." + e);
        }
    }


    private SeatDao() {
    }

    public static SeatDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean update(Seat seat) {
        return false;
    }

    @Override
    public List<Seat> findAll() {
        return null;
    }

    @Override
    public Optional<Seat> findById(Aircraft id) {
        return Optional.empty();
    }

    @Override
    public Seat save(Seat seat) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(seat);
            transaction.commit();
            return seat;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Aircraft id) {
        return false;
    }
}
