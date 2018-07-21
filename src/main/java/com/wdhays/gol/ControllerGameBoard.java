package main.java.com.wdhays.gol;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerGameBoard implements Initializable {

    private Image cellImageAlive= new Image(
            getClass().getResource("./images/cell-alive.png").toString(), true
    );
    private Image cellImageDead= new Image(
            getClass().getResource("./images/cell-dead.png").toString(), true
    );
    private GameOfLife gameOfLife;

    @FXML
    private GridPane gridPane;
    @FXML
    private Label gameSpeedLabelValue;
    @FXML
    private Label gameStateLabelValue;
    @FXML
    private Label generationLabelValue;

    public ControllerGameBoard(GameOfLife gameOfLife) {
        this.gameOfLife = gameOfLife;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Set up the grid.
        initializeGridPane();
        //Set up generation value label listener.
        gameOfLife.generationProperty().addListener(e ->
                generationLabelValue.setText(Long.toString(gameOfLife.getGeneration())));
        //Set up game state value label listener.
        gameOfLife.gameRunningProperty().addListener(e -> {
                    String newLabelString;
                    if(gameOfLife.isGameRunning()){
                        newLabelString = "Running";
                    } else {
                        newLabelString = "Paused";
                    }
                    gameStateLabelValue.setText(newLabelString);
            });
        //Set up game speed value label listener.
        gameOfLife.gameSpeedProperty().addListener(e ->
                gameSpeedLabelValue.setText(gameOfLife.getGameSpeed().toString()));
    }

    private void initializeGridPane(){

        int gridSize = gameOfLife.getGridSize();
        int cellSize = gameOfLife.getCellSize();

        // Set up the rows and columns.
        for (int i = 0; i < gridSize; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(cellSize));
            gridPane.getRowConstraints().add(new RowConstraints(cellSize));
        }

        //Add an image to each cell with on OnClick mouse event.
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                addImageCellsToGrid(i,j);
            }
        }
    }

    private void addImageCellsToGrid(int i, int j) {

        int cellSize = gameOfLife.getCellSize();

        // All cells will initially be dead.
        // Set up an ImageView for each cell.
        ImageView cellImageView = new ImageView();
        cellImageView.setFitWidth(cellSize);
        cellImageView.setFitHeight(cellSize);
        cellImageView.setImage(cellImageDead);

        // Add mouse on click to toggle cells.
        cellImageView.setOnMouseClicked(e -> {
            System.out.printf("Mouse clicked in cell [%d, %d]%n", i, j);
            if(cellImageView.getImage() == cellImageAlive) {
                System.out.println("The cell is alive! Kill it.");
                cellImageView.setImage(cellImageDead);
            } else {
                System.out.println("The cell is dead! Resurrect it.");
                cellImageView.setImage(cellImageAlive);
            }
        });

        // Add the ImageView to the grid at the col/row.
        gridPane.add(cellImageView, i, j);
    }
}
