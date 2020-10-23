import java.util.Scanner;
/**
 * @author Emmitt Luhning
 * @group 16
 */
public class Actions {
    private GameBoard gameBoard;
    private CircularLinkedList players;
    private Parser parser;
    private Player activePlayer;

    private Actions(CircularLinkedList players, GameBoard gameBoard) {
        this.players = players;
        this.parser = new Parser();
        this.gameBoard = gameBoard;
    }

    private void attack(String territory)
    {

        System.out.println("Which territory would you like to attack from?");
        gameBoard.getTerritory(territory).printStatus();
        //asks for and processes the input for attacking territory
        Scanner attack = new Scanner(System.in);
        String attacker = attack.nextLine();

        //checks if attacker actually owns this territory
        if(!gameBoard.getTerritory(attacker).getRuler().equals(activePlayer.getName()))
        {
            System.out.println("You don't own this territory");
            return;
        }

        //checks if attacking territory is adjacent to defending territory
        if(!gameBoard.isAdjacentTerritory(gameBoard.getTerritory(attacker), gameBoard.getTerritory(territory)))
        {
            System.out.println("Those territories are not adjacent");
            return;
        }

        //scans for input of attacker, stating dice amount
        System.out.println("How many dice would you like to attack with?");
        Scanner diceSizeAttack = new Scanner(System.in);
        int diceAmountAttack = diceSizeAttack.nextInt();

        //confirms if attacker has sufficient armies to use amount of dice
        if(!(gameBoard.getTerritory(attacker).getArmy() > diceAmountAttack + 1));
        {
            System.out.println("You do not have enough armies");
        }
        System.out.println("How many dice would the defender like to use");

        Scanner diceSizeDefend = new Scanner(System.in);
        int diceAmountDefend = diceSizeDefend.nextInt();
        if(!(gameBoard.getTerritory(territory).getArmy() >= diceAmountAttack));
        {
            System.out.println("You do not have enough armies");
        }

        int result = rollDie(diceAmountAttack, diceAmountDefend); //stores result of dice roll

        //case where defender's win, eliminating attacker's armies
        if(result < 0)
        {
            gameBoard.getTerritory(attacker).setArmy(gameBoard.getTerritory(territory).getArmy() + result);
        }
        //Case where attacker wins, eliminating defender's armies
        else if(result > 0)
        {
            gameBoard.getTerritory(territory).setArmy(gameBoard.getTerritory(territory).getArmy() - result);
            //sets new ruler if defender has no armies left
            if(gameBoard.getTerritory(territory).getArmy() == 0){
                gameBoard.getTerritory(territory).setRuler(activePlayer);
                gameBoard.getTerritory(territory).getRuler().removeRuledTerritory(gameBoard.getTerritory(territory));
                activePlayer.addRuledTerritory(gameBoard.getTerritory(territory));
                System.out.println(activePlayer.getName() + "has taken control of the territory");
            }
        }
        //Case where both sides lose one army.
        else if(result == 0)
        {
            gameBoard.getTerritory(territory).setArmy(gameBoard.getTerritory(territory).getArmy() - 1);
            //sets new ruler if defender has no armies left
            if(gameBoard.getTerritory(territory).getArmy() == 0){
                gameBoard.getTerritory(territory).setRuler(activePlayer);
                gameBoard.getTerritory(territory).getRuler().removeRuledTerritory(gameBoard.getTerritory(territory));
                activePlayer.addRuledTerritory(gameBoard.getTerritory(territory));
                System.out.println(activePlayer.getName() + "has taken control of the territory");
            }
        }

    }

    private int rollDie(int attack, int defend) {
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
        //return result (positive integers = num of attacking wins. Negative integers = num of defending wins.
        // 0 = one defending win, one attacking win)
        return result;
    }

    private void pass() {
        activePlayer = players.returnNextNode(activePlayer);
    }


    public void playGame() {
        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = process(command);
        }

    }
    private void printStatus()
    {
            gameBoard.printBoardStatus();
    }

    private boolean process(Command command) {
        boolean quit = false;
        if (command.isUnknown()) {
            System.out.println("What?");
        }
        if (command.equals("attack")) {
            if (command.hasSecondWord()) attack(command.getSecondWord());
            else System.out.println("Attack who?");
        }
        if (command.equals("pass")) pass();
        if (command.equals("status")) printStatus();
        return quit;
    }

}
