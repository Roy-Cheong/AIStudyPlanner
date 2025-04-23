package model;

public class PlanHistory {
    private int id;
    private int goalId;
    private String content;
    private String createdAt;

    public PlanHistory(int id, int goalId, String content, String createdAt) {
        this.id = id;
        this.goalId = goalId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public PlanHistory(int goalId, String content, String createdAt) {
        this.goalId = goalId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public int getGoalId() {
        return goalId;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
