/***
 * The Weapon class represents Weapons on the Board, not the WeaponCell but the actual Weapon object
 * It stores information about the Weapon such as Name, Row and Column of where it is on the board and which Estate it is in
 */
public class Weapon  {
    String wepName;

    int row;
    int col;
    private Estate estate;

    public Weapon(String name, int row, int col, Estate estate) {
        this.wepName = name;
        this.row = row;
        this.col = col;
        this.estate = estate;
    }

    /*
     * Getters and Setters
     */
    public void setEstate(Estate estate){
        this.estate = estate;
    }
    public Estate getEstate(){
        return this.estate;
    }
    public String getWepName() {
        return wepName;
    }

    @Override
    public String toString(){
        return wepName.substring(0, 2);
    }
}