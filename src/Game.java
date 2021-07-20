import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {
    ArrayList<Player> players = new ArrayList<>();
    ArrayList<Player> tempPlayers = new ArrayList<>();
    ArrayList<Card> deck = new ArrayList<>();
    ArrayList<Card> tempDeck = new ArrayList<>();
    ArrayList<Card> circumstance = new ArrayList<>();
    ArrayList<Card> refuteCards = new ArrayList<>();
    CharacterCard who = null;
    EstateCard where = null;
    WeaponCard what = null;
    boolean gameWon = false;
    Player winner = null;
    Pattern MovePat = Pattern.compile("[RGHFWASDE]");
    Pattern dirPat = Pattern.compile("[WASD]");

    Map<String, Estate> estates = new HashMap<>();


    private static Board board;


    public Game() {
    }

    public static void main(String[] args) {
        Game game = new Game();
        board = new Board(24, 24);
        board.setup();
        game.playerSetUp();
        game.setUpDeck();
        game.generateMurder();
        game.dealCards();
        game.playGame(board);
        game.estateSetup();
    }

    private void estateSetup() {
        estates.put("HH", new Estate("Haunted House"));
        estates.put("MM", new Estate("Mani Manor"));
        estates.put("PP", new Estate("Peril Palace"));
        estates.put("CC", new Estate("Calamity Castle"));
        estates.put("VC", new Estate("Vila Celia"));
    }

    public void playGame(Board board) {
        Random rand = new Random();
        int i = rand.nextInt(players.size());

        Player p = players.get(i);

        generateStartingOrder(p);
        while (!gameWon) {
            int count = 0;

            for (Player player : players) {
                if (!player.getIsOut()) {
                    gameWon = playersTurn(player);
                    if(gameWon){
                        break;
                    }

                }

                if(player.getIsOut()){
                    count++;
                }
            }
            if (count == players.size()) {
                break;
            }

        }
        if(gameWon){
            System.out.println("The winner is " + winner.getName()+ "!");

        }else {
            System.out.println("All Play's lost game is over!");
        }



    }

    /**
     * Allows the player to play game via a serious of inputs into the console
     */

    public boolean playersTurn(Player p) {


        Scanner input = new Scanner(System.in);
        String in;
        if (!p.getTurn()) {
            System.out.print("It is " + p.getName() + "'s turn please make sure they have the tablet and enter any key to continue: ");
            input.next();
            p.setTurn(true);
            p.setRollStatus(false);
            p.setGuessStatus(false);
            //p.clearVisted();

        }
        //Display the board before the players turn
        System.out.println(board);
        System.out.println(p.getName() + " has " + p.getSteps() + " number of steps");
        if (!p.getRollStatus()) {
            System.out.println("R(Roll), H(Show Hand), G(guess), F(Final Guess), E(END TURN) or WASD(Move)");
        } else {
            System.out.println("H(Show Hand), G(guess), F(Final Guess), E(END TURN) or WASD(Move)");
        }
        in = input.next().toUpperCase(Locale.ROOT);
        in = checkInput(in);
        if (in.equals("R") && !p.getRollStatus()) {
            p.roll();
            p.setRollStatus(true);
            playersTurn(p);
        } else if (in.equals("R") && p.getRollStatus()) {
            System.out.println("Already Rolled");
            playersTurn(p);

        }
        Matcher matcher = dirPat.matcher(in);
        boolean matchFound = matcher.matches();
        if (matchFound && p.getSteps() != 0) {
            p.move(board, in);
            playersTurn(p);
        } else if (matchFound && p.getSteps() == 0) {
            System.out.println("You are out of steps or please roll");
            playersTurn(p);
        }

        if (in.equals("H")) {
            p.printHand();
            playersTurn(p);
        }

        if (in.equals("G") && !p.getGuessStatus()) {
            p.setGuessStatus(true);
            refuteOrder(p);
            makeGuess(p);
            refuteCards.clear();
            clearScreen();
            refute(p.getGuess());
            clearScreen();
            System.out.println("Please past the tablet back to " + p.getName());
            input.next();
            System.out.println("Refute cards are: ");
            for (int i = 0; i < refuteCards.size(); i++) {
                System.out.println(i + ": " + refuteCards.get(i).getName());
            }

        }else if(in.equals("G") && p.getGuessStatus()){
            System.out.println("You have already guessed");
            playersTurn(p);
        }

        if (in.equals("F") && !p.getGuessStatus()) {
            makeGuess(p);
            p.setHasWon(checkWin(p.getGuess()));
            if(p.getHasWon()){
                winner = p;
                return true;
            }else{
                p.setIsout(true);
                System.out.println("You are out "+ p.getName() + " you can't guess or move but can still refute");
            }
            return false;
        }

        if (in.equals("E")) {
            p.setTurn(false);
            clearScreen();
            return false;
        }
        return false;
    }

    /**
     * Player is making final guess checks to see if they have won or not
     */

    public boolean checkWin(List<Card> guess){
        int count = 0;
        for(Card c : circumstance){
            for(Card c1 : guess){
                if(c.getName().equals(c1.getName())){
                    count++;
                }
            }
        }

        return count == 3;
    }

    /**
     * Goes through all the players aside from the guesser and checks if they can refute
     */
    public void refute(List<Card> guess) {
        //goes through the list of players in the correct refute order displaying the current guess cards
        for (int i = 0; i < tempPlayers.size(); i++) {
            System.out.println("The current Guess is:");
            for (Card c : guess) {
                System.out.print(c.getName() + " ");
            }
            //prints out who's turn it is to refute and there cards
            Scanner input = new Scanner(System.in);
            System.out.println("\nIt is " + tempPlayers.get(i).getName() + "'s time to make a refute ");
            tempPlayers.get(i).printHand();
            System.out.println("Please pick a card that refutes eg 1 or enter 4 if you cant refute");
            String in = input.next();
            //method checks to see if they have entered a number
            if (isNumeric(in)) {
                int j = Integer.parseInt(in.substring(0, 1));
                System.out.println(j);
                if (j > tempPlayers.get(i).getHand().size() - 1 && j != 4) {
                    System.out.println("Please enter a valid number");
                    refute(guess);
                }
                //following ifs check if they can refute or if the refute is legit
                if (j == 4) {
                    boolean check = checkRefute(guess, tempPlayers.get(i).getHand());
                    if (check) {
                        System.out.println("You can refute please try again");
                        refute(guess);
                    }
                } else {
                    Card r = tempPlayers.get(i).getHand().get(j);
                    boolean isRefute = isARefute(guess, r);
                    //if refute is legit card is added to one to show player making the guess
                    if (isRefute) {
                        refuteCards.add(r);
                    } else {
                        System.out.println("This refute is incorrect try again or select can't refute");
                        refute(guess);
                    }
                }
                //player is removed from the list and the next player goes
                tempPlayers.remove(tempPlayers.get(i));

            }
            clearScreen();
            refute(guess);

        }

    }

    /**
     * Checks to see if the card refute is a legit refute
     */
    public boolean isARefute(List<Card> guess, Card c) {
        //compares all the guess to the suggested card
        for (Card c1 : guess) {
            if (c1.getName().equals(c.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if player is telling truth when saying cant refute
     */
    public boolean checkRefute(List<Card> guess, List<Card> hand) {
        //compares all the cards in the guess to the players hand
        for (Card c1 : guess) {
            for (Card c2 : hand) {
                if (c1.getName().equals(c2.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks to see if a String is an int
     */
    public boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Allows the play to make a guess of one of each card, the estate being the estate they are currently in
     */

    public void makeGuess(Player p) {
        //clears the current players guess and prints out all the cards for them for the player
        p.clearGuess();
        for (int i = 0; i < tempDeck.size(); i++) {
            System.out.println(i + ": " + tempDeck.get(i).name);
        }

        //scans in the 3 cards as a string then break them into there numbers
        Scanner input = new Scanner(System.in);
        String in;
        System.out.println("Please select 3 cards as your guess eg 1,5,10");
        in = input.next();

        int i = Integer.parseInt(in.substring(0, 1));
        int j = Integer.parseInt(in.substring(2, 3));

        //last digit could be either singular or double so need to check for that
        int k;
        if (in.length() == 6) {
            k = Integer.parseInt(in.substring(4, 6));
        } else {
            k = Integer.parseInt(in.substring(4, 5));
        }

        //cards are in order from character to estatae to weapon so if one of the variables means player grabbed two or more of one type
        if (i > 3 || (j < 4 || j > 8) || (k < 9 || k > 13)) {
            System.out.println("Please pick one of each card");
            makeGuess(p);
        }

        CharacterCard gWho = (CharacterCard) tempDeck.get(i);
        EstateCard gWhere = (EstateCard) tempDeck.get(j);
        WeaponCard gWhat = (WeaponCard) tempDeck.get(k);


        //implement once we figure out how to check if they are in an estate
//        if(gWhere.getName() != player.getEstateName()){
//            System.out.println("Please pick the estate you are currently in");
//            makeGuess(p);
//        }
        p.addGuess(gWho);
        p.addGuess(gWhere);
        p.addGuess(gWhat);


    }

    /**
     * Make sure the order to refute is correct depending on who is making the guess
     */
    public void refuteOrder(Player p) {
        //clears previous order and clones the players in there orginal order into the array putting aside the player
        //who is making the guess
        tempPlayers.clear();
        Player guesser = null;
        for (Player p1 : players) {
            if (p1.getName().equals(p.getName())) {
                guesser = p1;
                tempPlayers.add(guesser);
            } else {
                tempPlayers.add(p1.clone());
            }
        }
        //moves the ones in front of the guesser to the back of the array then removes them from the front
        int i = tempPlayers.indexOf(guesser);

        for (int j = 1; j>= 0; j--) {
            Player temp = players.get(j);
            players.remove(j);
            players.add(temp);

        }
        //finally removes the guesser from the order
        tempPlayers.remove(guesser);

    }

    /**
     * Generate starting order for the game randomly chosen start but still follow same order as refute goes in
     */
    public void generateStartingOrder(Player p) {
        int i = players.indexOf(p);

        for (int j = i-1; j >= 0;j--) {
            Player temp = players.get(j);
            players.remove(j);
            players.add(temp);

        }

    }

    /**
     * Checks to make sure the player has entered a correct move key
     */
    public String checkInput(String in) {
        Scanner input = new Scanner(System.in);
        //if a match is found that key can be returned otherwise the player can keep trying until its valid
        Matcher matcher = MovePat.matcher(in);
        boolean matchFound = matcher.matches();
        if (matchFound) {
            return in;
        } else {
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
    public void playerSetUp() {
        //scans in a string from the console
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number of Players between 3 & 4: ");
        String numPlayers = input.next();

        if (!numPlayers.equals("3") && !numPlayers.equals("4")) {
            playerSetUp();
        }


        //players are then added to the array depending on the amount
        players.add(new Player("Lucilla", 9, 1));
        players.add(new Player("Bert", 14, 22));
        players.add(new Player("Malina", 1, 11));

        //4 player gets added in if nesscary
        if (numPlayers.equals("4")) {
            players.add(new Player("Percy", 22, 9));
        }

        for (Player p : players) {
            board.setPlayer(p);
        }

    }

    /**
     * Method to add all the cards to the game of the correct type
     * shuffles the array after the cards have been added
     */
    public void setUpDeck() {
        this.deck.add(new CharacterCard("Bert"));
        this.deck.add(new CharacterCard("Percy"));
        this.deck.add(new CharacterCard("Lucilla"));
        this.deck.add(new CharacterCard("Malina"));
        this.deck.add(new EstateCard("Haunted House"));
        this.deck.add(new EstateCard("Manic Manor"));
        this.deck.add(new EstateCard("Villa Celia"));
        this.deck.add(new EstateCard("Calamity Castle"));
        this.deck.add(new EstateCard("Peril Palace"));
        this.deck.add(new WeaponCard("Broom"));
        this.deck.add(new WeaponCard("Scissors"));
        this.deck.add(new WeaponCard("Knife"));
        this.deck.add(new WeaponCard("Shovel"));
        this.deck.add(new WeaponCard("iPad"));

        //clones the cards to a temp deck for making guesses
        for (Card c : deck) {
            tempDeck.add(c.clone());
        }
        Collections.shuffle(this.deck);
    }

    /**
     * Method to generate the murder circumstances one of each type of card is selected at random
     */
    public void generateMurder() {
        //if array size is 3 enough cards has been selected
        if (circumstance.size() == 3) {
            return;
        }
        //grabs a random card from the deck
        Random rand = new Random();

        Card c = this.deck.get(rand.nextInt(this.deck.size()));

        //checks what type of card it is if that is currently null then that card is the new circumstance
        //either generate murder is called again until all three cards are chosen
        //cards are then removed from the deck
        if (c instanceof CharacterCard) {
            if (this.who == null) {
                this.who = (CharacterCard) c;
                this.circumstance.add(this.who);
                this.deck.remove(this.who);
            }
            generateMurder();
        }
        if (c instanceof EstateCard) {
            if (this.where == null) {
                this.where = (EstateCard) c;
                this.circumstance.add(this.where);
                this.deck.remove(this.where);
            }
            generateMurder();
        }
        if (c instanceof WeaponCard) {
            if (this.what == null) {
                this.what = (WeaponCard) c;
                this.circumstance.add(this.what);
                this.deck.remove(this.what);
            }
            generateMurder();
        }

    }

    /**
     * Deals the remaining cards (after the murder circumstance as been selected)
     * out randomly amongst the players until there is none left
     */
    public void dealCards() {

        //until the deck is empty loops through the players until there is none left
        while (!this.deck.isEmpty()) {
            for (Player p : this.players) {
                if (this.deck.isEmpty()) {
                    break;
                }
                Random rand = new Random();
                Card c = this.deck.get(rand.nextInt(this.deck.size()));
                p.addHand(c);
                this.deck.remove(c);
            }
        }
    }

    /**
     * Clears the console screen so other players can't see what the other players have done
     */
    public static void clearScreen() {

        for (int i = 0; i < 150; ++i) System.out.println();

    }

}