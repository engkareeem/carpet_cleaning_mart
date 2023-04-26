package com.carpetcleaningmart.Utils;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

public class DBConnection {

    private static Connection connection = null;

    public static void main( String[] args) {
        connectToDB();
    }

    public static Connection getConnection(){
        if(connection == null){
            connectToDB();
        }
        return connection;
    }

    private static void connectToDB(){
        try {
            Driver driver = new org.sqlite.JDBC();
            DriverManager.registerDriver(driver);

            connection = DriverManager.getConnection("jdbc:sqlite:cleaning_carpet_mart.sqlite");
            System.out.println("Connection successful!");
        } catch ( Exception exception ) {
            System.out.println("Connection failed");
            System.err.println( exception.getClass().getName() + ": " + exception.getMessage() );
            System.exit(0);
        }
    }
}
