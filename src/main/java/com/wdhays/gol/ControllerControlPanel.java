package main.java.com.wdhays.gol;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerControlPanel implements Initializable {

    private GameOfLife gameOfLife;

    public ControllerControlPanel(GameOfLife gameOfLife) {
        this.gameOfLife = gameOfLife;
    }

    @FXML
    Button playButton;
    @FXML
    Button pauseButton;
    @FXML
    Button stopButton;
    @FXML
    Slider speedSlider;
    @FXML
    Button saveButton;
    @FXML
    Button loadButton;
    @FXML
    ComboBox rulesCombo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
        //Set up speedSlider options and property change listener.
        initializeSpeedSlider();
        speedSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                System.out.println(new_val.doubleValue());
            }
        });
    }

    public void initializeSpeedSlider(){
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
                        return 800d;
                    case "MedSlow":
                        return 400d;
                    case "Med":
                        return 200d;
                    case "MedFast":
                        return 100d;
                    case "Fast":
                        return 50d;
                    default:
                        return 50d;
                }
            }
        });
    }
}
