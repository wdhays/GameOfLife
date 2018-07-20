package main.java.com.wdhays.gol;

public enum RuleSet {
    STANDARD("Standard"),
    TEST1("Test1"),
    TEST2("Test2");
    //More to come...

    private String label;

    RuleSet(String label) {
        this.label = label;
    }

    public String toString() {
        return label;
    }
}
