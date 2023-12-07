package components.players;

import components.Card;
import utils.Color;

import java.util.Random;

// This is a player that just loops through the cards in its hand and plays the first on that is valid.
public class DumbComputerPlayer extends Player {

    public DumbComputerPlayer() {
        super("Simple Computer Player");
    }

    public DumbComputerPlayer(String name) {
        super(name);
    }
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

    // Choose the color randomly.
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
