package components;

import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * The {@code DeckTests} class contains JUnit tests for the {@link Deck} class.
 * It includes tests for drawing cards, returning cards, shuffling the deck, and handling empty deck scenarios.
 *
 * @author Riley Woolf
 * @version 1.0
 */
public class DeckTests {

    /**
     * Test for drawing a card from the deck.
     */
    @Test
    public void draw() {
        Deck deck = new Deck();
        int initialSize = deck.getCards().size();

        // Drawing a card should reduce the deck size by 1
        Card drawnCard = deck.draw();
        assertNotNull(drawnCard);
        assertEquals(initialSize - 1, deck.getCards().size());
    }

    /**
     * Test for returning a card to the deck.
     */
    @Test
    public void returnCard() {
        Deck deck = new Deck();
        int initialSize = deck.getCards().size();

        // Drawing a card
        Card drawnCard = deck.draw();
        assertNotNull(drawnCard);

        // Returning the card should increase the deck size by 1
        deck.returnCard(drawnCard);
        assertEquals(initialSize, deck.getCards().size());
    }

    /**
     * Test for shuffling the order of cards in the deck.
     */
    @Test
    public void shuffle() {
        Deck deck = new Deck();
        List<Card> originalOrder = new ArrayList<>(deck.getCards());

        // Shuffling the deck should change the order of cards
        deck.shuffle();
        assertNotEquals(originalOrder, deck.getCards());
    }

    /**
     * Test for drawing all cards from the deck.
     */
    @Test
    public void drawAllCards() {
        Deck deck = new Deck();

        // Drawing all the cards should leave the deck empty
        while (!deck.getCards().isEmpty()) {
            deck.draw();
        }

        assertTrue(deck.getCards().isEmpty());
    }

    /**
     * Test for drawing a card after the deck is empty, which should reinitialize the deck.
     */
    @Test
    public void drawAfterEmptyDeck() {
        Deck deck = new Deck();

        // Drawing all the cards should leave the deck empty
        while (!deck.getCards().isEmpty()) {
            deck.draw();
        }

        assertTrue(deck.getCards().isEmpty());

        // Drawing a card after an empty deck should reinitialize the deck
        deck.draw();
        assertFalse(deck.getCards().isEmpty());
    }
}
