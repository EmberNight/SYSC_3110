
    import java.util.Scanner;

    public class Parser
    {
        private CommandWords commands;
        private Scanner reader;


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

    }


