package com.example.museumapp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLCommandTester {
    // Login credentials
    String DB_URL;
    String DB_Name;
    String DB_User;
    String DB_Pass;
    int DB_Port;

    // Connection
    private Connection connection = null;
    private String pgURL = "jdbc:postgresql://%s:%d/%s";
    private boolean status = false;

    // Query results
    ResultSet resultSet;

    public SQLCommandTester() {
        DB_URL = BuildConfig.DB_URL;
        DB_Name = BuildConfig.DB_Name;
        DB_User = BuildConfig.DB_User;
        DB_Pass = BuildConfig.DB_Pass;
        DB_Port = BuildConfig.DB_Port;
        this.pgURL = String.format(this.pgURL,this.DB_URL, this.DB_Port, this.DB_Name);
    }

    private void connect() {
        try {
            connection = DriverManager.getConnection(pgURL, DB_User, DB_Pass);
            status = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getClass().getName()+ ":  "+ e.getMessage());
        }
    }

    public void disconnect() {
        try {
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getClass().getName()+ ":  "+ e.getMessage());
        }
        status = false;
    }

    public void testQuery(String query) {
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
                System.out.println(resultSet.getString(2));
                System.out.println(resultSet.getString(3));
            }

            statement.close();
        } catch (SQLException e) {
            System.out.print(e.getMessage());
            e.printStackTrace();
        }
    }

    // Need to implement in another thread.
    public int tryLogin(String username, String password) {
        int outcome = 0;
        if (username != null && password != null) {
            try {
                String query = "SELECT passcode FROM staff WHERE username = %c%s%c";
                query = String.format(query, '\'',username,'\'');

                Statement statement = connection.createStatement();
                resultSet = statement.executeQuery(query);

                try {
                    if (resultSet.next()) {
                        if (resultSet.getString("passcode").equals(password)) outcome = 3; // Correct password
                        else outcome = 2; // Wrong password
                    } else outcome = 1; // Wrong username/user doesn't exist
                } catch (SQLException e) {
                    System.out.print(e.getMessage());
                    e.printStackTrace();
                }

                statement.close();
            } catch (SQLException e) {
                System.out.print(e.getMessage());
                e.printStackTrace();
            }
        }
        return outcome; // No username/password entered
    }

}
