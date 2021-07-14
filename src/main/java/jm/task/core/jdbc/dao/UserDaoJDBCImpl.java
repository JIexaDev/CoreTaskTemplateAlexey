package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        } catch (SQLException throwables) {
            System.out.println("Не удалось создать таблицу");
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("drop table users;");
        } catch (SQLException throwables) {
            System.out.println("Не удалось удалить таблицу");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("insert into users (name, lastName, age)" +
                    " values('" + name + "', '" + lastName + "', " + age + ");");
            System.out.println("User с именем " + name + " добавлен в базу данных!");
        } catch (SQLException throwables) {
            System.out.println("Не удалось добавить строку");
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("delete from users where id=" + id + ";");
        } catch (SQLException throwables) {
            System.out.println("Не удалось удалить строку с данным id");
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
            return userList;
        } catch (SQLException throwables) {
            System.out.println("Не удалось получить список юзеров");
            return null;
        }
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("truncate table users;");
        } catch (SQLException throwables) {
            System.out.println("Не удалось очистить таблицу");
        }
    }
}