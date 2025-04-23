package controller;

import ai.HFService;
import dao.StudyGoalDAO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import model.StudyGoal;

import java.util.List;

public class PlanController {
    @FXML private ComboBox<String> subjectComboBox;
    @FXML private ComboBox<String> goalComboBox;
    @FXML private TextArea resultArea;

    private List<StudyGoal> allGoals;

    @FXML
    public void initialize() {
        StudyGoalDAO dao = new StudyGoalDAO();
        allGoals = dao.getAllGoals();

        subjectComboBox.getItems().clear();
        for (StudyGoal goal : allGoals) {
            if (!subjectComboBox.getItems().contains(goal.getSubjectName())) {
                subjectComboBox.getItems().add(goal.getSubjectName());
            }
        }

        subjectComboBox.setOnAction(e -> updateGoalComboBox());
    }

    private void updateGoalComboBox() {
        goalComboBox.getItems().clear();
        String selectedSubject = subjectComboBox.getValue();
        for (StudyGoal goal : allGoals) {
            if (goal.getSubjectName().equals(selectedSubject)) {
                goalComboBox.getItems().add(goal.getTitle());
            }
        }
    }

    @FXML
    public void handleGenerate() {
        String subject = subjectComboBox.getValue();
        String title = goalComboBox.getValue();
        if (subject == null || title == null) {
            resultArea.setText("⚠️ Please select both subject and goal.");
            return;
        }

        StudyGoal selectedGoal = allGoals.stream()
                .filter(g -> g.getSubjectName().equals(subject) && g.getTitle().equals(title))
                .findFirst()
                .orElse(null);

        if (selectedGoal == null) {
            resultArea.setText("⚠️ Could not find the selected goal.");
            return;
        }

        resultArea.setText("⏳ Generating your study plan...");
        new Thread(() -> {
            String prompt = util.PromptBuilder.buildPromptFromGoal(selectedGoal);
            System.out.println("🧠 Prompt Sent to Model:\n" + prompt); // Debug print
            String response = HFService.generatePlan(prompt);
            Platform.runLater(() -> resultArea.setText("📘 Your Personalized Study Plan:\n\n" + response));
        }).start();
    }
}
