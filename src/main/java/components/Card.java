package components;

import utils.CardType;
import utils.Color;

public class Card {
    private Color color;
    private final CardType type;
    private final int number;

    public Card(Color color, CardType type, int number) {
        this.color = color;
        this.type = type;
        this.number = number;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) { this.color = color; }

    public CardType getType() {
        return type;
    }

    public int getNumber() {
        return number;
    }

    // Checks if the given card is valid to play based on the current top of the discard pile.
    public boolean validMove(Card topCard) {
        // If the card to play is a wild, it's valid to play.
        if (this.getType() == CardType.WILD || this.getType() == CardType.WILD_DRAW_FOUR) {
            return true;
        } else if (this.getColor() == topCard.getColor()) {
        // If the cards have the same color, it's a valid move.
            return true;
        } else // If they're the same type of card, then it's a valid move.
            if (this.getType() == CardType.NUMBER) {
        // If they're number cards, check if they're the same number.
            return this.getNumber() == topCard.getNumber();
        } else return this.getType() == topCard.getType();
    }

    @Override
    public String toString() {
        if (type == CardType.NUMBER) {
            return color + " " + number;
        } else if (type == CardType.WILD || type == CardType.WILD_DRAW_FOUR) {
            return type.toString();
        } else {
            return color + " " + type;
        }
    }
}
