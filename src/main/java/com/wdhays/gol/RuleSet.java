package main.java.com.wdhays.gol;

import java.util.ArrayList;
import java.util.List;

public enum RuleSet {

    /*
    * Note: Game of Life rules can be represented by a string E.g. 23/3
    * where the numbers before the slash represent the number of living neighbors
    * a living cell needs to stay alive in the next generation and the numbers
    * after the slash represent the number of living neighbors a dead cell needs
    * to come to life in the next generation. The aliveRules and deadRules are
    * an interpretation of this that can be easily searched using a binary search
    * when determining a cells state in the next generation using their living
    * neighbor count.
    */
    STANDARD("Standard", new int[] {2,3}, new int[] {3}),
    HIGHLIFE("High Life", new int[] {2,3}, new int[] {3, 6}),
    WALLEDCITIES("Walled Cities", new int[] {2,3,4,5}, new int[] {4,5,6,7,8}),
    MAZE("Maze", new int[] {1,2,3,4,5}, new int[] {3}),
    GNARL("Gnarl", new int[] {1}, new int[] {1}),
    SEEDS("Seeds", new int[] {}, new int[] {2}),
    DAYANDNIGHT("Day and Night", new int[] {3,4,6,7,8}, new int[] {3,6,7,8}),
    FLAKES("Flakes", new int[] {0,1,2,3,4,5,6,7,8}, new int[] {3}),
    COAGULATION("Coagulation", new int[] {2,3,5,6,7,8}, new int[] {3,7,8}),
    LONGLIFE("Long Life", new int[] {5}, new int[] {3,4,5});

    private String label;
    private int[] aliveRules; //Rules that apply to cells currently alive.
    private int[] deadRules; //Rules that apply to cells currently dead.

    RuleSet(String label, int[] aliveRules, int[] deadRules) {
        this.label = label;
        this.aliveRules = aliveRules;
        this.deadRules = deadRules;
    }

    public String getLabel() {
        return label;
    }

    public static List<String> getRuleSetLabels() {
        List<String> allLabels = new ArrayList<>();
        for (RuleSet ruleSet : RuleSet.values()) {
            allLabels.add(ruleSet.getLabel());
        }
        return allLabels;
    }

    public static RuleSet fromString(String text) {
        for (RuleSet ruleSet : RuleSet.values()) {
            if (ruleSet.label.equalsIgnoreCase(text)) {
                return ruleSet;
            }
        }
        return null;
    }

    public int[] getAliveRules() {
        return aliveRules;
    }

    public int[] getDeadRules() {
        return deadRules;
    }
}
