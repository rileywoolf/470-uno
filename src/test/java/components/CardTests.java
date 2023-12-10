package components;

import org.junit.*;
import utils.CardType;
import utils.Color;

import static org.junit.Assert.*;

/**
 * The {@code CardTests} class contains JUnit tests for the {@link Card} class.
 * It includes tests for methods related to retrieving and manipulating card attributes,
 * as well as checking the validity of moves with respect to Uno game rules.
 *
 * @author Riley Woolf
 * @version 1.0
 */
public class CardTests {

    /**
     * Test for retrieving the color of a card.
     */
    @Test
    public void getColor() {
        Card card = new Card(Color.RED, CardType.NUMBER, 5);
        assertEquals(Color.RED, card.getColor());
    }

    /**
     * Test for setting the color of a card.
     */
    @Test
    public void setColor() {
        Card card = new Card(Color.BLUE, CardType.NUMBER, 7);
        card.setColor(Color.YELLOW);
        assertEquals(Color.YELLOW, card.getColor());
    }

    /**
     * Test for retrieving the type of a card.
     */
    @Test
    public void getType() {
        Card card = new Card(Color.GREEN, CardType.SKIP, 0);
        assertEquals(CardType.SKIP, card.getType());
    }

    /**
     * Test for retrieving the number of a number card.
     */
    @Test
    public void getNumber() {
        Card card = new Card(Color.RED, CardType.NUMBER, 3);
        assertEquals(3, card.getNumber());
    }

    /**
     * Test for checking the validity of moves with respect to Uno game rules.
     */
    @Test
    public void validMove() {
        Card topCard = new Card(Color.BLUE, CardType.NUMBER, 5);

        // Test valid moves
        Card validCard = new Card(Color.BLUE, CardType.NUMBER, 8);
        assertTrue(validCard.validMove(topCard));

        validCard = new Card(Color.GREEN, CardType.NUMBER, 5);
        assertTrue(validCard.validMove(topCard));

        validCard = new Card(Color.BLUE, CardType.REVERSE, -1);
        assertTrue(validCard.validMove(topCard));

        // Test invalid moves
        Card invalidCard = new Card(Color.RED, CardType.NUMBER, 2);
        assertFalse(invalidCard.validMove(topCard));

        invalidCard = new Card(Color.GREEN, CardType.SKIP, -1);
        assertFalse(invalidCard.validMove(topCard));
    }

    /**
     * Test for checking the validity of wild card moves.
     */
    @Test
    public void validMoveWild() {
        Card topCard = new Card(Color.YELLOW, CardType.SKIP, -1);

        // Wild cards are always valid moves
        Card wildCard = new Card(null, CardType.WILD, 0);
        assertTrue(wildCard.validMove(topCard));

        topCard = wildCard;
        wildCard = new Card(null, CardType.WILD_DRAW_FOUR, -1);
        assertTrue(wildCard.validMove(topCard));
    }

    /**
     * Test for generating a string representation of a number card.
     */
    @Test
    public void toStringNumberCard() {
        Card numberCard = new Card(Color.GREEN, CardType.NUMBER, 4);
        assertEquals("GREEN 4", numberCard.toString());
    }

    /**
     * Test for generating a string representation of a wild card.
     */
    @Test
    public void toStringWildCard() {
        Card wildCard = new Card(null, CardType.WILD, -1);
        assertEquals("WILD", wildCard.toString());
    }

    /**
     * Test for generating a string representation of a Wild Draw Four card.
     */
    @Test
    public void toStringWildDrawFourCard() {
        Card wildDrawFourCard = new Card(Color.BLUE, CardType.WILD_DRAW_FOUR, -1);
        assertEquals("WILD_DRAW_FOUR BLUE", wildDrawFourCard.toString());
    }
}
