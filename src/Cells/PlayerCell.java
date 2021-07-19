package Cells;

public class PlayerCell implements Cells.Cell {

    String name;

    public PlayerCell(String name){
        this.name = name;
    }


    @Override
    public String toString() {
        return name.substring(0, 2);
    }
}
