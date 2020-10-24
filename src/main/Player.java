/**
 * @author Emmitt Luhning
 */
public class Player {
    private final String name;
    private int armies;

    public Player(String name) {
        this.name = name;
        this.armies = 0;
    }

    public String getName() {
        return name;
    }

    public int getArmies() {
        return armies;
    }
    public void setArmies(int num) {
        armies = num;
    }
    public int removeArmies(int num) {
        armies -= num;

        if (armies < 0) {
            return 0;
        }
        return num;
    }
}

