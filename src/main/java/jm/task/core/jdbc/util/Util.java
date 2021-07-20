package jm.task.core.jdbc.util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (HibernateException e) {
            System.out.println("Не удалось создать сессию!");;
        }
        return sessionFactory;
    }
}
