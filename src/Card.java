/***
 * The Card class is a helper class for the different cards that players have in their hand
 * to make guesses and final guesses to win the game.
 */
public class Card implements Cloneable {
    String name;
    public Card(String name) {
    this.name = name;
    }

    @Override
    public Card clone(){
        return this;
    }

    public String getName(){
        return this.name;
    }
}