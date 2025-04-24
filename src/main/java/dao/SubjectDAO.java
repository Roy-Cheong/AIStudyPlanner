package dao;

import model.Subject;
import util.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {
    public SubjectDAO() {
        try (Statement stmt = DBConnector.getConnection().createStatement()) {
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS subjects (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    notes TEXT
                );
            """);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addSubject(Subject subject) {
        String sql = "INSERT INTO subjects(name, notes) VALUES (?, ?)";
        try (PreparedStatement pstmt = DBConnector.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, subject.getName());
            pstmt.setString(2, subject.getNotes());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Subject> getAllSubjects() {
        List<Subject> list = new ArrayList<>();
        String sql = "SELECT * FROM subjects";

        try (Statement stmt = DBConnector.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Subject(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("notes")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void deleteSubjectByName(String name) {
        try (PreparedStatement stmt = DBConnector.getConnection()
                .prepareStatement("DELETE FROM subjects WHERE name = ?")) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
