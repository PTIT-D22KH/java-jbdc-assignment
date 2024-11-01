/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jbdc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author P51
 */
public class DatabaseConnector {
    private static DatabaseConnector instance = null;
    private Connection connection = null;
    private DatabaseConnector() {
        try {
            String host = "localhost",
                    port = "3306",
                    user = "test_user",
                    password = "superSecretPassword!123",
                    name = "jbdc-assignment";
            Class.forName("com.mysql.cj.jdbc.Driver");
            String jbdc = "mysql";
            String url;
            url = String.format("jdbc:%s://%s:%s/%s", jbdc, host, port, name);
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connect to database successfully!");
        } catch (ClassNotFoundException e) {
            System.out.println("You haven't installed driver mysql");
            System.out.println(e.toString());
            throw new RuntimeException("MySQL driver not found!", e);
        } catch (SQLException e) {
            System.out.println("Failed to connect to database!");
            System.out.println(e.toString());
            throw new RuntimeException("Failed to connect to the database!", e);
        }
    }

    public static DatabaseConnector getInstance() {
        if (instance == null) {
            instance = new DatabaseConnector();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
    
}
