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
        Connection connection = null;
        Statement statement = null;
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
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
                connection.rollback();
            } catch (SQLException e) {
                System.err.println("При попытке роллбэка произошла ошибка!");
            }
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException throwables) {
                System.err.println("Не удалось закрыть соединение!");
            }
        }
    }

    public void dropUsersTable() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
            statement.execute("drop table users;");
            connection.commit();
        } catch (SQLException throwables) {
            System.out.println("Не удалось удалить таблицу");
            try {
                connection.rollback();
            } catch (SQLException e) {
                System.err.println("При попытке роллбэка произошла ошибка!");
            }
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException throwables) {
                System.err.println("Не удалось закрыть соединение!");
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Util.getConnection();
            preparedStatement = connection.prepareStatement("insert into users (name, lastName, age)" +
                    " values (?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("User с именем " + name + " добавлен в базу данных!");
        } catch (SQLException throwables) {
            System.out.println("Не удалось добавить строку");
            try {
                connection.rollback();
            } catch (SQLException e) {
                System.err.println("При попытке роллбэка произошла ошибка!");
            }
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            } catch (SQLException throwables) {
                System.err.println("Не удалось закрыть соединение!");
            }
        }
    }

    public void removeUserById(long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Util.getConnection();
            preparedStatement = connection.prepareStatement("delete from users where id=?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            System.out.println("Не удалось удалить строку с данным id");
            try {
                connection.rollback();
            } catch (SQLException e) {
                System.err.println("При попытке роллбэка произошла ошибка!");
            }
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            } catch (SQLException throwables) {
                System.err.println("Не удалось закрыть соединение!");
            }
        }
    }

    public List<User> getAllUsers() {
        Connection connection = null;
        Statement statement = null;
        List<User> userList = null;
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from users;");
            userList = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
            connection.commit();
        } catch (SQLException throwables) {
            System.out.println("Не удалось получить список юзеров");
            try {
                connection.rollback();
            } catch (SQLException e) {
                System.err.println("При попытке роллбэка произошла ошибка!");
            }
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException throwables) {
                System.err.println("Не удалось закрыть соединение!");
            }
        }
        return userList;
    }

    public void cleanUsersTable() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate("truncate table users;");
            connection.commit();
        } catch (SQLException throwables) {
            System.out.println("Не удалось очистить таблицу");
            try {
                connection.rollback();
            } catch (SQLException e) {
                System.err.println("При попытке роллбэка произошла ошибка!");
            }
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException throwables) {
                System.err.println("Не удалось закрыть соединение!");
            }
        }
    }
}