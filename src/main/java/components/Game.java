package components;

import components.players.Player;
import utils.CardType;
import utils.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Game} class represents the main logic of a game of Uno. It manages the game state,
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
 * <p>
 * This class provides methods for managing the main game loop, handling special card actions,
 * and updating the game state accordingly. It also contains methods for accessing information
 * about the current game state, such as the current player, deck, and direction of play.
 * </p>
 *
 * @author Riley Woolf
 * @version 1.1
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
     * Keeps track of any special rules for the game.
     */
    private final SpecialRules specialRules;

    /**
     * The list of players participating in the game.
     */
    private final List<Player> players;

    /**
     * The list of players participating in the game.
     */
    private final List<Integer> handSizes;

    /**
     * Flag indicating the direction of play (true for forward, false for backward).
     */
    private boolean forwardPlay;

    /**
     * The index of the current player in the list of players.
     */
    private int currentIndex;

    /**
     * The current player in the list of players.
     */
    private Player currentPlayer;

    /**
     * Constructs a new Game with the specified list of players and initializes the game state.
     *
     * @param players the list of players participating in the game
     * @param specialRules any special rules for the game
     */
    public Game(List<Player> players, SpecialRules specialRules) {
        this.players = new ArrayList<>(players);
        this.handSizes = new ArrayList<>();
        this.deck = new Deck();
        this.forwardPlay = true;
        this.currentIndex = 0;
        this.currentPlayer = players.get(currentIndex);
        this.specialRules = specialRules;

        // Deal the initial cards for the game.
        for (Player p : this.players) {
            for (int i = 0; i < 7; i++) {
                p.addCard(deck.draw());
            }
            handSizes.add(7);
        }
        for (int i = 0; i < 7; i++) {
            for (Player p : this.players) {
                p.addCard(deck.draw());
            }
        }
    }

    /**
     * Gets the deck used in the game.
     *
     * @return the deck used in the game
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Gets the list of players participating in the game.
     *
     * @return the list of players participating in the game
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Gets the index of the current player in the list of players.
     *
     * @return the index of the current player
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * Gets the current direction of play.
     *
     * @return true if the play is in forward direction, false otherwise
     */
    public boolean isForwardPlay() {
        return forwardPlay;
    }

    /**
     * Starts and manages the game. It handles player turns, card plays, special card effects,
     * and determines the winner of the game.
     */
    public Player play() {
        // Game initialization
        Card topCard = deck.draw();

        // Check if a special card was chosen as the top card.
        topCard = handleSpecialTopCard(topCard);

        // Main game loop
        while (true) {
            currentPlayer = players.get(currentIndex);

            Card card = getCard(topCard);
            if (card == null) continue;

            // Update hand size for the current player.
            handSizes.set(currentIndex, handSizes.get(currentIndex) - 1);

            topCard = card;
            topCard = handleSpecialCards(topCard);

            // Check if the player has won the game, exit game loop.
            if (currentPlayer.hasNoCards()) {
                break;
            }

            // Switch to the next player's turn.
            currentIndex = nextPlayer(currentIndex);
        }

        return currentPlayer;
    }

    /**
     * Handles special actions when the first card is a special card (Reverse, Skip, Draw Two, Wild, Wild Draw Four).
     * <p>
     * This method iterates through the special actions associated with the first card drawn in the game.
     * The loop continues until a non-Wild Draw Four card is encountered, ensuring that the game cannot start
     * with a Wild Draw Four.
     * </p>
     *
     * @param topCard the first card drawn in the game
     * @return the updated top card after handling special actions
     */
    public Card handleSpecialTopCard(Card topCard) {
        do {
            switch (topCard.getType()) {
                case REVERSE -> reversePlay();
                case SKIP -> currentIndex = nextPlayer(currentIndex);
                case DRAW_TWO -> {
                    drawCards(2, currentIndex, true);
                    currentPlayer = players.get(currentIndex);
                }
                case WILD -> topCard.setColor(currentPlayer.chooseColor());
                case WILD_DRAW_FOUR -> topCard = chooseNewStartingCard(topCard);
                case NUMBER -> {
                    if (specialRules.isZerosRotate() && topCard.getNumber() == 0) {
                        zeroCardRotateHands();
                    }

                    if (specialRules.isSevensSwitchHands() && topCard.getNumber() == 7) {
                        sevenCardSwitchHands();
                    }
                }
            }
        } while (topCard.getType() == CardType.WILD_DRAW_FOUR);

        return topCard;
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
     * Gets the next card to be played by the current player, handling drawing cards if necessary.
     *
     * @param topCard the current top card on the discard pile
     * @return the next card to be played
     */
    public Card getCard(Card topCard) {
        Card cardToPlay = currentPlayer.play(topCard);

        // If the player cannot make a move, have them draw a card.
        if (cardToPlay == null) {
            currentPlayer.addCard(deck.draw());
            currentIndex = nextPlayer(currentIndex);
            return null;
        } else {
            topCard = cardToPlay;

            // If the player did not declare Uno and only has one card left, make them draw cards.
            if (currentPlayer.hasUno() && !currentPlayer.declaredUno()) {
                drawCards(UNO_NO_CALL_PENALTY, currentIndex, false);
            }
        }
        return topCard;
    }

    /**
     * Handles special actions based on the type of the current top card.
     * <p>
     * This method processes special actions associated with a specific card type, such as Reverse, Skip, Draw Two,
     * Wild, Wild Draw Four, or specific number cards when special rules are enabled.
     * The special actions are executed based on the type of the current top card, potentially
     * modifying the game state.
     * </p>
     *
     * @param topCard the current top card on the discard pile
     * @return the updated top card after handling special actions
     */
    public Card handleSpecialCards(Card topCard) {
        // Logic for special cards (reverse, draw two, skip), and wilds.
        switch (topCard.getType()) {
            case REVERSE -> reversePlay();
            case SKIP -> currentIndex = nextPlayer(currentIndex);
            case DRAW_TWO -> drawCards(2, nextPlayer(currentIndex), true);
            case WILD -> topCard.setColor(currentPlayer.chooseColor());
            case WILD_DRAW_FOUR -> topCard.setColor(wildDrawFour());
            case NUMBER -> {
                if (specialRules.isZerosRotate() && topCard.getNumber() == 0) {
                    zeroCardRotateHands();
                }

                if (specialRules.isSevensSwitchHands() && topCard.getNumber() == 7) {
                    sevenCardSwitchHands();
                }
            }
        }
        return topCard;
    }

    /**
     * Reverses the direction of play.
     */
    public void reversePlay() {
        forwardPlay = !forwardPlay;
    }

    /**
     * Calculates the index of the next player based on the current player and the direction of play.
     *
     * @param curr the index of the current player
     * @return the index of the next player
     */
    public int nextPlayer(int curr) {
        if (forwardPlay) {
            curr++;
            return curr == players.size() ? 0 : curr;
        } else {
            curr--;
            return curr == -1 ? players.size() - 1 : curr;
        }
    }

    /**
     * Gives the specified user the specified number of cards from the deck and optionally skips their turn.
     *
     * @param numCards       the number of cards to draw
     * @param playerIndex    the index of the player who will draw the cards
     * @param goToNextPlayer true if the turn should be skipped, false otherwise
     */
    public void drawCards(int numCards, int playerIndex, boolean goToNextPlayer) {
        for (int i = 0; i < numCards; i++) {
            players.get(playerIndex).addCard(deck.draw());
        }

        // Update the card count for that player.
        handSizes.set(playerIndex, handSizes.get(playerIndex) + numCards);

        if (goToNextPlayer) {
            currentIndex = nextPlayer(currentIndex);
        }
    }

    /**
     * Handles a Wild Draw Four by giving four cards to the next player and letting the current player
     * choose the color for the top card.
     *
     * @return the chosen color for the top card
     */
    public Color wildDrawFour() {
        drawCards(4, nextPlayer(currentIndex), true);
        return players.get(currentIndex).chooseColor();
    }

    /**
     * Zero Card Rotation:
     * <p>
     * Rotates the hands of all players based on the presence of a zero card.
     * If there are only two players, their hands are directly swapped. Otherwise,
     * the hands are rotated either forward or backward based on the current direction of play.
     * The first and last elements of the list are also swapped to complete the rotation.
     * </p>
     */
    public void zeroCardRotateHands() {
        if (players.size() == 2) {
            swapHands(players.get(0), players.get(1));
            return;
        }

        if (forwardPlay) {
            for (int i = 0; i < players.size() - 1; i++) {
                swapHands(players.get(i), players.get(i + 1));
            }
        } else {
            for (int i = players.size() - 1; i > 0; i--) {
                swapHands(players.get(i), players.get(i - 1));
            }
        }

        // Swap the first and last elements of the list.
        swapHands(players.get(0), players.get(players.size() - 1));
    }

    /**
     * Seven Card Switch Hands:
     * <p>
     * Initiates a hand-switching action between the current player and another player,
     * determined by the current player's strategy (getPlayerToSwitchWith method).
     * </p>
     */
    public void sevenCardSwitchHands() {
        swapHands(currentPlayer, players.get(currentPlayer.getPlayerToSwitchWith(handSizes, forwardPlay)));
    }

    /**
     * Swaps the hands of two players.
     * <p>
     * This method exchanges the hands of two players, allowing for specific game scenarios
     * where players need to switch hands. It is used in the implementation of special actions
     * such as zero card rotation and seven card hand-switching.
     * </p>
     *
     * @param playerOne the first player
     * @param playerTwo the second player
     */
    private void swapHands(Player playerOne, Player playerTwo) {
        List<Card> temp = new ArrayList<>(playerOne.getHand());
        playerOne.setHand(playerTwo.getHand());
        playerTwo.setHand(temp);
    }
}
