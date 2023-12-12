package components.players;

import components.Card;
import utils.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * This AI Player class represents a medium-level computer player.
 * The player selects a playable card based on matching color or number, prioritizing
 * special cards and choosing a color based on the most common color in their hand.
 *
 * @author Riley Woolf
 * @version 1.0
 */
public class MediumAIPlayer extends AIPlayer {

    /**
     * Constructs a new medium-level AI player with the default name "Medium AI".
     *
     * @param print a boolean indicating whether to print game-related information
     * @param index the index of the player in the player and handSize arrays
     */
    public MediumAIPlayer(boolean print, int index) {
        super("Medium AI", index, print);
    }

    /**
     * Constructs a new medium-level AI player with the specified name.
     *
     * @param name  the name of the AI player
     * @param index the index of the player in the player and handSize arrays
     * @param print a boolean indicating whether to print game-related information
     */
    public MediumAIPlayer(String name, int index, boolean print) {
        super(name, index, print);
    }

    /**
     * {@inheritDoc}
     * Overrides the method to implement medium-level AI logic for choosing a card to play.
     *
     * @param topCard      the current top card on the discard pile
     * @param playableCards a list of playable cards in the player's hand
     * @return the selected card to play
     */
    @Override
    protected Card getCardToPlay(Card topCard, List<Card> playableCards) {
        List<Card> specialCards = new ArrayList<>();
        List<Card> wilds = new ArrayList<>();

        // Go through the list of playable cards to find the first playable number card.
        // Also keeps track of the different special and wild cards in the hand.
        for (Card c : playableCards) {
            switch (c.getType()) {
                case NUMBER -> {
                    hand.remove(c);
                    if (hasUno()) {
                        callUno();
                    }
                    return c;
                }
                case WILD, WILD_DRAW_FOUR -> wilds.add(c);
                case DRAW_TWO, SKIP, REVERSE -> specialCards.add(c);
            }
        }

        // If no number cards are playable, then play the first of the special cards, if any.
        if (!specialCards.isEmpty()) {
            hand.remove(specialCards.get(0));
            if (hasUno()) {
                callUno();
            }
            return specialCards.get(0);
        }

        // Just default to whatever is first in the wilds list if nothing else is playable so far.
        hand.remove(wilds.get(0));
        if (hasUno()) {
            callUno();
        }
        return wilds.get(0);
    }

    /**
     * {@inheritDoc}
     * Overrides the method to implement medium-level AI logic for choosing a color.
     *
     * @return the selected color
     */
    @Override
    public Color chooseColor() {
        Color color = getMostCommonColor();
        if (print) { System.out.println("Chose color: " + color.name()); }
        return color;
    }

    /**
     * {@inheritDoc}
     * Overrides the method to implement medium-level AI logic for choosing another player to switch hands with.
     *
     * @param handSizes a list containing the sizes of hands for each player in the game
     * @return the index of the player to switch hands with
     */
    @Override
    public int getPlayerToSwitchWith(List<Integer> handSizes) {
        int min = Integer.MAX_VALUE, index = -1;
        for (int i = 0; i < handSizes.size(); i++) {
            if (i == playerIndex) continue;
            if (handSizes.get(i) < min) {
                min = handSizes.get(i);
                index = i;
            }
        }
        return index;
    }
}

