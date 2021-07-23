import Cells.WeaponCell;

public class Weapon extends Move {
    String wepName;

    int row;
    int col;
    private Estate estate;
    public Weapon(String name, int row, int col, Estate estate) {
        super(row, col);

        this.wepName = name;
        this.row = row;
        this.col = col;
        this.estate = estate;
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

    public void setEstate(Estate estate){
        this.estate = estate;
    }
    public Estate getEstate(){
        return this.estate;
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