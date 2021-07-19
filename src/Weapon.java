public class Weapon extends Move {
    public Weapon() {
        super();
    }

    @Override
    public boolean isValid(Board b, String direction) {
        return false;
    }
}