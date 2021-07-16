import java.util.*;

public class Estate {
    private List<Player> playersInEstate;

    public Estate() {
        playersInEstate = new ArrayList<>();
    }

    public List<Player> getPlayersInEstate() {
        return this.playersInEstate;
    }

    public void addPlayersInEstate(Player aPlayerInEstate) {
        this.playersInEstate.add(aPlayerInEstate);
    }

    public void removePlayersInEstate(Player aPlayerInEstate) {
        this.playersInEstate.remove(aPlayerInEstate);
    }
}