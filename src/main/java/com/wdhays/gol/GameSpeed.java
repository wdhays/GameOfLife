package main.java.com.wdhays.gol;

public enum GameSpeed {
    SLOW(800, "Slow"),
    MEDSLOW(400, "MedSlow"),
    MEDIUM(200, "Medium"),
    MEDFAST(100, "MedFast"),
    FAST(50, "Fast");

    private int speed;
    private String label;

    GameSpeed(int speed, String label) {
        this.speed = speed;
        this.label = label;
    }

    public int toValue() {
        return speed;
    }

    public String toString() {
        return label;
    }
}
