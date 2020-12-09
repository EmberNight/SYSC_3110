import java.util.*;

/**
 * An implementation of the territories used during the game of Risk
 *
 * @author Jordan Peterkin
 */
public class Territory implements java.io.Serializable {

    private final String continentName;
    private final String name;
    private final Map<String, Territory> adjacentTerritories;
    private String ruler;
    private int army;

    /**
     * Constructor for Territory objects
     *
     * @param name          The name of the territory
     * @param continentName The name of the continent the territory is encompassed by
     */
    public Territory(String name, String continentName) {
        this.name = name.toLowerCase();
        this.continentName = continentName;
        army = 0;
        ruler = "";
        adjacentTerritories = new HashMap<>();
    }

    /**
     * Returns the name of the continent the territory is encompassed by
     *
     * @return the name of the continent the territory is encompassed by
     */
    public String getContinentName() {
        return continentName;
    }

    /**
     * Returns one of the Territory's adjacent territories specified by the given key
     *
     * @param key The key of the adjacent Territory to be returned
     * @return one of the Territory's adjacent territories specified by the given key
     */
    public Territory getAdjacentTerritory(String key) {
        return adjacentTerritories.get(key);
    }

    /**
     * Returns the set of the Territory's adjacent territories
     *
     * @return the set of the Territory's adjacent territories
     */
    public Collection<Territory> getAdjacentTerritories() {
        return adjacentTerritories.values();
    }

    /**
     * Returns the name of the Territory
     *
     * @return the name of the Territory
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the strength of the army currently occupying the Territory
     *
     * @return the strength of the army currently occupying the Territory
     */
    public int getArmy() {
        return army;
    }

    /**
     * Returns the current ruler of the Territory
     *
     * @return the current ruler of the Territory
     */
    public String getRuler() {
        return ruler;
    }

    /**
     * Adds a given territory to the map of the Territory's adjacent territories
     *
     * @param territory The territory to be added to the map of the Territory's adjacent territories
     * @param key       The key of the territory to be added to the map of the Territory's adjacent territories
     */
    public void setAdjacentTerritory(Territory territory, String key) {
        adjacentTerritories.put(key.toLowerCase(), territory);
    }

    /**
     * Sets the ruler of the territory to the given Player
     *
     * @param newRuler The new ruler of the territory
     */
    public void setRuler(String newRuler) {
        ruler = newRuler;
    }

    /**
     * Set the strength of the army currently occupying the Territory to the given value
     *
     * @param army The new strength of the army currently occupying the Territory to the given value
     */
    public void setArmy(int army) {
        this.army = Math.max(army, 0); // Set whatever is higher army or 0
    }

    /**
     * Adds the strength of the given army to the army currently occupying the Territory
     *
     * @param army The strength of the army to be added to the army currently occupying the Territory
     */
    public void addArmy(int army) {
        this.army += Math.abs(army);
    }

    /**
     * Removes the strength of the given army from the army currently occupying the Territory
     *
     * @param army The strength of the army to be removed from the army currently occupying the Territory
     */
    public void removeArmy(int army) {
        this.army -= Math.abs(army);
        this.army = Math.max(this.army, 0);
    }

    /**
     * Creates and returns a textual representation of the Territory to the terminal
     *
     * @return A string containing a textual representation of the Territory to the terminal
     */
    public String getStatus() {
        StringBuilder s = new StringBuilder();
        s.append("Territory: ").append(getName());
        s.append("   Army Count: ").append(getArmy());
        s.append("   Ruler: ").append(getRuler());
        s.append("   Adjacent Territories: ");
        for (String i : adjacentTerritories.keySet()) {
            s.append(adjacentTerritories.get(i).getName()).append("  ");
        }
        s.append("\n");
        return s.toString();
    }

    /**
     * Creates and returns a short textual representation of the Territory to the terminal
     *
     * @return A string containing a short textual representation of the Territory to the terminal
     */
    @Override
    public String toString() {
        return (this.getName() + " - " + this.getRuler() + " - " + this.getArmy());
    }
}

