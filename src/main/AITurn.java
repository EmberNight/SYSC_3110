import java.util.ArrayList;
import java.util.Random;

/**
 * @author Ashwin Stoparczyk
 * @group 16
 *
 * Completes the actions a normal Player would take during their turn for any AI players in the game
 */
public class AITurn {

    public enum OUTCOMES { //Enum containing every possible attack combination, along with the AI's corresponding behaviour
        ZERO_ZERO (false),
        ZERO_ONE (false),
        ZERO_TWO (false),
        ONE_ZERO (true),
        ONE_ONE (true),
        ONE_TWO (false),
        TWO_ZERO (true),
        TWO_ONE (true),
        TWO_TWO (true),
        ;

        private final boolean outcome;

        OUTCOMES(boolean outcome) {
            this.outcome = outcome;
        }

        private boolean outcome() {return outcome;}
    }

    private Player player;
    private GameBoard gameBoard;
    private GameActions gameActions;
    private ArrayList<Territory> safeTerritories;
    private int reinforcements;

    /**
     * Constructor for AITurn objects
     * @param player The AI Player
     * @param gameBoard The current GameBoard
     * @param gameActions The current GameActions
     * @param reinforcements The amount of reinforcements that can be placed by the AI
     */
    public AITurn(Player player, GameBoard gameBoard, GameActions gameActions, int reinforcements) {
        this.player = player;
        this.gameBoard = gameBoard;
        this.gameActions = gameActions;
        this.reinforcements = reinforcements;
        safeTerritories = new ArrayList<>();
    }

    /**
     * Sequentially goes through each phase of the AI Player's turn
     */
    public void startTurn(){
        reinforceTroops();
        attack();
        fortifyTroops();
    }

    /**
     * Randomly adds all the AI Player's reinforcements to their non-safe Territories
     */
    private void reinforceTroops() {
        safeTerritories = determineSafeTerritories();

        for (Territory t : gameBoard.getRulerTerritoryList(player.getName())){
            if (reinforcements == 0) break;
            if (!safeTerritories.contains(t)){
                Random r = new Random();
                int armies = r.nextInt(reinforcements);
                t.addArmy(armies);
                reinforcements -= armies;
            }
        }
    }

    /**
     * For every possible attackable Territory combination, determines if the average outcome is desirable and repeatedly makes attacks until it is impossible or undesirable to do so.
     * AI Player will always attack with the maximum possible dice, up to 2, and the defender will defend with the maximum possible dice, up to 2.
     */
    private void attack() {
        int attackerDice, defenderDice;

        for (Territory attackerTerritory : gameBoard.getRulerTerritoryList(player.getName())){
            for (Territory defenderTerritory : gameBoard.getAttackableTerritoryList(attackerTerritory, player.getName())){
                attackerDice = Math.min(attackerTerritory.getArmy()-1, 2);
                defenderDice = Math.min(defenderTerritory.getArmy(), 2);

                while (expectedOutcome(attackerDice,defenderDice) && !defenderTerritory.getRuler().equals(player.getName())) { //Attack same Territory until undesirable outcome or Territory is conquered
                    gameActions.attack(attackerTerritory.getName(), defenderTerritory.getName(), attackerDice, defenderDice);

                    attackerDice = Math.min(attackerTerritory.getArmy()-1, 2);
                    defenderDice = Math.min(defenderTerritory.getArmy(), 2);
                }
            }
        }
    }

    /**
     * Procedurally moves all but 1 army from safe Territories out to adjacent weak Territories
     */
    private void fortifyTroops() {
        safeTerritories = determineSafeTerritories();

        for (Territory st : safeTerritories){//Moves all but one army into an adjacent safe Territory. This prevents a safe Territory, surrounded by safe Territories, from keeping hold of its armies forever
            for (Territory at : st.getAdjacentTerritories()){
                if (safeTerritories.contains(at)){
                    int armies = st.getArmy()-1;
                    at.addArmy(armies);
                    st.removeArmy(armies);
                }
            }
        }

        for (Territory t : gameBoard.getRulerTerritoryList(player.getName())){ //If there is an adjacent safe Territory, send all possible reinforcements from that Territory to the weak Territory
            if(isWeakTerritory(t)){
                for (Territory st : safeTerritories){
                    if (t.getAdjacentTerritories().contains(st)){
                        int armies = st.getArmy()-1;
                        t.addArmy(armies);
                        st.removeArmy(armies);
                    }
                }
            }
        }
    }

    /**
     * Determines if an attack will, on average, have a desirable outcome.
     * If it does, there is a chance that the AI won't make the attack anyways
     * @param attackDice The number of dice the AI is attacking with
     * @param defenseDice The number of dice the defender is defending with
     * @return true if the outcome is determined to be desirable, false if the outcome is not determined to be desirable
     */
    private boolean expectedOutcome(int attackDice, int defenseDice){
        boolean outcome = false;

        if (attackDice == 0 && defenseDice == 0) return OUTCOMES.ZERO_ZERO.outcome();
        if (attackDice == 0 && defenseDice == 1) return OUTCOMES.ZERO_ONE.outcome();
        if (attackDice == 0 && defenseDice == 2) return OUTCOMES.ZERO_TWO.outcome();
        if (attackDice == 1 && defenseDice == 0) return OUTCOMES.ONE_ZERO.outcome();
        if (attackDice == 1 && defenseDice == 1) outcome = OUTCOMES.ONE_ONE.outcome();
        if (attackDice == 1 && defenseDice == 2) outcome = OUTCOMES.ONE_TWO.outcome();
        if (attackDice == 2 && defenseDice == 0) return OUTCOMES.TWO_ZERO.outcome();
        if (attackDice == 2 && defenseDice == 1) outcome = OUTCOMES.TWO_ONE.outcome();
        if (attackDice == 2 && defenseDice == 2) outcome = OUTCOMES.TWO_TWO.outcome();

        Random r = new Random();
        if (r.nextInt(10) > 3) outcome = !outcome; // 40% chance that the AI deviates from the "correct" play. Makes life interesting. Sometimes.

        return outcome;
    }

    /**
     * Determines the list of the AI's "safe" Territories.
     * "Safe" Territories are completely surrounded by friendly Territories
     * @return An ArrayList containing all of the AU's "safe" territories
     */
    private ArrayList<Territory> determineSafeTerritories(){
        ArrayList<Territory> safeTerritories = new ArrayList<>();
        boolean safe;

        for (Territory t : gameBoard.getRulerTerritoryList(player.getName())){
            safe = true;
            for (Territory at : t.getAdjacentTerritories()){
                if (!at.getRuler().equals(player.getName())){
                    safe = false;
                    break;
                }
            }
            if (safe) safeTerritories.add(t);
        }

        return safeTerritories;
    }

    /**
     * Determines if a given Territory is "weak"
     * "Weak" Territories have at least one adjacent enemy Territory that has a superior amount of armies
     * @param t The Territory to be assessed
     * @return true if the Territory is "weak", false if the Territory is not "weak"
     */
    private boolean isWeakTerritory(Territory t){
        for (Territory at : gameBoard.getAttackableTerritoryList(t, player.getName())){
            if (at.getArmy() > t.getArmy()) return true;
        }

        return false;
    }
}
