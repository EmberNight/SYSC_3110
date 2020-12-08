/**
 * @author Emmitt Luhning
 * <p>
 * An implementation of the people playing the game of Risk
 */
public class Player implements java.io.Serializable{
    private final String name;
    private int armies;
    private Boolean isAI;

    /**
     * Constructor for Player objects
     *
     * @param name The name of the Player
     */
    public Player(String name, Boolean isAI) {
        this.name = name;
        this.armies = 0;
        this.isAI = isAI;
    }

    /**
     * Returns the Player's name
     *
     * @return the Player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the number of the Player's armies
     *
     * @return the number of the Player's armies
     */
    public int getArmies() {
        return armies;
    }

    /**
     * Sets the Player's number of armies to the given value
     *
     * @param num The Player's new number of armies
     */
    public void setArmies(int num) {
        armies = num;
    }

    /**
     * Sets the Player's number of armies to the given value
     *
     * @param num The Player's new number of armies
     */
    public void addArmies(int num) {
        armies += Math.abs(num);
    }

    /**
     * Removes a given amount of armies from the Player
     *
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

    /**
     * Determines if the Player is an AI
     * @return true if the Player is an AI, false if the Player is not an AI
     */
    public boolean isAI() {
        return isAI;
    }

}

