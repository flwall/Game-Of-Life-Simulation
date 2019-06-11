package gameoflife;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class View implements Observer, EventHandler<ActionEvent> {

    private final Controller controller;

    private BorderPane masterpane;
    private GridPane gamePane;

    private Button start, stop;

    public View(Controller c) {
        this.controller = c;

        init();
    }

    private void init() {

        masterpane = new BorderPane();

        start = new Button("Start");
        stop = new Button("Stop");

        Slider timeSlider = new Slider(0, 1, 0.5);

        Slider sizeSlider = new Slider();

        HBox slidersBox = new HBox();

        slidersBox.getChildren().addAll(timeSlider, sizeSlider);
        start.setId("start");
        start.setOnAction(this);

        /*
         * stop.setOnAction((e) -> {
         * 
         * controller.getModel().stopGame();
         * 
         * });
         */
        Button next = new Button("Next");
        next.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                controller.getModel().nextGen();
            }
        });
        HBox hbox = new HBox();
        hbox.getChildren().addAll(start, next);

        gamePane = new GridPane();
        gamePane.setCache(true);
        hbox.setPadding(new Insets(10, 10, 10, 10));

        VBox box = new VBox();
        box.getChildren().addAll(gamePane, slidersBox);
        masterpane.setCenter(box);
        masterpane.setTop(hbox);
        masterpane.setPadding(new Insets(10, 10, 10, 10));
        // masterpane.setBottom(slidersBox);
        initGameScreen();
    }

    private void initGameScreen() {

        int cols = 73, rows = 47;

        controller.getModel().initField(rows, cols);

        gamePane.setGridLinesVisible(true);
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {

                Rectangle rec = new Rectangle(10, 10);

                rec.setFill(Color.WHITE);
                final int tmp = i, tmp2 = j;
                rec.setOnMouseClicked(new EventHandler<Event>() {

                    @Override
                    public void handle(Event event) {

                        controller.getModel().changeStateOfCell(tmp, tmp2);

                        Color red = Color.RED;
                        if (rec.getFill().equals(Color.RED)) {
                            rec.setFill(Color.WHITE);
                        } else {
                            rec.setFill(Color.RED);
                        }

                    }
                });

                gamePane.add(rec, i, j);
            }
        }

    }

    @Override
    public void update(Observable o, Object arg) {

        Platform.runLater(() -> {
            boolean[][] field = (boolean[][]) arg;

            GridPane p = gamePane;
            p.setGridLinesVisible(true);

            for (int i = 0; i < field.length; i++) {
                for (int j = 0; j < field[i].length; j++) {

                    Rectangle rec = new Rectangle(10, 10);
                    if (field[i][j]) {
                        rec.setFill(Color.RED);
                    } else {
                        rec.setFill(Color.WHITE);
                    }
                    final int tmp = i, tmp2 = j;
                    rec.setOnMouseClicked(new EventHandler<Event>() {

                        @Override
                        public void handle(Event event) {

                            controller.getModel().changeStateOfCell(tmp, tmp2);

                            if (!field[tmp][tmp2]) {
                                rec.setFill(Color.RED);
                            } else {
                                rec.setFill(Color.WHITE);
                            }

                        }
                    });

                    p.add(rec, i, j);
                }
            }
            // masterpane.add(p, 1, 1);
        });

    }

    public Scene getScene(int width, int height) {
        return new Scene(masterpane, width, height);
    }

    @Override
    public void handle(ActionEvent event) {
        if (!(event.getSource() instanceof Button))
            return;
        Node src = ((Node) event.getSource());
        Button b = (Button) src;
        if (src.getId().equals("stop")) {

            controller.getModel().stopGame();
            b.setId("start");
            b.setText("Start");
        } else if (src.getId().equals("start")) {
            controller.getModel().startGame();
            b.setId("stop");
            b.setText("Stop");
        }

    }
}
