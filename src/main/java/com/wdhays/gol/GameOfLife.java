package main.java.com.wdhays.gol;

public class GameOfLife {

    private GameBoard gameBoard;
    private int gridSize;
    private int cellSize;

    public GameOfLife(int gridSize, int cellSize) {
        this.gridSize = gridSize;
        this.cellSize = cellSize;
        this.gameBoard = new GameBoard(gridSize);
    }

    public int getGridSize() {
        return gridSize;
    }

    public int getCellSize() {
        return cellSize;
    }
}
