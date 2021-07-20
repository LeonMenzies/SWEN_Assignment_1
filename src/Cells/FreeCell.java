package Cells;

public class FreeCell extends Cells.Cell {
    public FreeCell(int row, int col){
        super(row, col);
    }


    @Override
    public String toString() {
        return "__";
    }
}
