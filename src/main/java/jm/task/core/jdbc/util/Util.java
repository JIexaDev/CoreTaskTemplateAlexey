package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private final static String URL = "jdbc:mysql://localhost:3306/my_schema";
    private final static String NAME = "root";
    private final static String PASS = "root";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(URL, NAME, PASS);
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException | ClassNotFoundException exception) {
            System.err.println("Не удалось установить соединение с БД!");
            return null;
        }
    }

    private static SessionFactory sessionFactory = null;
    public static SessionFactory getSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            Properties settings = new Properties();
            settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            settings.put(Environment.URL, URL);
            settings.put(Environment.USER, NAME);
            settings.put(Environment.PASS, PASS);
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
            settings.put(Environment.SHOW_SQL, "true");
            configuration.setProperties(settings);
            configuration.addAnnotatedClass(User.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (HibernateException e) {
            System.out.println("Не удалось создать сессию!");;
        }
        return sessionFactory;
    }
}
