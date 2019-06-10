package gameoflife;

import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

public class Model extends Observable implements Observer {

    private Thread gameThread;

    public void startGame() {
        if (gameThread != null) {
            gameThread.interrupt();
            gameThread = null;
        }

        GameOfLifeCalculation calc = new GameOfLifeCalculation();
        calc.addObserver(this);

        gameThread = new Thread(calc, "Game Of Life Calculator");
        gameThread.setDaemon(true);

        gameThread.start();

    }

    public void stopGame() {

        if (gameThread == null) {
            return;
        }

        gameThread.interrupt();
        gameThread = null;

    }

    @Override
    public void update(Observable o, Object arg) {

        setChanged();
        notifyObservers(arg);

    }

}
