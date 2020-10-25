/**
 * @author Emmitt Luhning
 * @group 16
 *
 * An implementation of the people playing the game of Risk
 */
public class Player {
    private final String name;
    private int armies;

    /**
     * Constructor for Player objects
     * @param name The name of the Player
     */
    public Player(String name) {
        this.name = name;
        this.armies = 0;
    }

    /**
     * Returns the Player's name
     * @return the Player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the number of the Player's armies
     * @return the number of the Player's armies
     */
    public int getArmies() {
        return armies;
    }

    /**
     * Sets the Player's number of armies to the given value
     * @param num The Player's new number of armies
     */
    public void setArmies(int num) {
        armies = num;
    }

    /**
     * Removes a given amount of armies from the Player
     * @param num The number of armies to be removed from the Player
     * @return 0 if the Player has no remaining armies, else the number of armies that were removed from the Player
     */
    public int removeArmies(int num) {
        armies -= num;

        if (armies < 0) {
            return 0;
        }
        return num;
    }
}

