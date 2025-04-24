package controller;

import dao.StudyGoalDAO;
import dao.SubjectDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Subject;

public class SubjectController {
    @FXML private TextField nameField;
    @FXML private TextField notesArea;
    @FXML private ListView<String> subjectList;

    private final SubjectDAO subjectDAO = new SubjectDAO();

    @FXML
    public void initialize() {
        refreshList();
    }

    @FXML
    public void handleAddSubject() {
        String name = nameField.getText().trim();
        String notes = notesArea.getText().trim();

        if (!name.isEmpty()) {
            subjectDAO.addSubject(new Subject(name, notes));
            nameField.clear();
            notesArea.clear();
            refreshList();
        }
    }

    private void refreshList() {
        subjectList.getItems().clear();
        for (Subject s : subjectDAO.getAllSubjects()) {
            subjectList.getItems().add(s.getName());
        }
    }

    @FXML
    public void handleDeleteSubject() {
        String selected = subjectList.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        // Confirm deletion
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you want to delete this subject?");
        alert.setContentText("Subject: " + selected);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Optional: Check for linked goals
                boolean hasGoals = new StudyGoalDAO().getAllGoals().stream()
                        .anyMatch(g -> g.getSubjectName().equals(selected));

                if (hasGoals) {
                    Alert warning = new Alert(Alert.AlertType.WARNING);
                    warning.setTitle("Deletion Blocked");
                    warning.setHeaderText("This subject has goals linked to it.");
                    warning.setContentText("Delete those goals first, or edit them to unlink from this subject.");
                    warning.show();
                    return;
                }

                subjectDAO.deleteSubjectByName(selected);
                refreshList();
            }
        });
    }

}
