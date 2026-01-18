package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Database Connection Utility
 * UPDATED: Supports Heroku PostgreSQL and Local PostgreSQL
 */
public class DBConnection {
    
    // ============================================================
    // LOCALHOST SETTINGS (Only used when testing on your laptop)
    // You must install Postgres locally to use this, or skip local testing.
    // ============================================================
	private static final String LOCAL_URL = "jdbc:postgresql://localhost:5432/postgres"; // Change 'ksstudio_db' to your local db name
    private static final String LOCAL_USER = "postgres";
    private static final String LOCAL_PASS = "postgres123"; // Your local Postgres password
    
    static {
        try {
            // Load the PostgreSQL Driver (Instead of Oracle)
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver loaded");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Driver not found! Check your pom.xml.");
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // 1. Check for Heroku's Environment Variable
            String dbUrl = System.getenv("DATABASE_URL");

            if (dbUrl != null && !dbUrl.isEmpty()) {
                // --- HEROKU PRODUCTION MODE ---
                // Parse the complex URL: postgres://user:pass@host:port/db
                URI dbUri = new URI(dbUrl);
                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                
                // Build the standard JDBC URL
                String dbUrlForJdbc = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";
                
                conn = DriverManager.getConnection(dbUrlForJdbc, username, password);
                System.out.println("Connected to Heroku Database.");
                
            } else {
                // --- LOCALHOST DEV MODE ---
                conn = DriverManager.getConnection(LOCAL_URL, LOCAL_USER, LOCAL_PASS);
                System.out.println("Connected to Local Database.");
            }

            if (conn != null) {
                conn.setAutoCommit(true);
            }
            
        } catch (URISyntaxException e) {
            System.err.println("Error parsing Heroku DATABASE_URL: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
        return conn;
    }
    
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}