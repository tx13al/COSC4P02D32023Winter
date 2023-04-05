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

    public static void disconnect(Connection connection) {
        try {
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


    //getting case info
    static class printCaseThread extends Thread {
        private int sid, floor;
        private float length, width, x, y;
        private List<Item> items;
        ArrayList<ShowCase> scase = new ArrayList<ShowCase>();
        public ArrayList<ShowCase> getCase() {
            return scase;
        }

        public void run() {
            try {
                Connection connection = connect();
                String SQL_command = "SELECT * FROM showcase";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_command);
                while (resultSet.next()) {
                    sid = resultSet.getInt("sid");
                    length = resultSet.getFloat("length_m");
                    width = resultSet.getFloat("width_m");
                    x = resultSet.getInt("x");
                    y = resultSet.getInt("y");
                    floor = resultSet.getInt("floor_no");
                    scase.add(new ShowCase(sid, floor, x, y, length, width, null));
                    scase.add(new ShowCase(sid, floor, x, y, length, width,null));
                }
                disconnect(resultSet, connection);
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println(e.getClass().getName()+ ":  "+ e.getMessage());
            }
        }



    }

    public static ArrayList<ShowCase> printCase() {
        ArrayList sc;
        printCaseThread thread = new printCaseThread();
        thread.start();
        try {
            thread.join();  //wait for the thread to stop.
        }
        catch (InterruptedException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        sc = thread.getCase();
        sc=thread.getCase();
        thread.interrupt();
        return sc;
    }

    static class addCaseThread extends Thread {
        int floor;
        float x, y, length, width;
        public addCaseThread(int floor, float x, float y, float length, float width) {
            super();
            this.floor = floor;
            this.x = x;
            this.y = y;
            this.length = length;
            this.width = width;
        }
        public void run() {
            try {
                Connection connection = connect();
                String SQL_command =
                        "INSERT INTO showcase (length_m, width_m, x, y, floor_no) VALUES ";
                SQL_command += ("(" + length);
                SQL_command += (", " + width);
                SQL_command += (", " + x);
                SQL_command += (", " + y);
                SQL_command += (", " + floor + ");");
                Statement statement = connection.createStatement();
                statement.executeUpdate(SQL_command);
                disconnect(connection);
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println(e.getClass().getName()+ ":  "+ e.getMessage());
            }
        }
    }

    //TODO If the case position cannot be put at that position, return false.
    public static boolean addCase(int floor, float x, float y, float length, float width) {
        addCaseThread thread = new addCaseThread(floor, x, y, length, width);
        thread.start();
        try {
            thread.join();
        }
        catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        thread.interrupt();
        return true;
    }

    static class getAllEmptyCasesThread extends Thread {
        ArrayList<ShowCase> cases = new ArrayList<ShowCase>();
        public void run() {
            try {
                Connection connection = connect();
                String SQL_command = "SELECT * FROM showcase";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_command);
                while (resultSet.next()) {
                    ShowCase showCase = new ShowCase(resultSet.getInt("sid"),
                            resultSet.getInt("floor_no"),
                            resultSet.getFloat("x"),
                            resultSet.getFloat("y"),
                            resultSet.getFloat("length_m"),
                            resultSet.getFloat("width_m"),
                            null);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println(e.getClass().getName()+ ":  "+ e.getMessage());
            }
        }
        public ArrayList<ShowCase> getCases() {
            return cases;
        }
    }

    public static ArrayList<ShowCase> getAllEmptyCases() {
        ArrayList<ShowCase> cases = null;
        getAllEmptyCasesThread thread = new getAllEmptyCasesThread();
        thread.start();
        try {
            thread.join();
        }
        catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        cases = thread.getCases();
        thread.interrupt();
        return cases;
    }

//delete case from database
//    static class deleteCaseThread extends Thread {
//        int sid;
//        public deleteCaseThread(int sid) {
//            super();
//            this.sid = sid;
//        }
//        public void run() {
//            try {
//                Connection connection = connect();
//                String SQL_command =
//                        "DELETE FROM showcase WHERE sid=";
//                SQL_command += ("(" + sid +");");
//                Statement statement = connection.createStatement();
//                statement.executeUpdate(SQL_command);
//                disconnect(connection);
//            } catch (SQLException e) {
//                e.printStackTrace();
//                System.err.println(e.getClass().getName()+ ":  "+ e.getMessage());
//            }
//        }
//    }
//
//    public static boolean deleteCase(int sid) {
//        deleteCaseThread thread = new deleteCaseThread(sid);
//        thread.start();
//        try {
//            thread.join();
//        }
//        catch (InterruptedException e) {
//            System.err.print(e.getMessage());
//        }
//        thread.interrupt();
//        return true;
//    }



}
