package Cells;

/***
 * The player cell represents each player on the board, this is just an extension of cell with a name for the player
 */
public class PlayerCell extends Cells.Cell {

    String name;

    public PlayerCell(int row, int col, String name) {
        super(row, col);
        this.name = name;
    }

    /**
     * To string for getting the player cell initials for display
     *
     * @return the players initials
     */
    @Override
    public String toString() {
        return name.substring(0, 2);
    }
}
