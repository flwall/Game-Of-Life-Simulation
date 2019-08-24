package gameoflife;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class GameOfLifeCalculation extends Observable implements Runnable {

    private boolean[][] field;
    List<Cell> changedCells = new LinkedList<>();

    public GameOfLifeCalculation(boolean[][] f) {

        // int cols = (int) (Math.random() * (100 - 10 + 1)) + 10;
        // int rows = (int) (Math.random() * (100 - 10 + 1)) + 10;

        this.field = f;

    }

    public void generateRandomField() {
        for (int j = 0; j < field.length; j++) {
            for (int k = 0; k < field[j].length; k++) {

                double isLiving = ((Math.random() * 2));

                if (isLiving > 0.25d) {
                    changeStateOfCell(j, k, false);
                } else {
                    changeStateOfCell(j, k, true);
                }

            }
        }
        update();
    }

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {

            nextGen();

            /*
             * try { Thread.sleep(100);
             * 
             * } catch (InterruptedException e) { break; }
             */

            update();

        }

    }

    public void nextGen() {
        boolean[][] tmp = copyArray(field);

        for (int j = 1; j < tmp.length - 1; j++) {
            for (int k = 1; k < tmp[j].length - 1; k++) {

                int aliveNeighbours = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int m = -1; m <= 1; m++) {
                        if (tmp[j + i][k + m]) {
                            aliveNeighbours++;
                        }
                    }
                }
                if (tmp[j][k]) {
                    aliveNeighbours--;
                }

                if (tmp[j][k] && aliveNeighbours < 2) {
                    changeStateOfCell(j, k, false);
                } else if (tmp[j][k] && aliveNeighbours > 3) {
                    changeStateOfCell(j, k, false);

                } else if (!tmp[j][k] && aliveNeighbours == 3) {
                    changeStateOfCell(j, k, true);

                }

            }
        }

    }

    private boolean[][] copyArray(boolean[][] field2) {
        boolean[][] tmp = new boolean[field2.length][field2[0].length];

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                tmp[i][j] = field[i][j];
            }
        }
        return tmp;
    }

    private void changeStateOfCell(int i, int j, boolean isLiving) {
        this.field[i][j] = isLiving;

        LiveState state = null;
        if (isLiving) {
            state = LiveState.LIVING;
        } else
            state = LiveState.DEAD;

        Cell c = new Cell(i, j, state);
        if (changedCells.contains(c)) {
            changedCells.get(changedCells.indexOf(c)).setState(state);
        } else {
            changedCells.add(c);
        }

    }

    public void changeStateOfCell(int i, int j) {
        field[i][j] = !field[i][j];
        changeStateOfCell(i, j, field[i][j]);

    }

    public void update() {
        setChanged();
        notifyObservers(new LinkedList<>(changedCells));
        changedCells.clear();

    }

}
