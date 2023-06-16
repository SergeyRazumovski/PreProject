package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (\n" +
                "id SERIAL PRIMARY KEY, \n" +
                "name VARCHAR(255) NOT NULL, \n" +
                "lastName VARCHAR(255) NOT NULL, \n" +
                "age INTEGER NOT NULL \n" +
                ");";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
            System.out.println("Таблица пользователей была создана");
            Util.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println("Ошибка в методе createUsersTable: " + e.getMessage());
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица пользователей удалена из базы данных");
            Util.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println("Ошибка в методе dropUsersTable" + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users(name, lastName, age) VALUES(?, ?, ?)";

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("Пользователь " + name + " сохранён в базе данных");
            Util.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println("Ошибка в методе saveUser: " + e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Пользователь с ID " + id + " удалён из базы данных");
            } else {
                System.out.println("Пользователь с ID " + id + " не найден в базе данных");
            }
            Util.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println("Ошибка в методе removeUserById: " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {
            System.out.println("Список пользователей в таблице:");
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age"));
                users.add(user);
            }
            Util.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println("Ошибка в методе getAllUsers: " + e.getMessage());
        }
        return users;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM users";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            int rowsDeleted = statement.executeUpdate(sql);
            System.out.println(rowsDeleted + " строки были удалены из таблицы пользователей");
            Util.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println("Ошибка в методе cleanUsersTable: " + e.getMessage());
        }
    }
}
