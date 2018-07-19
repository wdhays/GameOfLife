package main.java.com.wdhays.gol;

public class GameOfLife {

    private GameBoard gameBoard;
    private int gameBoardSize;
    private int cellSize;

    public GameOfLife(int gameBoardSize, int cellSize) {
        this.gameBoardSize = gameBoardSize;
        this.cellSize = cellSize;
        this.gameBoard = new GameBoard(gameBoardSize);
    }

    public int getGameBoardSize() {
        return gameBoardSize;
    }

    public int getCellSize() {
        return cellSize;
    }
}
