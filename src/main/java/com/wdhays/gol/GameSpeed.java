package main.java.com.wdhays.gol;

public enum GameSpeed {

    VERYSLOW(800, "V. Slow"),
    SLOW(400, "Slow"),
    MEDIUM(200, "Medium"),
    FAST(100, "Fast"),
    VERYFAST(50, "V. Fast");

    private int speed; //In milliseconds
    private String label;

    GameSpeed(int speed, String label) {
        this.speed = speed;
        this.label = label;
    }

    public int toValue() {
        return speed;
    }

    public String getLabel() {
        return label;
    }
}
