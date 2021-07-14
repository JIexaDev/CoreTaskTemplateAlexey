package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Jack", "Johnson", (byte) 23);
        userService.saveUser("John", "Jackson", (byte) 32);
        userService.saveUser("Ricardo", "Milos", (byte) 40);
        userService.saveUser("Van", "Darkholme", (byte) 30);
        userService.removeUserById(1);
        userService.removeUserById(2);
        List<User> allUsers = userService.getAllUsers();
        System.out.println(allUsers);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
