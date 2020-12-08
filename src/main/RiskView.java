/**
 * Functional interface for use with Risk
 *
 * @author Ashwin Stoparczyk
 */
public interface RiskView {

    /**
     * Updates the GUI when a attack has finished.
     *
     * @param ae The event that occurred
     */
    void attackUpdate(RiskEvent ae);

    /**
     * Updates the GUI when there is a new player.
     */
    void passUpdate();

    /**
     * Updates the GUI when the status pages needs updating.
     */
    void updateStatus();

    /**
     * Updates the GUI when a Player moves their armies
     * @param ae The event that occurred
     */
    void movementUpdate(RiskEvent ae);

    /**
     * Updates the GUI when a Player adds to their armies
     * @param ae The event that occurred
     */
    void addArmyUpdate(RiskEvent ae);


}