package controller;

import ai.HFService;
import dao.PlanHistoryDAO;
import dao.StudyGoalDAO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import model.PlanHistory;
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
    @FXML private ListView<String> historyListView;
    @FXML private ProgressBar deadlineProgressBar;

    private List<StudyGoal> allGoals;

    @FXML
    public void initialize() {
        StudyGoalDAO dao = new StudyGoalDAO();
        allGoals = dao.getAllGoals();
        modelComboBox.getItems().clear(); // Always clear first
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

        // 📜 Enable click-to-load on saved plans
        setupHistoryClickListener();
    }

    private void updateGoalComboBox() {
        goalComboBox.getItems().clear();
        String selectedSubject = subjectComboBox.getValue();
        for (StudyGoal goal : allGoals) {
            if (goal.getSubjectName().equals(selectedSubject)) {
                goalComboBox.getItems().add(goal.getTitle());
                goalComboBox.setOnAction(e -> loadHistoryForSelectedGoal());
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
        long totalDuration = ChronoUnit.DAYS.between(selectedGoal.getDeadline().minusDays(13), selectedGoal.getDeadline());

        double progress = totalDuration > 0
                ? 1.0 - ((double) daysRemaining / totalDuration)
                : 1.0;
        Platform.runLater(() -> deadlineProgressBar.setProgress(Math.max(0.05, Math.min(progress, 1.0))));
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

                // ✅ Copy to clipboard
                Clipboard clipboard = Clipboard.getSystemClipboard();
                ClipboardContent clipContent = new ClipboardContent();
                clipContent.putString(finalResponse);
                clipboard.setContent(clipContent);

                // ✅ Save to plan history
                PlanHistoryDAO historyDAO = new PlanHistoryDAO();
                PlanHistory history = new PlanHistory(selectedGoal.getId(), finalResponse, LocalDate.now().toString());
                historyDAO.savePlan(history);
                System.out.println("📝 Plan saved to history.");

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

    private void loadHistoryForSelectedGoal() {
        historyListView.getItems().clear();

        String selectedTitle = goalComboBox.getValue();
        String selectedSubject = subjectComboBox.getValue();
        if (selectedTitle == null || selectedSubject == null) return;

        StudyGoal selectedGoal = allGoals.stream()
                .filter(g -> g.getSubjectName().equals(selectedSubject) && g.getTitle().equals(selectedTitle))
                .findFirst()
                .orElse(null);

        if (selectedGoal != null) {
            PlanHistoryDAO dao = new PlanHistoryDAO();
            List<PlanHistory> historyList = dao.getPlansForGoal(selectedGoal.getId());
            for (PlanHistory plan : historyList) {
                historyListView.getItems().add("📅 " + plan.getCreatedAt() + " — " + plan.getContent().split("\n")[0]);
            }
        }
    }

    private void setupHistoryClickListener() {
        historyListView.setOnMouseClicked(event -> {
            String selectedItem = historyListView.getSelectionModel().getSelectedItem();
            if (selectedItem == null) return;

            String selectedTitle = goalComboBox.getValue();
            String selectedSubject = subjectComboBox.getValue();
            if (selectedTitle == null || selectedSubject == null) return;

            StudyGoal selectedGoal = allGoals.stream()
                    .filter(g -> g.getSubjectName().equals(selectedSubject) && g.getTitle().equals(selectedTitle))
                    .findFirst()
                    .orElse(null);

            if (selectedGoal != null) {
                PlanHistoryDAO dao = new PlanHistoryDAO();
                List<PlanHistory> historyList = dao.getPlansForGoal(selectedGoal.getId());

                // Find the matching plan by preview
                String selectedPreview = selectedItem.replaceFirst("📅 \\d{4}-\\d{2}-\\d{2} — ", "");
                PlanHistory match = historyList.stream()
                        .filter(ph -> ph.getContent().startsWith(selectedPreview))
                        .findFirst()
                        .orElse(null);

                if (match != null) {
                    resultArea.setText(match.getContent());
                }
            }
        });
    }
}

