import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TerritoryTest {
    private final Territory canada = new Territory("Canada", "North America");
    private final Territory usa = new Territory("United States", "North America");
    private final Territory mexico = new Territory("Mexico", "North America");

    @BeforeEach
    public void setup() {
        canada.setAdjacentTerritory(usa, "east");
        canada.setAdjacentTerritory(mexico, "south");

        usa.setAdjacentTerritory(canada, "west");
        usa.setAdjacentTerritory(mexico, "south");

        mexico.setAdjacentTerritory(usa, "north");
        mexico.setAdjacentTerritory(canada, "north-west");
    }

    @Test
    public void getContinentName() {
        assertEquals("North America", canada.getContinentName());
        assertEquals("North America", usa.getContinentName());
        assertEquals("North America", mexico.getContinentName());

        assertNotEquals("France", canada.getContinentName());
        assertNotEquals("Germany", usa.getContinentName());
        assertNotEquals("Canada", mexico.getContinentName());
    }

    @Test
    public void setRuler_GetRuler() {
        // Before Ruler is set
        assertEquals("", canada.getRuler());
        assertNotEquals("Canada", canada.getRuler());

        // Set Ruler
        usa.setRuler("BillyBobJoe!");

        // After Ruler is set
        assertEquals("BillyBobJoe!", usa.getRuler());
        assertNotEquals("Canada", usa.getRuler());

        // Change Ruler
        mexico.setRuler("Tanner Trautrim");

        // After Ruler is set
        assertEquals("Tanner Trautrim", mexico.getRuler());
        assertNotEquals("Canada", mexico.getRuler());
    }

    @Test
    public void getName() {
        assertEquals("Canada", canada.getName());
        assertEquals("United States", usa.getName());
        assertEquals("Mexico", mexico.getName());

        assertNotEquals("Canada", usa.getName());
        assertNotEquals("United States", mexico.getName());
        assertNotEquals("Mexico", canada.getName());
    }

    @Test
    public void setArmy_GetArmy() {
        assertEquals(0, canada.getArmy());
        assertEquals(0, usa.getArmy());
        assertEquals(0, mexico.getArmy());

        canada.setArmy(10);
        usa.setArmy(2);
        mexico.setArmy(12);

        assertEquals(10, canada.getArmy());
        assertEquals(2, usa.getArmy());
        assertEquals(12, mexico.getArmy());

        canada.setArmy(-10);
        usa.setArmy(-2);
        mexico.setArmy(-12);

        assertEquals(0, canada.getArmy());
        assertEquals(0, usa.getArmy());
        assertEquals(0, mexico.getArmy());
    }

    @Test
    public void setAdjacentTerritory_getAdjacentTerritory() {
        Map<String, Territory> expected = new HashMap<>();

        expected.put("east", usa);
        expected.put("west", canada);
        expected.put("north-west", canada);

        assertEquals(expected.get("east"), canada.getAdjacentTerritory("east"));
        assertEquals(expected.get("west"), usa.getAdjacentTerritory("west"));
        assertEquals(expected.get("north-west"), mexico.getAdjacentTerritory("north-west"));

        assertNotEquals(expected.get("east"), mexico.getAdjacentTerritory("north-west"));
    }

    @Test
    public void printStatus() {
        canada.printStatus();
    }
}
