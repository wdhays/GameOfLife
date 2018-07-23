package main.java.com.wdhays.gol;

public enum RuleSet {

    STANDARD("Standard", "23/3"),
    HIGHLIFE("High Life", "23/36"),
    WALLEDCITIES("Walled Cities", "2345/45678"),
    MAZE("Maze", "12345/3");

    private String label;
    private String ruleString;

    RuleSet(String label, String ruleString) {
        this.label = label;
        this.ruleString = ruleString;
    }

    public String toString() {
        return label;
    }

    public String getRuleString() {
        return ruleString;
    }
}
