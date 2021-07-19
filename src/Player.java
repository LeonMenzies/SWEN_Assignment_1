import java.util.*;
import java.util.Random;



public class Player extends Move {
    private boolean turn = false;
    private String name;
  //  private ArrayList<Cell> visted;
    private Random dice1 = new Random();
    private Random dice2 = new Random();
    private int upperBound = 7;
    private int steps = 0;
    boolean rollStatus = false;


    private List<Card> hand;


    public Player(String name) {
        this.name = name;
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


    public boolean getRollStatus(){
        return rollStatus;
    }

    public static void main(String[] args){
        Player p = new Player("Bob");
        p.roll();
        p.roll();

    }
    /**
     * Deals the remaining cards out randomly amongst the players until there is none left
     *
     */
    public void printHand(){

        System.out.println(this.name +"'s" +" current Hand: ");
        for(int i = 0; i < hand.size(); i++){
            System.out.println(i+": "+hand.get(i).name);
        }

    }
    public void setTurn(boolean aTurn) {
        this.turn = aTurn;
    }

    public boolean getTurn() {
        return this.turn;
    }

    public List<Card> getHand() {
        return this.hand;
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
}