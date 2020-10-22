import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class ContinentTest {
    private final Continent north_america = new Continent("North America", 3);

    private final Territory canada = new Territory("Canada", "North America");
    private final Territory usa = new Territory("United States", "North America");
    private final Territory mexico = new Territory("Mexico", "North America");

    @BeforeEach
    public void setup() {
        north_america.addTerritory(canada);
        north_america.addTerritory(usa);
        north_america.addTerritory(mexico);

        canada.setAdjacentTerritory(usa, "east");
        canada.setAdjacentTerritory(mexico, "south");

        usa.setAdjacentTerritory(canada, "west");
        usa.setAdjacentTerritory(mexico, "south");

        mexico.setAdjacentTerritory(usa, "north");
        mexico.setAdjacentTerritory(canada, "north-west");
    }

    @Test
    public void getName() {
        assertEquals(north_america.getName(), "North America");
        assertNotEquals(north_america.getName(), "Canada");
    }

    @Test
    public void setRuler_GetRuler() {
        // Before Ruler is set
        assertEquals("No Ruler", north_america.getRuler());
        assertNotEquals("Canada", north_america.getRuler());

        // Set Ruler
        north_america.setRuler("BillyBobJoe!");

        // After Ruler is set
        assertEquals("BillyBobJoe!", north_america.getRuler());
        assertNotEquals("Canada", north_america.getRuler());

        // Change Ruler
        north_america.setRuler("Tanner Trautrim");

        // After Ruler is set
        assertEquals("Tanner Trautrim", north_america.getRuler());
        assertNotEquals("Canada", north_america.getRuler());
    }

    @Test
    public void addTerritory_getTerritories() {
        ArrayList<Territory> expected = new ArrayList<>();
        expected.add(canada);
        expected.add(usa);
        expected.add(mexico);

        ArrayList<Territory>  actual = north_america.getTerritories();

        // Initial Setup
        if (expected.size() != actual.size()) { fail(); }

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }

        // Add Territory
        Territory baha = new Territory("baha", "North America");
        expected.add(baha);
        north_america.addTerritory(baha);

        if (expected.size() != actual.size()) { fail(); }

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }

    /**
     * This is a visual test only
     */
    @Test
    public void printStatus() {
        north_america.printStatus();
    }
}
