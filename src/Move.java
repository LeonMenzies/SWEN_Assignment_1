/*
 * The Move class is an abstract class used by the Player class to be able to see whether or not a move isValid or not
 */
public abstract class Move {

    int row, col;

    public Move(int row, int col){
        this.row = row;
        this.col = col;
    }

    public abstract boolean isValid(Board b, String direction);


    public void setLocation(int row, int col){}

}