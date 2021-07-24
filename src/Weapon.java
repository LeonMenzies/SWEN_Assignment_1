

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