public class AITurn {

    private static final double OUTCOME_CUTOFF = 0.5;

    public enum OUTCOMES {
        ZERO_ZERO (0),
        ZERO_ONE (0),
        ZERO_TWO (0),
        ONE_ZERO (1),
        ONE_ONE (0.5),
        ONE_TWO (0),
        TWO_ZERO (1),
        TWO_ONE (0),
        TWO_TWO (0.5),
        ;

        private final double outcome;

        OUTCOMES(double outcome) {
            this.outcome = outcome;
        }

        private double outcome() {return outcome;}
    };

    private Player player;
    private GameBoard gameBoard;
    private GameActions gameActions;

    public AITurn(Player player, GameBoard gameBoard, GameActions gameActions) {
        this.player = player;
        this.gameBoard = gameBoard;
        this.gameActions = gameActions;
    }

    public void startTurn(){
        reinforceTroops();
        attack();
        fortifyTroops();
    }

    private void fortifyTroops() {
    }

    private void attack() {
        int attackerDice, defenderDice;
        for (Territory attackerTerritory : gameBoard.getRulerTerritoryList(player.getName())){
            for (Territory defenderTerritory : gameBoard.getAttackableTerritoryList(attackerTerritory, player.getName())){
                attackerDice = Math.min(attackerTerritory.getArmy()-1, 2);
                defenderDice = Math.min(defenderTerritory.getArmy(), 2);
                if (expectedOutcome(attackerDice,defenderDice))
                    gameActions.attack(attackerTerritory.getName(), defenderTerritory.getName(), attackerDice, defenderDice);
            }
        }
    }

    private void reinforceTroops() {
    }

    private boolean expectedOutcome(int attackDice, int defenseDice){
        double outcome = 0;

        return outcome >= OUTCOME_CUTOFF;
    }
}
