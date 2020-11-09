import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Ashwin Stoparczyk
 * @group 16
 *
 * Test suite for GameBoard
 */
public class GameBoardTest {
    private final GameBoard gameBoard = new GameBoard(true);

    @BeforeEach
    public void setup(){
        gameBoard.setTerritoryRuler("canada", "P1");
        gameBoard.setTerritoryRuler("united states", "P1");
        gameBoard.setTerritoryRuler("mexico", "P2");
    }

    @Test
    public void testRulers(){
        assertEquals("P1", gameBoard.getTerritoryRuler("canada")); //Test for initial rulers
        assertEquals("P1", gameBoard.getTerritoryRuler("united states"));
        assertEquals("P2", gameBoard.getTerritoryRuler("mexico"));

        assertEquals("", gameBoard.getContinent("North America").getRuler()); //Test for initial continent ruler

        gameBoard.setTerritoryRuler("united states", "P3"); //Change all Territory rulers to new player
        assertEquals("P3", gameBoard.getTerritoryRuler("united states"));
        gameBoard.setTerritoryRuler("canada", "P3");
        assertEquals("P3", gameBoard.getTerritoryRuler("canada"));
        gameBoard.setTerritoryRuler("mexico", "P3");
        assertEquals("P3", gameBoard.getTerritoryRuler("mexico"));

        gameBoard.newContinentRuler("canada", "P3"); //Change continent ruler to new Player
        assertEquals("P3", gameBoard.getContinent("North America").getRuler());
    }

    @Test
    public void testRemoval(){
        assertFalse(gameBoard.isPlayerEliminated("P2")); //Make sure P2 is still in the game

        gameBoard.setTerritoryRuler("mexico", "P1"); //Remove P2 from the game
        assertTrue(gameBoard.isPlayerEliminated("P2"));
    }

    @Test
    public void printStatus(){
        System.out.println(gameBoard.printBoardStatus());
    } //Visual test

}
