package dao;

import model.StudyGoal;
import util.DBConnector;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudyGoalDAO {
    public StudyGoalDAO() {
        try (Statement stmt = DBConnector.getConnection().createStatement()) {
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS study_goals (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    subject_name TEXT NOT NULL,
                    title TEXT NOT NULL,
                    deadline TEXT NOT NULL,
                    notes TEXT
                );
            """);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addGoal(StudyGoal goal) {
        String sql = "INSERT INTO study_goals(subject_name, title, deadline, notes) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = DBConnector.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, goal.getSubjectName());
            pstmt.setString(2, goal.getTitle());
            pstmt.setString(3, goal.getDeadline().toString());
            pstmt.setString(4, goal.getNotes());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<StudyGoal> getAllGoals() {
        List<StudyGoal> list = new ArrayList<>();
        String sql = "SELECT * FROM study_goals";
        try (Statement stmt = DBConnector.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new StudyGoal(
                        rs.getInt("id"),
                        rs.getString("subject_name"),
                        rs.getString("title"),
                        LocalDate.parse(rs.getString("deadline")),
                        rs.getString("notes")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
