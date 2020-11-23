import java.util.EventObject;

/**
 * @author Ashwin Stoparczyk
 * <p>
 * Helper class for use with Risk
 */
public class RiskEvent extends EventObject {
    public static final int ATTACK = 1;

    private final int attackerLosses;
    private final int defenderLosses;
    private final boolean newContinentRuler;
    private final boolean newTerritoryRuler;
    private final int remainingPlayers;

    /**
     * Constructor for ActionEvent objects
     *  @param attackerLosses    The attacker's losses
     * @param defenderLosses    The defender's losses
     * @param newContinentRuler If the continent's ruler has been changed
     * @param newTerritoryRuler If the Territory's ruler has been changed
     * @param remainingPlayers  Players left in the game
     */
    public RiskEvent(int eventID, int attackerLosses, int defenderLosses, boolean newContinentRuler, boolean newTerritoryRuler, int remainingPlayers) {
        super(eventID);
        this.attackerLosses = attackerLosses;
        this.defenderLosses = defenderLosses;
        this.newContinentRuler = newContinentRuler;
        this.newTerritoryRuler = newTerritoryRuler;
        this.remainingPlayers = remainingPlayers;
    }

    public int getEventID() {
        return (int) this.getSource();
    }

    public int getAttackerLosses() {
        return attackerLosses;
    }

    public int getDefenderLosses() {
        return defenderLosses;
    }

    public int getRemainingPlayers() {
        return remainingPlayers;
    }

    public boolean isNewContinentRuler() {
        return newContinentRuler;
    }

    public boolean isNewTerritoryRuler() {
        return newTerritoryRuler;
    }
}
