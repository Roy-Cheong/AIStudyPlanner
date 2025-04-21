package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private static Connection conn;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                // Register the SQLite driver manually
                Class.forName("org.sqlite.JDBC");

                conn = DriverManager.getConnection("jdbc:sqlite:db/studyplanner.db");
                System.out.println("✅ Connected to SQLite!");
            } catch (ClassNotFoundException e) {
                System.out.println("❌ SQLite JDBC Driver not found.");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("❌ Failed to connect to the database.");
                e.printStackTrace();
            }
        }
        return conn;
    }
}
