package main.java.com.wdhays.gol;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GameOfLifeApplication extends Application
{
    private GameOfLife gameOfLife;

    @Override
    public void init(){
        System.out.println("In App Init!");
        gameOfLife = new GameOfLife(60, 10);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Create an instance of the pane that will be the root.
        BorderPane mainPane = new BorderPane();
        mainPane.setPadding(new Insets(25, 25, 25, 25));

        // Set up the FXML loader for the GameBoard FXML
        FXMLLoader gridLoader = new FXMLLoader(getClass().getResource("GameBoard.fxml"));
        // Create and attach an instance of the ControllerGameBoard to the loader.
        ControllerGameBoard gridController = new ControllerGameBoard(gameOfLife);
        gridLoader.setController(gridController);
        Parent gridLayout = gridLoader.load();
        // Add the gridLayout to the main pane.
        mainPane.setCenter(gridLayout);

        // Set up the FXML loader for the GameBoard FXML
        FXMLLoader controlLoader = new FXMLLoader(getClass().getResource("ControlPanel.fxml"));
        // Create and attach an instance of the ControllerGameBoard to the loader.
        ControllerControlPanel controlPanelController = new ControllerControlPanel(gameOfLife);
        controlLoader.setController(controlPanelController);
        Parent controlPanelLayout = controlLoader.load();
        // Add the gridLayout to the main pane.
        mainPane.setRight(controlPanelLayout);

        //Create a scene and add the main pane.
        Scene mainScene =  new Scene(mainPane, 855, 675);
        // Set up the stage.
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Game of Life");
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
