package dao;

import entity.User;
import exception.DaoException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Optional;
@Slf4j
public class UserDao implements Dao<Long, User> {

    private static final UserDao INSTANCE = new UserDao();
    private static SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            configuration.addAnnotatedClass(User.class);
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Failed to create sessionFactory object." + e);
        }
    }

    private UserDao() {
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        Transaction transaction = null;
        log.debug("Opening a new session");
        try (Session session = sessionFactory.openSession()) {
            log.debug("Starting a new transaction");
            log.info("Attempting to save user with name: {}", user.getName());
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            log.info("Successfully saved user with ID: {}", user.getId());
            return user;
        } catch (Exception e) {
            log.error("Failed to save user with name: {}", user.getName(), e);
            if (transaction != null) {
                transaction.rollback();
                log.warn("Transaction rolled back due to an error");
            }

            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
