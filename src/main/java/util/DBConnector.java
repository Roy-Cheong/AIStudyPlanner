package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private static Connection conn;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection("jdbc:sqlite:db/studyplanner.db");
                System.out.println("Connected to SQLite!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
}
