package model;

import java.time.LocalDate;

public class StudyGoal {
    private int id;
    private String subjectName;
    private String title;
    private LocalDate deadline;
    private String notes;

    public StudyGoal(int id, String subjectName, String title, LocalDate deadline, String notes) {
        this.id = id;
        this.subjectName = subjectName;
        this.title = title;
        this.deadline = deadline;
        this.notes = notes;
    }

    public StudyGoal(String subjectName, String title, LocalDate deadline, String notes) {
        this(-1, subjectName, title, deadline, notes);
    }

    public int getId() { return id; }
    public String getSubjectName() { return subjectName; }
    public String getTitle() { return title; }
    public LocalDate getDeadline() { return deadline; }
    public String getNotes() { return notes; }
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
