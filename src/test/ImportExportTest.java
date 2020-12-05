import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for ImportExport
 *
 * @author Ashwin Stoparczyk
 */

public class ImportExportTest {

    @Test
    public void testSaveLoad(){ //Some dialogue box shows up stating that the attack phase started, click the button on it to advance the test. It does nothing to affect the test
        Risk baseGame = new Risk("Base game", true);

        ImportExport.saveGame(baseGame);

        Risk savedGame = new Risk("Saved game", true);
        ImportExport.loadGame(savedGame);

        //phase tests
        //assertEquals(baseGame.getPhase(), savedGame.getPhase());

        //gameBoard tests
        assertEquals(baseGame.getGameBoard().getArmy("canada"), savedGame.getGameBoard().getArmy("canada")); //Test armies of Territories
        assertEquals(baseGame.getGameBoard().getArmy("united states"), savedGame.getGameBoard().getArmy("united states"));

        assertEquals(baseGame.getGameBoard().getContinent("North America").getTerritories().get(0).
                getRuler(),savedGame.getGameBoard().getContinent("North America").getTerritories().get(0).getRuler()); //Test Continent's Territories' rulers
        assertEquals(baseGame.getGameBoard().getContinent("North America").getTerritories().get(1).
                getRuler(),savedGame.getGameBoard().getContinent("North America").getTerritories().get(1).getRuler());

        //gameActions tests
        assertEquals(baseGame.getGameActions().getActivePlayer(), savedGame.getGameActions().getActivePlayer()); //Test active players
        assertEquals(baseGame.getGameActions().getCurrentPlayersArmies(), savedGame.getGameActions().getCurrentPlayersArmies()); //Test active player's armies

        //playerList tests
        assertEquals(baseGame.getGameActions().getActivePlayer(), savedGame.getGameActions().getActivePlayer()); //Test player's name

        //Territory tests
        assertEquals(baseGame.getAdjacentTerritory(), savedGame.getAdjacentTerritory()); //Test adjacent Territory
        assertEquals(baseGame.getAttackerTerritory(), savedGame.getAttackerTerritory()); //Test attacker Territory
    }
}
