import Cells.*;

import java.util.*;
import java.util.Random;

public class Player extends Move implements Cloneable {
    private boolean turn = false;
    private String name;

    int row;
    int col;

    //private ArrayList<Cells.Cell> visited;
    private Map<Integer, Integer> visited = new HashMap<>();

    private Random dice1 = new Random();
    private Random dice2 = new Random();

    private int upperBound = 7;
    private int steps = 0;
    private boolean rollStatus = false;
    private boolean isOut = false;

    private ArrayList<Card> guesses;
    private List<Card> hand;

    public Player(String name, int row, int col) {
        this.name = name;
        this.row = row;
        this.col = col;
        hand = new ArrayList<>();
        guesses = new ArrayList<>();
    }

    public String getName(){
        return this.name;
    }

    public int getSteps(){
        return this.steps;
    }

    public void roll(){
        if(turn){
            int d1 = dice1.nextInt(upperBound);
            int d2 = dice2.nextInt(upperBound);
            steps = d1+d2;
            rollStatus = true;
        }
    }

    public boolean getIsOut(){
        return isOut;
    }
    public boolean getRollStatus(){
        return rollStatus;
    }


    public void printHand(){
        System.out.println(this.name +"'s" +" current Hand: ");
        for(int i = 0; i < hand.size(); i++){
            System.out.println(i+": "+hand.get(i).name);
        }
    }

    @Override
    public Player clone(){
        Player p = new Player(this.name, this.row, this.col);
        for(Card c: this.hand){
            p.hand.add(c.clone());
        }
        return p;
    }

    public void move(Board b, String direction){
        if(isValid(b, direction)){
            steps--;

            Cell[][] cells = b.getCells();
            PlayerCell playerCell = (PlayerCell) cells[row][col];
            cells[row][col] = new FreeCell();

            switch (direction){
                case "W":
                    cells[row - 1][col] = playerCell;
                    row = row - 1;
                    visited.put(row, col);
                    b.setCells(cells);
                    break;

                case "A":
                    cells[row][col - 1] = playerCell;
                    col = col - 1;
                    visited.put(row, col);
                    b.setCells(cells);
                    break;

                case "S":
                    cells[row + 1][col] = playerCell;
                    row = row + 1;
                    visited.put(row, col);
                    b.setCells(cells);
                    break;

                case "D":
                    cells[row][col + 1] = playerCell;
                    col = col + 1;
                    visited.put(row, col);
                    b.setCells(cells);
                    break;

                default:
                    break;
            }
        } else {
            System.out.println("Move is not valid");
        }
    }

    @Override
    public boolean isValid(Board b, String direction) {
        Cell[][] cells = b.getCells();

        switch (direction) {
            case "W":
                if(row > 0) {
                    return cells[row - 1][col] instanceof FreeCell;
                }

            case "A":
                if(col > 0) {
                    return cells[row][col - 1] instanceof FreeCell;
                }

            case "S":
                if(col < 24) {
                    return cells[row][col + 1] instanceof FreeCell;
                }

            case "D":
                if(row < 24) {
                    return cells[row + 1][col] instanceof FreeCell;
                }

            default:
                return false;
        }
    }

    public void setTurn(boolean aTurn) {
        this.turn = aTurn;
    }

    public boolean getTurn() {
        return this.turn;
    }

    public List<Card> getGuess(){
        return this.guesses;
    }

    public List<Card> getHand() {
        return this.hand;
    }

    public void addGuess(Card c){
        this.guesses.add(c);
    }

    public void clearGuess(){
        this.guesses.clear();
    }

    public void addHand(Card card) {
        this.hand.add(card);
    }

    public void removeHand(Card card) {
        this.hand.remove(card);
    }

    public String toString() {
        return super.toString() + "[" + "turn" + ":" + getTurn() + "]";
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}