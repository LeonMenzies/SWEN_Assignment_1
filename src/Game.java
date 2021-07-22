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
        game.playGame();
    }


    /**
     * Starts the game and decides the order in which the plays will start
     * Game goes until someone has won or all the players are out
     */
    public void playGame() {

        //gets at the random who is going to start the game
        Random rand = new Random();
        int i = rand.nextInt(players.size());
        Player p = players.get(i);
        //then generates the starting order based on who starts first
        generateStartingOrder(p);
        while (!gameWon) {

            int count = 0;
            //loops through the players checking if they are out of have won the game
            for (Player player : players) {
                if (!player.getIsOut()) {
                    gameWon = playersTurn(player);
                    if (gameWon) {
                        break;
                    }

                }
                if (player.getIsOut()) {
                    count++;
                }
            }
            //if all plays are out game is over
            if (count == players.size()) {
                break;
            }

        }
        //either winner is displayed or no one won
        if (gameWon) {
            System.out.println("The winner is " + winner.getName() + "!");

        } else {
            System.out.println("All Play's lost game is over!");
        }


    }

    /**
     * Allows the player to play game via a serious of inputs into the console
     *
     * @param p current player
     * @return true of false if the player has won
     */

    public boolean playersTurn(Player p) {

        Scanner input = new Scanner(System.in);
        String in;
        while (true) {
            //sets up the player for there turn clears all the variables from last turn
            if (!p.getTurn()) {
                System.out.print("It is " + p.getName() + "'s turn please make sure they have the tablet and enter any key to continue: ");
                input.next();
                p.setTurn(true);
                p.setRollStatus(false);
                p.setGuessStatus(false);
                p.clearVisited();

            }
            //Display the board before the players turn and there steps
            System.out.println(board);
            System.out.println(p.getName() + " has " + p.getSteps() + " number of steps");

            //if player has not rolled option is displayed
            if (!p.getRollStatus()) {
                System.out.println("R(Roll), H(Show Hand), G(guess), F(Final Guess), E(END TURN) or WASD(Move)");
            } else {
                System.out.println("H(Show Hand), G(guess), F(Final Guess), E(END TURN) or WASD(Move)");
            }

            in = input.next().toUpperCase();

            in = checkInput(in);

            //rolls the dice for the player and makes so they cant roll again
            if (in.equals("R") && !p.getRollStatus()) {
                p.roll();
                p.setRollStatus(true);

            } else if (in.equals("R") && p.getRollStatus()) {
                System.out.println("Already Rolled");


            }
            //if input matches a move direction moves the player and updates there steps unless they are out of steps
            Matcher matcher = dirPat.matcher(in);
            boolean matchFound = matcher.matches();
            if (matchFound && p.getSteps() != 0) {
                p.move(board, in);

            } else if (matchFound && p.getSteps() == 0) {
                System.out.println("You are out of steps or please roll");

            }

            if (in.equals("H")) {
                p.printHand();

            }

            //set so player cant make another guess this turn, gets the order the following players will make a guess
            //adds the cards to the refute list, clears the screen then displays the refute cards for the player
            //also checks to see if the play is in an estate
            if (in.equals("G") && !p.getGuessStatus() && !p.getEstateIn().equals("null")) {
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

            } else if (in.equals("G") && p.getGuessStatus() && !p.getEstateIn().equals("null")) {
                System.out.println("You have already guessed");

            } else if(in.equals("G") && !p.getGuessStatus() && p.getEstateIn().equals("null") ) {
                System.out.println("You need to be in an estate to make a guess");
            }

            //player makes a guess then the cards a checked against the circumstance
            //if player is successful they have won otherwise they are out
            //also checks to see if the player is in a estate
            if (in.equals("F") && !p.getGuessStatus() && !p.getEstateIn().equals("null")) {
                makeGuess(p);
                p.setHasWon(checkWin(p.getGuess()));
                if (p.getHasWon()) {
                    winner = p;
                    return true;
                } else {
                    p.setIsout(true);
                    System.out.println("You are out " + p.getName() + " you can't guess or move but can still refute");
                }
                return false;
            } else if(in.equals("F") && !p.getGuessStatus() && p.getEstateIn().equals("null")){
                System.out.println("You must be in an estate to make a final guess");
            }

            //ends the plays turn
            if (in.equals("E") && (p.getSteps() == 0 || !p.getEstateIn().equals("null"))) {
                p.setTurn(false);
                clearScreen();
                return false;
            } else {
                System.out.println("You must be in an estate or out of steps to end your turn");
            }
        }

    }

    /**
     * Player is making final guess checks to see if they have won or not
     *
     * @param guess list of the current guess
     * @return true or false if all the cards match
     */

    public boolean checkWin(List<Card> guess) {
        int count = 0;
        //goes through the circumstance and compares them to the players guess if count equals 3 then all cards matched
        for (Card c : circumstance) {
            for (Card c1 : guess) {
                if (c.getName().equals(c1.getName())) {
                    count++;
                }
            }
        }

        return count == 3;
    }

    /**
     * Goes through all the players aside from the guesser and checks if they can refute
     *
     * @param guess list of the current guess
     */
    public void refute(List<Card> guess) {
        //goes through the list of players in the correct refute order displaying the current guess cards
        for (int i = 0; i < tempPlayers.size(); i++) {
            System.out.println("The current Guess is:");
            for (Card c : guess) {
                System.out.print(c.getName() + " ");
            }
            System.out.println();
            while (true) {
                //prints out who's turn it is to refute and there cards
                Scanner input = new Scanner(System.in);
                System.out.println("It is " + tempPlayers.get(i).getName() + "'s time to make a refute ");
                tempPlayers.get(i).printHand();
                System.out.println("Please pick a card that refutes eg 1 or enter 4 if you cant refute");
                String in = input.next();
                //method checks to see if they have entered a number
                if (isNumeric(in)) {
                    int j = Integer.parseInt(in.substring(0, 1));
                    System.out.println(j);
                    if (j > tempPlayers.get(i).getHand().size() - 1 && j != 4) {
                        System.out.println("Please enter a valid number");

                    }
                    //following ifs check if they can refute or if the refute is legit
                    else if (j == 4) {
                        boolean check = checkRefute(guess, tempPlayers.get(i).getHand());
                        if (check) {
                            System.out.println("You can refute please try again");

                        } else {
                            break;
                        }
                    } else {
                        //pulls the card and checks it is a refute
                        Card r = tempPlayers.get(i).getHand().get(j);
                        boolean isRefute = isARefute(guess, r);
                        //if refute is legit card is added to one to show player making the guess
                        if (isRefute) {
                            //player is removed from the list and the next player goes
                            Player temp = players.get(i);
                            tempPlayers.remove(temp);
                            refuteCards.add(r);
                            break;
                        } else {
                            System.out.println("This refute is incorrect try again or select can't refute");

                        }
                    }


                }


            }
            //clears the screen before the next players refute
            clearScreen();
        }

    }

    /**
     * Checks to see if the card refute is a legit refute
     *
     * @param guess list of the current guess
     * @param c     the card the player is putting in as the refute
     * @return true or false if the the card is an actual refute or not
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
     *
     * @param guess list of the current guess
     * @param hand  list of the players hand
     * @return true or false if the play can refute or not
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
     *
     * @param str String to be checked
     * @return true or false if can be parsed as int or not
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
     *
     * @param p player making the guess
     */

    public void makeGuess(Player p) {

        //clears the current players guess array and prints all the cards in the deck for them
        p.clearGuess();
        int count = 0;
        for (Card c : tempDeck) {
            if (c instanceof EstateCard) {
                if (!c.getName().equals(p.getEstateIn())) {
                    continue;
                }
            }
            System.out.println(count + ": " + c.getName());
            count++;
        }

        //int and strings required for selecting the right cards from the deck
        int i;
        int j;
        int k;
        String cardC;
        String cardE;
        String cardW;
        while (true) {
            Scanner input = new Scanner(System.in);
            String in;
            System.out.println("Please select 3 cards as your guess eg 1,4,9");
            in = input.next();

            if (in.length() != 5) {
                System.out.println("Please select three cards");

            } else {
                //brings the input into substrings of the correct numbers
                cardC = in.substring(0, 1);
                cardE = in.substring(2, 3);
                cardW = in.substring(4, 5);


                //need to check that the strings input are actually numbers
                if (isNumeric(cardC) && isNumeric(cardE) && isNumeric(cardW)) {
                    i = Integer.parseInt(cardC);
                    j = Integer.parseInt(cardE);
                    k = Integer.parseInt(cardW);
                    //cards are in order from character to estate to weapon so if one of the numbers is out means player grabbed two or more of one type
                    if (i > 3 || j != 4 || (k < 5 || k > 9)) {
                        System.out.println("Please pick one of each card");

                    } else {
                        break;
                    }
                }
            }


        }
        //generates the three cards of each type that the play is guessing
        //and add them to the players guess array
        CharacterCard gWho = (CharacterCard) tempDeck.get(i);
        EstateCard gWhere = (EstateCard) tempDeck.get(j);
        WeaponCard gWhat = (WeaponCard) tempDeck.get(k);
        p.addGuess(gWho);
        p.addGuess(gWhere);
        p.addGuess(gWhat);

    }


    /**
     * Make sure the order to refute is correct depending on who is making the guess
     *
     * @param p player making the guess
     */
    public void refuteOrder(Player p) {
        //clears previous order and clones the players in there original order into the array putting aside the player
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

        for (int j = i - 1; j >= 0; j--) {
            Player temp = players.get(j);
            players.remove(j);
            players.add(temp);

        }

        //finally removes the guesser from the order
        tempPlayers.remove(guesser);

    }

    /**
     * Generate starting order for the game randomly chosen start but still follow same order as refute goes in
     *
     * @param p starting player
     */
    public void generateStartingOrder(Player p) {
        int i = players.indexOf(p);
        //index of starting player and players in front of that player are removed from the front and added to a separate array
        List<Player> toAdd = new ArrayList<>();
        for (int j = i - 1; j >= 0; j--) {
            Player temp = players.get(j);
            players.remove(j);
            toAdd.add(temp);

        }
        //reserve the order as they are added in the wrong way then the new array is added to the end of the players array
        Collections.reverse(toAdd);

        players.addAll(toAdd);

    }

    /**
     * Checks to make sure the player has entered a correct move key
     *
     * @param in String to be checked
     * @return string which is the players move
     */
    public String checkInput(String in) {

        //if a match is found that key can be returned otherwise the player can keep trying until its valid

        while (true) {
            Scanner input = new Scanner(System.in);

            //length greater then one cant be a correct character entered
            if (in.length() > 1) {
                System.out.println("Please enter a Valid Move");
                in = input.next().toUpperCase();
            }
            //checks to see if input matches pattern if true returns that string
            Matcher matcher = MovePat.matcher(in);
            boolean matchFound = matcher.matches();

            if (matchFound) {
                return in;
            } else {
                System.out.println("Please enter a Valid Move");
                in = input.next().toUpperCase();

            }
        }
    }

    /**
     * Method to set Up the right amount of players in the game either 3 or 4
     * Right amount of players are then added to the player Array for the game
     */
    public void playerSetUp() {
        //scans in a string from the console
        String numPlayers;
        do {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter the number of Players between 3 & 4: ");
            numPlayers = input.next();

        } while (!numPlayers.equals("3") && !numPlayers.equals("4"));

        //players are then added to the array depending on the amount
        players.add(new Player("Lucilla", 9, 1));
        players.add(new Player("Bert", 14, 22));
        players.add(new Player("Malina", 1, 11));

        //4 player gets added in if necessary
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