import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class GameBoardTest {
    private final GameBoard gameBoard = new GameBoard(true);

    @BeforeEach
    public void setup(){
        //gameBoard.setTerritoryRuler("Canada", "P1");
        //gameBoard.setTerritoryRuler("United States", "P1");
        //gameBoard.setTerritoryRuler("Mexico", "P2");
    }

    @Test
    public void testRulers(){
        assertEquals("P1", gameBoard.getTerritoryRuler("Canada"));
        assertEquals("P1", gameBoard.getTerritoryRuler("United States"));
        assertEquals("P2", gameBoard.getTerritoryRuler("Mexico"));
    }

    @Test
    public void printStatus(){
        System.out.println(gameBoard.printBoardStatus());
    }

}
