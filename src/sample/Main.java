package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Cell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {
    private static final int CELLS_PER_ROW = 10;
    private static final int CELL_WIDTH = 80;
    private static final int CELL_HEIGHT = 30;
    private static final String FILENAME = "sample-input.txt";

    private static final Map<Character, Color> letterToColor = new HashMap<>();
    static {
        letterToColor.put('A', Color.BLACK);
        letterToColor.put('B', Color.BLUE);
        letterToColor.put('C', Color.DARKGREEN);
        letterToColor.put('D', Color.ORANGE);
        letterToColor.put('E', Color.RED);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 800, 600, Color.WHITE);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hello World");

        try (BufferedReader in = new BufferedReader(new FileReader(FILENAME))) {
            Group square = new Group();

            String line = in.readLine();
            for (int row = 0; line != null; row++) {
                for (int cell = 0; line != null && cell < CELLS_PER_ROW; cell++) {
                    if (line.length() < 1)
                        throw new RuntimeException("Error: expected line with one character.");
                    if (!letterToColor.containsKey(line.charAt(0)))
                        throw new RuntimeException("Error: expected line with letter from: " + letterToColor);
                    Color color = letterToColor.get(line.charAt(0));

                    Rectangle rectangle = new Rectangle(CELL_WIDTH * cell, CELL_HEIGHT * row,
                            CELL_WIDTH, CELL_HEIGHT);

                    rectangle.setFill(color);
                    rectangle.setStroke(Color.BLACK);
                    square.getChildren().add(rectangle);

                    line = in.readLine();
                }
            }
            root.getChildren().add(square);
        } finally {
            primaryStage.show();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
