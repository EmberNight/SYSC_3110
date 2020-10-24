
    import java.util.Scanner;

    public class Parser
    {

        private final CommandWords commands;
        private final Scanner reader;


        public Parser()
        {
            commands = new CommandWords();
            reader = new Scanner(System.in);
        }


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

        public void printCommands() {
            new CommandWords().printCommands();
        }
    }


