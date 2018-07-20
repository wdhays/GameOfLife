package main.java.com.wdhays.gol;

public enum RuleSet {
    STANDARD("Standard");
    //More to come...

    private String label;

    RuleSet(String label) {
        this.label = label;
    }

    public String toString() {
        return label;
    }
}
