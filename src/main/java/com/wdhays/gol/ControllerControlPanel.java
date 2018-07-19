package main.java.com.wdhays.gol;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerControlPanel implements Initializable {

    @FXML
    private void testButton1Action(){
        System.out.println("Test button one was pressed!");
    }
    @FXML
    private void testButton2Action(){
        System.out.println("Test button two was pressed!");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }
}
