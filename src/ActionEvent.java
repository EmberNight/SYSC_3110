import java.util.ArrayList;
import java.util.EventObject;

/**
 * @author Ashwin Stoparczyk
 * @group 16
 *
 * Helper class for use with
 */
public class ActionEvent extends EventObject {

    private Territory territory;
    private String ruler;
    private ArrayList<Integer> result;

    /**
     * Constructor for ActionEvent objects
     * @param actions The Actions object which created the ActionEvent
     * @param territory The defending Player's Territory
     * @param ruler The defending Player
     * @param result The numerical result of the attack
     */
    public ActionEvent(Actions actions, Territory territory, String ruler, ArrayList<Integer> result) {
        super(actions);
        this.territory = territory;
        this.ruler = ruler;
        this.result = result;
    }

    /**
     * Returns the defending Player's Territory
     * @return the defending Player's Territory
     */
    public Territory getTerritory() {
        return territory;
    }

    /**
     * Returns the defending Player
     * @return the defending Player
     */
    public String getRuler() {
        return ruler;
    }

    /**
     * Returns the numerical result of the attack
     * @return the numerical result of the attack
     */
    public ArrayList<Integer> getResult() {
        return result;
    }
}
