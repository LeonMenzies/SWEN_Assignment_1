import Cells.*;

import java.util.*;

public class Board {

    Map<String, Estate> estates = new HashMap<>();


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


    public Board(int width, int height){
        cells = new Cell[width][height];
        estates.put("Haunted Door", new Estate("Haunted House"));
        estates.put("Manic Door", new Estate("Manic Manor"));
        estates.put("Peril Door", new Estate("Peril Palace"));
        estates.put("Calamity Door", new Estate("Calamity Castle"));
        estates.put("Villa Door", new Estate("Villa Celia"));
    }

    public void redrawEstates(){
        for(Map.Entry<String, Estate> mp : estates.entrySet()){
            mp.getValue().redrawEstate(this);
        }
    }

    public void setup() {

        Scanner sc = new Scanner(boardCells).useDelimiter("\\|");

        int row = 0;
        int col = 0;

        while(sc.hasNext()){

            switch(sc.next()) {
                case "__":
                    cells[row][col++] = new FreeCell(row, col);
                    break;
                case "GC":
                    cells[row][col++] = new GreyCell(row, col);
                    break;
                case "CC":
                    EstateCell cc = new EstateCell(row, col,"Calamity", "Castle", false);

                    Estate e = estates.get("Calamity Door");
                    e.addCell(cc);

                    cells[row][col++] = cc;
                    estates.get("Calamity Door").addCell(cc);
                    break;
                case "CD":
                    EstateCell cd = new EstateCell(row, col,"Calamity", "Door", true);

                    cells[row][col++] = cd;
                    estates.get("Calamity Door").addCell(cd);
                    break;
                case "PP":
                    EstateCell pp = new EstateCell(row, col,"Peril", "Palace", false);

                    cells[row][col++] = pp;
                    estates.get("Peril Door").addCell(pp);
                    break;
                case "PD":
                    EstateCell pd = new EstateCell(row, col,"Peril", "Door", true);

                    cells[row][col++] = pd;
                    estates.get("Peril Door").addCell(pd);
                    break;
                case "MM":
                    EstateCell mm = new EstateCell(row, col,"Manic", "Manor", false);

                    cells[row][col++] = mm;
                    estates.get("Manic Door").addCell(mm);
                    break;
                case "MD":
                    EstateCell md = new EstateCell(row, col,"Manic", "Door", true);

                    cells[row][col++] = md;
                    estates.get("Manic Door").addCell(md);
                    break;
                case "HH":
                    EstateCell hh = new EstateCell(row, col,"Haunted", "House", false);

                    cells[row][col++] = hh;
                    estates.get("Haunted Door").addCell(hh);
                    break;
                case "HD":
                    EstateCell hd = new EstateCell(row, col,"Haunted", "Door", true);

                    cells[row][col++] = hd;
                    estates.get("Haunted Door").addCell(hd);
                    break;
                case "VC":
                    EstateCell vc = new EstateCell(row, col,"Villa", "Celia", false);

                    cells[row][col++] = vc;
                    estates.get("Villa Door").addCell(vc);
                    break;
                case "VD":
                    EstateCell vd = new EstateCell(row, col,"Villa", "Door", true);

                    cells[row][col++] = vd;
                    estates.get("Villa Door").addCell(vd);
                    break;
                default:
                    row++;
                    col = 0;
            }
        }
    }

    public void setPlayer(Player p){
        cells[p.getRow()][p.getCol()] = new PlayerCell(p.getRow(), p.getCol(), p.getName());
    }

    public Estate getEstate(String name){
        return estates.get(name);
    }

    public Cell getCell(int row, int col){
        return cells[row][col];
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

    public void redrawCell(int row, int col, Cell c){
        cells[row][col] = c;
    }

    public Cell[][] getCells(){
        return cells;
    }

    public void setCells(Cell[][] cells){
        this.cells = cells;
    }
}