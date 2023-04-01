package com.example.museumapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
            System.err.println(e.getClass().getName()+ ":  "+ e.getMessage());
        }
        return null;
    }

    public static void disconnect(ResultSet resultSet, Connection connection) {
        try {
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":  "+ e.getMessage());
        }
    }

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
                if (resultSet.next()) { //if we have matched passcode for this username.
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
        }
    }

    public static int tryLogin(String username, String password) {
        int outcome;
        TryLoginThread thread = new TryLoginThread(username, password);
        thread.start();
        try {
            thread.join();  //wait for the thread to stop.
        }
        catch (InterruptedException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        outcome = thread.getOutcome();
        thread.interrupt(); //thread terminates
        return outcome;
    }

    static class GetAllNoDuplicateNamesThread extends Thread {
        private ArrayList<String> names = new ArrayList<String>();

        public ArrayList<String> getNames() {
            return names;
        }

        public void run() {
            try {
                Connection connection = connect();
                String SQL_command = "SELECT DISTINCT obj_name FROM item";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_command);
                while (resultSet.next()) {
                    names.add(resultSet.getString("obj_name"));
                }
                disconnect(resultSet, connection);
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println(e.getClass().getName()+ ":  "+ e.getMessage());
            }
        }
    }

    public static ArrayList<String> getAllNoDuplicateNames() {
        ArrayList<String> names;
        GetAllNoDuplicateNamesThread thread = new GetAllNoDuplicateNamesThread();
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        names = thread.getNames();
        thread.interrupt(); //thread terminates
        return names;
    }


    static class printcasethread extends Thread{
        private int sid,x,y,floor;
        private float length,width;
        private List<Item> items;

        ArrayList<ShowCase> scase ;
        public ArrayList<ShowCase> getcase() {
            return scase;
        }

        public void run() {
            try {
                Connection connection = connect();
                String SQL_command = "SELECT DISTINCT obj_name FROM item";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_command);
                while (resultSet.next()) {
                    sid = resultSet.getInt("sid");
                    length = resultSet.getInt("length");
                    width = resultSet.getInt("width");
                    x = resultSet.getInt("x");
                    y = resultSet.getInt("y");
                    floor = resultSet.getInt("floor");
                    scase.add(new ShowCase(sid, length, width, x, y, floor,null));
                }
                disconnect(resultSet, connection);
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println(e.getClass().getName()+ ":  "+ e.getMessage());
            }
        }



    }

    public static ArrayList<ShowCase> printcase(){
        ArrayList sc;
        printcasethread thread=new printcasethread();
        thread.start();
        try {
            thread.join();  //wait for the thread to stop.
        }
        catch (InterruptedException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        sc=thread.getcase();
        thread.interrupt();

        return sc;
    }




}
