package gameoflife;

import java.util.Observable;
import java.util.Observer;

public class Model extends Observable implements Observer {

    private Thread gameThread;
    private int rows,cols;

    public int getRows() {
        return this.rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return this.cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

  

    private GameOfLifeCalculation calc;

    public void initField(int rows, int cols) {

        this.rows=rows;
        this.cols=cols;
        boolean[][] field = new boolean[cols][rows];

        calc = new GameOfLifeCalculation(field);
        calc.addObserver(this);

    }

    public void nextGen() {

        if (calc == null) {
            return;
        }
        calc.nextGen();

        calc.update();

    }

    public void startGame() {
        if (gameThread != null) {
            gameThread.interrupt();
        }
        if (calc == null) {
            initField(47, 73);
            return;

        }

        /*
         * int rows = 47; int cols = 73; field = new boolean[cols][rows];
         */
        // calc = new GameOfLifeCalculation(field);
        // calc.addObserver(this);

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
    public void generateRandomField(){
        if(gameThread!=null) gameThread.interrupt();

        boolean[][] field=new boolean [cols][rows];
        calc=new GameOfLifeCalculation(field);
        calc.addObserver(this);
        calc.generateRandomField();
        
    }

    @Override
    public void update(Observable o, Object arg) {

        setChanged();
        notifyObservers(arg);

    }

    public void changeStateOfCell(int i, int j) {
        calc.changeStateOfCell(i, j);
        calc.update();

    }

	public void interrupt() {
        
        gameThread.interrupt(); //
        
	}


}
