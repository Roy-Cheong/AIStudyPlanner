package controller;

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
}
