package com.example.museumapp;

import com.example.museumapp.map.Edge;
import com.example.museumapp.objects.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
                Statement statement = connection.createStatement();
                String SQL_command = "SELECT passcode FROM staff WHERE username = %c%s%c";
                SQL_command = String.format(SQL_command, '\'',username,'\'');
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
                Statement statement = connection.createStatement();
                String SQL_command = "SELECT DISTINCT obj_name FROM item";
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

    static class addCaseThread extends Thread {
        private int floor;
        private float x, y, length, width;
        private ArrayList<Edge> edges;
        private boolean acceptable;
        public addCaseThread(int floor, float x, float y, float length,
                             float width, ArrayList<Edge> edges) {
            super();
            this.floor = floor;
            this.x = x;
            this.y = y;
            this.length = length;
            this.width = width;
            this.edges = edges;
            this.acceptable = true;
        }

        public boolean getAcceptable() {
            return this.acceptable;
        }

        private boolean in(float x, float y, ArrayList<Edge> edges) {
            int count = 0;
            for (Edge edge: edges) {
                if (((edge.from_y >= y) && (edge.to_y < y)) ||
                        ((edge.from_y < y) && (edge.to_y >= y))) {
                    if (edge.from_x == edge.to_x) {
                        if (edge.from_x > x) {
                            count += 1;
                        }
                        if (edge.from_x == x) { //(x,y) is right on the edge.
                            return true;
                        }
                    }
                    else {
                        float slope = (edge.to_y - edge.from_y) / (edge.to_x - edge.from_x);
                        float interX = (y - edge.from_y) / slope + edge.from_x;
                        if (interX > x) {
                            count += 1;
                        }
                        if (interX == x) {  //(x,y) is right on the edge.
                            return true;
                        }
                    }
                }
            }
            return (count % 2 == 1);
        }

        private boolean conflict() {
            ArrayList<Edge> caseEdges = new ArrayList<Edge>();
            Edge e1 = new Edge(this.x, this.y, this.x + this.length, this.y);
            Edge e2 = new Edge(this.x + this.length, this.y, this.x + this.length, this.y + this.width);
            Edge e3 = new Edge(this.x + this.length, this.y + this.width, this.x, this.y + this.width);
            Edge e4 = new Edge(this.x, this.y + this.width, this.x, this.y);
            caseEdges.add(e1);
            caseEdges.add(e2);
            caseEdges.add(e3);
            caseEdges.add(e4);
            for (Edge edge: edges) {
                if (this.in(edge.from_x, edge.from_y, caseEdges)) {
                    return true;
                }
                if (this.in(edge.to_x, edge.to_y, caseEdges)) {
                    return true;
                }
                if (!this.in(this.x, this.y, edges)) {
                    return true;
                }
                if (!this.in(this.x + this.length, this.y, edges)) {
                    return true;
                }
                if (!this.in(this.x + this.length, this.y + this.width, edges)) {
                    return true;
                }
                if (!this.in(this.x, this.y + this.width, edges)) {
                    return true;
                }
            }
            return false;
        }

        public void run() {
            try {
                if (this.conflict()) {
                    this.acceptable = false;
                    return;
                }
                Connection connection = connect();
                Statement statement = connection.createStatement();
                //see if the position of showcase is available.
                String SQL_insert_command =
                        "INSERT INTO showcase (length_m, width_m, x, y, floor_no) VALUES ";
                SQL_insert_command += ("(" + this.length);
                SQL_insert_command += (", " + this.width);
                SQL_insert_command += (", " + this.x);
                SQL_insert_command += (", " + this.y);
                SQL_insert_command += (", " + this.floor + ");");
                statement.executeUpdate(SQL_insert_command);
                disconnect(connection);
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println(e.getClass().getName()+ ":  "+ e.getMessage());
            }
        }
    }

    public static boolean addCase(int floor, float x, float y, float length, float width, ArrayList<Edge> edges) {
        boolean acceptable = true;
        addCaseThread thread = new addCaseThread(floor, x, y, length, width, edges);
        thread.start();
        try {
            thread.join();
        }
        catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        acceptable = thread.getAcceptable();
        thread.interrupt();
        return acceptable;
    }

    static class getAllEmptyCasesThread extends Thread {
        ArrayList<ShowCase> cases = new ArrayList<ShowCase>();
        public void run() {
            try {
                Connection connection = connect();
                Statement statement = connection.createStatement();
                String SQL_command = "SELECT * FROM showcase";
                ResultSet resultSet = statement.executeQuery(SQL_command);
                while (resultSet.next()) {
                    ShowCase showCase = new ShowCase(resultSet.getInt("sid"),
                            resultSet.getInt("floor_no"),
                            resultSet.getFloat("x"),
                            resultSet.getFloat("y"),
                            resultSet.getFloat("length_m"),
                            resultSet.getFloat("width_m"),
                            null);
                    cases.add(showCase);
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

    static class getAllItemsOfShowCaseThread extends Thread {
        ArrayList<Item> items = new ArrayList<Item>();
        int sid;

        public getAllItemsOfShowCaseThread(int sid) {
            super();
            this.sid = sid;
        }

        public void run() {
            try {
                Connection connection = connect();
                Statement statement = connection.createStatement();
                String SQL_command = "SELECT * FROM item WHERE sid = " + this.sid;
                ResultSet resultSet = statement.executeQuery(SQL_command);
                while (resultSet.next()) {
                    String ID = resultSet.getString("obj_id");
                    String name = resultSet.getString("obj_name");
                    String description = resultSet.getString("obj_desc");
                    int startYear = resultSet.getInt("obj_start_year");
                    int endYear = resultSet.getInt("obj_end_year");
                    String itemUrl = resultSet.getString("obj_url");
                    String imageUrl = resultSet.getString("obj_img");
                    int closetID = this.sid;
                    Item item = new Item(ID, name, description, startYear, endYear, itemUrl, imageUrl, closetID);
                    items.add(item);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println(e.getClass().getName() + ":  " + e.getMessage());
            }
        }

        public ArrayList<Item> getItems() {
            return items;
        }
    }

    public static ArrayList<Item> getAllItemsOfShowCase(int sid) {
        ArrayList<Item> items = null;
        getAllItemsOfShowCaseThread thread = new getAllItemsOfShowCaseThread(sid);
        thread.start();
        try {
            thread.join();
        }
        catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        items = thread.getItems();
        thread.interrupt();
        return items;
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
