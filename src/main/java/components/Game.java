package components;

import components.players.Player;
import utils.CardType;
import utils.Color;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Deck deck;
    private final List<Player> players;
    boolean forwardPlay;
    int currentIndex;

    public Game(List<Player> players) {
        this.players = new ArrayList<>(players);
        this.deck = new Deck();
        this.forwardPlay = true;
        this.currentIndex = 0;

        // Deal the initial cards for the game.
        for (int i = 0; i < 7; i++) {
            for (Player p : this.players) {
                p.addCard(deck.draw());
            }
        }
    }

    // TODO: currently there is no way to call Uno or call out others for missing Uno...
    public void play() {
        Card topCard = deck.draw();
        Player currentPlayer = players.get(currentIndex);

        // Check if a special card was chosen as the top card.
        do {
            switch (topCard.getType()) {
                case REVERSE -> forwardPlay = !forwardPlay;
                case SKIP -> currentIndex = nextPlayer(currentIndex);
                case DRAW_TWO -> drawCards(2);
                case WILD -> topCard.setColor(currentPlayer.chooseColor());
                case WILD_DRAW_FOUR -> topCard = chooseNewStartingCard(topCard);
            }
        } while (topCard.getType() == CardType.WILD_DRAW_FOUR);

        while (true) {
            currentPlayer = players.get(currentIndex);

            Card cardToPlay = currentPlayer.play(topCard);

            // If the player cannot make a move, have them draw a card.
            if (cardToPlay == null) {
                currentPlayer.addCard(deck.draw());
                currentIndex = nextPlayer(currentIndex);
                continue;
            } else {
                topCard = cardToPlay;
            }

            // Logic for special cards (reverse, draw two, skip), and wilds.
            switch(topCard.getType()) {
                case REVERSE -> forwardPlay = !forwardPlay;
                case SKIP -> currentIndex = nextPlayer(currentIndex);
                case DRAW_TWO -> drawCards(2);
                case WILD -> topCard.setColor(currentPlayer.chooseColor());
                case WILD_DRAW_FOUR -> topCard.setColor(wildDrawFour());
            }

            // Check if the player has won the game, exit game loop.
            if (currentPlayer.hasNoCards()) {
                break;
            }

            // Switch to next player's turn.
            currentIndex = nextPlayer(currentIndex);
        }
        System.out.println("WINNER WINNER CHICKEN DINNER: " + currentPlayer.getName());
    }

    // Handles when the first card is a wild draw four.
    private Card chooseNewStartingCard(Card oldCard) {
        // Return the old card to the deck.
        deck.returnCard(oldCard);
        // Draw a new card to be the top card.
        return deck.draw();
    }

    // Calculates the index of the next user based on current user and what direction of play.
    private int nextPlayer(int curr) {
        if (forwardPlay) {
            curr++;
            return curr == players.size() ? 0 : curr;
        } else {
            curr--;
            return curr == -1 ? players.size() - 1 : curr;
        }
    }

    // Gives the specified user the specified number of cards from the deck and skips their turn.
    private void drawCards(int numCards) {
        for (int i = 0; i < numCards; i++) {
            players.get(nextPlayer(currentIndex)).addCard(deck.draw());
        }
        currentIndex = nextPlayer(currentIndex);
    }

    // Handles a wild draw four by giving four to the next player and letting the current player choose the color.
    private Color wildDrawFour()  {
        drawCards(4);
        return players.get(currentIndex).chooseColor();
    }
}
