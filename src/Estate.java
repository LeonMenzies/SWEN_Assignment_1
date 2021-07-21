import Cells.Cell;
import Cells.EstateCell;
import Cells.PlayerCell;

import java.util.*;

/***
 * The estate class stores the information about the cells the estate is made up of as well as the players and weapons contained in its fields
 */
public class Estate {
    private String estateName;
    private List<Player> playersInEstate;
    private List<Cell> estateCellList;
    private List<PlayerCell> pcInEstate;
    private List<Integer> availableCells;
    private Map<String, Cell> exitCells;


    public Estate(String estateName, List<Integer> availableCells) {
        this.playersInEstate = new ArrayList<>();
        this.estateName = estateName;
        this.estateCellList = new ArrayList<>();
        this.exitCells = new HashMap<>();
        this.playersInEstate = new ArrayList<>();
        this.pcInEstate = new ArrayList<>();
        this.availableCells = availableCells;
    }

    /**
     * Add a cell that makes up the estate on the baord
     *
     * @param c the cell to add to the arraylist of estate cells
     */
    public void addCell(EstateCell c) {
        this.estateCellList.add(c);
    }

    /**
     * Redraw the estate on the board to update it when players enter or exit the estate
     *
     * @param b the board to redraw on
     */
    public void redrawEstate(Board b) {
        int getCell = availableCells.get(playersInEstate.size());

        for (Cell c : estateCellList) {
            b.redrawCell(c.getRow(), c.getCol(), c);
        }

        //Special method for picking the right location to redraw the players in the estate
        for (PlayerCell pc : pcInEstate) {
            b.redrawCell(estateCellList.get(getCell).getRow(), estateCellList.get(getCell).getCol(), pc);
        }
    }

    /**
     * Add exit cells so weh a player exits the estate knows where to put the player
     *
     * @param exitCell  the exit CEll itself
     * @param direction the direction a player must go to get to the exit cell
     */
    public void addExitCell(Cell exitCell, String direction) {
        this.exitCells.put(direction, exitCell);
    }

    /**
     * Add a player object to this estate asw ell as the player cell for drawing
     *
     * @param aPlayerInEstate the player object to be added
     * @param pc              the Cell for redrawing in the board
     */
    public void addPlayersInEstate(Player aPlayerInEstate, PlayerCell pc) {
        this.pcInEstate.add(pc);
        this.playersInEstate.add(aPlayerInEstate);
    }

    /**
     * When a player tries to exit this method checks weather the given direction is valid
     *
     * @param direction direction the player wants to go
     * @return the cell the player is going to
     */
    public Cell containsExit(String direction) {
        if (exitCells.containsKey(direction)) {
            return exitCells.get(direction);
        }
        return null;
    }

    /**
     * Remove a player from this estate object
     *
     * @param aPlayerInEstate the player to be removed
     */
    public void removePlayersInEstate(Player aPlayerInEstate) {
        this.playersInEstate.remove(aPlayerInEstate);

        Cell toRemove = null;

        //Find the player in the list of estate cells and remove it
        for (Cell c : pcInEstate) {
            if (c.toString().equals(aPlayerInEstate.toString())) {
                toRemove = c;
            }
        }
        if (toRemove != null) {
            this.pcInEstate.remove(toRemove);
        }
    }
}