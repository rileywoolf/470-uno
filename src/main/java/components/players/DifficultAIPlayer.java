package components.players;

import components.Card;
import utils.Color;

import java.util.ArrayList;
import java.util.List;

public class DifficultAIPlayer extends AIPlayer {
    /**
     * Constructs a new medium-level AI player with the default name "Difficult AI".
     *
     * @param index the index of the player in the player and handSize arrays
     * @param print whether to print the AI options and choices
     */
    public DifficultAIPlayer(int index, boolean print) { super("Difficult AI", index, print); }

    /**
     * Constructs a new AI player with the specified name.
     *
     * @param name  the name of the AI player
     * @param index the index of the player in the player and handSize arrays
     * @param print whether to print the AI options and choices
     */
    public DifficultAIPlayer(String name, int index, boolean print) {
        super(name, index, print);
    }

    /**
     * {@inheritDoc}
     * Overrides the method to implement difficult-level AI logic for choosing a card to play.
     *
     * @param topCard      the current top card on the discard pile
     * @param playableCards a list of playable cards in the player's hand
     * @return the selected card to play
     */
    @Override
    protected Card getCardToPlay(Card topCard, List<Card> playableCards) {
        List<Color> colorOrder = getMostCommonColor();
        List<Card> numbers = new ArrayList<>();
        List<Card> specials = new ArrayList<>();
        List<Card> wilds = new ArrayList<>();

        // Separate the hand into numbers, specials, and wilds.
        for (Card c : playableCards) {
            switch (c.getType()) {
                case NUMBER -> numbers.add(c);
                case WILD, WILD_DRAW_FOUR -> wilds.add(c);
                case DRAW_TWO, SKIP, REVERSE -> specials.add(c);
            }
        }

        // Prioritize playing a number card of the highest color.
        for (Color color : colorOrder) {
            for (Card card : numbers) {
                if (card.getColor() == color) {
                    printCard(card);
                    return card;
                }
            }
        }

        // Next prioritize playing a special card of the highest color.
        for (Color color : colorOrder) {
            for (Card card : specials) {
                if (card.getColor() == color) {
                    printCard(card);
                    return card;
                }
            }
        }

        // Finally, prioritize playing wilds last.
        printCard(wilds.get(0));
        return wilds.get(0);
    }

    /**
     * {@inheritDoc}
     * Overrides the method to implement difficult-level AI logic for choosing a color.
     * Same as the medium-level AI at this point.
     *
     * @return the selected color
     */
    @Override
    public Color chooseColor() {
        Color color = getMostCommonColor().get(0);
        if (print) { System.out.println("Chose color: " + color.name()); }
        return color;
    }

    /**
     * {@inheritDoc}
     * Overrides the method to choose a player index to switch hands with based on the list of hand sizes,
     * the current player's index, and the direction of play.
     *
     * <p>
     * The player chooses the smallest hand that is closest in the specified direction of play.
     * </p>
     *
     * @param handSizes    a list containing the sizes of hands for each player in the game
     * @param forwardPlay  a boolean indicating the direction of play (true for forward, false for backward)
     * @return the index of the player to switch hands with
     */
    @Override
    public int getPlayerToSwitchWith(List<Integer> handSizes, boolean forwardPlay) {
        int minHandSize = Integer.MAX_VALUE;
        int targetIndex = -1;

        int playerCount = handSizes.size();

        // Iterate in the direction of play.
        for (int i = 0; i < playerCount; i++) {
            int indexToCheck = (playerIndex + (forwardPlay ? i : -i) + playerCount) % playerCount;

            if (indexToCheck != playerIndex && handSizes.get(indexToCheck) < minHandSize) {
                minHandSize = handSizes.get(indexToCheck);
                targetIndex = indexToCheck;
            }
        }

        if (print) System.out.println("Chose to switch hands with player " + (targetIndex + 1));

        return targetIndex;
    }
}
