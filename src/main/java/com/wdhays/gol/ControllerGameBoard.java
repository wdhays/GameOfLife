package main.java.com.wdhays.gol;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerGameBoard implements Initializable {

    int rowCount = 60;
    int colCount = 60;
    int cellWidth = 10;
    int cellHeight = 10;
    Image cellImageAlive= new Image(getClass().getResource("./images/cell-alive.png").toString(), true);
    Image cellImageDead= new Image(getClass().getResource("./images/cell-dead.png").toString(), true);

    @FXML
    private GridPane gridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeGridPane();
    }

    private void initializeGridPane(){
        // Set up the rows and columns.
        for (int i = 0; i < colCount; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(cellWidth));
        }
        for (int i = 0; i < rowCount; i++) {
            gridPane.getRowConstraints().add(new RowConstraints(cellHeight));
        }

        //Add an image to each cell with on OnClick mouse event.
        for (int i = 0; i < colCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                addImageCellsToGrid(i,j);
            }
        }
    }

    private void addImageCellsToGrid(int i, int j) {

        ImageView cellImageView = new ImageView();
        cellImageView.setFitWidth(cellWidth);
        cellImageView.setFitHeight(cellHeight);
        cellImageView.setImage(cellImageDead);

        cellImageView.setOnMouseClicked(e -> {
            System.out.printf("Mouse clicked in cell [%d, %d]%n", i, j);
            if(cellImageView.getImage() == cellImageAlive) {
                System.out.println("The cell is alive! Kill it.");
                cellImageView.setImage(cellImageDead);
            } else {
                System.out.println("The cell is dead! Resurrect it.");
                cellImageView.setImage(cellImageAlive);
            }
        });

        gridPane.add(cellImageView, i, j);
    }
}
