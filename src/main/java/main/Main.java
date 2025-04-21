package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("AI Study Planner is alive!");
        primaryStage.setScene(new Scene(label, 400, 200));
        primaryStage.setTitle("AI Study Planner");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
