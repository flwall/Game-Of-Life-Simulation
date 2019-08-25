package gameoflife;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private Button start;

    public View(Controller c) {
        this.controller = c;

        init();
    }

    private void init() {

        masterpane = new BorderPane();

        start = new Button("Start");

        Slider timeSlider = new Slider(0, 1, 0.5);

        Slider sizeSlider = new Slider();

        HBox slidersBox = new HBox();

        slidersBox.getChildren().addAll(timeSlider, sizeSlider);
        start.setId("start");
        start.setOnAction(this);

        Button next = new Button("Next");

        next.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                controller.getModel().nextGen();
            }
        });

        

        HBox hbox = new HBox();
        Button randomField = new Button("Generate Random Field");
        randomField.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                controller.getModel().generateRandomField();
            }
        });

        Button clear=new Button("Clear");
        clear.setOnAction(e->{
            controller.getModel().clearField();


        }); 
        hbox.getChildren().addAll(start, next, randomField, clear);

        gamePane = new GridPane();
        // gamePane.setCache(true);
        hbox.setPadding(new Insets(10, 10, 10, 10));

        VBox box = new VBox();
        box.getChildren().addAll(gamePane);
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
                        Platform.runLater(new Runnable() {

                            @Override
                            public void run() {
                                controller.getModel().changeStateOfCell(tmp, tmp2);
                            }
                        });

                    }
                });

                gamePane.add(rec, i, j);
            }
        }

    }

    @Override
    public void update(Observable o, Object arg) {

        runAndWait(new Runnable() {

            @Override
            public void run() {
                toRun(arg);
            }
        });

        /*
         * this.runAndWait(new Runnable() {
         * 
         * @Override public void run() { toRun(arg); } });
         */

    }

    private void toRun(Object arg) {
        List<Cell> cells = null;
        if (arg instanceof LinkedList) {
            cells = (LinkedList<Cell>) arg;
        } else
            return;

        GridPane p = gamePane;
        p.setGridLinesVisible(true);
        for (Cell c : cells) {
            Node node = p.getChildren().get(controller.getModel().getRows() * c.getRow()+1 + c.getCol());
            if (!(node instanceof Rectangle)) {
                Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Node is not a Rectangle");
                continue;
            }

            Rectangle rec = (Rectangle) node;
            if (c.getState() == LiveState.LIVING) {
                rec.setFill(Color.RED);
            } else {
                rec.setFill(Color.WHITE);
            }
        }

    }

    // unneccessary
    public void runAndWait(Runnable action) {
        if (action == null)
            throw new NullPointerException("action");

        // run synchronously on JavaFX thread
        if (Platform.isFxApplicationThread()) {
            action.run();
            return;
        }

        // queue on JavaFX thread and wait for completion
        final CountDownLatch doneLatch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                action.run();
            } finally {
                doneLatch.countDown();
            }
        });

        try {
            doneLatch.await();
        } catch (InterruptedException e) {
            controller.getModel().interrupt();
        }
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
