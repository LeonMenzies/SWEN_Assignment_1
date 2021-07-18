import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Game {
    ArrayList<Player> players = new ArrayList<>();
    ArrayList<Card> Deck = new ArrayList<>();
    ArrayList<Card> circumstance = new ArrayList<>();
    CharacterCard who = null;
    EstateCard where = null;
    WeaponCard what = null;

    public Game() {
    }

    public static void main(String[] args) {
        Game game = new Game();
        Board board = new Board();
        game.playerSetUp();
        game.setUpDeck();
        game.generateMurder();
        game.dealCards();
    }

    /**
     * Method to set Up the right amount of players in the game either 3 or 4
     * Right amount of players are then added to the player Array for the game
     */
    public void playerSetUp(){
        //scans in a string from the console
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the number of Players between 3 & 4: ");
        String numPlayers = input.next();

        if(!numPlayers.equals("3") && !numPlayers.equals("4")){
            playerSetUp();
        }
        //players are then added to the array depending on the amount
        players.add(new Player("Bert"));
        players.add(new Player("Percy"));
        players.add(new Player("Lucilla"));
        if(numPlayers.equals("4")){
            players.add(new Player("Malina"));
        }
    }
    /**
     * Method to add all the cards to the game of the correct type
     * shuffles the array after the cards have been added
     */
    public void setUpDeck(){
        this.Deck.add(new CharacterCard("Bert"));
        this.Deck.add(new CharacterCard("Pery"));
        this.Deck.add(new CharacterCard("Lucilla"));
        this.Deck.add(new CharacterCard("Malina"));
        this.Deck.add(new EstateCard("Haunted House"));
        this.Deck.add(new EstateCard("Manic Manor"));
        this.Deck.add(new EstateCard("Villa Celia"));
        this.Deck.add(new EstateCard("Calamity Castle"));
        this.Deck.add(new EstateCard("Peril Palace"));
        this.Deck.add(new WeaponCard("Broom"));
        this.Deck.add(new WeaponCard("Scissors"));
        this.Deck.add(new WeaponCard("Knife"));
        this.Deck.add(new WeaponCard("Shovel"));
        this.Deck.add(new WeaponCard("iPad"));
        Collections.shuffle(this.Deck);
    }
    /**
     * Method to generate the murder circumstances one of each type of card is selected at random
     *
     */
    public void generateMurder(){
        //if array size is 3 enough cards has been selected
        if(circumstance.size() == 3){
            return;
        }
        //grabs a random card from the deck
        Random rand = new Random();

        Card c = this.Deck.get(rand.nextInt(this.Deck.size()));

        //checks what type of card it is if that is currently null then that card is the new circumstance
        //either generate murder is called again until all three cards are chosen
        //cards are then removed from the deck
        if(c instanceof CharacterCard){
            if(this.who == null){
                this.who = (CharacterCard)c;
                this.circumstance.add(this.who);
                this.Deck.remove(this.who);
            }
            generateMurder();
        }
        if(c instanceof EstateCard){
            if(this.where == null){
                this.where = (EstateCard)c;
                this.circumstance.add(this.where);
                this.Deck.remove(this.where);
            }
            generateMurder();
        }
        if(c instanceof WeaponCard){
            if(this.what == null){
                this.what = (WeaponCard) c;
                this.circumstance.add(this.what);
                this.Deck.remove(this.what);
            }
            generateMurder();
        }

    }
    /**
     * Deals the remaining cards (after the murder circumstance as been selected)
     * out randomly amongst the players until there is none left
     *
     */
    public void dealCards(){
        //until the deck is empty loops through the players until there is none left
        while(!this.Deck.isEmpty()){
            for(Player p : this.players){
                if(this.Deck.isEmpty()){
                    break;
                }
                Random rand = new Random();
                Card c = this.Deck.get(rand.nextInt(this.Deck.size()));
                p.addHand(c);
                this.Deck.remove(c);
            }
        }
    }
}