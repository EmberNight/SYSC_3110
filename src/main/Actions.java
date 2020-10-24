import java.util.*;

/**
 * @author Emmitt Luhning
 */
public class Actions {
    private final GameBoard gameBoard;
    private final ArrayList<Player> players;
    private final Parser parser;
    private Player activePlayer;
    private int activePlayerIndex;

    public Actions(ArrayList<Player> players, GameBoard gameBoard) {
        this.players = players;
        this.parser = new Parser();
        this.gameBoard = gameBoard;
        this.activePlayerIndex = 0;
        this.activePlayer = players.get(activePlayerIndex);

        initialArmyAllocation();
        initialArmyPlacement();
        gameBoard.initializeContinentRulers();
    }

    private void initialArmyAllocation() {
        int armies = 0;

        switch (players.size()){
            case 2:
                armies = 5;
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
                i = -1; // Must be -1 because the for loop will increase it by one
            }
        }

        Random ran = new Random();
        for (int i = 0; i < numPlayers; i++) {
            Player player = players.get(i);
            ArrayList<String> playersTerritories = assignedTerritories.get(i);

            try {
                territory = playersTerritories.get(ran.nextInt(playersTerritories.size())); // Gets a random territory
            } catch (Exception e) {
                System.out.println("The map isn't big enough for " + players.size() + " players. (Unallocated player)");
                System.exit(-2);
                return;
            }

            gameBoard.addTerritoryArmy(territory, player.removeArmies(1));

            // If it is the last player reset it back to the start
            if (i == numPlayers - 1) {
                // If the last player has no armies we are done
                if (player.getArmies() == 0){
                    break;
                }
                i = -1; // Must be -1 because the for loop will increase it by one
            }
        }
    }

    private void attack(String defenderTerritory)
    {
        Scanner input = new Scanner(System.in);
        String currPlayer = activePlayer.getName();
        String attackerTerritory;

        // Checks if you own the territory if you do return.
        if (gameBoard.getTerritoryRuler(defenderTerritory).equals(currPlayer)) {
            System.out.println("You cannot attack your own territory.");
            return;
        }

        System.out.println("Which territory would you like to attack from?");

        // If you have no adjacent territories return.
        Set<String> adjacentTerritories = gameBoard.getAdjacentTerritories(defenderTerritory);
        boolean unownedTerritory = false;
        for (String s : adjacentTerritories) {
            if (gameBoard.getTerritoryRuler(s).equals(currPlayer)) {
                gameBoard.printTerritoryStatus(s);
                unownedTerritory = true;
            }
        }

        if (!unownedTerritory) {
            System.out.println("You don't own any adjacent territories to attack from");
            return;
        }

        //asks for and processes the input for attacking territory
        System.out.print("> ");
        attackerTerritory = input.nextLine();

        //checks if attacker actually owns this territory
        if(!gameBoard.getTerritory(attackerTerritory).getRuler().equals(activePlayer.getName()))
        {
            System.out.println("You don't own this territory");
            return;
        }

        //checks if attacking territory is adjacent to defending territory
        if(!gameBoard.isAdjacentTerritory(attackerTerritory, defenderTerritory))
        {
            System.out.println("Those territories are not adjacent");
            return;
        }

        //scans for input of attacker, stating dice amount
        System.out.println("How many dice would you like to attack with (1 to 3)?");

        int diceAttacker = 0;
        while (true) {
            try {
                System.out.print("> ");
                diceAttacker = input.nextInt();
            } catch (Exception e) {
                System.out.println("Number of dice must be a number.");
            }

            if (diceAttacker >= 1 && diceAttacker <= 3) {
                break;
            }
            System.out.println("Attacker must have 1 to 3 dice");
        }

        //confirms if attacker has sufficient armies to use amount of dice
        if(!(gameBoard.getArmy(attackerTerritory) >= diceAttacker + 1))
        {
            System.out.println("The attacker's territory does not have enough armies to use " + diceAttacker + " dice");
            return;
        }

        System.out.println("How many dice would the defender like to use (1 to 2)?");

        int diceDefender = 0;
        while (true) {
            try {
                System.out.print("> ");
                diceDefender = input.nextInt();
            } catch (Exception e) {
                System.out.println("Number of dice must be a number.");
            }

            if (diceDefender >= 1 && diceDefender <= 2) {
                break;
            }
            System.out.println("Defender must have 1 to 2 dice");
        }

        if(!(gameBoard.getArmy(defenderTerritory) >= diceDefender)) {
            System.out.println("The defender's territory does not have enough armies to use " + diceDefender + " dice");
            return;
        }

        ArrayList<Integer> result = rollDie(diceAttacker, diceDefender); //stores result of dice roll
        int attackerLosses = result.get(0);
        int defenderLosses = result.get(1);

        gameBoard.removeTerritoryArmy(attackerTerritory, attackerLosses);
        gameBoard.removeTerritoryArmy(defenderTerritory, defenderLosses);

        if (gameBoard.getArmy(defenderTerritory) == 0) {
            System.out.println(currPlayer + " has claimed " + defenderTerritory + " as their own!");
            String defender = gameBoard.getTerritoryRuler(defenderTerritory);
            gameBoard.removeTerritoryArmy(attackerTerritory, 1);
            gameBoard.addTerritoryArmy(defenderTerritory, 1);
            gameBoard.setTerritoryRuler(defenderTerritory, currPlayer);
            gameBoard.setContinentRuler(defenderTerritory, currPlayer);
            removeEliminatedPlayer(defender);
        }

        if (players.size() == 1) {
            System.out.println(players.get(0).getName() + " Wins!");
            System.exit(0);
        }
    }

    private ArrayList<Integer> rollDie(int numAttackDie, int numDefendDie) {
        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<Integer> attackerRolls = new ArrayList<>();
        ArrayList<Integer> defenderRolls = new ArrayList<>();
        Random ran = new Random();
        int attackerLosses = 0;
        int defenderLosses = 0;

        System.out.print("Attacker Rolled: ");
        for(int i = 0; i < numAttackDie; i++) {
            int roll = ran.nextInt(6) + 1;
            attackerRolls.add(roll);
            System.out.print(roll + " ");
        }
        System.out.println();

        System.out.print("Defender Rolled: ");
        for(int i = 0; i < numDefendDie; i++) {
            int roll = ran.nextInt(6) + 1;
            defenderRolls.add(roll);
            System.out.print(roll + " ");
        }
        System.out.println();

        Collections.sort(attackerRolls);
        Collections.sort(defenderRolls);

        Collections.reverse(attackerRolls);
        Collections.reverse(defenderRolls);

        int diceToCheck = numAttackDie;
        int diceLoss = numAttackDie - numDefendDie;

        if (diceLoss < 0) {
            attackerLosses = Math.abs(diceLoss);
            diceToCheck = numAttackDie;
        } else if (diceLoss > 0) {
            defenderLosses = diceLoss;
            diceToCheck = numDefendDie;
        }

        for (int i = 0; i < diceToCheck; i++) {
            if (attackerRolls.get(i) > defenderRolls.get(i)) {
                defenderLosses++;
            } else {
                attackerLosses++;
            }
        }

        result.add(attackerLosses);
        result.add(defenderLosses);

        System.out.println("The attacker had " + result.get(0) + " loss(es).");
        System.out.println("The defender had " + result.get(1) + " loss(es).");

        return result;
    }

    private void removeEliminatedPlayer(String player) {
        if (gameBoard.isPlayerEliminated(player)) {
            for(int i = 0; i < players.size(); i++) {
                if(players.get(i).getName().equals(player)) {
                    System.out.println(player + " was eliminated from the game.");
                    players.remove(i);
                    break;
                }
            }
        }
    }

    private void pass() {
        activePlayerIndex++;

        if (activePlayerIndex >= players.size()) {
            activePlayerIndex = 0;
        }

        activePlayer = players.get(activePlayerIndex);
        printCurrentPlayer();
    }

    private void printCurrentPlayer() {
        System.out.println(activePlayer.getName() + "'s Turn!");
    }

    public void playGame() {
        printCurrentPlayer();
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
            parser.printCommands();
        } else {
            if (command.getCommandWord().equals("attack")) {
                if (command.hasSecondWord()) attack(command.getSecondWord());
                else System.out.println("Attack who?");
            }
            if (command.getCommandWord().equals("pass")) pass();
            if (command.getCommandWord().equals("status")) printStatus();
            if (command.getCommandWord().equals("quit")) quit = true;
        }
        return quit;
    }
}
