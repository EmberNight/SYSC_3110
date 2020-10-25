/**
 * Helper class for use with Parser
 */
public class Command
{

    private final String commandWord;
    private final String secondWord;


    /**
     * Constructor for Command objects
     * @param firstWord The first word of the command
     * @param secondWord The second word of the command
     */
    public Command(String firstWord, String secondWord)
    {
        commandWord = firstWord;
        this.secondWord = secondWord;
    }

    /**
     * Returns the first word of the command
     * @return The first word of the command
     */
    public String getCommandWord()
        {
            return commandWord;
        }

    /**
     * Returns the second word of the command
     * @return The second word of the command
     */
    public String getSecondWord()
        {
            return secondWord;
        }

    /**
     * Determines if the command exists
     * @return true if the command is an empty string, false if the command is not an empty string
     */
    public boolean isUnknown()
        {
            return (commandWord == null);
        }

    /**
     * Determines if the command has a second word
     * @return true if the command has a second word, false if the command does not have a second word
     */
    public boolean hasSecondWord()
        {
            return (secondWord != null);
        }
}


