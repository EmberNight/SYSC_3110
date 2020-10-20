import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandWordsTest {
    @Test
    public void isCommandTrue() {
        CommandWords cw = new CommandWords();
        String[] commands = {"attack", "pass", "status", "start"};
        for (String s : commands) {
            assertTrue(cw.isCommand(s));
        }
    }

    @Test
    public void isCommandFalse() {
        CommandWords cw = new CommandWords();
        String[] commands = {"attacked", "drawer", "height", "barn", "*@(DSA)"};
        for (String s : commands) {
            assertFalse(cw.isCommand(s));
        }
    }
}
