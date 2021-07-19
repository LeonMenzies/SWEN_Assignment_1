import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {
    ArrayList<Player> players = new ArrayList<>();
    ArrayList<Player> tempPlayers = new ArrayList<>();
    ArrayList<Card> Deck = new ArrayList<>();
    ArrayList<Card> circumstance = new ArrayList<>();
    CharacterCard who = null;
    EstateCard where = null;
    WeaponCard what = null;
    boolean gameWon = false;
    Player winner = null;
    Pattern MovePat = Pattern.compile("[RGHFWASDE]");
    Pattern dirPat = Pattern.compile("[WASD]");


    public Game() {
    }

    public static void main(String[] args) {
        Game game = new Game();
        Board board = new Board();
        game.playerSetUp();
        game.setUpDeck();
        game.generateMurder();
        game.dealCards();
        game.playGame(board);
    }

    public void playGame(Board board){

       while(!gameWon){
           for(Player p: players){

              playersTurn(p);

           }

       }

    }

    public void playersTurn(Player p){
        Scanner input = new Scanner(System.in);
        String in;
        if(!p.getTurn()) {
            System.out.println("It is "+p.getName()+"'s turn please make sure they have the tablet and enter any key to continue: ");
            in = input.next();
            p.setTurn(true);

        }
        System.out.println(p.getName() +" has "+p.getSteps()+" number of steps");
        if(!p.getRollStatus()) {
            System.out.println("R(Roll), H(Show Hand), G(guess), F(Final Guess), E(END TURN) or WASD(Move)");
        }else{
            System.out.println("H(Show Hand), G(guess), F(Final Guess), E(END TURN) or WASD(Move)");
        }
        in = input.next();
        in = checkInput(in);
        if(in.equals("R")&& !p.getRollStatus()){
            p.roll();
            playersTurn(p);
        }else if(in.equals("R")&& p.getRollStatus()){
            System.out.println("Already Rolled");
            playersTurn(p);
        }
        Matcher matcher = dirPat.matcher(in);
        boolean matchFound = matcher.matches();
        if(matchFound && p.getSteps() != 0){

            //playermove method needs to be implementede
            playersTurn(p);
        }else if(matchFound && p.getSteps() == 0){
            System.out.println("You are out of steps or please roll");
            playersTurn(p);
        }

        if(in.equals("G")) {
            refuteOrder(p);
        }
        if(in.equals("E")){
            p.setTurn(false);
            clearScreen();
            return;
        }
    }

    public void refuteOrder(Player p){
      for(Player p1 : tempPlayers){
          System.out.println(p1.getName());
      }


    }

    /**
     *Checks to make sure the player has entered a correct move key
     */
    public String checkInput(String in){
        Scanner input = new Scanner(System.in);
        Matcher matcher = MovePat.matcher(in);
        boolean matchFound = matcher.matches();
       if(matchFound){
           return in;
       }else{
           System.out.println("Please enter a Valid Move");
           in = input.next();
           checkInput(in);
       }
        return in;
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
        players.add(new Player("Lucilla"));
        players.add(new Player("Bert"));
        players.add(new Player("Malina"));


        if(numPlayers.equals("4")){
            players.add(new Player("Percy"));
        }
        tempPlayers = players;
    }
    /**
     * Method to add all the cards to the game of the correct type
     * shuffles the array after the cards have been added
     */
    public void setUpDeck(){
        this.Deck.add(new CharacterCard("Bert"));
        this.Deck.add(new CharacterCard("Percy"));
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
        //randoms the order of the players so undecided who gets extra cards and who starts
        Collections.shuffle(players);
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
    public static void clearScreen() {

        System.out.print("\033[H\033[2J");
        System.out.flush();

    }



}