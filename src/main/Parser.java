/**
 * Reads and parses command words
 */
import java.util.Scanner;

public class Parser
{

    private final CommandWords commands;
    private final Scanner reader;

    /**
     * Constructor for Parser objects
     */
    public Parser()
    {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    /**
     * Reads and parses a Command
     * @return A parsed Command if the command is valid, or an invalid parsed Command if the command is invalid
     */
    public Command getCommand()
    {
        String inputLine;
        String word1 = null;
        String word2 = null;

        System.out.print("> ");

        inputLine = reader.nextLine();
        inputLine = inputLine.toLowerCase();

        Scanner wordScanner = new Scanner(inputLine);
        if(wordScanner.hasNext()) {
            word1 = wordScanner.next();
            if(wordScanner.hasNext()) {
                word2 = wordScanner.next();
            }
        }


        if(commands.isCommand(word1)) {
            return new Command(word1, word2);
        }
        else {
            return new Command(null, word2);
        }
    }

    /**
     * Prints a textual representation of all valid command words to the terminal
     */
    public void printCommands() {
        new CommandWords().printCommands();
    }
}


