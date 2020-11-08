import java.util.ArrayList;
import java.util.EventObject;

/**
 * @author Ashwin Stoparczyk
 *
 * Helper class for use with
 */
public class ActionEvent extends EventObject {
    public static final int ATTACK = 1;

    private int eventID;
    private int attackerLosses;
    private int defenderLosses;
    private boolean newContinentRuler;
    private boolean newTerritoryRuler;

    /**
     * Constructor for ActionEvent objects
     * @param attackerLosses The attacker's losses
     * @param defenderLosses The defender's losses
     * @param newContinentRuler If the continent's ruler has been changed
     * @param newTerritoryRuler If the Territory's ruler has been changed
     */
    public ActionEvent(int eventID, int attackerLosses, int defenderLosses, boolean newContinentRuler, boolean newTerritoryRuler) {
        super(eventID);
        this.attackerLosses = attackerLosses;
        this.defenderLosses = defenderLosses;
        this.newContinentRuler = newContinentRuler;
        this.newTerritoryRuler = newTerritoryRuler;
    }

    public int getEventID() {
        return eventID;
    }

    public int getAttackerLosses() {
        return attackerLosses;
    }

    public int getDefenderLosses() {
        return defenderLosses;
    }

    public boolean isNewContinentRuler() {
        return newContinentRuler;
    }

    public boolean isNewTerritoryRuler() {
        return newTerritoryRuler;
    }
}
