package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;
//1.1.3 Задача
//комментарий для проверки изменений в ветке master
public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Сергей", "Разумовский", (byte) 37);
        userService.saveUser("Екатерина", "Разумовская", (byte) 32);
        userService.saveUser("Арсений", "Разумовский", (byte) 6);
        userService.saveUser("Варвара", "Разумовская", (byte) 1);

        List<User> userList = userService.getAllUsers();
        userList.forEach(System.out::println);
        userService.removeUserById(2);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
