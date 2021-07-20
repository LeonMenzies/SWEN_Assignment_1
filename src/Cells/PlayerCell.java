package Cells;

public class PlayerCell extends Cells.Cell {

    String name;

    public PlayerCell(int row, int col, String name){
        super(row, col);
        this.name = name;
    }


    @Override
    public String toString() {
        return name.substring(0, 2);
    }
}
