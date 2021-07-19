import java.util.*;
import java.util.Random;



public class Player extends Move implements Cloneable {
    private boolean turn = false;
    private String name;

    int row;
    int col;
  //  private ArrayList<Cells.Cell> visted;
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