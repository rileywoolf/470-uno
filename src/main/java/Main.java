import components.Game;
import components.SpecialRules;
import components.players.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask the user how many players to play with.
        System.out.println("How many players?");
        int numPlayers = scanner.nextInt();

        // Populate the list of players with the types of players the user wants.
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            System.out.println("What type of player do you want Player " + (i + 1) + " to be?");
            System.out.println("Human (h), Easy Computer (e), Medium Computer (m), Difficult Computer (d)");
            String type = scanner.next();

            switch (type) {
                case "h" -> players.add(new HumanPlayer("Player " + (i + 1), i));
                case "e" -> players.add(new EasyAIPlayer("Player " + (i + 1), i));
                case "m" -> {
                    System.out.print("Do you want to print out the AI's game moves? (true/false) ");
                    boolean printAI = scanner.nextBoolean();
                    players.add(new MediumAIPlayer("Player " + (i + 1), i, printAI));
                }
                case "d" -> {
                    System.out.print("Do you want to print out the AI's game moves? (true/false) ");
                    boolean printAI = scanner.nextBoolean();
                    players.add(new DifficultAIPlayer("Player " + (i + 1), i, printAI));
                }
            }
        }

        // Ask the user what rules they want to play with.
        System.out.print("Do you want to play with special rules? (true/false) ");
        SpecialRules specialRules;
        if (scanner.nextBoolean()) {
//            System.out.print("Allow stacking of draw two cards? (true/false) ");
//            boolean stacking = scanner.nextBoolean();
            // TODO: uncomment lines above when stacking is implemented.
            boolean stacking = false;
            System.out.print("Allow hands to rotate when a 0 is played? (true/false) ");
            boolean zeros = scanner.nextBoolean();
            System.out.print("Allow player to switch hands when a 7 is played? (true/false) ");
            boolean sevens = scanner.nextBoolean();
//            System.out.print("Allow players to jump in when someone plays a card matching one in their hand? (true/false) ");
//            boolean jumpIn = scanner.nextBoolean();
            // TODO: uncomment lines above when jump in is implemented.
            boolean jumpIn = false;
            specialRules = new SpecialRules(stacking, zeros, sevens, jumpIn);
        } else {
            specialRules = new SpecialRules();
        }

        // Ask the user how many games they want to play with those players, count the number of wins for each player.
        System.out.println("How many games do you want to play?");
        int numGames = scanner.nextInt();

        while (numGames < 1) {
            System.out.println("Invalid number of games, please re-enter.");
            numGames = scanner.nextInt();
        }

        if (numGames == 1) {
            Game game = new Game(players, specialRules);
            Player winner = game.play();
            System.out.println("WINNER WINNER CHICKEN DINNER: " + winner.getName());
        } else {
            Map<Player, Integer> playerToWins = new HashMap<>();
            for (int i = 0; i < numGames; i++) {
                Game game = new Game(players, specialRules);
                Player winner = game.play();

                playerToWins.put(winner, playerToWins.getOrDefault(winner, 0) + 1);
            }

            for (Player p : playerToWins.keySet()) {
                System.out.println("PLAYER: " + p.getName() + " PLAYER TYPE: " + p.getPlayerType() + " WINS: "
                        + playerToWins.get(p));
            }

        }

        System.out.println("Thanks for playing! Exiting game...");
    }
}
