/**
 * @author Emmitt Luhning
 * @group 16
 *
 * Provides methods for manipulating the GameBoard class during runtime
 */
import java.util.*;

public class Actions {
        private final GameBoard gameBoard;
        private final ArrayList<Player> players;
        private Player activePlayer;
        private int activePlayerIndex;
        private ActionsView actionView;

        /**
         * Constructor for Actions objects
         *
         * @param players   A list of players who will be playing the game
         * @param gameBoard The digital game board that will be used to play the game
         */
        public Actions(ArrayList<Player> players, GameBoard gameBoard) {
                this.players = players;
                this.gameBoard = gameBoard;
                this.activePlayerIndex = 0;
                this.activePlayer = players.get(activePlayerIndex);

                initialArmyAllocation();
                initialArmyPlacement();
                gameBoard.initializeContinentRulers();
        }

        /**
         * Calculates and distributes the initial amount of armies to all players
         * @author trautrim
         */
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

        /**
         * Allocates each territory an amount of armies from Players based on the number of players in the game
         * @author trautrim
         */
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

        /**
         * Attacks an enemy territory from an adjacent owned territory, and resolves the outcome of the attack.
         *
         * @param defenderTerritory The territory being attacked
         */
        public void attack(String attackerTerritory, String defenderTerritory, int attackersDice, int defendersDice) {
                String currPlayer = activePlayer.getName();


                ArrayList<Integer> result = rollDie(attackersDice, defendersDice); //stores result of dice roll
                int attackerLosses = result.get(0);
                int defenderLosses = result.get(1);

                gameBoard.removeTerritoryArmy(attackerTerritory, attackerLosses);
                gameBoard.removeTerritoryArmy(defenderTerritory, defenderLosses);

                if (gameBoard.getArmy(defenderTerritory) == 0) {
                        String defender = gameBoard.getTerritoryRuler(defenderTerritory);
                        gameBoard.removeTerritoryArmy(attackerTerritory, 1);
                        gameBoard.addTerritoryArmy(defenderTerritory, 1);
                        gameBoard.setTerritoryRuler(defenderTerritory, currPlayer);
                        gameBoard.setContinentRuler(defenderTerritory, currPlayer);
                        removeEliminatedPlayer(defender);
                }

                //new ActionEvent(this, gameBoard.getTerritory(defenderTerritory), gameBoard.getTerritoryRuler(defenderTerritory), result, activePlayer);
                actionView.updateStatus();
        }

        /**
         * Calculates the combat strength of an attacker and defender, and determines the outcome of the battle
         * @param numAttackDie The amount of dice the attacker is fighting with
         * @param numDefendDie The amount of dice the defender is fighting with
         * @return The outcome of the battle: < 0 for defensive victory, > 0 for offensive victory and 0 for tie
         */
        private ArrayList<Integer> rollDie(int numAttackDie, int numDefendDie) {
                ArrayList<Integer> result = new ArrayList<>();
                ArrayList<Integer> attackerRolls = new ArrayList<>();
                ArrayList<Integer> defenderRolls = new ArrayList<>();
                Random ran = new Random();
                int attackerLosses = 0;
                int defenderLosses = 0;

                for(int i = 0; i < numAttackDie; i++) {
                        int roll = ran.nextInt(6) + 1;
                        attackerRolls.add(roll);
                        System.out.print(roll + " ");
                }

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


                return result;
        }

        /**
         * Checks if the given Player should be eliminated from the game, then eliminates them if they should
         * @param player The Player to be possibly eliminated
         */
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


        /**
         * Passes the turn on from the current player to the next
         */
        public void pass() {
                activePlayerIndex++;


                if (activePlayerIndex >= players.size()) {
                        activePlayerIndex = 0;
                }

        }

        public String getActivePlayer() {
                return activePlayer.getName();
        }
}