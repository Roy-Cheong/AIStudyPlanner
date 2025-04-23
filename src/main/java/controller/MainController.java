package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

import java.io.IOException;

public class MainController {

    @FXML
    private AnchorPane contentPane;
    private boolean isDarkMode = false;

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
    @FXML private ToggleButton themeToggle;

    @FXML
    public void toggleTheme() {
        boolean darkMode = themeToggle.isSelected();
        Scene scene = themeToggle.getScene();
        scene.getStylesheets().clear();
        String themePath = isDarkMode ? "/style/dark.css" : "/style/style.css";
        scene.getStylesheets().add(getClass().getResource(themePath).toExternalForm());

        if (darkMode) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/style/dark.css").toExternalForm());
            themeToggle.setText("☀️");
        } else {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
            themeToggle.setText("🌙");
        }
    }



}
