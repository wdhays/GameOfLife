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
                gameOfLife.initializeTimeline(SLOW);
                break;
            case 1:
                gameOfLife.initializeTimeline(MEDSLOW);
                break;
            case 2:
                gameOfLife.initializeTimeline(MEDIUM);
                break;
            case 3:
                gameOfLife.initializeTimeline(MEDFAST);
                break;
            case 4:
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
        //TODO
        gameOfLife.play();
    }

    private void pauseBtnOnAction() {
        System.out.println("Pause button was pressed!");
        //TODO
        gameOfLife.pause();
    }

    private void clearBtnOnAction() {
        System.out.println("Clear button was pressed!");
        //TODO
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
                if (n == 0) return "Slow";
                if (n == 1) return "MedSlow";
                if (n == 2) return "Med";
                if (n == 3) return "MedFast";
                if (n == 4) return "Fast";
                return "Slow";
            }

            @Override
            public Double fromString(String s) {
                switch (s) {
                    case "Slow":
                        return 0d;
                    case "MedSlow":
                        return 1d;
                    case "Med":
                        return 2d;
                    case "MedFast":
                        return 3d;
                    case "Fast":
                        return 4d;
                    default:
                        return 0d;
                }
            }
        });
    }
}
