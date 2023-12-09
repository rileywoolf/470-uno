package components.players;

import components.Card;

import java.util.ArrayList;
import java.util.List;

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
     * Constructs a new AI player with the specified name.
     *
     * @param name the name of the AI player
     */
    public AIPlayer(String name) {
        super(name);
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
        List<Card> playableCards = new ArrayList<>();

        for (Card c : hand) {
            if (c.validMove(topCard)) {
                playableCards.add(c);
            }
        }

        return getCardToPlay(topCard, playableCards);
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
}
