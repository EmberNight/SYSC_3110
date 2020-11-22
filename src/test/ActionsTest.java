import org.junit.jupiter.api.Test;
import java.util.ArrayList;

/**
 * @author Ashwin Stoparczyk
 *
 * Test suite for GameActions
 */
public class ActionsTest implements RiskView{

    private final ArrayList<Player> playersList = makePlayerList();
    private final GameBoard gameBoard = new GameBoard(true);
    private final GameActions gameActions = new GameActions(this, playersList, gameBoard);


    public ArrayList<Player> makePlayerList(){ //Helper function to instantiate playersList properly
        ArrayList<Player> playersList = new ArrayList<>();

        for (int i = 1; i <= 3; i++){
            playersList.add(new Player("player " + i));
        }

        return playersList;
    }

    @Test
    public void testAttack(){ //Visual test, displays the outcome of a randomized attack event
        gameActions.attack("canada", "united states", 2, 2);
    }

    @Test
    public void testPass(){ //Visual test, displays the game cycling through each player's turn then back to the first
        passUpdate();
        gameActions.pass();
        gameActions.pass();
        gameActions.pass();
    }

    @Override
    public void attackUpdate(RiskEvent ae) {
        String outcome = "The attacker lost: " + ae.getAttackerLosses() + " armies\n" +
                "The defender lost: " + ae.getDefenderLosses() + " armies\n";

        if (ae.isNewTerritoryRuler()) {
            outcome += "The attacker won the territory";
        }

        if (ae.isNewContinentRuler()) {
            outcome += " and the Continent";
        }

        System.out.println(outcome);
    }

    @Override
    public void passUpdate() { System.out.println("It is now " + gameActions.getActivePlayer() + "'s turn.");

    }

    @Override
    public void updateStatus() {
        System.out.println(gameBoard.toString());
    }

    @Override
    public void movementUpdate(RiskEvent ae) {

    }
    @Override
    public void addArmyUpdate(RiskEvent ae) {

    }
}

