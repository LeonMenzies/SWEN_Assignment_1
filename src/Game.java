import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    ArrayList<Player> players = new ArrayList<>();
    ArrayList<Card> Deck = new ArrayList<>();
    public Game() {
    }

    public static void main(String[] args) {
        Game game = new Game();
        Board board = new Board();
        game.playerSetUp();
        game.setUpDeck();
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
     * Method to add all the cards to the game of the correct
     */
    public void setUpDeck(){
        Deck.add(new CharacterCard("Bert"));
        Deck.add(new CharacterCard("Pery"));
        Deck.add(new CharacterCard("Lucilla"));
        Deck.add(new CharacterCard("Malina"));
        Deck.add(new EstateCard("Haunted House"));
        Deck.add(new EstateCard("Manic Manor"));
        Deck.add(new EstateCard("Villa Celia"));
        Deck.add(new EstateCard("Calamity Castle"));
        Deck.add(new EstateCard("Peril Palace"));
        Deck.add(new WeaponCard("Broom"));
        Deck.add(new WeaponCard("Scissors"));
        Deck.add(new WeaponCard("Knife"));
        Deck.add(new WeaponCard("Shovel"));
        Deck.add(new WeaponCard("iPad"));
    }
}