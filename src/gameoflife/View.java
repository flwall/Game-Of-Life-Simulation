package gameoflife;

import java.util.Observable;
import java.util.Observer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class View implements Observer {

    private final Controller controller;

    private GridPane masterpane;

    private Button start, stop;

    public View(Controller c) {
        this.controller = c;

        init();
    }

    private void init() {

        masterpane = new GridPane();

        start = new Button("Start");
        stop = new Button("Stop");
        start.setOnAction((e) -> {
            controller.getModel().startGame();

        });

        stop.setOnAction((e) -> {

            controller.getModel().stopGame();

        });

        masterpane.add(start, 0, 0);
        masterpane.add(stop, 1, 0);

    }

    @Override
    public void update(Observable o, Object arg) {

        Platform.runLater(() -> {
            boolean[][] field = (boolean[][]) arg;

            GridPane p = new GridPane();

            for (int i = 0; i < field.length; i++) {
                for (int j = 0; j < field[i].length; j++) {

                    Rectangle rec = new Rectangle(10, 10);
                    if (field[i][j]) {
                        rec.setFill(Color.RED);
                    } else {
                        rec.setFill(Color.WHITE);
                    }

                    p.add(rec, i, j + 1);
                }
            }
            masterpane.add(p, 1, 1);
        });

    }

    public Scene getScene(int width, int height) {
        return new Scene(masterpane, width, height);
    }
}
