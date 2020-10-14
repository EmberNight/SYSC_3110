import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestTestClass {
    @Test
    public void isArg_is_true() {
        TestClass tc = new TestClass();
        assertTrue(tc.isArg("arg"));
    }

    @Test
    public void isArg_is_false() {
        TestClass tc = new TestClass();
        assertFalse(tc.isArg("NotArg"));
    }
}
