package gameoflife;

import java.util.Observable;
import java.util.Observer;

public class Model extends Observable implements Observer {

    private Thread gameThread;

    private boolean[][] field;

    private GameOfLifeCalculation calc;

    public void initField(int rows, int cols) {

        field = new boolean[cols][rows];

calc=new GameOfLifeCalculation(field);
calc.addObserver(this);



    }

    public void nextGen() {

        if (calc == null) {
            return;
        }
        calc.nextGen();

        setChanged();
        notifyObservers(field);

    }

    public void startGame() {
        if (gameThread != null) {
            gameThread.interrupt();
        }
        if(calc==null){
            initField(0, 0);
            return;
        }

        /*
         * int rows = 47; int cols = 73; field = new boolean[cols][rows];
         */
        //calc = new GameOfLifeCalculation(field);
        //calc.addObserver(this);

        gameThread = new Thread(calc, "Game Of Life Calculator");
        gameThread.setDaemon(true);

        gameThread.start();

    }

    public void stopGame() {

        if (gameThread == null) {
            return;
        }

        gameThread.interrupt();

        // gameThread=null;

    }

    @Override
    public void update(Observable o, Object arg) {

        setChanged();
        notifyObservers(arg);

    }

    public void changeStateOfCell(int i, int j) {
        calc.changeStateOfCell(i, j);

    }

}
