package gameoflife;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {

    private static Controller controller;

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setScene(controller.getView().getScene(350, 350));
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Game Of Life Simulation");
        primaryStage.show();
    }

       public static void main(String[] args) {
           
        controller = new Controller();
        launch(args);
        
       
    }

}
