package components.players;

import components.Card;
import utils.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Player} class represents an abstract player in a card game.
 * It provides common functionality and methods that are expected for a player
 * participating in the UNO card game.
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
     * The list of cards in the player's hand.
     */
    protected List<Card> hand;

    /**
     * A flag indicating whether the player has declared Uno.
     */
    protected boolean declaredUno;

    /**
     * Constructs a new player with the specified name.
     *
     * @param name the name of the player
     */
    public Player(String name) {
        this.name = name;
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
}
