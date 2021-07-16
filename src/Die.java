import java.util.*;

public class Die {
    private int value;

    private List<Player> players;

    public Die(int aValue) {
        value = aValue;
        players = new ArrayList<>();
    }

    public void setValue(int aValue) {
        this.value = aValue;
    }

    public int getValue() {
        return this.value;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
    }

    public int genValue() {
        return 0;
    }

    public String toString() {
        return super.toString() + "[" + "value" + ":" + getValue() + "]";
    }
}