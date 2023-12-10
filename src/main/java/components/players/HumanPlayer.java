package components.players;

import components.Card;
import utils.Color;
import utils.PrintUtils;

import java.util.Scanner;

/**
 * The {@code HumanPlayer} class represents a human player in a card game.
 * It extends the {@link Player} class, providing functionality for the human player
 * to play cards and choose colors during the game.
 *
 * <p>
 * The player can play a card by selecting the card index from their hand,
 * draw a card by entering -1, or declare Uno by entering -2.
 * </p>
 *
 * <p>
 * The class uses a {@link Scanner} object to read input from the console.
 * </p>
 *
 * @author Riley Woolf
 * @version 1.0
 */
public class HumanPlayer extends Player {
    /**
     * The {@code Scanner} object used to read input from the console.
     */
    private final Scanner scanner;

    /**
     * Constructs a new HumanPlayer with the default name "Human" and initializes the scanner.
     */
    public HumanPlayer() {
        super("Human");
        scanner = new Scanner(System.in);
    }

    /**
     * Constructs a new HumanPlayer with the specified name and initializes the scanner.
     *
     * @param name the name of the HumanPlayer
     */
    public HumanPlayer(String name) {
        super(name);
        scanner = new Scanner(System.in);
    }

    /**
     * Overrides the play method from the {@link Player} class. Allows the human player
     * to play a card by selecting the card index from their hand. The player can also draw
     * a card (-1) or declare Uno (-2).
     *
     * @param topCard the current top card on the table
     * @return the card played by the HumanPlayer, or {@code null} if no valid move is possible
     */
    @Override
    public Card play(Card topCard) {
        PrintUtils.displayTopCard(topCard);
        PrintUtils.displayHand(name, hand);

        boolean validMove = false;
        declaredUno = false;
        int index;

        // Loop until the user chooses a valid card to play.
        do {
            System.out.println("Select which card to play, enter -1 to draw, and -2 to declare UNO: ");
            index = scanner.nextInt();

            if (index == -1) {
                return null;
            } else if (index == -2) {
                declaredUno = true;
                continue;
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

    /**
     * Overrides the chooseColor method from the {@link Player} class.
     * Allows the human player to select a color by entering 'r', 'y', 'g', or 'b'.
     *
     * @return the chosen color by the HumanPlayer
     */
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
}
