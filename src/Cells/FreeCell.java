package Cells;

/***
 * The free cell class represents the free cells on the board that the players can move into
 */
public class FreeCell extends Cells.Cell {
    public FreeCell(int row, int col){
        super(row, col);
    }


    @Override
    public String toString() {
        return "__";
    }
}
