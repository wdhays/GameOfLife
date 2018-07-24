package main.java.com.wdhays.gol;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
    private ComboBox rulesCombo;
    @FXML
    private Button randomButton;
    @FXML
    private TextField randomTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Set up speedSlider options and property change listener.
        initializeSpeedSlider();
        speedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                speedSlider.setValue(newValue.intValue());
                if(newValue.intValue() != oldValue.intValue()) {
                    speedSliderChangeAction();
                }
        });
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
        rulesCombo.valueProperty().addListener((observable, oldValue, newValue) -> rulesComboChangeAction());
    }

    private void rulesComboChangeAction() {
        System.out.println("The rules combo value was changed!");
        System.out.println("The new value is " + rulesCombo.getValue());
        gameOfLife.setRuleSet(RuleSet.fromString(rulesCombo.getValue().toString()));
    }

    private void speedSliderChangeAction() {
        System.out.println("The speed slider value was changed!");
        System.out.println("The new value is " + speedSlider.getValue());
        int speedSliderValue = (int)speedSlider.getValue();
        if (speedSliderValue == 0) {
            gameOfLife.initializeTimeline(SLOW);
        } else if (speedSliderValue == 1) {
            gameOfLife.initializeTimeline(MEDSLOW);
        } else if (speedSliderValue == 2) {
            gameOfLife.initializeTimeline(MEDIUM);
        } else if (speedSliderValue == 3) {
            gameOfLife.initializeTimeline(MEDFAST);
        } else if (speedSliderValue == 4) {
            gameOfLife.initializeTimeline(FAST);
        }
    }

    private void saveBtnOnAction(ActionEvent e) {
        System.out.println("Save button was pressed!");

        Button eventSource = (Button) e.getSource();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save GOL File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("GOL Files", "*.gol"));
        File saveFile = fileChooser.showSaveDialog(eventSource.getScene().getWindow());
        if (saveFile != null) {
            System.out.println("Attempting save to: " + saveFile);
            try {
                gameOfLife.saveGameBoardToFile(saveFile);
            } catch (IOException e1) {
                e1.printStackTrace();
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
                gameOfLife.loadGameBoardFromFile(selectedFile);
                //TODO Need to update the game board.
                gameOfLife.setGeneration(0);
            } catch (IOException e1) {
                e1.printStackTrace();
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
                if (n == 0) return GameSpeed.SLOW.toString();
                if (n == 1) return GameSpeed.MEDSLOW.toString();
                if (n == 2) return GameSpeed.MEDIUM.toString();
                if (n == 3) return GameSpeed.MEDFAST.toString();
                if (n == 4) return GameSpeed.FAST.toString();
                return GameSpeed.SLOW.toString();
            }
            @Override
            public Double fromString(String s) {
                if (s.equals(GameSpeed.SLOW.toString())) return 0d;
                if (s.equals(GameSpeed.MEDSLOW.toString())) return 1d;
                if (s.equals(GameSpeed.MEDIUM.toString())) return 2d;
                if (s.equals(GameSpeed.MEDFAST.toString())) return 3d;
                if (s.equals(GameSpeed.FAST.toString())) return 4d;
                return 0d;
            }
        });
    }
}
