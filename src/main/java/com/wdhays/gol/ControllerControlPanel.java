package main.java.com.wdhays.gol;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.util.StringConverter;

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
        playButton.setOnAction(event -> playBtnOnAction());
        pauseButton.setOnAction(event -> pauseBtnOnAction());
        clearButton.setOnAction(event -> clearBtnOnAction());
        //Set up action listeners for the save and load buttons.
        saveButton.setOnAction(event -> saveBtnOnAction());
        loadButton.setOnAction(event -> loadBtnOnAction());
        //Set up the rules combo box to be populated by the RuleSet enum.
        rulesCombo.getItems().setAll(RuleSet.values());
        rulesCombo.getSelectionModel().selectFirst();
        rulesCombo.valueProperty().addListener((observable, oldValue, newValue) -> rulesComboChangeAction());
    }

    private void rulesComboChangeAction() {
        System.out.println("The rules combo value was changed!");
        System.out.println("The new value is " + rulesCombo.getValue());
        //TODO
    }

    private void speedSliderChangeAction() {
        System.out.println("The speed slider value was changed!");
        System.out.println("The new value is " + speedSlider.getValue());
        int switchCase = (int)speedSlider.getValue();
        switch (switchCase) {
            case 0:
                gameOfLife.setGameSpeed(SLOW);
                gameOfLife.initializeTimeline(SLOW);
                break;
            case 1:
                gameOfLife.setGameSpeed(MEDSLOW);
                gameOfLife.initializeTimeline(MEDSLOW);
                break;
            case 2:
                gameOfLife.setGameSpeed(MEDIUM);
                gameOfLife.initializeTimeline(MEDIUM);
                break;
            case 3:
                gameOfLife.setGameSpeed(MEDFAST);
                gameOfLife.initializeTimeline(MEDFAST);
                break;
            case 4:
                gameOfLife.setGameSpeed(FAST);
                gameOfLife.initializeTimeline(FAST);
                break;
        }
    }

    private void saveBtnOnAction() {
        System.out.println("Save button was pressed!");
        //TODO
    }

    private void loadBtnOnAction() {
        System.out.println("Load button was pressed!");
        //TODO
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
                if (s == GameSpeed.SLOW.toString()) return 0d;
                if (s == GameSpeed.MEDSLOW.toString()) return 1d;
                if (s == GameSpeed.MEDIUM.toString()) return 2d;
                if (s == GameSpeed.MEDFAST.toString()) return 3d;
                if (s == GameSpeed.FAST.toString()) return 4d;
                return 0d;
            }
        });
    }
}
