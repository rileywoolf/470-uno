package components.players;

import components.Card;
import utils.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Player} class represents an abstract player in a card game, like Uno.
 * It provides common functionality and methods that are expected for a player
 * participating in the UNO card game.
 *
 * <p>
 * The player is identified by a name, has a unique player index, and maintains a hand of cards.
 * The class includes methods for checking Uno status, adding cards to the hand,
 * and defining strategies for playing cards, choosing colors, and selecting players to switch hands with.
 * </p>
 *
 *
 * @author Riley Woolf
 * @version 1.0
 */
public abstract class Player {
    /**
     * The name of the player.
     */
    protected String name;

    /**
     * The unique index assigned to the player.
     */
    protected int playerIndex;

    /**
     * The list of cards in the player's hand.
     */
    protected List<Card> hand;

    /**
     * A flag indicating whether the player has declared Uno.
     */
    protected boolean declaredUno;

    /**
     * Constructs a new player with the specified name and player index.
     *
     * @param name        the name of the player
     * @param playerIndex the unique index assigned to the player
     */
    public Player(String name, int playerIndex) {
        this.name = name;
        this.playerIndex = playerIndex;
        this.hand = new ArrayList<>();
        this.declaredUno = false;
    }

    /**
     * Gets the name of the player.
     *
     * @return the name of the player
     */
    public String getName() { return name; }

    /**
     * Gets the hand of the player.
     *
     * @return the hand of the player
     */
    public List<Card> getHand() { return hand; }

    /**
     * Sets the hand of the player with the specified list of cards.
     *
     * @param hand the list of cards to set as the player's hand
     */
    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    /**
     * Checks if the player has declared Uno.
     *
     * @return {@code true} if the player has declared Uno, {@code false} otherwise
     */
    public boolean declaredUno() { return declaredUno; }

    /**
     * Checks if the player has only one card in hand, indicating Uno.
     *
     * @return {@code true} if the player has Uno, {@code false} otherwise
     */
    public boolean hasUno() { return hand.size() == 1; }

    /**
     * Checks if the player has no cards in hand.
     *
     * @return {@code true} if the player has no cards, {@code false} otherwise
     */
    public boolean hasNoCards() { return hand.isEmpty(); }

    /**
     * Adds a card to the player's hand.
     *
     * @param card the card to be added to the hand
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * Abstract method representing the player's strategy to play a card.
     *
     * @param topCard the current top card on the table
     * @return the card played by the player
     */
    public abstract Card play(Card topCard);

    /**
     * Abstract method representing the player's strategy to choose a color
     * when required (e.g., after playing a Wild card).
     *
     * @return the chosen color
     */
    public abstract Color chooseColor();

    /**
     * Abstract method representing the player's strategy to select another player to switch hands with.
     * The implementation of this method should determine the index of the player in the list of players
     * with whom the current player wishes to exchange hands.
     *
     * @param handSizes    a list containing the sizes of hands for each player in the game
     * @param forwardPlay  a boolean indicating the direction of play (true for forward, false for backward)
     * @return the index of the player to switch hands with
     */
    public abstract int getPlayerToSwitchWith(List<Integer> handSizes, boolean forwardPlay);


}
