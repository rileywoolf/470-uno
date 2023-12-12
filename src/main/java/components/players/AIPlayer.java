package components.players;

import components.Card;
import utils.Color;
import utils.PrintUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
     * Retrieves the most common color in the AI player's hand.
     *
     * @return the most common color
     */
    protected Color getMostCommonColor() {
        final int RED_INDEX = 0, BLUE_INDEX = 1, YELLOW_INDEX = 2, GREEN_INDEX = 3;
        int[] colorCounts = new int[4];
        // Go through and count the number of each color of cards in the hand.
        for (Card c : hand) {
            switch (c.getColor()) {
                case RED -> colorCounts[RED_INDEX] += 1;
                case BLUE -> colorCounts[BLUE_INDEX] += 1;
                case YELLOW -> colorCounts[YELLOW_INDEX] += 1;
                case GREEN -> colorCounts[GREEN_INDEX] += 1;
            }
        }

        // Find the most common color.
        int max = colorCounts[0], maxInd = 0;
        for (int i = 1; i < colorCounts.length; i++) {
            if (colorCounts[i] > max) {
                max = colorCounts[i];
                maxInd = i;
            }
        }

        if (max != 0) {
            switch (maxInd) {
                case 0 -> {
                    return Color.RED;
                }
                case 1 -> {
                    return Color.BLUE;
                }
                case 2 -> {
                    return Color.YELLOW;
                }
                case 3 -> {
                    return Color.GREEN;
                }
            }
        }

        // If there are no remaining cards of any color, just randomly choose a color.
        Random r = new Random();
        Color color;
        switch (r.nextInt(4)) {
            case 0 -> color = Color.BLUE;
            case 1 -> color = Color.GREEN;
            case 2 -> color = Color.RED;
            default -> color = Color.YELLOW;
        }
        return color;
    }

    /**
     * Sets the {@code declaredUno} flag to {@code true}, indicating that the player has called Uno.
     * This method is used internally to update the player's Uno status.
     */
    protected void callUno() {
        declaredUno = true;
    }
}
