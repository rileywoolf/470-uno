import components.Game;
import components.players.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        boolean printAI = true;
        List<Player> players = new ArrayList<>();
        players.add(new MediumAIPlayer("Player One", printAI));
        players.add(new HumanPlayer("Player Two"));
        Game game = new Game(players);

        game.play();
    }
}
