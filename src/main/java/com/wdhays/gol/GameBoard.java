package main.java.com.wdhays.gol;

public class GameBoard {

    private Cell[][] grid;
    private int gridSize;

    public GameBoard(int gridSize) {
        this.gridSize = gridSize;
        this.grid = new Cell[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public void setGrid(Cell[][] grid) {
        this.grid = new Cell[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                this.grid[i][j] = new Cell(grid[i][j]);
            }
        }
    }

    public void setGrid(long[][] gridIntArray) {
        this.grid = new Cell[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                this.grid[i][j] = new Cell(gridIntArray[i][j] != 0, gridIntArray[i][j]);
            }
        }
    }

    public void clearGrid() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j].setAlive(false);
                grid[i][j].setAge(0);
            }
        }
    }

    public void printGrid() {
        for (int i = 0; i < gridSize ; i++) {
            for (int j = 0; j < gridSize ; j++) {
                if (grid[i][j].isAlive()) {
                    System.out.print("1");
                } else {
                    System.out.print("0");
                }
            }
            System.out.println();
        }
    }
}
