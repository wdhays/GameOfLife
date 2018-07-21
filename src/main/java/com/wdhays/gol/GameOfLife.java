package main.java.com.wdhays.gol;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.util.Duration;

public class GameOfLife {

    private GameBoard gameBoard;
    private ObjectProperty<GameSpeed> gameSpeed;
    private Timeline timeLine;
    private ObjectProperty<RuleSet> ruleSet;  //TODO make a property
    private BooleanProperty gameRunning;
    private LongProperty generation;
    private int gridSize;
    private int cellSize;

    public GameOfLife(int gridSize, int cellSize) {
        //Set up the gameBoard
        this.gridSize = gridSize;
        this.cellSize = cellSize;
        this.gameBoard = new GameBoard(gridSize);
        //Set up the properties.
        this.gameRunning = new SimpleBooleanProperty();
        this.gameRunning.set(false);
        this.ruleSet = new SimpleObjectProperty<>();
        this.ruleSet.set(RuleSet.STANDARD);
        this.gameSpeed = new SimpleObjectProperty<>();
        this.gameSpeed.set(GameSpeed.SLOW);
        this.generation = new SimpleLongProperty();
        this.generation.set(0);
        //Call method to initialize a TimeLine.
        initializeTimeline(GameSpeed.SLOW);
    }

    public void initializeTimeline(GameSpeed gameSpeed) {

        //Set the game speed to the method parameter.
        this.gameSpeed.set(gameSpeed);
        //Stop any old timeline if it is still running.
        if(timeLine != null) {
            timeLine.stop();
        }
        //Set up a new KeyFrame with he desired game speed interval.
        KeyFrame keyFrame = new KeyFrame(Duration.millis(gameSpeed.toValue()), e -> {
            //This is the stuff that will be done each interval.
            System.out.println("Frame!");
            nextGeneration();
            generation.set(generation.get() + 1);
        });
        //Attach the keyframe to the Timeline.
        timeLine = new Timeline(keyFrame);
        timeLine.setCycleCount(Timeline.INDEFINITE);
        //If the game was running before make it run again.
        if(gameRunning.get()) {
            timeLine.play();
        }
    }

    private void nextGeneration() {
        //TODO
    }

    public void play() {
        gameRunning.set(true);
        timeLine.play();
    }

    public void pause() {
        gameRunning.set(false);
        timeLine.pause();
    }

    public void clear() {
        gameRunning.set(false);
        timeLine.stop();
        generation.set(0);
        gameBoard.clearGrid();
    }

    public long getGeneration() {
        return generation.get();
    }

    public LongProperty generationProperty() {
        return generation;
    }

    public void setGeneration(long generation) {
        this.generation.set(generation);
    }

    public boolean isGameRunning() {
        return gameRunning.get();
    }

    public BooleanProperty gameRunningProperty() {
        return gameRunning;
    }

    public void setGameRunning(boolean gameRunning) {
        this.gameRunning.set(gameRunning);
    }

    public GameSpeed getGameSpeed() {
        return gameSpeed.get();
    }

    public ObjectProperty<GameSpeed> gameSpeedProperty() {
        return gameSpeed;
    }

    public void setGameSpeed(GameSpeed gameSpeed) {
        this.gameSpeed.set(gameSpeed);
    }

    public RuleSet getRuleSet() {
        return ruleSet.get();
    }

    public ObjectProperty<RuleSet> ruleSetProperty() {
        return ruleSet;
    }

    public void setRuleSet(RuleSet ruleSet) {
        this.ruleSet.set(ruleSet);
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public int getCellSize() {
        return cellSize;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public boolean getCellState(int i, int j) {
        return gameBoard.getGrid()[i][j].isAlive();
    }

    public void toggleCellState(int i, int j) {
        gameBoard.getGrid()[i][j].toggleAlive();
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }
}
