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
    @FXML private Button addGoalButton;
    private final StudyGoalDAO goalDAO = new StudyGoalDAO();
    private final SubjectDAO subjectDAO = new SubjectDAO();
    private StudyGoal selectedGoal = null;


    @FXML
    public void initialize() {
        for (Subject s : subjectDAO.getAllSubjects()) {
            subjectBox.getItems().add(s.getName());
        }
        refreshGoals();

        goalList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                String[] parts = newVal.split(" \\(");
                String title = parts[0];
                selectedGoal = goalDAO.getAllGoals().stream()
                        .filter(g -> g.getTitle().equals(title))
                        .findFirst()
                        .orElse(null);
                if (selectedGoal != null) {
                    goalField.setText(selectedGoal.getTitle());
                    subjectBox.setValue(selectedGoal.getSubjectName());
                    deadlinePicker.setValue(selectedGoal.getDeadline());
                    notesArea.setText(selectedGoal.getNotes());

                    // 🔁 Change button to update mode
                    addGoalButton.setText("✏️ Update Goal");
                }
            } else {
                clearForm(); // resets and sets text back
            }
        });
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
            clearForm();
            refreshGoals();
        }
    }

    @FXML
    public void handleUpdateGoal() {
        if (selectedGoal != null) {
            selectedGoal.setSubjectName(subjectBox.getValue());
            selectedGoal.setTitle(goalField.getText());
            selectedGoal.setDeadline(deadlinePicker.getValue());
            selectedGoal.setNotes(notesArea.getText());
            goalDAO.updateGoal(selectedGoal);
            clearForm();
            refreshGoals();
        }
    }

    @FXML
    public void handleDeleteGoal() {
        if (selectedGoal != null) {
            goalDAO.deleteGoal(selectedGoal.getId());
            clearForm();
            refreshGoals();
        }
    }

    private void refreshGoals() {
        goalList.getItems().clear();
        for (StudyGoal g : goalDAO.getAllGoals()) {
            goalList.getItems().add(g.getTitle() + " (" + g.getSubjectName() + ")");
        }
    }

    private void clearForm() {
        goalField.clear();
        notesArea.clear();
        deadlinePicker.setValue(null);
        subjectBox.getSelectionModel().clearSelection();
        selectedGoal = null;
        addGoalButton.setText("➕ Add Goal");
    }
}

