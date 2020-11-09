import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.ArrayList;

public class ActionsTest implements RiskView{

    private final ArrayList<Player> playersList = makePlayerList();
    private final GameBoard gameBoard = new GameBoard(true);
    private final GameActions gameActions = new GameActions(this, playersList, gameBoard);


    public ArrayList<Player> makePlayerList(){
        ArrayList<Player> playersList = new ArrayList<>();

        for (int i = 1; i <= 3; i++){
            playersList.add(new Player("player " + i));
        }

        return playersList;
    }

    @Test
    public void testAttack(){
        gameActions.attack("canada", "united states", 2, 2);

    }

    @Test
    public void testPass(){
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
}

