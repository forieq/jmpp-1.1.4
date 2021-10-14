package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        UserService us = new UserServiceImpl();
        us.createUsersTable();
        us.saveUser("James", "Gosling", (byte) 65);
        us.saveUser("Alexey", "Vladykin", (byte) 45);
        us.saveUser("Leo", "Messi", (byte) 35);
        us.saveUser("Anya", "Taylor-Joy", (byte) 25);
        ArrayList<User> users = new ArrayList<>(us.getAllUsers());
        users.forEach(System.out::println);
        us.cleanUsersTable();
        us.dropUsersTable();
    }
}
