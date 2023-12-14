package components.players;

import components.Card;
import utils.Color;
import utils.PrintUtils;

import java.util.*;

/**
 * The {@code AIPlayer} class represents an abstract artificial intelligence (AI) player
 * in a card game. It extends the {@link Player} class, providing AI-specific functionality
 * for playing cards in the game.
 *
 * @author Riley Woolf
 * @version 1.0
 */
public abstract class AIPlayer extends Player {

    /**
     * A boolean flag indicating whether to print the options and choices available to
     * and made by the AI player.
     */
    protected boolean print;


    /**
     * Constructs a new AI player with the specified name.
     *
     * @param name the name of the AI player
     * @param index the index of the player in the player and handSize arrays
     * @param print whether to print the AI options and choices
     */
    public AIPlayer(String name, int index, boolean print) {
        super(name, index);
        this.print = print;
    }

    /**
     * Overrides the play method from the {@link Player} class. For any AI implementation,
     * this method searches through the hand for a list of playable cards and defers to
     * the concrete implementation to choose which card to play.
     *
     * @param topCard the current top card on the table
     * @return the card played by the AI player
     */
    @Override
    public Card play(Card topCard) {
        if (print) {
            PrintUtils.displayTopCard(topCard);
            PrintUtils.displayHand(name, hand);
        }

        List<Card> playableCards = new ArrayList<>();

        for (Card c : hand) {
            if (c.validMove(topCard)) {
                playableCards.add(c);
            }
        }

        return playableCards.isEmpty() ? null : getCardToPlay(topCard, playableCards);
    }

    /**
     * Abstract method representing the AI player's strategy to choose a card to play
     * from the list of playable cards.
     *
     * @param topCard the current top card on the table
     * @param playableCards a list of cards that can be played
     * @return the chosen card to be played by the AI player
     */
    protected abstract Card getCardToPlay(Card topCard, List<Card> playableCards);

    /**
     * Retrieves the order of the colors in the AI player's hand.
     *
     * @return the colors in most- to least-common order
     */
    protected List<Color> getMostCommonColor() {
        // Create a map to store the count of each color.
        Map<Color, Integer> colorToCount = new HashMap<>();

        // Count the number of cards for each color.
        for (Card c : hand) {
            colorToCount.put(c.getColor(), colorToCount.getOrDefault(c.getColor(), 0) + 1);
        }

        // Create a list of colors sorted by count in descending order.
        List<Color> sortedColors = new ArrayList<>(colorToCount.keySet());
        sortedColors.sort(Comparator.comparingInt(colorToCount::get).reversed());

        return sortedColors;
    }

    /**
     * Sets the {@code declaredUno} flag to {@code true}, indicating that the player has called Uno.
     * This method is used internally to update the player's Uno status.
     */
    protected void callUno() {
        declaredUno = true;
    }

    /**
     * Protected method to print information about a played card during a player's turn.
     * This method displays the player's name and the details of the played card if printing is enabled.
     *
     * <p>
     * Note: The actual printing of card details is delegated to the {@link PrintUtils#displayCard(Card)} method.
     * </p>
     *
     * @param c the card to be printed
     */
    protected void printCard(Card c) {
        if (print) {
            System.out.print(name + "' Turn: ");
            PrintUtils.displayCard(c);
        }
    }

}
