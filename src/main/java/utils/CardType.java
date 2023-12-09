package utils;

/**
 * The {@code CardType} enum represents the possible types of cards in a card game.
 * Each card type has a distinct meaning and behavior in the game.
 * <p>
 * The enum includes:
 * <ul>
 *     <li>{@code NUMBER}: Represents a number card with a specific value.</li>
 *     <li>{@code SKIP}: Represents a Skip card that skips the next player's turn.</li>
 *     <li>{@code REVERSE}: Represents a Reverse card that reverses the order of play.</li>
 *     <li>{@code DRAW_TWO}: Represents a Draw Two card that requires the next player to draw two cards.</li>
 *     <li>{@code WILD}: Represents a Wild card.</li>
 *     <li>{@code WILD_DRAW_FOUR}: Represents a Wild Draw Four card that allows the player to choose a color
 *         and requires the next player to draw four cards.</li>
 * </ul>
 * </p>
 * <p>
 * Each card type provides a distinct behavior when played in the context of a card game.
 * </p>
 *
 * @author Riley Woolf
 * @version 1.0
 */
public enum CardType {
    /**
     * Represents a number card with a specific value.
     */
    NUMBER,

    /**
     * Represents a Skip card that skips the next player's turn.
     */
    SKIP,

    /**
     * Represents a Reverse card that reverses the order of play.
     */
    REVERSE,

    /**
     * Represents a Draw Two card that requires the next player to draw two cards.
     */
    DRAW_TWO,

    /**
     * Represents a Wild card.
     */
    WILD,

    /**
     * Represents a Wild Draw Four card that allows the player to choose a color
     * and requires the next player to draw four cards.
     */
    WILD_DRAW_FOUR
}
