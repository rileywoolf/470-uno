package components;

import utils.CardType;
import utils.Color;

/**
 * The {@code Card} class represents a card in a card game, like Uno. Each card has a color, a type, and a number
 * (if applicable). The class provides methods to get and set the card's attributes, check if it's a
 * valid move based on the current top card on the discard pile, and convert the card to a string representation.
 *
 * @author Riley Woolf
 * @version 1.0
 */
public class Card {
    /**
     * The color of the card.
     */
    private Color color;

    /**
     * The type of the card (e.g., Number, Wild, Wild Draw Four).
     */
    private final CardType type;

    /**
     * The number on the card (applicable only for Number cards).
     */
    private final int number;

    /**
     * Constructs a new Card with the specified color, type, and number.
     *
     * @param color  the color of the card
     * @param type   the type of the card (e.g., Number, Wild, Wild Draw Four)
     * @param number the number on the card (applicable only for Number cards)
     */
    public Card(Color color, CardType type, int number) {
        this.color = color;
        this.type = type;
        this.number = number;
    }

    /**
     * Gets the color of the card.
     *
     * @return the color of the card
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color of the card.
     *
     * @param color the new color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Gets the type of the card.
     *
     * @return the type of the card
     */
    public CardType getType() {
        return type;
    }

    /**
     * Gets the number on the card (applicable only for Number cards).
     *
     * @return the number on the card
     */
    public int getNumber() {
        return number;
    }

    /**
     * Checks if the given card is a valid move based on the current top card on the discard pile.
     *
     * @param topCard the current top card on the discard pile
     * @return {@code true} if the move is valid, {@code false} otherwise
     */
    public boolean validMove(Card topCard) {
        if (this.getType() == CardType.WILD || this.getType() == CardType.WILD_DRAW_FOUR) {
            return true;
        } else if (this.getColor() == topCard.getColor()) {
            return true;
        } else if (this.getType() == CardType.NUMBER) {
            return this.getNumber() == topCard.getNumber();
        } else {
            return this.getType() == topCard.getType();
        }
    }

    /**
     * Returns a string representation of the card.
     *
     * @return a string representation of the card
     */
    @Override
    public String toString() {
        if (type == CardType.NUMBER) {
            return color + " " + number;
        } else if (type == CardType.WILD || type == CardType.WILD_DRAW_FOUR) {
            return type + (color == null ? "" : " " + color);
        } else {
            return color + " " + type;
        }
    }
}
