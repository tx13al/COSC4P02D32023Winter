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
    Thread thread;

    public SQLCommandTester() {
        DB_URL = BuildConfig.DB_URL;
        DB_Name = BuildConfig.DB_Name;
        DB_User = BuildConfig.DB_User;
        DB_Pass = BuildConfig.DB_Pass;
        DB_Port = BuildConfig.DB_Port;
        this.pgURL = String.format(this.pgURL,this.DB_URL, this.DB_Port, this.DB_Name);

        System.out.println("URL:          " + pgURL);

        System.out.println("Connecting to database");
        connect();
        System.out.println("Connection status: " + status);

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM staff";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
                System.out.println(resultSet.getString(2));
                System.out.println(resultSet.getString(3));
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.print(e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Disconnecting from database");
        disconnect();
        System.out.println("Connection status: " + status);
    }

    private void connect() {

        try {
            connection = DriverManager.getConnection(pgURL, DB_User, DB_Pass);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getClass().getName()+ ":  "+ e.getMessage());
        }

//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try
//                {
//                    Class.forName("org.postgresql.Driver");
//                    connection = DriverManager.getConnection(pgURL, DB_User, DB_Pass);
//                    status = true;
//                }
//                catch (Exception e) {
//                    status = false;
//                    System.out.print(e.getMessage());
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        thread.start();
//
//        try{
//            thread.join();
//        } catch (Exception e){
//            e.printStackTrace();
//            this.status = false;
//        }

    }

    private void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getClass().getName()+ ":  "+ e.getMessage());
        }
        status = false;
    }


}
