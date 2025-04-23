package controller;

import ai.HFService;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import util.PromptBuilder;

public class PlanController {

    @FXML private TextArea outputArea;

    @FXML
    public void handleGenerate() {
        outputArea.setText("⏳ Generating your study plan...");
        new Thread(() -> {
            String prompt = PromptBuilder.buildPromptFromGoals();
            String result = HFService.generatePlan(prompt);

            javafx.application.Platform.runLater(() -> outputArea.setText(result));
        }).start();
    }
}
