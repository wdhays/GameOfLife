package main.java.com.wdhays.gol;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerGameBoard implements Initializable {

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
        gameOfLife.generationProperty().addListener(e -> {
                generationLabelValue.setText(Long.toString(gameOfLife.getGeneration()));
                updateGridRectangles();
        });
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
                initializeGridRectangles(i,j);
            }
        }
    }

    private void initializeGridRectangles(int row, int col) {

        int cellSize = gameOfLife.getCellSize();

        // All cells will initially be dead, build a Rectangle for each.
        Rectangle cellRect = new Rectangle();
        cellRect.setHeight(cellSize);
        cellRect.setWidth(cellSize);
        cellRect.setFill(Color.WHITE);
        cellRect.setStroke(Color.WHITE);
        cellRect.setStrokeType(StrokeType.INSIDE);
        cellRect.setStrokeWidth(0.5);
        cellRect.setSmooth(false);

        // Add mouse on click to toggle cells.
        cellRect.setOnMouseClicked(e -> {
            if(gameOfLife.getCellState(row, col, true)) {
                System.out.println("The cell is alive! Kill it.");
                gameOfLife.toggleCellState(row, col, true);
                gameOfLife.toggleCellState(row, col, false);
                cellRect.setFill(Color.WHITE);
            } else {
                System.out.println("The cell is dead! Resurrect it.");
                gameOfLife.toggleCellState(row, col, true);
                gameOfLife.toggleCellState(row, col, false);
                cellRect.setFill(Color.BLACK);

            }
        });

        // Add the Rectangle to the grid at the col/row.
        gridPane.add(cellRect, col, row);
    }

    private void updateGridRectangles() {
        //Update each node in the grid.
        for (Node child : gridPane.getChildren()) {
            //Get the row and col of the current grid node.
            int col = gridPane.getColumnIndex(child);
            int row = gridPane.getRowIndex(child);
            Rectangle cellRect = (Rectangle)child;

            if(gameOfLife.getCellState(row, col, true)) {
                cellRect.setFill(Color.BLACK);
            } else {
                cellRect.setFill(Color.WHITE);
            }
        }
    }
}
