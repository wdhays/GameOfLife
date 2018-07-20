package main.java.com.wdhays.gol;

public class GameOfLife {

    private GameBoard gameBoard;
    private int gridSize;
    private int cellSize;
    private RuleSet ruleSet;

    public GameOfLife(int gridSize, int cellSize) {
        this.gridSize = gridSize;
        this.cellSize = cellSize;
        this.gameBoard = new GameBoard(gridSize);
        this.ruleSet = RuleSet.STANDARD;
    }

    public int getGridSize() {
        return gridSize;
    }

    public int getCellSize() {
        return cellSize;
    }
}
