package com.example.museumapp;
import java.sql.Connection;
import java.sql.DriverManager;

public class SQLCommandTester {
    // Login credentials
    String DB_URL;
    String DB_Name;
    String DB_User;
    String DB_Pass;
    int DB_Port;

    // Connection
    private Connection connection;
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
        System.out.println("Disconnecting from database");
        disconnect();
        System.out.println("Connection status: " + status);
    }

    private void connect() {
        thread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    Class.forName("org.postgresql.Driver");
                    connection = DriverManager.getConnection(DB_URL, DB_User, DB_Pass);
                    status = true;
                }
                catch (Exception e)
                {
                    status = false;
                    System.out.print(e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
            this.status = false;
        }
    }

    private void disconnect() {
        thread.interrupt();
        status = false;
    }


}
