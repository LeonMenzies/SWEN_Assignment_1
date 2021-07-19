import Cells.*;

import java.util.*;

public class Board {


    Cell[][] cells;

    // @formatter:off
    String boardCells =
            "|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|\n" +
            "|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|\n" +
            "|__|__|HH|HH|HH|HH|HH|__|__|__|__|__|__|__|__|__|__|MM|MM|MM|MM|MM|__|__|\n" +
            "|__|__|HH|HH|HH|HH|HD|__|__|__|__|__|__|__|__|__|__|MM|MM|MM|MM|MM|__|__|\n" +
            "|__|__|HH|HH|HH|HH|HH|__|__|__|__|__|__|__|__|__|__|MM|MM|MM|MM|MM|__|__|\n" +
            "|__|__|HH|HH|HH|HH|HH|__|__|__|__|GC|GC|__|__|__|__|MD|MM|MM|MM|MM|__|__|\n" +
            "|__|__|HH|HH|HH|HD|HH|__|__|__|__|GC|GC|__|__|__|__|MM|MM|MM|MD|MM|__|__|\n" +
            "|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|\n" +
            "|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|\n" +
            "|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|\n" +
            "|__|__|__|__|__|__|__|__|__|VC|VC|VC|VD|VC|VC|__|__|__|__|__|__|__|__|__|\n" +
            "|__|__|__|__|__|GC|GC|__|__|VC|VC|VC|VC|VC|VD|__|__|GC|GC|__|__|__|__|__|\n" +
            "|__|__|__|__|__|GC|GC|__|__|VD|VC|VC|VC|VC|VC|__|__|GC|GC|__|__|__|__|__|\n" +
            "|__|__|__|__|__|__|__|__|__|VC|VC|VD|VC|VC|VC|__|__|__|__|__|__|__|__|__|\n" +
            "|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|\n" +
            "|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|\n" +
            "|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|\n" +
            "|__|__|CC|CD|CC|CC|CC|__|__|__|__|GC|GC|__|__|__|__|PP|PD|PP|PP|PP|__|__|\n" +
            "|__|__|CC|CC|CC|CC|CD|__|__|__|__|GC|GC|__|__|__|__|PP|PP|PP|PP|PP|__|__|\n" +
            "|__|__|CC|CC|CC|CC|CC|__|__|__|__|__|__|__|__|__|__|PP|PP|PP|PP|PP|__|__|\n" +
            "|__|__|CC|CC|CC|CC|CC|__|__|__|__|__|__|__|__|__|__|PD|PP|PP|PP|PP|__|__|\n" +
            "|__|__|CC|CC|CC|CC|CC|__|__|__|__|__|__|__|__|__|__|PP|PP|PP|PP|PP|__|__|\n" +
            "|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|\n" +
            "|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|\n";
    // @formatter:on

    private final List<Player> players;
    private final List<Estate> estates;
    private final List<Weapon> weapons;

    public Board(int width, int height){
        cells = new Cell[width][height];

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


    public void setup() {

        Scanner sc = new Scanner(boardCells).useDelimiter("\\|");

        int row = 0;
        int col = 0;

        while(sc.hasNext()){

            switch(sc.next()) {
                case "__":
                    cells[row][col++] = new FreeCell();
                    break;
                case "GC":
                    cells[row][col++] = new GreyCell();
                    break;
                case "CC":
                    cells[row][col++] = new EstateCell("Calamity", "Castle", false);
                    break;
                case "CD":
                    cells[row][col++] = new EstateCell("Calamity", "Door", true);
                    break;
                case "PP":
                    cells[row][col++] = new EstateCell("Peril", "Palace", false);
                    break;
                case "PD":
                    cells[row][col++] = new EstateCell("Peril", "Door", true);
                    break;
                case "MM":
                    cells[row][col++] = new EstateCell("Manic", "Manor", false);
                    break;
                case "MD":
                    cells[row][col++] = new EstateCell("Manic", "Door", true);
                    break;
                case "HH":
                    cells[row][col++] = new EstateCell("Haunted", "House", false);
                    break;
                case "HD":
                    cells[row][col++] = new EstateCell("Haunted", "Door", true);
                    break;
                case "VC":
                    cells[row][col++] = new EstateCell("Villa", "Celia", false);
                    break;
                case "VD":
                    cells[row][col++] = new EstateCell("Villa", "Door", true);
                    break;
                default:
                    row++;
                    col = 0;
            }
        }
    }

    public void setPlayer(Player p){
        cells[p.getRow()][p.getCol()] = new PlayerCell(p.getName());
    }


    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        for(Cell[] c1 : cells) {

            for(Cell c2 : c1){
                sb.append("|");
                sb.append(c2.toString());
            }
            sb.append("|");
            sb.append("\n");
        }
        return sb.toString();
    }

    public Cell[][] getCells(){
        return cells;
    }

    public void setCells(Cell[][] cells){
        this.cells = cells;
    }
}