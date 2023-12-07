package components.players;

import components.Card;
import utils.Color;
import utils.ConsoleColors;

import java.util.Scanner;

public class HumanPlayer extends Player {
    private final Scanner scanner;

    public HumanPlayer() {
        super("Human");
        scanner = new Scanner(System.in);
    }

    public HumanPlayer(String name) {
        super(name);
        scanner = new Scanner(System.in);
    }

    // Allows the user to play a card.
    @Override
    public Card play(Card topCard) {
        displayColor(topCard);
        System.out.println("Top card:  " + topCard);
        resetColor();

        displayHand();

        boolean validMove;
        int index;
        // Loop until the user chooses a valid card to play.
        do {
            System.out.println("Select which card to play, or enter -1 to draw: ");
            index = scanner.nextInt();
            if (index == -1) {
                return null;
            }
            // Loop until the user enters a valid card index.
            while (index >= hand.size()) {
                System.out.println("Invalid selection, please choose again.");
                index = scanner.nextInt();
            }

            validMove = hand.get(index).validMove(topCard);
        } while (!validMove);

        return hand.remove(index);
    }

    // Allows the user to select which color they want.
    @Override
    public Color chooseColor() {
        System.out.println("Which color do you want to choose?");
        System.out.println("Red (r), Yellow (y), Green (g), Blue (b)");

        do {
            String color = scanner.next();
            switch (color) {
                case "r":
                    return Color.RED;
                case "y":
                    return Color.YELLOW;
                case "g":
                    return Color.GREEN;
                case "b":
                    return Color.BLUE;
                default:
                    System.out.println("Invalid color, please enter r, y, g, or b to make selection.");
            }
        } while (true);
    }

    private void displayHand() {
        System.out.println(name + "'s hand:");

        for (int i = 0; i < hand.size(); i++) {
            displayColor(hand.get(i));

            System.out.print(i + ": " + hand.get(i) + "  ");
        }
        resetColor();
        System.out.println();
    }

    private void displayColor(Card card) {
        if (card.getColor() == null) {
            return;
        }
        switch (card.getColor()) {
            case RED -> System.out.print(ConsoleColors.RED);
            case BLUE -> System.out.print(ConsoleColors.BLUE);
            case YELLOW -> System.out.print(ConsoleColors.YELLOW);
            case GREEN -> System.out.print(ConsoleColors.GREEN);
            default -> System.out.print(ConsoleColors.RESET);
        }
    }

    private void resetColor() {
        System.out.print(ConsoleColors.RESET);
    }
}
