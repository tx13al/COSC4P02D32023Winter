package com.example.museumapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {
    static String DB_User = BuildConfig.DB_User;
    static String DB_Pass = BuildConfig.DB_Pass;
    static private String pgURL =
            "jdbc:postgresql://" + BuildConfig.DB_URL + ":" + BuildConfig.DB_Port + "/" + BuildConfig.DB_Name;

    private static Connection connect() {
        try {
            return DriverManager.getConnection(pgURL, DB_User, DB_Pass);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getClass().getName()+ ":  "+ e.getMessage());
        }
        return null;
    }

    public static void disconnect(ResultSet resultSet, Connection connection) {
        try {
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getClass().getName()+ ":  "+ e.getMessage());
        }
    }

    /*
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
    */

    static class TryLoginThread extends Thread {
        private String username, password;
        private int outcome;
        public TryLoginThread(String username, String password) {
            super();
            this.username = username;
            this.password = password;
        }

        public int getOutcome() {
            return outcome;
        }

        public void run() {
            try {
                Connection connection = connect();
                String SQL_command = "SELECT passcode FROM staff WHERE username = %c%s%c";
                SQL_command = String.format(SQL_command, '\'',username,'\'');
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_command);
                try {
                    if (resultSet.next()) {
                        if (resultSet.getString("passcode").equals(password)) {
                            this.outcome = 3;    //Correct password
                        }
                        else {
                            this.outcome = 2;    //Wrong password
                        }
                    }
                    else {
                        this.outcome = 1;    //Wrong username OR user does not exist
                    }
                    disconnect(resultSet, connection);
                } catch (SQLException e) {
                    System.err.print(e.getMessage());
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                System.err.print(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static int tryLogin(String username, String password) {
        int outcome;
        TryLoginThread thread = new TryLoginThread(username, password);
        thread.start();
        try {
            thread.join();
        }
        catch (InterruptedException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        outcome = thread.getOutcome();
        thread.interrupt();
        return outcome;
    }

}
