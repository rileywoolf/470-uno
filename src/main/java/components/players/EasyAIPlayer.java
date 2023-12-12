package components.players;

import components.Card;
import utils.Color;

import java.util.List;
import java.util.Random;

/**
 * The {@code EasyAIPlayer} class represents a simple computer player in a card game.
 * This player selects the first valid card in its hand and plays it. Additionally, when required
 * to choose a color (e.g., after playing a Wild card), it randomly selects one of the available colors.
 *
 * <p>
 * The class extends the {@link Player} class and implements a straightforward strategy.
 * This class is designed for demonstration and testing purposes, providing a basic AI opponent.
 * </p>
 *
 * @author Riley Woolf
 * @version 1.0
 */
public class EasyAIPlayer extends Player {
    /**
     * The {@code Random} object used for generating random values.
     */
    private final Random rand = new Random();

    /**
     * Constructs a new EasyAIPlayer with the default name "Simple Computer."
     *
     * @param index the index of the player in the player and handSize arrays
     */
    public EasyAIPlayer(int index) {
        super("Simple Computer", index);
    }

    /**
     * Constructs a new EasyAIPlayer with the specified name.
     *
     * @param name the name of the EasyAIPlayer
     * @param index the index of the player in the player and handSize arrays
     */
    public EasyAIPlayer(String name, int index) {
        super(name, index);
    }

    /**
     * Overrides the play method from the {@link Player} class. This EasyAIPlayer
     * loops through its hand and plays the first valid card it encounters.
     *
     * @param topCard the current top card on the table
     * @return the card played by the EasyAIPlayer, or {@code null} if no valid move is possible
     */
    @Override
    public Card play(Card topCard) {
        boolean validMove;
        int index = -1;
        // Loop until a valid card is selected.
        do {
            index++;
            if (index == hand.size()) {
                return null;
            }
            validMove = hand.get(index).validMove(topCard);
        } while (!validMove);

        // Calls UNO when applicable only some of the time.
        if (hand.size() == 2) {
            declaredUno = rand.nextBoolean();
        }
        return hand.remove(index);
    }

    /**
     * Overrides the chooseColor method from the {@link Player} class. This EasyAIPlayer
     * randomly chooses a color when required (e.g., after playing a Wild card).
     *
     * @return the randomly chosen color
     */
    @Override
    public Color chooseColor() {
        Color color;
        switch (rand.nextInt(4)) {
            case 0 -> color = Color.BLUE;
            case 1 -> color = Color.GREEN;
            case 2 -> color = Color.RED;
            default -> color = Color.YELLOW;
        }
        return color;
    }

    /**
     * Overrides the getPlayerToSwitchWith method from the {@link Player} class. This EasyAIPlayer
     * randomly selects another player to switch hands with.
     *
     * @param handSizes a list containing the sizes of hands for each player in the game
     * @return the index of the player to switch hands with
     */
    @Override
    public int getPlayerToSwitchWith(List<Integer> handSizes) {
        while (true) {
            int index = rand.nextInt(handSizes.size());

            if (index != playerIndex) return index;
        }
    }
}
