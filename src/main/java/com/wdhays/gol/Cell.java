package main.java.com.wdhays.gol;

public class Cell {

    boolean isAlive;

    /**/
    public Cell(boolean isAlive) {
        this.isAlive = isAlive;
    }

    /**/
    public boolean isAlive() {
        return isAlive;
    }

    /**/
    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    /**/
    public void toggleAlive() {
        setAlive(!isAlive);
    }
}
