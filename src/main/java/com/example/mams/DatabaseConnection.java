package com.example.mams;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection(){
        String databaseName = "java_mams";
        String databaseUser = "root"; // change it according to your MYSQL DB username
        String databasePassword = "Kc301a041";// change it according to your MYSQL DB user password
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return databaseLink;
    }
}
