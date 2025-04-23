package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainController {

    @FXML
    private AnchorPane contentPane;

    @FXML
    public void initialize() {
        loadUI("dashboard");
    }

    public void handleDashboard() {
        loadUI("dashboard");
    }

    public void handleSubjects() {
        loadUI("subject_screen");
    }

    public void handleGoals() {
        loadUI("goal_screen");
    }

    public void handlePlanner() {
        loadUI("planner_screen");
    }

    private void loadUI(String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/" + fxmlFile + ".fxml"));
            contentPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
