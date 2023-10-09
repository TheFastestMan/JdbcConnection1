package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory configureWithAnnotatedClass(Class<?> annotatedClass) {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            configuration.addAnnotatedClass(annotatedClass);
            sessionFactory = configuration.buildSessionFactory();
            return sessionFactory;
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Failed to create sessionFactory object." + e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
