import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
/**
 * @author Emmitt Luhning
 * @group 16
 */
public class Actions {
    private final GameBoard gameBoard;
    private final ArrayList<Player> players;
    private final Parser parser;
    private Player activePlayer;
    private int activePlayerIndex;

    private Actions(ArrayList<Player> players, GameBoard gameBoard) {
        this.players = players;
        this.parser = new Parser();
        this.gameBoard = gameBoard;
        this.activePlayerIndex = 0;
        this.activePlayer = players.get(activePlayerIndex);

        initialArmyAllocation();
        initialArmyPlacement();
    }

    private void initialArmyAllocation() {
        int armies = 0;

        switch (players.size()){
            case 2:
                armies = 50;
                break;
            case 3:
                armies = 35;
                break;
            case 4:
                armies = 30;
                break;
            case 5:
                armies = 25;
                break;
            case 6:
                armies = 20;
                break;
            default:
                break;
        }

        for(Player p : players) {
            p.setArmies(armies);
        }
    }

    private void initialArmyPlacement() {
        ArrayList<ArrayList<String>> assignedTerritories = new ArrayList<>();
        String territory;
        int numPlayers = players.size();

        // Creates array list to temporarily store territories
        for (Player ignored : players) {
            assignedTerritories.add(new ArrayList<>());
        }

        // Randomly give players territories and allot initial armies.
        for (int i = 0; i < numPlayers; i++){
            territory = gameBoard.getUnallocatedTerritory();

            // If there are no territories left to assign quit
            if (territory == null) {
                break;
            }

            Player player = players.get(i);

            gameBoard.setTerritoryRuler(territory, player.getName());
            gameBoard.addTerritoryArmy(territory, player.removeArmies(1));
            assignedTerritories.get(i).add(territory);

            // If it is the las player reset it back to the start
            if (i == numPlayers - 1) {
                i = 0;
            }
        }

        Random ran = new Random();
        for (int i = 0; i < numPlayers; i++) {
            Player player = players.get(i);
            ArrayList<String> playersTerritories = assignedTerritories.get(i);
            territory = playersTerritories.get(ran.nextInt(playersTerritories.size())); // Gets a random territory

            gameBoard.addTerritoryArmy(territory, player.removeArmies(1));

            // If it is the las player reset it back to the start
            if (i == numPlayers - 1) {
                // If the last player has no armies we are done
                if (player.getArmies() == 0){
                    break;
                }
                i = 0;
            }
        }
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
        if(!(gameBoard.getArmy(attacker) > diceAmountAttack + 1))
        {
            System.out.println("You do not have enough armies");
        }
        System.out.println("How many dice would the defender like to use");

        Scanner diceSizeDefend = new Scanner(System.in);
        int diceAmountDefend = diceSizeDefend.nextInt();
        if(!(gameBoard.getArmy(territory) >= diceAmountAttack));
        {
            System.out.println("You do not have enough armies");
        }

        int result = rollDie(diceAmountAttack, diceAmountDefend); //stores result of dice roll

        //case where defender's win, eliminating attacker's armies
        if(result < 0)
        {
            gameBoard.addTerritoryArmy(attacker, gameBoard.getArmy(attacker) + result);
        }
        //Case where attacker wins, eliminating defender's armies
        else if(result > 0)
        {
            gameBoard.addTerritoryArmy(territory, gameBoard.getTerritory(territory).getArmy() - result);
            //sets new ruler if defender has no armies left
            if(gameBoard.getArmy(territory) == 0){
                gameBoard.setTerritoryRuler(territory, activePlayer.getName());
                System.out.println(activePlayer.getName() + "has taken control of the territory");
            }
        }
        //Case where both sides lose one army.
        else if(result == 0)
        {
            gameBoard.addTerritoryArmy(territory, gameBoard.getArmy(territory) - 1);
            gameBoard.addTerritoryArmy(attacker, gameBoard.getArmy(attacker) - 1);
            //sets new ruler if defender has no armies left
            if(gameBoard.getArmy(territory) == 0){
                gameBoard.setTerritoryRuler(territory, activePlayer.getName());
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
        activePlayerIndex++;

        if (activePlayerIndex >= players.size()) {
            activePlayerIndex = 0;
        }

        activePlayer = players.get(activePlayerIndex);
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
