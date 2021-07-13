package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.*;
import jm.task.core.jdbc.model.User;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        User user1 = new User("Jack", "Johnson", (byte) 23);
        User user2 = new User("John", "Jackson", (byte) 32);
        User user3 = new User("Ricardo", "Milos", (byte) 40);
        User user4 = new User("Van", "Darkholme", (byte) 30);

        UserDao userDao = new UserDaoJDBCImpl();
        userDao.createUsersTable();
        userDao.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        userDao.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        userDao.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        userDao.saveUser(user4.getName(), user4.getLastName(), user4.getAge());
        List<User> allUsers = userDao.getAllUsers();
        System.out.println(allUsers);
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
