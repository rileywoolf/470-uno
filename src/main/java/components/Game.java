package components;

import components.players.Player;
import utils.CardType;
import utils.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Game} class represents the main logic of a card game. It manages the game state,
 * player turns, card plays, and determines the winner of the game.
 * <p>
 * The game follows standard Uno rules with additional logic to handle special cards
 * (e.g., Reverse, Skip, Draw Two, Wild, Wild Draw Four) and player actions.
 * </p>
 * <p>
 * The class uses the {@link Deck} and {@link Player} classes to handle the deck of cards
 * and player interactions, respectively.
 * </p>
 * <p>
 * Usage example:
 * <pre>
 * {@code
 * List<Player> players = new ArrayList<>();
 * // Add players to the list...
 * Game unoGame = new Game(players);
 * unoGame.play();
 * }
 * </pre>
 * </p>
 * <p>
 * Note: This implementation assumes standard Uno rules and might require modification for
 * variations in specific Uno game implementations.
 * </p>
 *
 * @author Riley Woolf
 * @version 1.0
 */
public class Game {
    /**
     * The penalty for not calling Uno when having only one card left.
     */
    private static final int UNO_NO_CALL_PENALTY = 2;

    /**
     * The deck of cards used in the game.
     */
    private final Deck deck;

    /**
     * The list of players participating in the game.
     */
    private final List<Player> players;

    /**
     * Flag indicating the direction of play (true for forward, false for backward).
     */
    private boolean forwardPlay;

    /**
     * The index of the current player in the list of players.
     */
    private int currentIndex;

    /**
     * Constructs a new Game with the specified list of players and initializes the game state.
     *
     * @param players the list of players participating in the game
     */
    public Game(List<Player> players) {
        this.players = new ArrayList<>(players);
        this.deck = new Deck();
        this.forwardPlay = true;
        this.currentIndex = 0;

        // Deal the initial cards for the game.
        for (int i = 0; i < 7; i++) {
            for (Player p : this.players) {
                p.addCard(deck.draw());
            }
        }
    }

    /**
     * Starts and manages the game. It handles player turns, card plays, special card effects,
     * and determines the winner of the game.
     */
    public void play() {
        // Game initialization
        Card topCard = deck.draw();
        Player currentPlayer = players.get(currentIndex);

        // Check if a special card was chosen as the top card.
        do {
            switch (topCard.getType()) {
                case REVERSE -> forwardPlay = !forwardPlay;
                case SKIP -> currentIndex = nextPlayer(currentIndex);
                case DRAW_TWO -> {
                    drawCards(2, currentIndex);
                    currentIndex = nextPlayer(currentIndex);
                    currentPlayer = players.get(currentIndex);
                }
                case WILD -> topCard.setColor(currentPlayer.chooseColor());
                case WILD_DRAW_FOUR -> topCard = chooseNewStartingCard(topCard);
            }
        } while (topCard.getType() == CardType.WILD_DRAW_FOUR);

        // Main game loop
        while (true) {
            currentPlayer = players.get(currentIndex);

            Card cardToPlay = currentPlayer.play(topCard);

            // If the player cannot make a move, have them draw a card.
            if (cardToPlay == null) {
                currentPlayer.addCard(deck.draw());
                currentIndex = nextPlayer(currentIndex);
                continue;
            } else {
                topCard = cardToPlay;

                // If the player did not declare Uno and only has one card left, make them draw cards.
                if (currentPlayer.hasUno() && !currentPlayer.declaredUno()) {
                    drawCards(UNO_NO_CALL_PENALTY, currentIndex);
                }
            }

            // Logic for special cards (reverse, draw two, skip), and wilds.
            switch(topCard.getType()) {
                case REVERSE -> forwardPlay = !forwardPlay;
                case SKIP -> currentIndex = nextPlayer(currentIndex);
                case DRAW_TWO -> drawCards(2, nextPlayer(currentIndex));
                case WILD -> topCard.setColor(currentPlayer.chooseColor());
                case WILD_DRAW_FOUR -> topCard.setColor(wildDrawFour());
            }

            // Check if the player has won the game, exit game loop.
            if (currentPlayer.hasNoCards()) {
                break;
            }

            // Switch to the next player's turn.
            currentIndex = nextPlayer(currentIndex);
        }

        System.out.println("WINNER WINNER CHICKEN DINNER: " + currentPlayer.getName());
    }

    /**
     * Handles when the first card is a Wild Draw Four by returning the old card to the deck
     * and drawing a new card to be the top card.
     *
     * @param oldCard the old Wild Draw Four card
     * @return the new top card for the game
     */
    private Card chooseNewStartingCard(Card oldCard) {
        // Return the old card to the deck.
        deck.returnCard(oldCard);
        // Draw a new card to be the top card.
        return deck.draw();
    }

    /**
     * Calculates the index of the next player based on the current player and the direction of play.
     *
     * @param curr the index of the current player
     * @return the index of the next player
     */
    private int nextPlayer(int curr) {
        if (forwardPlay) {
            curr++;
            return curr == players.size() ? 0 : curr;
        } else {
            curr--;
            return curr == -1 ? players.size() - 1 : curr;
        }
    }

    /**
     * Gives the specified user the specified number of cards from the deck and skips their turn.
     *
     * @param numCards the number of cards to draw
     * @param playerIndex the index of the player who will draw the cards
     */
    private void drawCards(int numCards, int playerIndex) {
        for (int i = 0; i < numCards; i++) {
            players.get(playerIndex).addCard(deck.draw());
        }

        if (playerIndex != currentIndex) {
            currentIndex = nextPlayer(currentIndex);
        }
    }

    /**
     * Handles a Wild Draw Four by giving four cards to the next player and letting the current player
     * choose the color for the top card.
     *
     * @return the chosen color for the top card
     */
    private Color wildDrawFour()  {
        drawCards(4, nextPlayer(currentIndex));
        return players.get(currentIndex).chooseColor();
    }
}
