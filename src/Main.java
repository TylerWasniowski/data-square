package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {
    private static final int COLUMN_SIZE = 7;
    private static final int ROW_SIZE = 7;
    private static final int GAP_SIZE = 1;

    private static final double CELL_WIDTH = 30.0;
    private static final double CELL_HEIGHT = 30.0;

    private static final String FILENAME = "sample-input.txt";

    private static final Map<Character, Color> characterToColor = new HashMap<>();
    static {
        characterToColor.put('A', Color.BLACK);
        characterToColor.put('B', Color.BLUE);
        characterToColor.put('C', Color.DARKGREEN);
        characterToColor.put('D', Color.ORANGE);
        characterToColor.put('E', Color.RED);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 800, 600, Color.WHITE);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Data Square");

        Group square = new Group();
        try (BufferedReader in = new BufferedReader(new FileReader(FILENAME))) {

            String line = in.readLine();

            int minRow = 0;
            int maxRow = (ROW_SIZE * (GAP_SIZE + 1))- 1;
            int minColumn = minRow;
            int maxColumn = (COLUMN_SIZE * (GAP_SIZE + 1)) - 1;
            while (line != null) {
                if (minRow > maxRow || minColumn > maxColumn)
                    throw new RuntimeException("Not enough space for whole file");

                for (int column = minColumn; line != null && column <= maxColumn; column++) {
                    square.getChildren().add(makeRectangle(line, minRow, column));
                    line = in.readLine();
                }
                for (int row = minRow + 1; line != null && row <= maxRow; row++) {
                    square.getChildren().add(makeRectangle(line, row, maxColumn));
                    line = in.readLine();
                }

                minRow += GAP_SIZE;
                minColumn += GAP_SIZE;

                for (int column = maxColumn - 1; line != null && column >= minColumn; column--) {
                    square.getChildren().add(makeRectangle(line, maxRow, column));
                    line = in.readLine();
                }
                for (int row = maxRow - 1; line != null && row > minRow; row--) {
                    square.getChildren().add(makeRectangle(line, row, minColumn));
                    line = in.readLine();
                }

                maxRow -= GAP_SIZE;
                maxColumn -= GAP_SIZE;

                minRow++;
                maxRow--;
                minColumn++;
                maxColumn--;
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            root.getChildren().add(square);
            primaryStage.show();
        }
    }

    private static Rectangle makeRectangle(String line, int row, int column) {
        if (line.length() < 1)
            throw new RuntimeException("Error: expected line with one character.");
        if (!characterToColor.containsKey(line.charAt(0)))
            throw new RuntimeException("Error: expected line with character from: " + characterToColor.keySet());

        Rectangle rectangle = new Rectangle(CELL_WIDTH * column, CELL_HEIGHT * row,
                CELL_WIDTH, CELL_HEIGHT);

        rectangle.setFill(characterToColor.get(line.charAt(0)));

        return rectangle;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
