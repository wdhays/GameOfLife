package main.java.com.wdhays.gol;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameOfLife extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    int rowCount = 60;
    int colCount = 60;
    int cellWidth = 10;
    int cellHeight = 10;
    GridPane gridPane;
    Button testBtn1;
    Button testBtn2;

    @Override public void start(Stage primaryStage) {

        // Create and set up the gridPane.
        gridPane = new GridPane();
        for (int i = 0; i < colCount; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(cellWidth));
        }
        for (int i = 0; i < rowCount; i++) {
            gridPane.getRowConstraints().add(new RowConstraints(cellHeight));
        }

        //Add a rect that we can change the color of to each grid cell.
        for (int i = 0; i < colCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                addRectToGrid(i,j);
            }
        }

        // Create and set up the controls pane.
        VBox controlsPane = new VBox();
        testBtn1 = new Button();
        testBtn1.setText("Button 1");
        testBtn1.setOnAction(e -> testButton1Action());

        testBtn2 = new Button();
        testBtn2.setText("Button 2");
        testBtn2.setOnAction(e -> testButton2Action());

        controlsPane.getChildren().addAll(testBtn1, testBtn2);
        controlsPane.setPrefWidth(200);
        controlsPane.setPadding(new Insets(10));
        controlsPane.setSpacing(10);

        // Create the main pane and add the nested panes of the main pane.
        BorderPane mainLayout = new BorderPane();
        mainLayout.setCenter(gridPane);
        mainLayout.setRight(controlsPane);
        // Add the main pane to the scene.
        Scene mainScene =  new Scene(mainLayout, 800, 600);

        // Set up the stage.
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Game of Life");
        primaryStage.show();
    }

    private void addRectToGrid(int i, int j) {
        Rectangle rectangle = new Rectangle(cellWidth, cellHeight);
        rectangle.setFill(Color.WHITE);
        rectangle.setOnMouseClicked(e -> {
            System.out.printf("Mouse clicked in cell [%d, %d]%n", i, j);
            if(rectangle.getFill().equals(Color.WHITE)) {
                rectangle.setFill(Color.BLACK);
            } else {
                rectangle.setFill(Color.WHITE);
            }
        });
        gridPane.add(rectangle, i, j);
    }

    private void testButton1Action(){
        System.out.println("Test button one was pressed!");
    }

    private void testButton2Action(){
        System.out.println("Test button two was pressed!");
    }
}
