package main.java.com.wdhays.gol;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.util.Duration;

import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class GameOfLife {

    private GameBoard gameBoard;
    private GameBoard gameBoardNext;
    private Timeline timeLine;
    private ObjectProperty<GameSpeed> gameSpeed;
    private ObjectProperty<RuleSet> ruleSet;
    private BooleanProperty gameRunning;
    private BooleanProperty needsRedraw;
    private LongProperty generation;
    private boolean useCellAge;
    private int gridSize;
    private int cellSize;

    public GameOfLife(int gridSize, int cellSize) {
        //Set up the gameBoard
        this.gridSize = gridSize;
        this.cellSize = cellSize;
        this.useCellAge = false;
        this.gameBoard = new GameBoard(gridSize);
        this.gameBoardNext = new GameBoard(gridSize);
        //Set up the properties.
        this.gameRunning = new SimpleBooleanProperty();
        this.gameRunning.set(false);
        this.needsRedraw = new SimpleBooleanProperty();
        this.needsRedraw.set(false);
        this.ruleSet = new SimpleObjectProperty<>();
        this.ruleSet.set(RuleSet.STANDARD);
        this.gameSpeed = new SimpleObjectProperty<>();
        this.gameSpeed.set(GameSpeed.VERYSLOW);
        this.generation = new SimpleLongProperty();
        this.generation.set(0);
        //Call method to initialize a TimeLine.
        initializeTimeline(GameSpeed.VERYSLOW);
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

        int[] aliveRules = ruleSet.get().getAliveRules();
        int[] deadRules = ruleSet.get().getDeadRules();

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {

                //Get a count of the living neighbors for this cell.
                int livingNeighborCount = getLivingNeighborCount(row, col);

                //If the cell is currently alive.
                if(getCellState(row, col, true)) {
                    //If the livingNeighborCount IS NOT in the aliveRules array kill it in next generation.
                    if(Arrays.binarySearch(aliveRules, livingNeighborCount) < 0) {
                        toggleCellState(row, col, false);
                    } else {
                        //The cell lived, increase it's age.
                        gameBoardNext.getGrid()[row][col].incrementAge();
                    }
                //If the cell is currently dead.
                } else {
                    //If the livingNeighborCount IS in the deadRules array resurrect it in next generation.
                    if (Arrays.binarySearch(deadRules, livingNeighborCount) >= 0) {
                        toggleCellState(row, col, false);
                    }
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

    public void generateRandomGrid(double chanceOfLife) {
        long[][] grid = new long[gridSize][gridSize];
        Random random = new Random();
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = random.nextDouble() <= chanceOfLife ? 1 : 0;
            }
        }
        //Set the game boards to the loaded state.
        gameBoard.setGrid(grid);
        gameBoardNext.setGrid(grid);
        setGeneration(0);
        //Trigger a redraw.
        setNeedsRedraw(true);
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
        setGeneration(0);
    }

    public BooleanProperty needsRedrawProperty() {
        return needsRedraw;
    }

    public void setNeedsRedraw(boolean needsRedraw) {
        this.needsRedraw.set(needsRedraw);
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

    public boolean getUseCellAge() {
        return useCellAge;
    }

    public void setUseCellAge(boolean useCellAge) {
        this.useCellAge = useCellAge;
    }

    public GameSpeed getGameSpeed() {
        return gameSpeed.get();
    }

    public ObjectProperty<GameSpeed> gameSpeedProperty() {
        return gameSpeed;
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

    public int getCellSize() {
        return cellSize;
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

    public boolean saveGameBoardToFile(File saveFile) throws IOException {
        //Does the file even exist?
        if(saveFile.exists()) {
            if (saveFile.canWrite()) {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(saveFile));
                bufferedWriter.write(buildGridCSVString());
                bufferedWriter.close();
                return true;
            } else {
                System.out.println("The file was not writable.");
                return false;
            }
        } else {
            //The files does not exist, it needs to be created.
            if (saveFile.createNewFile()) {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(saveFile));
                bufferedWriter.write(buildGridCSVString());
                bufferedWriter.close();
                return true;
            } else {
                System.out.println("The file could not be created.");
                return false;
            }
        }
    }

    public boolean loadGameBoardFromFile(File selectedFile) throws IOException, NumberFormatException {

        if(selectedFile.canRead()) {

            boolean badFormatFlag = false;

            //Load the file data into a 2d array of booleans.
            System.out.println("We can load!");
            long[][] newGameBoard = new long[gridSize][gridSize];
            BufferedReader bufferedReader = new BufferedReader(new FileReader(selectedFile));
            String singleLine;
            int rowIndex = 0;
            while((singleLine = bufferedReader.readLine()) != null)
            {
                String[] lineArray = singleLine.split(",");
                int colIndex = 0;
                for(String value : lineArray)
                {
                    newGameBoard[rowIndex][colIndex] = Long.parseLong(value);
                    colIndex++;
                }
                if(colIndex != gridSize) {
                    badFormatFlag = true;
                    break;
                }
                rowIndex++;
            }
            bufferedReader.close();

            if (badFormatFlag || rowIndex != gridSize) {
                System.out.println("The file was not the correct format!");
                return false;
            } else {
                //Set the game boards to the loaded state.
                gameBoard.setGrid(newGameBoard);
                gameBoardNext.setGrid(newGameBoard);
                return true;
            }
        } else {
            System.out.println("The file was not readable.");
            return false;
        }
    }

    public String buildGridCSVString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < gridSize; i++) {
            for(int j = 0; j < gridSize; j++) {
                stringBuilder.append(Long.toString(gameBoard.getGrid()[i][j].getAge()));
                if(j < gridSize - 1) {
                    stringBuilder.append(",");
                }
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
