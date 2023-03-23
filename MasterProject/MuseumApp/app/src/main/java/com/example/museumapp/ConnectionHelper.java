package com.example.museumapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionHelper {
    static private final String URL = "mapdb4p02.cda8cggjr3rt.us-east-1.rds.amazonaws.com";
    static private final String DatabaseName = "MapDB4P02";
    static private final String UserName = "dbadmin4p02";
    static private final String Password = "4p02dbadmin";
    static private Connection connection;
    public static void connect() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, UserName, Password);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void disconnect() {
        try {
            connection.close();
        }
        catch (SQLException e) {
            System.out.println("e");
        }
    }
}
