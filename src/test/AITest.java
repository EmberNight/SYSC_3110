import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * Unit Test for AITurn
 *
 * @author Emmitt Luhning
 */
public class AITest implements RiskView {

    private final ArrayList<Player> playersList = makePlayerList();
    private final GameBoard gameBoard = new GameBoard(true);
    private final GameActions gameActions = new GameActions(this, playersList, gameBoard);
    private final AITurn aiTurn = new AITurn(gameBoard, gameActions);

    public ArrayList<Player> makePlayerList() {
        ArrayList<Player> playersList = new ArrayList<>();
        playersList.add(new Player("player " + 0, true));
        playersList.add(new Player("player " + 1, true));
        playersList.add(new Player("player " + 2, false));
        return playersList;
    }

    @Test
    //Passes to next player if completed turn successfully, until it reaches player 2 (not AI)
    public void testStartTurn() {
        aiTurn.startTurn();
    }

    @Override
    public void attackUpdate(RiskEvent ae) {
    }

    @Override
    public void passUpdate() {
        System.out.println("It is now " + gameActions.getActivePlayer() + "'s turn.");
    }

    @Override
    public void updateStatus() {
    }

    @Override
    public void movementUpdate(RiskEvent ae) {

    }

    @Override
    public void addArmyUpdate(RiskEvent ae) {

    }


}
