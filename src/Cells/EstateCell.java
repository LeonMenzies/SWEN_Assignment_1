package Cells;

public class EstateCell extends Cells.Cell{

    public boolean isDoor;


    String nameFirst;
    String nameSecond;

    public EstateCell(int row, int col, String name1, String name2, Boolean isDoor){
        super(row, col);
        this.nameFirst = name1;
        this.nameSecond = name2;
        this.isDoor = isDoor;

    }

    public String getName(){
        return  nameFirst + " " + nameSecond;
    }



    @Override
    public String toString() {
        if(isDoor){
            return "__";
        }
        return nameFirst.substring(0, 1) + nameSecond.substring(0, 1);
    }
}
