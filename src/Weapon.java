public class Weapon extends Move {
    String wepName;
    public Weapon(String name) {
        super();
        this.wepName = name;
    }



    @Override
    public boolean isValid(Board b, String direction) {
        return false;
    }


}