package gameoflife;

import java.util.Observable;

public class GameOfLifeCalculation extends Observable implements Runnable {

    private boolean[][] field;

    public GameOfLifeCalculation() {
        int cols = (int) (Math.random() * (100 - 10 + 1)) + 10;
        int rows = (int) (Math.random() * (100 - 10 + 1)) + 10;

        field = new boolean[rows][cols];

        for (int j = 0; j < field.length; j++) {
            for (int k = 0; k < field[j].length; k++) {

                int isLiving = (int) ((Math.random() * 2));

                if (isLiving == 0) {
                    field[j][k] = false;
                } else {
                    field[j][k] = true;
                }

            }
        }

    }

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {

            for (int j = 1; j < field.length - 1; j++) {
                for (int k = 1; k < field[j].length - 1; k++) {

                    int aliveNeighbours = 0;
                    for (int i = -1; i <= 1; i++) {
                        for (int m = -1; m <= 1; m++) {
                            if (field[j + i][k + m]) {
                                aliveNeighbours++;
                            }
                        }
                    }
                    if (field[j][k]) {
                        aliveNeighbours--;
                    }

                    if (field[j][k] && aliveNeighbours < 2) {
                        field[j][k] = false;
                    } else if (field[j][k] && aliveNeighbours > 3) {
                        field[j][k] = false;
                    } else if (!field[j][k] && aliveNeighbours == 3) {
                        field[j][k] = true;
                    }

                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {

            }

            setChanged();
            notifyObservers(field);

        }

    }

}
