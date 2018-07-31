package main.java.com.wdhays.gol;

import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
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
    private BufferedReader patternFileReader;
    private Image patternImage;

    Pattern(String patternName) {
        this.patternName = patternName;
        try {
            this.patternFileReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("patterns/" + patternName + ".gol")));
            this.patternImage = new Image(getClass().getResource("patterns/" + patternName + ".png").toString(), true);
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println("Failed to load pattern File or Image!");
            e.printStackTrace();
        }
    }

    public BufferedReader getPatternFile() {
        return patternFileReader;
    }

    public Image getPatternImage() {
        return patternImage;
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

    public static Pattern fromString(String text) {
        for (Pattern pattern : Pattern.values()) {
            if (pattern.patternName.equalsIgnoreCase(text)) {
                return pattern;
            }
        }
        return null;
    }
}
