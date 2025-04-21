package model;

public class Subject {
    private int id;
    private String name;
    private String notes;

    public Subject(int id, String name, String notes) {
        this.id = id;
        this.name = name;
        this.notes = notes;
    }

    public Subject(String name, String notes) {
        this(-1, name, notes);
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getNotes() { return notes; }

    public void setName(String name) { this.name = name; }
    public void setNotes(String notes) { this.notes = notes; }
}
