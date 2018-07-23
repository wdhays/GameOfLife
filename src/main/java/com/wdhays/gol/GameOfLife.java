package main.java.com.wdhays.gol;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.util.Duration;

public class GameOfLife {

    private GameBoard gameBoard;
    private GameBoard gameBoardNext;
    private ObjectProperty<GameSpeed> gameSpeed;
    private Timeline timeLine;
    private ObjectProperty<RuleSet> ruleSet;
    private BooleanProperty gameRunning;
    private LongProperty generation;
    private int gridSize;
    private int cellSize;

    public GameOfLife(int gridSize, int cellSize) {
        //Set up the gameBoard
        this.gridSize = gridSize;
        this.cellSize = cellSize;
        this.gameBoard = new GameBoard(gridSize);
        this.gameBoardNext = new GameBoard(gridSize);
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
            //Generate the next game board state.
            nextGeneration();
            //Update the generation.
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

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {

                int livingNeighborCount = getLivingNeighborCount(row, col);

                //Use the correct RuleSet, defaults to STANDARD
                if (ruleSet.get() == RuleSet.STANDARD) {

                    //If the current cell is alive.
                    if(!getCellState(row, col, true)) {
                        if(livingNeighborCount == 3) {
                            toggleCellState(row, col, false);
                        }
                    } else {
                        if (livingNeighborCount != 2 && livingNeighborCount != 3) {
                            toggleCellState(row, col, false);
                        }
                    }

                } else {
                    //TODO
                    System.out.println("Other");
                }
            }
        }
        //Replace the old game board with the new one.
        gameBoard.setGrid(gameBoardNext.getGrid());
    }

    private int getLivingNeighborCount(int row, int col){

        int livingNeighborCount = 0;
        int colUp, colDown, rowRight, rowLeft;

        //Handle row wrap.
        if (row == 0) {
            rowRight = row + 1;
            rowLeft = gridSize - 1;
        } else if (row == gridSize - 1) {
            rowRight = 0;
            rowLeft = row - 1;
        } else {
            rowRight = row + 1;
            rowLeft = row - 1;
        }

        //Handle column wrap.
        if (col == 0) {
            colUp = col + 1;
            colDown = gridSize - 1;
        } else if (col == gridSize - 1) {
            colUp = 0;
            colDown = col - 1;
        } else {
            colUp = col + 1;
            colDown = col - 1;
        }

        //Count living neighbors.
        if(getCellState(rowRight, colUp, true)) livingNeighborCount++;
        if(getCellState(rowRight, col, true)) livingNeighborCount++;
        if(getCellState(rowRight, colDown, true)) livingNeighborCount++;
        if(getCellState(row, colUp, true)) livingNeighborCount++;
        if(getCellState(row, colDown, true)) livingNeighborCount++;
        if(getCellState(rowLeft, colUp, true)) livingNeighborCount++;
        if(getCellState(rowLeft, col, true)) livingNeighborCount++;
        if(getCellState(rowLeft, colDown, true)) livingNeighborCount++;

        return livingNeighborCount;
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
        gameBoard.clearGrid();
        gameBoardNext.clearGrid();
        generation.set(0);
    }

    public long getGeneration() {
        return generation.get();
    }

    public final LongProperty generationProperty() {
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

    public boolean getCellState(int i, int j, boolean current) {
        if(current) {
            return gameBoard.getGrid()[i][j].isAlive();
        } else {
            return gameBoardNext.getGrid()[i][j].isAlive();
        }
    }

    public void toggleCellState(int i, int j, boolean current) {
        if(current) {
            gameBoard.getGrid()[i][j].toggleAlive();
        } else {
            gameBoardNext.getGrid()[i][j].toggleAlive();
        }
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }
}
