import java.util.ArrayList;
import java.util.List;

/**
 * @author Emmitt Luhning
 */
public class Player {
    private String name;
    private int Armies;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getArmies() {
        return Armies;
    }
    public void setArmies(int num) {
        Armies = num;
    }
    public void addArmies(int num) {
        Armies += num;
    }
    public int removeArmies(int num) {
        Armies -= num;
        return num;
    }
}

