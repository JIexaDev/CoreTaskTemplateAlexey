package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.Driver;
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
}
