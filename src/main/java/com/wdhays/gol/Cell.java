package main.java.com.wdhays.gol;

public class Cell {

    private boolean isAlive;

    public Cell() {
        isAlive = false;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public void toggleAlive() {
        setAlive(!isAlive);
    }
}
