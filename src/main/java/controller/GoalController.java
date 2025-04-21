package controller;

import dao.StudyGoalDAO;
import dao.SubjectDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.StudyGoal;
import model.Subject;

import java.time.LocalDate;

public class GoalController {
    @FXML private ComboBox<String> subjectBox;
    @FXML private TextField goalField;
    @FXML private DatePicker deadlinePicker;
    @FXML private TextArea notesArea;
    @FXML private ListView<String> goalList;

    private final StudyGoalDAO goalDAO = new StudyGoalDAO();
    private final SubjectDAO subjectDAO = new SubjectDAO();

    @FXML
    public void initialize() {
        for (Subject s : subjectDAO.getAllSubjects()) {
            subjectBox.getItems().add(s.getName());
        }
        refreshGoals();
    }

    @FXML
    public void handleAddGoal() {
        String subject = subjectBox.getValue();
        String goal = goalField.getText();
        LocalDate deadline = deadlinePicker.getValue();
        String notes = notesArea.getText();

        if (subject != null && !goal.isEmpty() && deadline != null) {
            StudyGoal g = new StudyGoal(subject, goal, deadline, notes);
            goalDAO.addGoal(g);
            goalField.clear();
            notesArea.clear();
            deadlinePicker.setValue(null);
            refreshGoals();
        }
    }

    private void refreshGoals() {
        goalList.getItems().clear();
        for (StudyGoal g : goalDAO.getAllGoals()) {
            goalList.getItems().add(g.getTitle() + " (" + g.getSubjectName() + ")");
        }
    }
}
