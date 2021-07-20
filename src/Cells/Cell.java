package Cells;

public abstract class Cell {
    int row, col;


    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }


    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setCoords(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
