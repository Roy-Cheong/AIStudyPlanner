package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        Scene scene = new Scene(root);

        // ✅ Apply global stylesheet
        scene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());

        primaryStage.setTitle("AI Study Planner");
        primaryStage.setScene(scene);
        primaryStage.setWidth(700);
        primaryStage.setHeight(800);
        primaryStage.setMinWidth(650);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
