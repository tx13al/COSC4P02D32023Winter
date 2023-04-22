package com.example.museumapp;

import com.example.museumapp.map.Edge;
import com.example.museumapp.objects.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

public class DatabaseHelper {
    static private String DB_User = BuildConfig.DB_User;
    static private String DB_Pass = BuildConfig.DB_Pass;
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

        public int getOutcome() {
            return outcome;
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
        private int sid;
        public addCaseThread(int floor, float x, float y, float length,
                             float width, ArrayList<Edge> edges) {
            super();
            this.floor = floor;
            this.x = x;
            this.y = y;
            this.length = length;
            this.width = width;
            this.edges = edges;
            this.sid = -1;
        }

        public int getSid() {
            return this.sid;
        }

        private boolean on(float x, float y, Edge edge) {   //check if (x, y) is on the edge.
            if (edge.from_x == edge.to_x) {
                if ((edge.from_x == x) &&
                        (((edge.from_y >= y) && (edge.to_y <= y)) ||
                                ((edge.from_y <= y) && (edge.to_y >= y)))) {
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                float slope = (edge.to_y - edge.from_y) / (edge.to_x - edge.from_x);
                float interY = slope * (x - edge.from_x) + edge.from_y;
                if (interY == y) {
                    return true;
                }
                else {
                    return false;
                }
            }
        }

        private boolean in(float x, float y, ArrayList<Edge> edges) {
            //check if the x, y is in the edge.
            int count = 0;
            for (Edge edge: edges) {
                if (on(x, y, edge)) {
                    return true;
                }
                else if (((edge.from_y >= y) && (edge.to_y < y)) ||
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

        //check if the (x, y) is on any edge in the list of edges
        private boolean on(float x, float y, ArrayList<Edge> caseEdges) {
            for (Edge edge: edges) {
                if (on(x, y, edge)) {
                    return true;
                }
            }
            return false;
        }

        //Roughly check the cases area's availability.
        private boolean conflict() {
            //check four points of the case if they are in the area.
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
            ArrayList<Edge> caseEdges = new ArrayList<Edge>();
            Edge e1 = new Edge(x, y, x + length, y);
            Edge e2 = new Edge(x + length, y, x + length, y + width);
            Edge e3 = new Edge(x + length, y + width, x, y + width);
            Edge e4 = new Edge(x, y + width, x, y);
            caseEdges.add(e1);
            caseEdges.add(e2);
            caseEdges.add(e3);
            caseEdges.add(e4);
            for (Edge edge: edges) {
                if (!this.on(edge.from_x, edge.from_y, caseEdges)) {
                    if (this.in(edge.from_x, edge.from_y, caseEdges)) {
                        return true;
                    }
                }
                if (!this.on(edge.to_x, edge.to_y, caseEdges)) {
                    if (this.in(edge.to_x, edge.to_y, caseEdges)) {
                        return true;
                    }
                }
            }
            return false;
        }

        public void run() {
            try {
                if (this.conflict()) {
                    this.sid = -1;
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
                String SQL_get_sid_command = "SELECT sid FROM showcase WHERE floor_no = " + this.floor;
                SQL_get_sid_command += " AND x = " + this.x;
                SQL_get_sid_command += " AND y = " + this.y;
                SQL_get_sid_command += " AND length_m = " + this.length;
                SQL_get_sid_command += " AND width_m = " + this.width;
                ResultSet resultSet = statement.executeQuery(SQL_get_sid_command);
                if (resultSet.next()) {
                    this.sid = resultSet.getInt("sid");
                }
                disconnect(resultSet,connection);
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println(e.getClass().getName()+ ":  "+ e.getMessage());
            }
        }
    }

    public static int addCase(int floor, float x, float y, float length, float width, ArrayList<Edge> edges) {
        int sid = -1;
        addCaseThread thread = new addCaseThread(floor, x, y, length, width, edges);
        thread.start();
        try {
            thread.join();
        }
        catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        sid = thread.getSid();
        thread.interrupt();
        return sid;
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
                disconnect(resultSet,connection);
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
                disconnect(resultSet,connection);
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

    static class deleteItemInShowCaseThread extends Thread {
        boolean success = false;
        String obj_id;
        int sid;

        public deleteItemInShowCaseThread(String item_id, int sid) {
            this.obj_id = item_id;
            this.sid = sid;
        }

        public void run() {
            try {
                Connection connection = connect();
                Statement statement = connection.createStatement();
                String SQL_query_sid_by_obj_id_command = "SELECT sid FROM item WHERE obj_id = '" + this.obj_id + "'";
                ResultSet resultSet = statement.executeQuery(SQL_query_sid_by_obj_id_command);
                if (resultSet.next()) {
                    if (resultSet.getInt("sid") == this.sid) {
                        String SQL_remove_item_sid_command = "UPDATE item SET sid = NULL WHERE obj_id = '" + this.obj_id + "'";
                        statement.executeUpdate(SQL_remove_item_sid_command);
                        success = true;
                    }
                }
                disconnect(resultSet,connection);
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println(e.getClass().getName() + ":  " + e.getMessage());
            }
        }

        public boolean getSuccess() {
            return this.success;
        }
    }

    public static boolean deleteItemInShowCase(Item item, ShowCase showCase) {
        boolean success = false;
        deleteItemInShowCaseThread thread = new deleteItemInShowCaseThread(item.getItemID(), showCase.getClosetID());
        thread.start();
        try{
            thread.join();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        success = thread.getSuccess();
        thread.interrupt();
        return success;
    }

    static class deleteShowCaseThread extends Thread {
        ShowCase showCase;

        public deleteShowCaseThread(ShowCase showCase) {
            this.showCase = showCase;
        }

        public void run() {
            try {
                Connection connection = connect();
                Statement statement = connection.createStatement();
                String SQL_delete_showCase = "DELETE FROM showcase WHERE sid = " + showCase.getClosetID();
                statement.executeUpdate(SQL_delete_showCase);
                disconnect(connection);
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println(e.getClass().getName() + ":  " + e.getMessage());
            }
        }
    }

    public static void deleteShowCase(MapPin mapPin) {
        ShowCase showCase = mapPin.getShowCase();
        deleteShowCaseThread thread = new deleteShowCaseThread(showCase);
        thread.start();
        try{
            thread.join();
        }
        catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        thread.interrupt();
        return;
    }

}
