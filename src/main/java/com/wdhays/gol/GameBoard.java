package main.java.com.wdhays.gol;

public class GameBoard {

    private Cell[][] grid;
    private int gridSize;

    public GameBoard(int gridSize) {
        this.gridSize = gridSize;
        this.grid = new Cell[gridSize][gridSize];
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public void setGrid(Cell[][] grid) {
        this.grid = grid;
    }

    public void clearGrid() {
        this.grid = new Cell[gridSize][gridSize];
    }
}
