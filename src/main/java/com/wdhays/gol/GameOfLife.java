package main.java.com.wdhays.gol;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameOfLife {

    private GameBoard gameBoard;
    private GameSpeed gameSpeed;
    private Timeline timeLine;
    private RuleSet ruleSet;
    private boolean gameRunning;
    private int generation;
    private int gridSize;
    private int cellSize;

    public GameOfLife(int gridSize, int cellSize) {
        this.gridSize = gridSize;
        this.cellSize = cellSize;
        this.gameRunning = false;
        this.gameBoard = new GameBoard(gridSize);
        this.ruleSet = RuleSet.STANDARD;
        this.gameSpeed = GameSpeed.SLOW;
        this.generation = 0;
        initializeTimeline(gameSpeed);
    }

    public void initializeTimeline(GameSpeed gameSpeed) {
        if(timeLine != null) {
            timeLine.stop();
        }
        KeyFrame keyFrame = new KeyFrame(Duration.millis(gameSpeed.toValue()), e -> {
            System.out.println("Frame!");
            nextGeneration();
            generation++;
        });
        timeLine = new Timeline(keyFrame);
        timeLine.setCycleCount(Timeline.INDEFINITE);
        if(gameRunning) {
            timeLine.play();
        }
    }

    private void nextGeneration() {
        //TODO
    }

    public void play() {
        gameRunning = true;
        timeLine.play();
    }

    public void pause() {
        gameRunning = false;
        timeLine.pause();
    }

    public void clear() {
        gameRunning = false;
        timeLine.stop();
        gameBoard.clearGrid();
    }

    public int getGridSize() {
        return gridSize;
    }

    public int getCellSize() {
        return cellSize;
    }
}
