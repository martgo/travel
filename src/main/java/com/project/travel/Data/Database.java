package com.project.travel.Data;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {

    private static Connection conn;

    static {
        String url = "jdbc:postgresql://localhost/Countries";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "W@rszawa321");
        props.setProperty("ssl", "false");
        try {
            conn = DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return conn;
    }
}