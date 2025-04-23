package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

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
        loadUI("plan_screen");
    }

    public void loadUI(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + fxml + ".fxml"));
            Region pane = loader.load();
            contentPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
