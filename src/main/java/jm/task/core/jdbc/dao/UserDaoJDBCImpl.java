package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE `my_schema`.`users` (\n" +
                    "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NULL,\n" +
                    "  `lastName` VARCHAR(45) NULL,\n" +
                    "  `age` TINYINT NULL,\n" +
                    "  PRIMARY KEY (`id`));");
            connection.commit();
        } catch (SQLException throwables) {
            System.out.println("Не удалось создать таблицу");
            try {
                Util.getConnection().rollback();
            } catch (SQLException e) {
                System.err.println("При попытке роллбэка произошла ошибка!");;
            }
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("drop table users;");
            connection.commit();
        } catch (SQLException throwables) {
            System.out.println("Не удалось удалить таблицу");
            try {
                Util.getConnection().rollback();
            } catch (SQLException e) {
                System.err.println("При попытке роллбэка произошла ошибка!");;
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "insert into users (name, lastName, age) values (?, ?, ?)";
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("User с именем " + name + " добавлен в базу данных!");
        } catch (SQLException throwables) {
            System.out.println("Не удалось добавить строку");
            try {
                Util.getConnection().rollback();
            } catch (SQLException e) {
                System.err.println("При попытке роллбэка произошла ошибка!");
            }
        }
    }

    public void removeUserById(long id) {
        String sql = "delete from users where id=?";
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            System.out.println("Не удалось удалить строку с данным id");
            try {
                Util.getConnection().rollback();
            } catch (SQLException e) {
                System.err.println("При попытке роллбэка произошла ошибка!");
            }
        }
    }

    public List<User> getAllUsers() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from users;");
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
            connection.commit();
            return userList;
        } catch (SQLException throwables) {
            System.out.println("Не удалось получить список юзеров");
            try {
                Util.getConnection().rollback();
            } catch (SQLException e) {
                System.err.println("При попытке роллбэка произошла ошибка!");;
            }
            return null;
        }
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("truncate table users;");
            connection.commit();
        } catch (SQLException throwables) {
            System.out.println("Не удалось очистить таблицу");
            try {
                Util.getConnection().rollback();
            } catch (SQLException e) {
                System.err.println("При попытке роллбэка произошла ошибка!");;
            }
        }
    }
}