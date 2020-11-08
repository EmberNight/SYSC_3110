/**
 * @author Ashwin Stoparczyk
 *
 * Functional interface for use with
 */
public interface RiskView {
    void attackUpdate(RiskEvent ae);
    void passUpdate();
    void updateStatus();
}