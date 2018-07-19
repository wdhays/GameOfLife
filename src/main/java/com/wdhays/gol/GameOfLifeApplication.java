package main.java.com.wdhays.gol;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GameOfLifeApplication extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override public void start(Stage primaryStage) throws Exception {

        BorderPane mainPane = new BorderPane();

        // Add the grid pane to the scene.
        Parent gridLayout = FXMLLoader.load(getClass().getResource("GameBoard.fxml"));
        mainPane.setCenter(gridLayout);

        //Add the control panel to the scene.
        Parent controlPanelLayout = FXMLLoader.load(getClass().getResource("ControlPanel.fxml"));
        mainPane.setRight(controlPanelLayout);

        //Create a scene and add the main pane.
        Scene mainScene =  new Scene(mainPane, 800, 600);
        // Set up the stage.
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Game of Life");
        primaryStage.show();
    }
}
