package utils;

import components.Card;

import java.util.List;

/**
 * The {@code PrintUtils} class provides utility methods for displaying card-related information
 * in the console with color formatting.
 *
 * @author Riley Woolf
 * @version 1.0
 */
public class PrintUtils {

    /**
     * Displays the current hand of a player in the console.
     *
     * @param name the name of the player
     * @param hand the list of cards in the player's hand
     */
    public static void displayHand(String name, List<Card> hand) {
        System.out.println(name + "'s hand:");

        for (int i = 0; i < hand.size(); i++) {
            displayColor(hand.get(i));

            System.out.print(i + ": " + hand.get(i) + "  ");
        }
        resetColor();
        System.out.println();
    }

    /**
     * Displays the color of a card in the console using color formatting.
     *
     * @param card the card whose color is to be displayed
     */
    public static void displayColor(Card card) {
        if (card.getColor() == null) {
            System.out.print(ConsoleColors.RESET);
            return;
        }
        switch (card.getColor()) {
            case RED -> System.out.print(ConsoleColors.RED_BOLD);
            case BLUE -> System.out.print(ConsoleColors.BLUE_BOLD);
            case YELLOW -> System.out.print(ConsoleColors.YELLOW_BOLD);
            case GREEN -> System.out.print(ConsoleColors.GREEN_BOLD);
            default -> System.out.print(ConsoleColors.RESET);
        }
    }

    /**
     * Displays the top card on the table with color formatting in the console.
     *
     * @param topCard the current top card on the table
     */
    public static void displayTopCard(Card topCard) {
        displayColor(topCard);
        System.out.println("Top card:  " + topCard);
        resetColor();
    }

    /**
     * Resets the console color to the default.
     */
    public static void resetColor() {
        System.out.print(ConsoleColors.RESET);
    }
}
