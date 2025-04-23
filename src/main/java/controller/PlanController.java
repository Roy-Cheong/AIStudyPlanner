package controller;

import ai.HFService;
import dao.StudyGoalDAO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import model.StudyGoal;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlanController {

    @FXML private ComboBox<String> subjectComboBox;
    @FXML private ComboBox<String> goalComboBox;
    @FXML private ComboBox<String> modelComboBox;
    @FXML private TextArea resultArea;
    @FXML private Label countdownLabel;
    @FXML private Button copyButton;

    private List<StudyGoal> allGoals;

    @FXML
    public void initialize() {
        StudyGoalDAO dao = new StudyGoalDAO();
        allGoals = dao.getAllGoals();

        modelComboBox.getItems().addAll("HuggingFace - Mixtral", "Custom Model");
        modelComboBox.getSelectionModel().selectFirst();

        subjectComboBox.getItems().clear();
        for (StudyGoal goal : allGoals) {
            if (!subjectComboBox.getItems().contains(goal.getSubjectName())) {
                subjectComboBox.getItems().add(goal.getSubjectName());
            }
        }

        subjectComboBox.setOnAction(e -> updateGoalComboBox());

        // Monospace font for better formatting
        resultArea.setFont(Font.font("JetBrains Mono", 13));
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
            countdownLabel.setText("");
            return;
        }

        StudyGoal selectedGoal = allGoals.stream()
                .filter(g -> g.getSubjectName().equals(subject) && g.getTitle().equals(title))
                .findFirst()
                .orElse(null);

        if (selectedGoal == null) {
            resultArea.setText("⚠️ Could not find the selected goal.");
            countdownLabel.setText("");
            return;
        }

        long daysRemaining = ChronoUnit.DAYS.between(LocalDate.now(), selectedGoal.getDeadline());
        String countdownText = daysRemaining >= 0
                ? "⏳ " + daysRemaining + " day(s) left until deadline"
                : "⚠️ Deadline has passed!";
        countdownLabel.setText(countdownText);

        resultArea.setText("⏳ Generating your study plan...");
        new Thread(() -> {
            String prompt = util.PromptBuilder.buildPromptFromGoal(selectedGoal);
            System.out.println("🧠 Prompt Sent to Model:\n" + prompt);
            String response = HFService.generatePlan(prompt);

            int firstDay = response.indexOf("Day 1");
            int secondDay = response.indexOf("Day 1", firstDay + 1);
            String cleanedPlan = secondDay > 0
                    ? response.substring(secondDay).trim()
                    : (firstDay > 0 ? response.substring(firstDay).trim() : response);

            // Format headings and checklist items
            cleanedPlan = cleanedPlan
                    .replaceAll("Day (\\d+) \\((\\d{4}-\\d{2}-\\d{2})\\):", "\n── Day $1 [$2] ──")
                    .replaceAll("\\(subgoal \\d+\\)", "✏️")
                    .replaceAll("(?m)^- ", "- ☐ ");

            String summary = "\n\n📌 Summary:\n- Subject: " + subject + "\n- Goal: " + title + "\n- Deadline: " + selectedGoal.getDeadline();

            String finalResponse = "📘 Your Personalized Study Plan:\n" + cleanedPlan + summary;

            Platform.runLater(() -> {
                resultArea.setText(finalResponse);
                Clipboard clipboard = Clipboard.getSystemClipboard();
                ClipboardContent clipContent = new ClipboardContent();
                clipContent.putString(finalResponse);
                clipboard.setContent(clipContent);

                Tooltip tooltip = new Tooltip("✅ Copied to clipboard!");
                tooltip.setAutoHide(true);
                tooltip.show(copyButton, copyButton.getScene().getWindow().getX() + copyButton.getLayoutX(),
                        copyButton.getScene().getWindow().getY() + copyButton.getLayoutY() + 30);
            });
        }).start();
    }

    @FXML
    public void handleCopy() {
        String content = resultArea.getText();
        if (content != null && !content.isEmpty()) {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent clipContent = new ClipboardContent();
            clipContent.putString(content);
            clipboard.setContent(clipContent);

            showAlert("Copied!", "Plan has been copied to clipboard.");
        }
    }

    @FXML
    public void handlePreviewPrompt() {
        String subject = subjectComboBox.getValue();
        String title = goalComboBox.getValue();

        if (subject == null || title == null) {
            showAlert("Prompt Preview", "Please select both a subject and a goal first.");
            return;
        }

        StudyGoal selectedGoal = allGoals.stream()
                .filter(g -> g.getSubjectName().equals(subject) && g.getTitle().equals(title))
                .findFirst()
                .orElse(null);

        if (selectedGoal == null) {
            showAlert("Prompt Preview", "Could not find the selected goal.");
            return;
        }

        String prompt = util.PromptBuilder.buildPromptFromGoal(selectedGoal);
        showAlert("Prompt Preview", prompt);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
}

