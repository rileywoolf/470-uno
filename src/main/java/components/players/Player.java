package components.players;

import components.Card;
import utils.Color;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    protected String name;
    protected List<Card> hand;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public String getName() { return name; }

    public boolean hasNoCards() { return hand.isEmpty(); }

    public void addCard(Card card) {
        hand.add(card);
    }

    public abstract Card play(Card topCard);
    public abstract Color chooseColor();
}
