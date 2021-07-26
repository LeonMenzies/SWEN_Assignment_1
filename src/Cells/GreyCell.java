package Cells;

/***
 * The grey cell class represents the grey cells on the board that the players can't move into
 */
public class GreyCell extends Cells.Cell {

    public GreyCell(int row, int col){
        super(row, col);
    }


    @Override
    public String toString() {
        return "GC";
    }
}
