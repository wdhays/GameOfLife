package main.java.com.wdhays.gol;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static main.java.com.wdhays.gol.GameSpeed.*;

public class ControllerControlPanel implements Initializable {

    private GameOfLife gameOfLife;

    public ControllerControlPanel(GameOfLife gameOfLife) {
        this.gameOfLife = gameOfLife;
    }

    @FXML
    private Button playButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button clearButton;
    @FXML
    private Slider speedSlider;
    @FXML
    private Button saveButton;
    @FXML
    private Button loadButton;
    @FXML
    private ComboBox<String> rulesCombo;
    @FXML
    private Button randomButton;
    @FXML
    private TextField randomTextField;
    @FXML
    private ComboBox<String> patternsCombo;
    @FXML
    private ImageView patternImageView;
    @FXML
    private Button addPatternButton;
    @FXML
    private CheckBox useColorsCheckBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Set up speedSlider options and property change listener.
        initializeSpeedSlider();
        speedSlider.valueProperty().addListener(getSliderChangeListener());
        //Set up action listeners for the play, pause, and stop buttons.
        playButton.setOnAction(e -> playBtnOnAction());
        pauseButton.setOnAction(e -> pauseBtnOnAction());
        clearButton.setOnAction(e -> clearBtnOnAction());
        //Set up action listeners for the save and load buttons.
        saveButton.setOnAction(this::saveBtnOnAction);
        loadButton.setOnAction(this::loadBtnOnAction);
        //Set up the rules combo box to be populated by the RuleSet enum.
        rulesCombo.getItems().setAll(RuleSet.getRuleSetLabels());
        rulesCombo.getSelectionModel().selectFirst();
        rulesCombo.valueProperty().addListener(getRulesComboChangeListener());
        //Set up the action listeners for the random button.
        randomButton.setOnAction(e -> randomBtnOnAction());
        //Set up the patterns combo box to be populated by the Pattern enum.
        patternsCombo.getItems().setAll(Pattern.getPatternNames());
        patternsCombo.getSelectionModel().selectFirst();
        patternsCombo.valueProperty().addListener(getPatternsComboChangeListener());
        //Set up the pattern image view with a default image.
        patternImageView.setImage(Objects.requireNonNull(Pattern.fromString(patternsCombo.getValue())).getPatternImage());
        //Set up add pattern button.
        addPatternButton.setOnAction(e -> addPatternOnAction());
        //Set up the use cell age colors checkbox.
        useColorsCheckBox.selectedProperty().addListener(useCellAgeColorsChangeListener());
    }

    private ChangeListener<Boolean> useCellAgeColorsChangeListener() {
        return (observable, oldValue, newValue) -> {
            System.out.println("The draw colors checkbox value has changed to: " + newValue);
            gameOfLife.setUseCellAge(newValue);
            //Trigger redraw.
            gameOfLife.setNeedsRedraw(true);
        };
    }

    private ChangeListener<String> getRulesComboChangeListener() {
        return (observable, oldValue, newValue) -> {
            System.out.println("The rules combo value was changed!");
            System.out.println("The new value is " + rulesCombo.getValue());
            gameOfLife.setRuleSet(RuleSet.fromString(rulesCombo.getValue()));
        };
    }

    private ChangeListener<String> getPatternsComboChangeListener() {
        //Update the pattern image view to the currently selected patterns thumbnail.
        return (observable, oldValue, newValue) -> {
            System.out.println("The pattern combo value was changed!");
            System.out.println("The new value is " + patternsCombo.getValue());
            patternImageView.setImage(Objects.requireNonNull(Pattern.fromString(patternsCombo.getValue())).getPatternImage());
        };
    }

    private ChangeListener<Number> getSliderChangeListener() {
        return (observable, oldValue, newValue) -> {
            //This bit keeps to property listener from firing unless the slider in on a tick mark.
            //The slider value is a double, but we are only interested in the whole numbers at ticks.
            speedSlider.setValue(newValue.intValue());
            if(newValue.intValue() != oldValue.intValue()) {
                    speedSliderChangeAction();
            }
        };
    }

    private void addPatternOnAction() {
        try {
            if (gameOfLife.loadGameBoardFromPatternFile(Objects.requireNonNull(Pattern.fromString(patternsCombo.getValue())).getPatternFile())) {
                System.out.println("Pattern successfully added!");
                //Trigger a redraw.
                gameOfLife.setNeedsRedraw(true);
                gameOfLife.setGeneration(0);
            } else {
                System.out.println("Pattern load failed!");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void randomBtnOnAction() {
        System.out.println("The random button was pressed!");
        //Force the live chance value to be in our range.
        double randomValue;
        try {
            randomValue = Double.valueOf(randomTextField.getText());
            if(randomValue > 1) {
                randomValue = 1.0;
                randomTextField.setText(Double.toString(randomValue));
            } else if (randomValue < 0) {
                randomValue = 0.0;
                randomTextField.setText(Double.toString(randomValue));
            }
        } catch(NumberFormatException e) {
            randomValue = 0.1;
            randomTextField.setText(Double.toString(randomValue));
        }
        //Update the game board.
        System.out.println("The random text field value is " + randomValue);
        gameOfLife.generateRandomGrid(randomValue);
    }

    private void saveBtnOnAction(ActionEvent event) {

        System.out.println("Save button was pressed!");
        Button eventSource = (Button) event.getSource();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save GOL File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("GOL Files", "*.gol"));
        File saveFile = fileChooser.showSaveDialog(eventSource.getScene().getWindow());

        if (saveFile != null) {
            System.out.println("Attempting save to: " + saveFile);
            try {
                if (gameOfLife.saveGameBoardToFile(saveFile)) {
                    System.out.println("Save successful!");
                } else {
                    System.out.println("Save failed!");
                    System.out.println("The file could not be created or was not writable!");
                }
            } catch (IOException e) {
                System.out.println("There was an error saving the file!");
                e.printStackTrace();
            }
        }
    }

    private void loadBtnOnAction(ActionEvent e) {

        System.out.println("Load button was pressed!");
        Button eventSource = (Button) e.getSource();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load GOL File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("GOL Files", "*.gol"));
        File selectedFile = fileChooser.showOpenDialog(eventSource.getScene().getWindow());

        if (selectedFile != null) {
            System.out.println("Attempting load from: " + selectedFile);
            try {
                if (gameOfLife.loadGameBoardFromFile(selectedFile)) {
                    //Trigger a redraw.
                    gameOfLife.setNeedsRedraw(true);
                    gameOfLife.setGeneration(0);
                    System.out.println("Load successful!");
                } else {
                    System.out.println("Load failed!");
                    int gridSize = gameOfLife.getGridSize();
                    System.out.println("The file needs to be a " + gridSize + "x" + gridSize + " CSV of long ints!");
                }
            } catch (IOException e1) {
                System.out.println("There was an error loading the file!");
                e1.printStackTrace();
            } catch (NumberFormatException e1) {
                System.out.println("The file had bad data!");
                int gridSize = gameOfLife.getGridSize();
                System.out.println("The file needs to be a " + gridSize + "x" + gridSize + " CSV of long ints!");
            }
        }
    }

    private void playBtnOnAction() {
        System.out.println("Play button was pressed!");
        gameOfLife.play();
    }

    private void pauseBtnOnAction() {
        System.out.println("Pause button was pressed!");
        gameOfLife.pause();
    }

    private void clearBtnOnAction() {
        System.out.println("Clear button was pressed!");
        gameOfLife.clear();
        //Trigger a redraw.
        gameOfLife.setNeedsRedraw(true);
    }

    private void speedSliderChangeAction() {
        System.out.println("The speed slider value was changed!");
        System.out.println("The new value is " + speedSlider.getValue());
        int speedSliderValue = (int)speedSlider.getValue();
        if (speedSliderValue == 0) {
            gameOfLife.initializeTimeline(VERYSLOW);
        } else if (speedSliderValue == 1) {
            gameOfLife.initializeTimeline(SLOW);
        } else if (speedSliderValue == 2) {
            gameOfLife.initializeTimeline(MEDIUM);
        } else if (speedSliderValue == 3) {
            gameOfLife.initializeTimeline(FAST);
        } else if (speedSliderValue == 4) {
            gameOfLife.initializeTimeline(VERYFAST);
        }
    }

    private void initializeSpeedSlider(){
        // Set up the speedSlider options
        speedSlider.setMin(0);
        speedSlider.setMax(4);
        speedSlider.setValue(0);
        speedSlider.setShowTickLabels(true);
        speedSlider.setShowTickMarks(true);
        speedSlider.setMajorTickUnit(1);
        speedSlider.setMinorTickCount(0);
        speedSlider.setBlockIncrement(1);
        speedSlider.setSnapToTicks(true);
        speedSlider.setLabelFormatter(new StringConverter<>() {
            @Override
            public String toString(Double n) {
                if (n == 0) return GameSpeed.VERYSLOW.getLabel();
                if (n == 1) return GameSpeed.SLOW.getLabel();
                if (n == 2) return GameSpeed.MEDIUM.getLabel();
                if (n == 3) return GameSpeed.FAST.getLabel();
                if (n == 4) return GameSpeed.VERYFAST.getLabel();
                return GameSpeed.SLOW.toString();
            }
            @Override
            public Double fromString(String s) {
                if (s.equals(GameSpeed.VERYSLOW.getLabel())) return 0d;
                if (s.equals(GameSpeed.SLOW.getLabel())) return 1d;
                if (s.equals(GameSpeed.MEDIUM.getLabel())) return 2d;
                if (s.equals(GameSpeed.FAST.getLabel())) return 3d;
                if (s.equals(GameSpeed.VERYFAST.getLabel())) return 4d;
                return 0d;
            }
        });
    }
}
