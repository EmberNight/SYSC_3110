/**
 * An implementation of the continents used during the game of Risk
 */
import java.util.*;

public class Continent {

    private final ArrayList<Territory> territories;
    private final String name;
    private final int value;
    private String ruler;

    /**
     * Constructor for Continent objects
     * @param name The name of the continent
     * @param value The value of the continent
     */
    public Continent(String name, int value){
        territories = new ArrayList<>();
        this.value = value;
        ruler = "";
        this.name = name;
    }

    /**
     * Returns the name of the continent
     * @return the name of the continent
     */
    public String getName(){
        return name;
    }

    /**
     * Returns the ruler of the continent
     * @return the ruler of the continent
     */
    public String getRuler(){
        return ruler;
    }

    /**
     * Returns the value of the continent
     * @return the value of the continent
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the list of territories encompassed by the continent
     * @return the list of territories encompassed by the continent
     */
    public ArrayList<Territory> getTerritories(){
        return territories;
    }

    /**
     * Sets the ruler of the continent to the given Player
     * @param ruler The new ruler of the continent
     */
    public void setRuler(String ruler){
        this.ruler = ruler;
    }

    /**
     * Adds a new territory to the continent's list of encompassed territories
     * @param territory the new territory to be added to the continent's list of encompassed territories
     */
    public void addTerritory(Territory territory){
        territories.add(territory);
    }

    /**
     * Prints a textual representation of the continent to the terminal
     */
    public void printStatus(){
        System.out.println("Continent: " + getName());
        System.out.println("Ruler: " + getRuler());
        System.out.println("Territories: ");
        for (Territory territory : territories) {
            territory.printStatus();
        }
    }
}
