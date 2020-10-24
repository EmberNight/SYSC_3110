public class CommandWords {

    private final String[] validCommands = {"attack", "pass", "status", "quit"}; //Contains all valid command words

    /**
     * Constructor - initialise the command words.
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

    public void printCommands() {
        System.out.print("Valid commands: ");
        for (String s : validCommands) {
            System.out.print(s + " ");
        }
        System.out.println();
    }
}