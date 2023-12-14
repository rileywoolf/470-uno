package components;

import components.players.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.CardType;
import utils.Color;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTests {

    private Game twoPlayerGame;
    private List<Player> twoPlayers;
    private Game threePlayerGame;
    private List<Player> threePlayers;

    private final Card reverse = new Card(Color.RED, CardType.REVERSE, -1);
    private final Card drawTwo = new Card(Color.RED, CardType.DRAW_TWO, -1);
    private final Card skip = new Card(Color.RED, CardType.SKIP, -1);
    private final Card wild = new Card(null, CardType.WILD, -1);
    private final Card wildFour = new Card(null, CardType.WILD_DRAW_FOUR, -1);

    @BeforeEach
    void setUp() {
        // Initialize the game and players before each test
        twoPlayers = new ArrayList<>();
        twoPlayers.add(new TestPlayer("Player1"));
        twoPlayers.add(new TestPlayer("Player2"));
        twoPlayerGame = new Game(twoPlayers, new SpecialRules());

        threePlayers = new ArrayList<>();
        threePlayers.add(new TestPlayer("Player1"));
        threePlayers.add(new TestPlayer("Player2"));
        threePlayers.add(new TestPlayer("Player3"));
        threePlayerGame = new Game(threePlayers, new SpecialRules());
    }

    @Test
    void testGameInitialization() {
        // Verify that the game and deck are properly initialized
        assertNotNull(twoPlayerGame);
        assertNotNull(twoPlayerGame.getDeck());
        assertEquals(2, twoPlayerGame.getPlayers().size());

        assertNotNull(threePlayerGame);
        assertNotNull(threePlayerGame.getDeck());
        assertEquals(3, threePlayerGame.getPlayers().size());
    }

    @Test
    void testNextPlayer() {
        // Verify that the next player is calculated correctly in forward play.
        assertEquals(1, twoPlayerGame.nextPlayer(0));
        assertEquals(0, twoPlayerGame.nextPlayer(1));

        // Switch to backward play and verify that the next player is calculated correctly in backward play.
        twoPlayerGame.reversePlay();

        assertEquals(1, twoPlayerGame.nextPlayer(0));
        assertEquals(0, twoPlayerGame.nextPlayer(1));

        // Make sure the next player is calculated correctly for forward play with 3 players.
        assertEquals(1, threePlayerGame.nextPlayer(0));
        assertEquals(2, threePlayerGame.nextPlayer(1));
        assertEquals(0, threePlayerGame.nextPlayer(2));

        // Switch to backward play and verify that the next player is calculated correctly in backward play.
        threePlayerGame.reversePlay();

        assertEquals(2, threePlayerGame.nextPlayer(0));
        assertEquals(1, threePlayerGame.nextPlayer(2));
        assertEquals(0, threePlayerGame.nextPlayer(1));
    }

    @Test
    void testDrawCards() {
        // Verify that drawing cards adds them to the player's hand
        twoPlayerGame.drawCards(2, 0, false);
        assertEquals(9, twoPlayerGame.getPlayers().get(0).getHand().size());
    }

    //----------------------------------------------SPECIAL TOP CARD TESTS----------------------------------------------
    // Make sure the special top cards are handled correctly for both 2 and 3 player games.
    @Test
    void testHandleSpecialTopCard_Reverse() {
        Card result = twoPlayerGame.handleSpecialTopCard(reverse);
        assertFalse(twoPlayerGame.isForwardPlay());
        assertEquals(reverse, result);
        result = twoPlayerGame.handleSpecialTopCard(reverse);
        assertTrue(twoPlayerGame.isForwardPlay());
        assertEquals(reverse, result);

        result = threePlayerGame.handleSpecialTopCard(reverse);
        assertFalse(threePlayerGame.isForwardPlay());
        assertEquals(reverse, result);
        result = threePlayerGame.handleSpecialTopCard(reverse);
        assertTrue(threePlayerGame.isForwardPlay());
        assertEquals(reverse, result);
    }
    @Test
    void testHandleSpecialTopCard_Skip() {
        assertEquals(0, twoPlayerGame.getCurrentIndex());
        Card result = twoPlayerGame.handleSpecialTopCard(skip);
        assertEquals( 1, twoPlayerGame.getCurrentIndex());
        assertEquals(skip, result);

        assertEquals(0, threePlayerGame.getCurrentIndex());
        result = threePlayerGame.handleSpecialTopCard(skip);
        assertEquals(1, threePlayerGame.getCurrentIndex());
        assertEquals(skip, result);
    }
    @Test
    void testHandleSpecialTopCard_DrawTwo() {
        assertEquals(0, twoPlayerGame.getCurrentIndex());
        assertEquals(7, twoPlayerGame.getPlayers().get(0).getHand().size());
        Card result = twoPlayerGame.handleSpecialTopCard(drawTwo);
        assertEquals(1, twoPlayerGame.getCurrentIndex());
        assertEquals(9, twoPlayerGame.getPlayers().get(0).getHand().size());
        assertEquals(drawTwo, result);

        assertEquals(0, threePlayerGame.getCurrentIndex());
        assertEquals(7, threePlayerGame.getPlayers().get(0).getHand().size());
        result = threePlayerGame.handleSpecialTopCard(drawTwo);
        assertEquals(1, threePlayerGame.getCurrentIndex());
        assertEquals(9, threePlayerGame.getPlayers().get(0).getHand().size());
        assertEquals(drawTwo, result);
    }
    @Test
    void testHandleSpecialTopCard_Wild() {
        assertEquals(0, twoPlayerGame.getCurrentIndex());
        Card result = twoPlayerGame.handleSpecialTopCard(new Card(null, CardType.WILD, -1));
        assertNotEquals(wild, result);
        assertEquals(Color.BLUE, result.getColor());

        assertEquals(0, threePlayerGame.getCurrentIndex());
        result = threePlayerGame.handleSpecialTopCard(new Card(null, CardType.WILD, -1));
        assertNotEquals(wild, result);
        assertEquals(Color.BLUE, result.getColor());
    }
    @Test
    void testHandleSpecialTopCard_WildDrawFour() {
        assertEquals(0, twoPlayerGame.getCurrentIndex());
        Card result = twoPlayerGame.handleSpecialTopCard(wildFour);
        assertNotEquals(wildFour, result);
        assertNotEquals(CardType.WILD_DRAW_FOUR, result.getType());

        assertEquals(0, threePlayerGame.getCurrentIndex());
        result = threePlayerGame.handleSpecialTopCard(wildFour);
        assertNotEquals(wildFour, result);
        assertNotEquals(CardType.WILD_DRAW_FOUR, result.getType());
    }

    //------------------------------------------------SPECIAL CARD TESTS------------------------------------------------
    @Test
    void testHandleSpecialCards_Reverse() {
        Card result = twoPlayerGame.handleSpecialCards(reverse);
        assertFalse(twoPlayerGame.isForwardPlay());
        assertEquals(reverse, result);
        result = twoPlayerGame.handleSpecialCards(reverse);
        assertTrue(twoPlayerGame.isForwardPlay());
        assertEquals(reverse, result);

        result = threePlayerGame.handleSpecialCards(reverse);
        assertFalse(threePlayerGame.isForwardPlay());
        assertEquals(reverse, result);
        result = threePlayerGame.handleSpecialCards(reverse);
        assertTrue(threePlayerGame.isForwardPlay());
        assertEquals(reverse, result);
    }
    @Test
    void testHandleSpecialCards_Skip() {
        assertEquals(0, twoPlayerGame.getCurrentIndex());
        Card result = twoPlayerGame.handleSpecialCards(skip);
        assertEquals( 1, twoPlayerGame.getCurrentIndex());
        assertEquals(skip, result);

        assertEquals(0, threePlayerGame.getCurrentIndex());
        result = threePlayerGame.handleSpecialCards(skip);
        assertEquals(1, threePlayerGame.getCurrentIndex());
        assertEquals(skip, result);
    }
    @Test
    void testHandleSpecialCards_DrawTwo() {
        assertEquals(0, twoPlayerGame.getCurrentIndex());
        assertEquals(7, twoPlayerGame.getPlayers().get(1).getHand().size());
        Card result = twoPlayerGame.handleSpecialCards(drawTwo);
        assertEquals(1, twoPlayerGame.getCurrentIndex());
        assertEquals(9, twoPlayerGame.getPlayers().get(1).getHand().size());
        assertEquals(drawTwo, result);

        assertEquals(0, threePlayerGame.getCurrentIndex());
        assertEquals(7, threePlayerGame.getPlayers().get(1).getHand().size());
        result = threePlayerGame.handleSpecialCards(drawTwo);
        assertEquals(1, threePlayerGame.getCurrentIndex());
        assertEquals(9, threePlayerGame.getPlayers().get(1).getHand().size());
        assertEquals(drawTwo, result);
    }
    @Test
    void testHandleSpecialCards_Wild() {
        assertEquals(0, twoPlayerGame.getCurrentIndex());
        Card result = twoPlayerGame.handleSpecialCards(new Card(null, CardType.WILD, -1));
        assertNotEquals(wild, result);
        assertEquals(Color.BLUE, result.getColor());

        assertEquals(0, threePlayerGame.getCurrentIndex());
        result = threePlayerGame.handleSpecialCards(new Card(null, CardType.WILD, -1));
        assertNotEquals(wild, result);
        assertEquals(Color.BLUE, result.getColor());
    }
    @Test
    void testHandleSpecialCards_WildDrawFour() {
        assertEquals(0, twoPlayerGame.getCurrentIndex());
        Card result = twoPlayerGame.handleSpecialCards(new Card(null, CardType.WILD_DRAW_FOUR, -1));
        assertNotEquals(wildFour, result);
        assertEquals(Color.BLUE, result.getColor());
        assertEquals(11, twoPlayerGame.getPlayers().get(1).getHand().size());

        assertEquals(0, threePlayerGame.getCurrentIndex());
        result = threePlayerGame.handleSpecialCards(new Card(null, CardType.WILD_DRAW_FOUR, -1));
        assertNotEquals(wildFour, result);
        assertEquals(Color.BLUE, result.getColor());
        assertEquals(11, threePlayerGame.getPlayers().get(1).getHand().size());
    }

    class TestPlayer extends Player {
        public TestPlayer(String name) {
            super(name, 1);
        }

        @Override
        public Card play(Card topCard) {
            return null;
        }

        // Always choose blue.
        @Override
        public Color chooseColor() {
            return Color.BLUE;
        }

        @Override
        public int getPlayerToSwitchWith(List<Integer> handSizes, boolean forwardPlay) {
            return 0;
        }
    }
}
