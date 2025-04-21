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
}
