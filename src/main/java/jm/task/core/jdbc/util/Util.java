package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/jmdb";
    private static final String USERNAME = "dan";
    private static final String PASSWORD = "default";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static SessionFactory factory = null;

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver load failed.");
        } catch (SQLException e) {
            System.err.println("SQL connection failed.");
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        if (factory != null) {
            return factory;
        }
        try {
            Properties props = new Properties();
            props.put(Environment.URL, URL);
            props.put(Environment.USER, USERNAME);
            props.put(Environment.PASS, PASSWORD);
            props.put(Environment.DRIVER, DRIVER);
            props.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
            props.put(Environment.SHOW_SQL, "true");
            props.put(Environment.HBM2DDL_AUTO, "create-drop");

            Configuration cfg = new Configuration();
            cfg.setProperties(props);
            cfg.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(cfg.getProperties()).build();

            factory = cfg.buildSessionFactory(serviceRegistry);
        } catch (HibernateException e) {
            System.err.println("Hibernate connection failed");
        }
        return factory;
    }
}
