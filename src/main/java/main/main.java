package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by wojciech on 06.06.17.
 */
public class main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Wyszukiwarka pojazdów");
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
            primaryStage.setScene(new Scene(root, 300, 250));
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("start" + e);
            e.printStackTrace();
        }
    }
}
