package main.java.com.wdhays.gol;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameOfLifeApplication extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override public void start(Stage primaryStage) throws Exception {

        // Add the main pane to the scene.
        Parent mainLayout = FXMLLoader.load(getClass().getResource("GameOfLife.fxml"));
        Scene mainScene =  new Scene(mainLayout, 800, 600);

        // Set up the stage.
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Game of Life");
        primaryStage.show();
    }
}
