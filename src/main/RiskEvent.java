import java.util.EventObject;

/**
 * @author Ashwin Stoparczyk
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

    /**
     * Returns this RiskEvent's eventID
     * @return this RiskEvent's eventID
     */
    public int getEventID() {
        return (int) this.getSource();
    }

    /**
     * Returns this RiskEvent's attackerLosses
     * @return this RiskEvent's attackerLosses
     */
    public int getAttackerLosses() {
        return attackerLosses;
    }

    /**
     * Returns this RiskEvent's defenderLosses
     * @return this RiskEvent's defenderLosses
     */
    public int getDefenderLosses() {
        return defenderLosses;
    }

    /**
     * Returns this RiskEvent's remainingPlayers
     * @return this RiskEvent's remainingPlayers
     */
    public int getRemainingPlayers() {
        return remainingPlayers;
    }

    /**
     * Determines if this RiskEvent causes a change in a Continent's ruler
     * @return true if this RiskEvent causes a change in a Continent's ruler, false if this RiskEvent does not cause a change in a Continent's ruler
     */
    public boolean isNewContinentRuler() {
        return newContinentRuler;
    }

    /**
     * Determines if this RiskEvent causes a change in a Territory's ruler
     * @return true if this RiskEvent causes a change in a Territory's ruler, false if this RiskEvent does not cause a change in a Territory's ruler
     */
    public boolean isNewTerritoryRuler() {
        return newTerritoryRuler;
    }
}
