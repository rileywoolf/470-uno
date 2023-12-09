package components;

import utils.CardType;
import utils.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The {@code Deck} class represents a deck of cards used in a card game.
 * It contains methods to initialize, shuffle, draw, and return cards to the deck.
 * <p>
 * The deck is initially created with standard cards, including number cards, special cards
 * (e.g., Skip, Reverse, Draw Two), and wild cards (Wild and Wild Draw Four).
 * </p>
 * <p>
 * The class uses the {@link Card} class to represent individual cards.
 * </p>
 *
 * @author Riley Woolf
 * @version 1.0
 */
public class Deck {
    /**
     * The list of cards in the deck.
     */
    private final List<Card> cards;

    /**
     * Constructs a new Deck and initializes it by creating and shuffling the cards.
     */
    public Deck() {
        cards = new ArrayList<>();
        initialize();
        shuffle();
    }

    /**
     * Initializes the deck by adding standard cards, including number cards,
     * special cards (Skip, Reverse, Draw Two), and wild cards (Wild and Wild Draw Four).
     * The deck is created with four sets of each wild card.
     */
    private void initialize() {
        for (Color color : Color.values()) {
            // Add all the number cards.
            for (int i = 0; i < 10; i++) {
                cards.add(new Card(color, CardType.NUMBER, i));
                cards.add(new Card(color, CardType.NUMBER, i));
            }

            // Add the special cards that have colors.
            cards.add(new Card(color, CardType.SKIP, -1));
            cards.add(new Card(color, CardType.SKIP, -1));
            cards.add(new Card(color, CardType.REVERSE, -1));
            cards.add(new Card(color, CardType.REVERSE, -1));
            cards.add(new Card(color, CardType.DRAW_TWO, -1));
            cards.add(new Card(color, CardType.DRAW_TWO, -1));
        }

        // Add the wild cards, four of each.
        for (int i = 0; i < 4; i++) {
            cards.add(new Card(null, CardType.WILD, -1));
            cards.add(new Card(null, CardType.WILD_DRAW_FOUR, -1));
        }
    }

    /**
     * Shuffles the cards in the deck.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Draws a card from the deck. If the deck is empty, it initializes the deck again
     * before drawing a card.
     *
     * @return the card drawn from the deck
     */
    public Card draw() {
        if (cards.isEmpty()) {
            // Initialize the deck again.
            initialize();
        }
        return cards.remove(0);
    }

    /**
     * Returns the given card to the deck and shuffles the deck.
     *
     * @param card the card to be returned to the deck
     */
    public void returnCard(Card card) {
        cards.add(card);
        shuffle();
    }
}