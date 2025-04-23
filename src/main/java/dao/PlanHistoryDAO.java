package dao;

import model.PlanHistory;
import util.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanHistoryDAO {

    public PlanHistoryDAO() {
        try (Statement stmt = DBConnector.getConnection().createStatement()) {
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS plan_history (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    goal_id INTEGER NOT NULL,
                    content TEXT NOT NULL,
                    created_at TEXT NOT NULL,
                    FOREIGN KEY(goal_id) REFERENCES study_goals(id)
                );
            """);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void savePlan(PlanHistory plan) {
        String sql = "INSERT INTO plan_history(goal_id, content, created_at) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = DBConnector.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, plan.getGoalId());
            pstmt.setString(2, plan.getContent());
            pstmt.setString(3, plan.getCreatedAt().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PlanHistory> getPlansForGoal(int goalId) {
        List<PlanHistory> plans = new ArrayList<>();
        String sql = "SELECT * FROM plan_history WHERE goal_id = ? ORDER BY created_at DESC";
        try (PreparedStatement pstmt = DBConnector.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, goalId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                plans.add(new PlanHistory(
                        rs.getInt("id"),
                        rs.getInt("goal_id"),
                        rs.getString("content"),
                        rs.getString("created_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plans;
    }
}
