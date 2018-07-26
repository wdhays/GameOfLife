package main.java.com.wdhays.gol;

public class Cell {

    private boolean isAlive;
    private long age;

    public Cell() {
        isAlive = false;
        age = 0;
    }

    public Cell(boolean isAlive, long age) {
        this.isAlive = isAlive;
        this.age = age;
    }

    public Cell(Cell toCopy) {
        this.isAlive = toCopy.isAlive;
        this.age = toCopy.age;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public void toggleAlive() {
        if(isAlive) {
            isAlive = false;
            age = 0;
        } else {
            isAlive = true;
            age = 1;
        }
    }

    public void incrementAge() {
        age = age + 1;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }
}
