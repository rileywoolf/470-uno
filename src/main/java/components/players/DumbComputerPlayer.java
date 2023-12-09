package components.players;

import components.Card;
import utils.Color;

import java.util.Random;

/**
 * The {@code DumbComputerPlayer} class represents a simple computer player in a card game.
 * This player selects the first valid card in its hand and plays it. Additionally, when required
 * to choose a color (e.g., after playing a Wild card), it randomly selects one of the available colors.
 *
 * @author Riley Woolf
 * @version 1.0
 */
public class DumbComputerPlayer extends Player {

    /**
     * Constructs a new DumbComputerPlayer with the default name "Simple Computer Player."
     */
    public DumbComputerPlayer() {
        super("Simple Computer Player");
    }

    /**
     * Constructs a new DumbComputerPlayer with the specified name.
     *
     * @param name the name of the DumbComputerPlayer
     */
    public DumbComputerPlayer(String name) {
        super(name);
    }

    /**
     * Overrides the play method from the {@link Player} class. This DumbComputerPlayer
     * loops through its hand and plays the first valid card it encounters.
     *
     * @param topCard the current top card on the table
     * @return the card played by the DumbComputerPlayer, or {@code null} if no valid move is possible
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

        return hand.remove(index);
    }

    /**
     * Overrides the chooseColor method from the {@link Player} class. This DumbComputerPlayer
     * randomly chooses a color when required (e.g., after playing a Wild card).
     *
     * @return the randomly chosen color
     */
    @Override
    public Color chooseColor() {
        Random random = new Random();
        Color color;
        switch (random.nextInt(4)) {
            case 0 -> color = Color.BLUE;
            case 1 -> color = Color.GREEN;
            case 2 -> color = Color.RED;
            default -> color = Color.YELLOW;
        }
        return color;
    }
}
