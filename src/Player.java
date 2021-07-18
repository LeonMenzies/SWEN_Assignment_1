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

    private List<Card> hand;
   // private List<Estate> estates;
  //  private List<Die> dies;

    public Player(String name) {
     //   super();
        this.name = name;
        hand = new ArrayList<>();
       // estates = new ArrayList<>();
       // dies = new ArrayList<>();
    }


    public void roll(){
        if(turn){
            int d1 = dice1.nextInt(upperBound);
            int d2 = dice2.nextInt(upperBound);
            steps = d1+d2;
            System.out.println(steps);
        }

    }

    public static void main(String[] args){
        Player p = new Player("Bob");
        p.roll();
        p.roll();

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

//    public List<Estate> getEstates() {
//        return this.estates;
//    }

//    public List<Die> getDies() {
//        return this.dies;
//    }
    public void addHand(Card card) {
        this.hand.add(card);
    }

    public void removeHand(Card card) {
        this.hand.remove(card);
    }

//    public void addEstate(Estate aEstate) {
//        this.estates.add(aEstate);
//    }
//
//    public void removeEstate(Estate aEstate) {
//        this.estates.remove(aEstate);
//    }
//
//    public void addDy(Die aDy) {
//        this.dies.add(aDy);
//    }
//
//    public void removeDy(Die aDy) {
//        this.dies.remove(aDy);
//    }

    public String toString() {
        return super.toString() + "[" + "turn" + ":" + getTurn() + "]";
    }
}