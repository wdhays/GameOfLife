package main.java.com.wdhays.gol;

public enum GameSpeed {
    SLOW(800),
    MEDSLOW(400),
    MEDIUM(200),
    MEDFAST(100),
    FAST(50);

    private int speed;

    GameSpeed(int speed) {
        this.speed = speed;
    }

    public int toValue() {
        return speed;
    }

    public String toString() {
        return Integer.toString(speed);
    }
}
