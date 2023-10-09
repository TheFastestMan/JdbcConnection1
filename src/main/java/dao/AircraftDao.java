package dao;

import entity.Aircraft;

import exception.DaoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class AircraftDao implements Dao<Long, Aircraft> {

    private static final AircraftDao INSTANCE = new AircraftDao();
    private static SessionFactory sessionFactory;


    static {
        sessionFactory = HibernateUtil.configureWithAnnotatedClass(Aircraft.class);
    }


    private AircraftDao() {
    }

    public static AircraftDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean update(Aircraft aircraft) {
        return false;
    }

    @Override
    public List<Aircraft> findAll() {
        return null;
    }

    @Override
    public Optional<Aircraft> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Aircraft save(Aircraft aircraft) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(aircraft);
            transaction.commit();
            return aircraft;

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
