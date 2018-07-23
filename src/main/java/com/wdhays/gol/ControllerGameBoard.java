package main.java.com.wdhays.gol;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
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
    private int startDragX;
    private int startDragY;
    private boolean previousState;

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

        // Add mouse on click to toggle cells.
        cellRect.setOnMouseClicked(this::mouseClickedEvent);
        //
        cellRect.setOnDragDetected(this::mouseDragStartEvent);
        //
        cellRect.setOnMouseDragOver(this::mouseDragOverEvent);
        //
        cellRect.setOnMouseDragReleased(this::mouseDragEndEvent);

        // Add the Rectangle to the grid at the col/row.
        gridPane.add(cellRect, col, row);
    }

    //
    private void mouseClickedEvent(MouseEvent e) {
        //Get the source of the event and info.
        Rectangle eventSource = (Rectangle) e.getSource();
        int eventSourceCol = GridPane.getColumnIndex(eventSource);
        int eventSourceRow = GridPane.getRowIndex(eventSource);

        //If clicked toggle the eventSource's state.
        if(gameOfLife.getCellState(eventSourceRow, eventSourceCol, true)) {
            System.out.println("The cell is alive! Kill it.");
            gameOfLife.toggleCellState(eventSourceRow, eventSourceCol, true);
            gameOfLife.toggleCellState(eventSourceRow, eventSourceCol, false);
            eventSource.setFill(Color.WHITE);
        } else {
            System.out.println("The cell is dead! Resurrect it.");
            gameOfLife.toggleCellState(eventSourceRow, eventSourceCol, true);
            gameOfLife.toggleCellState(eventSourceRow, eventSourceCol, false);
            eventSource.setFill(Color.BLACK);
        }
    }

    //
    private void mouseDragOverEvent(MouseDragEvent e) {
        //Get the source of the event and info.
        Rectangle eventSource = (Rectangle) e.getSource();
        int eventSourceCol = GridPane.getColumnIndex(eventSource);
        int eventSourceRow = GridPane.getRowIndex(eventSource);

        //Get the min/max row/col values of the box being drawn.
        int maxX = Math.max(startDragX, eventSourceCol);
        int minX = Math.min(startDragX, eventSourceCol);
        int maxY = Math.max(startDragY, eventSourceRow);
        int minY = Math.min(startDragY, eventSourceRow);

        //Loop over all nodes in the grid.
        for (Node child : gridPane.getChildren()) {

            //Get the row and col of the current grid node.
            int dragCol = GridPane.getColumnIndex(child);
            int dragRow = GridPane.getRowIndex(child);
            Rectangle cellRectTemp = (Rectangle)child;

            //If the node is in the bow being draw draw it in a different color else draw it normal.
            if(dragCol <= maxX && dragCol >= minX && dragRow <= maxY && dragRow >= minY) {
                if(gameOfLife.getCellState(dragRow, dragCol, true)) {
                    cellRectTemp.setFill(Color.DARKGRAY);
                } else {
                    cellRectTemp.setFill(Color.LIGHTGRAY);
                }
            } else {
                if(gameOfLife.getCellState(dragRow, dragCol, true)) {
                    cellRectTemp.setFill(Color.BLACK);
                } else {
                    cellRectTemp.setFill(Color.WHITE);
                }
            }
        }
    }

    //
    private void mouseDragEndEvent(MouseDragEvent e) {

        //Get the source of the event and info.
        Rectangle eventSource = (Rectangle) e.getSource();
        int eventSourceCol = GridPane.getColumnIndex(eventSource);
        int eventSourceRow = GridPane.getRowIndex(eventSource);

        //Get the min/max row/col values of the box being drawn.
        int maxX = Math.max(startDragX, eventSourceCol);
        int minX = Math.min(startDragX, eventSourceCol);
        int maxY = Math.max(startDragY, eventSourceRow);
        int minY = Math.min(startDragY, eventSourceRow);

        //Loop over all nodes in the grid, if the node is within the drawn box toggle it's state.
        for (Node child : gridPane.getChildren()) {

            //Get the row/col and reference of the child grid node.
            int endCol = GridPane.getColumnIndex(child);
            int endRow = GridPane.getRowIndex(child);
            Rectangle cellRectTemp = (Rectangle)child;

            //Check if the node is in the bounds to the drawn box.
            if(endCol <= maxX && endCol >= minX && endRow <= maxY && endRow >= minY) {

                if(gameOfLife.getCellState(endRow, endCol, true)) {
                    cellRectTemp.setFill(Color.WHITE);
                    gameOfLife.toggleCellState(endRow, endCol, true);
                    gameOfLife.toggleCellState(endRow, endCol, false);
                } else {
                    cellRectTemp.setFill(Color.BLACK);
                    gameOfLife.toggleCellState(endRow, endCol, true);
                    gameOfLife.toggleCellState(endRow, endCol, false);
                }
            }
        }

        //Reset to the game state to when the drag started.
        if (previousState) {
            gameOfLife.play();
        }
    }

    //
    private void mouseDragStartEvent(MouseEvent e) {
        //Get the source of the event.
        Rectangle eventSource = (Rectangle) e.getSource();
        //Start the drag event.
        eventSource.startFullDrag();
        //Record some state information when the drag started.
        startDragX = GridPane.getColumnIndex(eventSource);
        startDragY = GridPane.getRowIndex(eventSource);
        previousState = gameOfLife.isGameRunning();
        //Pause the game while the drag is happening.
        gameOfLife.pause();
    }

    //
    private void updateGridRectangles() {
        //Update each node in the grid.
        for (Node child : gridPane.getChildren()) {
            //Get the row and col of the current grid node.
            int col = GridPane.getColumnIndex(child);
            int row = GridPane.getRowIndex(child);
            Rectangle cellRect = (Rectangle)child;

            if(gameOfLife.getCellState(row, col, true)) {
                cellRect.setFill(Color.BLACK);
            } else {
                cellRect.setFill(Color.WHITE);
            }
        }
    }
}
