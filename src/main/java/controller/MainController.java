package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
        loadUI("plan_screen");
    }

    private void loadUI(String fxml) {
        try {
            Node node = FXMLLoader.load(getClass().getResource("/fxml/" + fxml + ".fxml"));

            AnchorPane.setTopAnchor(node, 0.0);
            AnchorPane.setBottomAnchor(node, 0.0);
            AnchorPane.setLeftAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);

            contentPane.getChildren().setAll(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
