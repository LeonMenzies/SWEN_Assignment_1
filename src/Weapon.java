import Cells.WeaponCell;

public class Weapon extends Move {
    String wepName;

    int row;
    int col;

    public Weapon(String name, int row, int col) {
        super();
        this.wepName = name;
        this.row = row;
        this.col = col;
    }

    public void move(Board b, String destination){
        if(isValid(b, destination)){

        }
    }

    @Override
    public boolean isValid(Board b, String destination) {
        Estate destinationEstate = b.getEstate(destination);
        WeaponCell wc = (WeaponCell) b.getCell(row, col);

        //destinationEstate.addWeaponInEstate(this, wc);
        return false;
    }

    public String getWepName() {
        return wepName;
    }

    public void setWepName(String wepName) {
        this.wepName = wepName;
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
}