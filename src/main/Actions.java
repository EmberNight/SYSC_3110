import java.util.Scanner;

public class Actions {
    private GameBoard gameBoard;
    private CircularLinkedList players;
    private Parser parser;
    private String activePlayer;

    private Actions(CircularLinkedList players, GameBoard gameBoard) {
        this.players = players;
        this.parser = new Parser();
        this.gameBoard = gameBoard;
    }

    private void attack(String territory)
    {
        System.out.println("Which territory would you like to attack from?");

        Scanner attack = new Scanner(System.in);
        String attacker = attack.nextLine();
        if(!gameBoard.getRuler(attacker))
        {
            System.out.println("You do not own this territory");
        }

        System.out.println("How many dice would you like to attack with?");
        Scanner diceSizeAttack = new Scanner(System.in);
        int diceAmountAttack = diceSizeAttack.nextInt();

        if(!gameBoard.getArmies(attacker) > diceAmountAttack + 1);
        {
            System.out.println("You do not have enough armies");
        }
        System.out.println("How many dice would the defender like to use");

        Scanner diceSizeDefend = new Scanner(System.in);
        int diceAmountDefend = diceSizeDefend.nextInt();
        if(!gameBoard.getArmies(territory) >= diceAmountAttack);
        {
            System.out.println("You do not have enough armies");
        }

        int result = rollDie(diceAmountAttack, diceAmountDefend); //stores result of dice roll
        //case where defender's win, eliminating attacker's armies
        if(result < 0)
        {
            gameBoard.addTerritoryArmy(attacker, gameBoard.getArmies(attacker) + result);
        }
        //Case where attacker wins, eliminating defender's armies
        else if(result > 0)
        {
            gameBoard.addTerritoryArmy(territory, gameBoard.getArmies(territory) - result);
            //sets new ruler if defender has no armies left
            if(gameBoard.getArmies(territory) == 0){
                gameBoard.setTerritoryRuler(territory, activePlayer);
            }
        }
        //Case where both sides lose one army.
        else if(result == 0)
        {
            gameBoard.addTerritoryArmy(territory, gameBoard.getArmies(territory) - 1;
            gameBoard.addTerritoryArmy(territory, gameBoard.getArmies(territory) - 1);
            //sets new ruler if defender has no armies left
            if(gameBoard.getArmies(territory) == 0){
                gameBoard.setTerritoryRuler(territory, activePlayer);
            }
        }

    }

    public int rollDie(int attack, int defend) {
        int currentAttack;
        int largestAttack = 0;
        int secondLargestAttack = 0;

        int result = 0;

        int currentDefense;
        int largestDefense = 0;
        int secondLargestDefense = 0;

        for (int i = 0; i < attack; i++)
        {
            currentAttack = (int) (Math.random() * 6 + 1);
            if (currentAttack > largestAttack) {
                secondLargestAttack = largestAttack;
                largestAttack = currentAttack;
            }
            else if (currentAttack < largestAttack && currentAttack > secondLargestAttack) {
                secondLargestAttack = currentAttack;
            }
        }
        for (int j = 0; j < defend; j++)
        {
            currentDefense = (int) (Math.random() * 6 + 1);
            if (currentDefense > largestDefense) {
                secondLargestDefense = largestDefense;
                largestDefense = currentDefense;
            }
            else if (currentDefense < largestDefense) {
                secondLargestDefense = currentDefense;
            }
        }

        if (largestAttack > largestDefense)  result = result + 1;
        else if(largestAttack < largestDefense || largestAttack == largestDefense) result = result - 1;
        if(attack>1 && defend > 1)
        {
            if (secondLargestAttack > secondLargestDefense) result = result + 1;
            else if(secondLargestAttack < secondLargestDefense || secondLargestAttack == secondLargestDefense) result = result - 1;

        }

        return result;
    }

    public void pass() {

    }

    public void status() {
    }

    public void playGame() {
        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = process(command);
        }

    }

    public boolean process(Command command) {
        boolean quit = false;
        if (command.isUnknown()) {

        }
        if (command.equals("attack")) {
            if (command.hasSecondWord()) attack(command.getSecondWord());
            else System.out.println("Attack who?");
        }
        if (command.equals("pass")) pass();
        return quit;
    }



}
