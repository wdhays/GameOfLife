package main.java.com.wdhays.gol;

import java.util.ArrayList;
import java.util.List;

public enum Pattern {

    GLIDER("Glider"),
    LIGHTWEIGHT("Lightweight-Spaceship"),
    ACHIMSP11("Achims-p11"),
    KOKSGALAXY("Koks-Galaxy-p8"),
    SPIDER("Spider"),
    COPPERHEAD("Copperhead"),
    PENTADECATHLON("Pentadecathlon"),
    P112P15("112P15"),
    PULSAR("Pulsar"),
    CARNIVALSHUTTLE("Carnival-Shuttle");

    private String patternName;

    Pattern(String patternName) {
        this.patternName = patternName;
    }

    public String getPatternName() {
        return patternName;
    }

    public static List<String> getPatternNames() {
        List<String> allNames = new ArrayList<>();
        for (Pattern pattern : Pattern.values()) {
            allNames.add(pattern.getPatternName());
        }
        return allNames;
    }
}
