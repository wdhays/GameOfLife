package main.java.com.wdhays.gol;

public class GameBoard {

    Cell[][] gameBoard;
    int rowCount;
    int colCount;

    public GameBoard(int rowCount, int colCount) {
        this.rowCount = rowCount;
        this.colCount = colCount;
        gameBoard = new Cell[colCount][rowCount];
    }

    public Cell[][] getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(Cell[][] gameBoard) {
        this.gameBoard = gameBoard;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColCount() {
        return colCount;
    }

}
