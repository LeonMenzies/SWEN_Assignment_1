import java.util.*;

public class Board {
    private final List<Player> players;
    private final List<Estate> estates;
    private final List<Weapon> weapons;

    public Board() {
        players = new ArrayList<>();
        estates = new ArrayList<>();
        weapons = new ArrayList<>();
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public List<Estate> getEstates() {
        return this.estates;
    }

    public List<Weapon> getWeapons() {
        return this.weapons;
    }

    public void addPlayer(Player aPlayer) {
        this.players.add(aPlayer);
    }

    public void removePlayer(Player aPlayer) {
        this.players.remove(aPlayer);
    }

    public void addEstate(Estate aEstate) {
        this.estates.add(aEstate);
    }

    public void removeEstate(Estate aEstate) {
        this.estates.remove(aEstate);
    }

    public void addWeapon(Weapon aWeapon) {
        this.weapons.add(aWeapon);
    }

    public void removeWeapon(Weapon aWeapon) {
        this.weapons.remove(aWeapon);
    }

    public void delete() {
        players.clear();
        estates.clear();
        weapons.clear();
    }

    public void apply(Move m) {

    }

    public void randomWeapRooms() {

    }
}