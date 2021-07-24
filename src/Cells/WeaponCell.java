package Cells;

public class WeaponCell extends Cells.Cell{
    String weaponName;


    public WeaponCell(int row, int col, String weaponName) {
        super(row, col);
        this.weaponName = weaponName;
    }



    @Override
    public String toString() {
        return weaponName.substring(0, 2);
    }
}
