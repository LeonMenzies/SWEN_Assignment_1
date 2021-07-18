import java.util.*;



public class Player extends Move {
    private boolean turn = false;
    private String name;
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