import java.util.ArrayList;
import java.util.Scanner;

/**
 * Initializes the game of Risk
 *
 * @author trautrim
 */
public class InitializeGame {

    /**
     * Main function of project
     * @param argv Default
     */
    public static void main(String[] argv) {
        ArrayList<Player> players = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of players: ");
        int numPlayers = scanner.nextInt();

        if (numPlayers < 2 || numPlayers > 6) {
            System.out.println("Risk needs 2 to 6 players to play!1");
            System.exit(1);
        }

        for(int i = 0; i < numPlayers; i++) {
            System.out.printf("Player %d enter your name: ", i + 1);
            players.add(new Player(scanner.next()));
        }

        new Actions(players, new GameBoard()).playGame();
    }
}
