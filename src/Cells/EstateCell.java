package Cells;

public class EstateCell implements Cells.Cell {

    public boolean isDoor;

    String nameFirst;
    String nameSecond;

    public EstateCell(String name1, String name2, Boolean isDoor){
        this.nameFirst = name1;
        this.nameSecond = name2;
        this.isDoor = isDoor;
    }


    @Override
    public String toString() {
        return nameFirst.substring(0, 1) + nameSecond.substring(0, 1);
    }
}
