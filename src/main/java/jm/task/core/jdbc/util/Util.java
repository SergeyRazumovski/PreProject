package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    public static Connection getConnection() {
        Connection connection = null;
        try {
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String user = "postgres";
            String password = "02633512";
            connection = DriverManager.getConnection(url, user, password);
//            System.out.println("Подключение к базе данных");
        } catch (SQLException e) {
            System.out.println("Ошибка в методе getConnection: " + e.getMessage());
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
//                System.out.println("Соединение с базой данных закрыто");
            } catch (SQLException e) {
                System.out.println("Ошибка в методе closeConnection: " + e.getMessage());
            }
        }
    }

}
