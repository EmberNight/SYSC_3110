import java.util.*;

/**
 * An implementation of the continents used during the game of Risk
 *
 * @author Jordan Peterkin
 */
public class Continent {

    private final ArrayList<Territory> territories;
    private String name;
    private int value;
    private String ruler;

    /**
     * Default constructor for Continent objects
     */
    public Continent() {
        territories = new ArrayList<>();
        this.value = 0;
        ruler = "";
        this.name = "";
    }

    /**
     * Constructor for Continent objects
     *
     * @param name  The name of the continent
     * @param value The value of the continent
     */
    public Continent(String name, int value) {
        territories = new ArrayList<>();
        this.value = value;
        ruler = "";
        this.name = name;
    }

    /**
     * Returns the name of the continent
     *
     * @return the name of the continent
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the ruler of the continent
     *
     * @return the ruler of the continent
     */
    public String getRuler() {
        return ruler;
    }

    /**
     * Returns the value of the continent
     *
     * @return the value of the continent
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the list of territories encompassed by the continent
     *
     * @return the list of territories encompassed by the continent
     */
    public ArrayList<Territory> getTerritories() {
        return territories;
    }

    /**
     * Sets the name of the continent
     *
     * @param name Name of the continent
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the value of the continent
     *
     * @param value Value of the continent
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Sets the ruler of the continent to the given Player
     *
     * @param ruler The new ruler of the continent
     */
    public void setRuler(String ruler) {
        this.ruler = ruler;
    }

    /**
     * Adds a new territory to the continent's list of encompassed territories
     *
     * @param territory the new territory to be added to the continent's list of encompassed territories
     */
    public void addTerritory(Territory territory) {
        territories.add(territory);
    }

    /**
     * Creates and returns a textual representation of the Continent to the terminal
     *
     * @return A string containing a textual representation of the Continent to the terminal
     */
    public String getStatus() {
        StringBuilder s = new StringBuilder();
        s.append("Continent: ").append(getName()).append("    Ruler: ");
        if (this.getRuler().equals("")) s.append("no current ruler\n");
        else s.append(this.getRuler()).append("\n");
        for (Territory territory : territories) {
            s.append("    ").append(territory.getStatus());
        }
        return s.toString();
    }

}
