/**
 * @author Ashwin Stoparczyk
 * @group 16
 *
 * Provides a list of recognized command words
 */
public class CommandWords {

    private final String[] validCommands = {"attack", "pass", "status", "quit"}; //Contains all valid command words

    /**
     * Constructor for CommandWords objects
     */
    public CommandWords(){}

    /**
     * Check whether a given input is a valid command word.
     * @return true if a given string is a valid command, false if it isn't.
     */
    public boolean isCommand(String aString){
        for (String command : validCommands){
            if (command.equals(aString)) return true;
        }
        return false; //Invalid command
    }

    /**
     * Prints a textual representation of all valid command words to the terminal
     */
    public void printCommands() {
        System.out.print("Valid commands: ");
        for (String s : validCommands) {
            System.out.print(s + " ");
        }
        System.out.println();
    }
}