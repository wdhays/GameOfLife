package main.java.com.wdhays.gol;

public class GameBoard {

    private Cell[][] grid;

    public GameBoard(int size) {
        this.grid = new Cell[size][size];
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public void setGrid(Cell[][] grid) {
        this.grid = grid;
    }
}
