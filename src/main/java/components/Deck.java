package components;

import utils.CardType;
import utils.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        initialize();
        shuffle();
    }

    // Create the deck.
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

    // Shuffle the cards in the deck.
    public void shuffle() {
        Collections.shuffle(cards);
    }

    // Draw a card from the deck.
    public Card draw() {
        if (cards.isEmpty()) {
            // Initialize the deck again.
            initialize();
        }
        return cards.remove(0);
    }

    // Puts the given card back in the deck.
    public void returnCard(Card card) {
        cards.add(card);
        shuffle();
    }
}
