package gameoflife;


enum LiveState {
    LIVING, DEAD
}

public class Cell implements Comparable<Cell> {

    private int row, col;
    private LiveState state;

    public Cell(int row, int col, LiveState state) {
        this.setState(state);
        this.row = row;
        this.col = col;

    }

    public LiveState getState() {
        return state;
    }

    public void setState(LiveState state) {
        this.state = state;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public int compareTo(Cell o) {
        if (row == o.getRow() && col == o.getCol()) {
            return 0;
        }
        return -1;

    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Cell))
            return false;

        Cell c = (Cell) obj;
        return c.getRow() == row && c.getCol() == col;

    }

}