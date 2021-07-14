package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private final static String URL = "jdbc:mysql://localhost:3306/my_schema";
    private final static String NAME = "root";
    private final static String PASS = "root";

    public static void registerDriver() {
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException throwables) {
            System.err.println("Не удалось зарегестрировать драйвер!");
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, NAME, PASS);
        } catch (SQLException throwables) {
            System.err.println("Не удалось установить соединение с БД!");
            return null;
        }
    }
}
