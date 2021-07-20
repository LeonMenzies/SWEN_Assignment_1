import Cells.*;

import java.util.*;
import java.util.Random;

public class Player extends Move implements Cloneable {
    private boolean turn = false;
    private Estate estateIn = null;

    private String name;

    int row;
    int col;

    private List<Cell> visited;

    private Random dice1 = new Random();
    private Random dice2 = new Random();

    private int upperBound = 6;
    private Cell currentCell;

    private int steps = 0;
    private boolean rollStatus = false;
    private boolean isOut = false;
    private boolean hasWon = false;
    private boolean hasGuessed = false;

    private ArrayList<Card> guesses;
    private List<Card> hand;

    public Player(String name, int row, int col) {
        this.name = name;
        this.row = row;
        this.col = col;
        hand = new ArrayList<>();
        guesses = new ArrayList<>();
        currentCell = new FreeCell(row, col);
        visited = new ArrayList<>();
    }

    public String getName(){
        return this.name;
    }

    public int getSteps(){
        return this.steps;
    }

    public void roll(){
        if(turn){
            int d1 = dice1.nextInt(upperBound)+1;
            int d2 = dice2.nextInt(upperBound)+1;
            steps = d1+d2;

        }
    }

    public void setHasWon(boolean b){
        hasWon = b;
    }
    public boolean getHasWon(){
        return hasWon;
    }
    public void setIsout(boolean b){
        isOut = b;
    }

    public boolean getIsOut(){
        return isOut;
    }
    public boolean getRollStatus(){
        return rollStatus;
    }
    public void setRollStatus(boolean b){
        rollStatus = b;
    }

    public boolean getGuessStatus(){
        return hasGuessed;
    }

    public void setGuessStatus(boolean b){
        hasGuessed = b;
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
            cells[row][col] = new FreeCell(row, col);

            visited.add(b.getCell(row, col));


            switch (direction){
                case "W":
                    cells[row - 1][col] = playerCell;
                    row = row - 1;
                    b.setCells(cells);
                    break;

                case "A":
                    cells[row][col - 1] = playerCell;
                    col = col - 1;
                    b.setCells(cells);
                    break;

                case "S":
                    cells[row + 1][col] = playerCell;
                    row = row + 1;
                    b.setCells(cells);
                    break;

                case "D":
                    cells[row][col + 1] = playerCell;
                    col = col + 1;
                    b.setCells(cells);
                    break;

                default:
                    break;
            }
            b.redrawEstates();
        } else {
            System.out.println("Move is not valid");
        }
    }

    @Override
    public boolean isValid(Board b, String direction) {

        int tempRow = row;
        int tempCol = col;

        switch(direction){
            case"W":
                tempRow = tempRow - 1;
                break;
            case"A":
                tempCol--;
                break;
            case"S":
                tempRow++;
                break;
            case"D":
                tempCol++;
                break;
            default:
                break;
        }
        Cell[][] c = b.getCells();

        if(outOfBounds(tempRow, tempCol) || !checkVisited(c[tempRow][tempCol])){
            return false;
        }

        if(c[tempRow][tempCol] != null){
            if(c[tempRow][tempCol] instanceof FreeCell){
                return true;
            }
            if(c[tempRow][tempCol] instanceof EstateCell){
                EstateCell ec = (EstateCell)c[tempRow][tempCol];

                if(ec.isDoor){

                    estateIn = b.getEstate(ec.getName());
                    estateIn.addPlayersInEstate(this, (PlayerCell) c[row][col]);


                    return true;
                }
            }
        }
        return false;
    }

    private boolean outOfBounds(int tempRow, int tempCol) {
        if(tempCol > 23 || tempCol > 23 || tempCol < 0 || tempRow < 0){
            return true;
        }
        return false;
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

    public boolean checkVisited(Cell c){
        if(visited.contains(c)){
            return false;
        }
        return true;
    }

    public void clearVisited() { visited.clear(); }
}