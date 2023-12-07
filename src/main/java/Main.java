import components.Game;
import components.players.HumanPlayer;
import components.players.Player;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Player> players = new ArrayList<>();
        players.add(new HumanPlayer("Player One"));
        players.add(new HumanPlayer("Player Two"));
        players.add(new HumanPlayer("Player Three"));
        Game game = new Game(players);

        game.play();
    }
}
