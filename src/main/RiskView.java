/**
 * @author Ashwin Stoparczyk
 *
 * Functional interface for use with Risk
 */
public interface RiskView {
    /**
     * Updates the GUI when a attack has finished.
     *
     * @param ae Event that occurred
     */
    void attackUpdate(RiskEvent ae);

    /**
     * Updates the gui when there is a new player.
     */
    void passUpdate();

    /**
     * Updates the gui when the status pages needs updating.
     */
    void updateStatus();
}