import Cells.Cell;
import Cells.EstateCell;
import Cells.PlayerCell;

import java.util.*;

public class Estate {
    public String estateName;

    private List<Player> playersInEstate;

    List<Cell> estateCellList;

    public Estate(String estateName) {
        playersInEstate = new ArrayList<>();
        this.estateName = estateName;
        estateCellList = new ArrayList<Cell>();
    }


    public void addCell(EstateCell c){
        this.estateCellList.add(c);
    }

    public void redrawEstate(Board b){
        for(Cell c : estateCellList){
            b.redrawCell(c.getRow(), c.getCol(), c);
        }
    }

    public List<Player> getPlayersInEstate() {
        return this.playersInEstate;
    }

    public void addPlayersInEstate(Player aPlayerInEstate, PlayerCell pc) {

        Cell c = null;
        while(c instanceof EstateCell == false){
            c = estateCellList.remove((int) ((Math.random() * estateCellList.size())));
            pc.setCoords(c.getCol(), c.getRow());
        }

        estateCellList.add(pc);

        this.playersInEstate.add(aPlayerInEstate);
    }

    public void removePlayersInEstate(Player aPlayerInEstate) {
        this.playersInEstate.remove(aPlayerInEstate);
    }
}