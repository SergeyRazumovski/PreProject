package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "02633512";

//    private static final SessionFactory sessionFactory = buildSessionFactory();

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
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

//    private static SessionFactory buildSessionFactory() {
//        try {
//            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
//                    .applySettings(getHibernateProperties())
//                    .build();
//            MetadataSources sources = new MetadataSources(registry)
//                    .addAnnotatedClass(User.class);
//            Metadata metadata = sources.getMetadataBuilder().build();
//            return metadata.getSessionFactoryBuilder().build();
//        } catch (Exception e) {
//            throw new RuntimeException("Error building session factory", e);
//        }
//
//    }
//
//    public static SessionFactory getSessionFactory() {
//        return sessionFactory;
//    }
//
//    private static DataSource getDataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.postgresql.Driver");
//        dataSource.setUrl(URL);
//        dataSource.setUsername(USER);
//        dataSource.setPassword(PASSWORD);
//        return dataSource;
//    }
//
//    private static Properties getHibernateProperties() {
//    Properties properties = new Properties();
//    properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//    properties.put("hibernate.show_sql", "true");
//    properties.put("hibernate.hbm2ddl.auto", "update");
//    return properties;
//    }
}
