public abstract class Move {
    //public abstract void apply(Board b);
    public abstract boolean isValid(Board b, String direction);

    public void setLocation(int row, int col){}

}