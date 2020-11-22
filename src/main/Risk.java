import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * GUI implementation
 *
 * @author Tanner trautrim
 * @author Jordan Peterkin
 * @author Emmitt Luhning
 */
public class Risk extends JFrame implements RiskView {
    private final GameBoard gameBoard;
    private final GameActions gameActions;

    private JMenuBar menuBar;
    private JMenuItem quit, nextPhase;
    private JLabel titleAction;
    private JList<Territory> attackerTerritories, adjacentTerritories;
    private JScrollPane attackerScroller, adjacentScroller, statusScroller;
    private JButton button;
    private JTextArea statusText;

    private Territory attackerTerritory;
    private Territory adjacentTerritory;


    private int currentPhase;

    public Risk(String label) {
        super(label);

        currentPhase = -1; // So allocation happen right away
        attackerTerritory = null;
        adjacentTerritory = null;

        ArrayList<Player> playersList = initializeStatus();
        gameBoard = new GameBoard();
        gameActions = new GameActions(this, playersList, gameBoard);

        createMenus();
        createButtons();
        createStatusArea();
        createLists();
        assignFunctions();
        updateStatusArea();

        buildFrame();
        beginGame();
    }

    /**
     * Creates all the function events.
     */
    private void assignFunctions() {
        attackerTerritories.addListSelectionListener(e -> updateTerritories());
        adjacentTerritories.addListSelectionListener(e -> updateAdjacentTerritory());
        quit.addActionListener(e -> System.exit(0));
        nextPhase.addActionListener(e -> updatePhase());
    }

    private void commitArmies() {
        if (attackerTerritory == null || adjacentTerritory == null) {
            JOptionPane.showMessageDialog(this, "Must select the origin territory and the destination territory", "Movement Cancelled", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int numArmies;
        try {
            numArmies = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Number of units to move.", "Movement", JOptionPane.INFORMATION_MESSAGE));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Movement Cancelled", "Bad input.", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        gameActions.commitArmies(attackerTerritory.getName(), adjacentTerritory.getName(), numArmies);
    }

    private void addArmies() {
        if (attackerTerritory == null) {
            JOptionPane.showMessageDialog(this, "Must select the territory you want to add to", "Addition Cancelled", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int numArmies;
        try {
            numArmies = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Number of units to add. You have " + gameActions.getCurrentPlayersArmies() + " armies to add.", "Add Armies", JOptionPane.INFORMATION_MESSAGE));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Addition Cancelled", "Bad input.", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        gameActions.addArmies(attackerTerritory.getName(), numArmies);
        if (gameActions.getCurrentPlayersArmies() > 0) {
            titleAction.setText("Phase: Add Armies (" + gameActions.getCurrentPlayersArmies() + " Remaining)");
        } else {
            updatePhase();
        }
    }

    public void beginGame() {
        updateAttackerTerritories();
        JOptionPane.showMessageDialog(this, gameActions.getActivePlayer() + "'s Turn", "Starting Game", JOptionPane.INFORMATION_MESSAGE);
        updatePhase();
    }

    /**
     * Advances the state of the game.
     */
    private void updatePhase() {
        updateAttackerTerritories();

        switch (currentPhase) {
            case 0:
                startAttackPhase();
                currentPhase = 1;
                break;
            case 1:
                startMovementPhase();
                currentPhase = 2;
                break;
            case 2:
                gameActions.changeTurns(); // Without the break it will immediately go to default after.
            default:
                allocateTroopPhase();
                currentPhase = 0;
        }
    }

    /**
     * Notifies the player that it is now the allocation phase.
     * Sets up the gui for the allocation phase.
     */
    private void allocateTroopPhase() {
        JOptionPane.showMessageDialog(this,
                "The army allocation phase has started, you have " + gameActions.getCurrentPlayersArmies() + " armies to add",
                "Add Troops!",
                JOptionPane.INFORMATION_MESSAGE);
        titleAction.setText("Phase: Add Armies (" + gameActions.getCurrentPlayersArmies() + " Remaining)");
        button.setText("Add Armies");

        if (button.getActionListeners().length != 0) {
            button.removeActionListener(button.getActionListeners()[0]);
        }
        button.addActionListener(e -> addArmies());
    }

    /**
     * Notifies the player that it is now the movement phase.
     * Sets up the gui for the movement phase.
     */
    private void startMovementPhase() {
        JOptionPane.showMessageDialog(this,
                "The army movement phase has started",
                "Commit Troops!",
                JOptionPane.INFORMATION_MESSAGE);
        titleAction.setText("Phase: Move Armies");
        button.setText("Move Armies");

        if (button.getActionListeners().length != 0) {
            button.removeActionListener(button.getActionListeners()[0]);
        }
        button.addActionListener(e -> commitArmies());
    }

    /**
     * Notifies the player that it is now the attack phase.
     * Sets up the gui for the attack phase.
     */
    private void startAttackPhase() {
        JOptionPane.showMessageDialog(this,
                "The attack phase has started",
                "Attack!",
                JOptionPane.INFORMATION_MESSAGE);
        titleAction.setText("Phase: Attack");
        button.setText("Attack");

        if (button.getActionListeners().length != 0) {
            button.removeActionListener(button.getActionListeners()[0]);
        }

        button.addActionListener(e -> performAttack());
    }

    /**
     * Performs an attack and checks the inputs for errors.
     */
    private void performAttack() {
        if (attackerTerritory == null || adjacentTerritory == null) {
            JOptionPane.showMessageDialog(this, "Must select attacking and defending territories", "Attack Cancelled", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (attackerTerritory.getArmy() == 1) {
            JOptionPane.showMessageDialog(this, "Attacker does not have enough units", "Attack Cancelled", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        gameActions.attack(attackerTerritory.getName(), adjacentTerritory.getName(), attackerTerritory.getArmy() - 1, adjacentTerritory.getArmy());
    }

    /**
     * Updates the selected defender territory.
     */
    private void updateAdjacentTerritory() {
        adjacentTerritory = adjacentTerritories.getSelectedValue();
    }

    /**
     * Updates the defender and friendly territory lists.
     */
    private void updateTerritories() {
        if (currentPhase == 3) {
            adjacentTerritories.setListData(gameBoard.getFriendlyTerritoryList(attackerTerritories.getSelectedValue(), gameActions.getActivePlayer()));
        } else {
            adjacentTerritories.setListData(gameBoard.getAttackableTerritoryList(attackerTerritories.getSelectedValue(), gameActions.getActivePlayer()));
        }

        attackerTerritory = attackerTerritories.getSelectedValue();
        adjacentTerritory = null;
    }

    /**
     * Updates the attackers territory list.
     */
    private void updateAttackerTerritories() {
        attackerTerritories.setListData(gameBoard.getRulerTerritoryList(gameActions.getActivePlayer()));

        attackerTerritory = null;
        adjacentTerritory = null;
    }


    /**
     * Updates the status list.
     */
    private void updateStatusArea() {
        statusText.setText(gameBoard.printBoardStatus());
    }

    /**
     * Creates the players.
     *
     * @return Players
     */
    private ArrayList<Player> initializeStatus() {
        int numOfPlayers = 0;
        int numOfAI = 0;
        while (numOfPlayers < 2 || numOfPlayers > 6) {
            try {
                numOfPlayers = Integer.parseInt(JOptionPane.showInputDialog(this,
                        "Enter Number Of Players (2-6)",
                        "Start Game",
                        JOptionPane.INFORMATION_MESSAGE));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Bad input value.", "Game Closing", JOptionPane.INFORMATION_MESSAGE);
                System.exit(-1);
            }
            try {
                numOfAI = Integer.parseInt(JOptionPane.showInputDialog(this,
                        "Enter Number Of AI Players (max " + (6 - numOfPlayers) + ")",
                        "Start Game",
                        JOptionPane.INFORMATION_MESSAGE));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Bad input value.", "Game Closing", JOptionPane.INFORMATION_MESSAGE);
                System.exit(-1);
            }
        }
        ArrayList<Player> playersList = new ArrayList<>();

        int realPlayers = 0;
        for (int i = 1; i <= numOfPlayers; i++) {
            playersList.add(new Player("Player " + i, false));
            realPlayers = i;
        }
        for (int i = realPlayers + 1; i <= realPlayers + numOfAI; i++) {
            playersList.add(new Player("Player " + i, true));
        }

        return playersList;
    }

    /**
     * Takes all of the elements and creates the frame.
     */
    private void buildFrame() {
        JPanel actionArea = new JPanel();
        JPanel statusArea = new JPanel();

        actionArea.setLayout(new BorderLayout());
        statusArea.setLayout(new BorderLayout());

        JLabel titleStatus = new JLabel("Game Status");
        titleAction = new JLabel("Phase:");

        actionArea.add(titleAction, BorderLayout.PAGE_START);
        actionArea.add(attackerScroller, BorderLayout.WEST);
        actionArea.add(adjacentScroller, BorderLayout.EAST);
        actionArea.add(button, BorderLayout.PAGE_END);

        statusArea.add(titleStatus, BorderLayout.PAGE_START);
        statusArea.add(statusScroller, BorderLayout.EAST);

        this.setLayout(new BorderLayout());
        this.add(menuBar, BorderLayout.PAGE_START);
        this.add(actionArea, BorderLayout.WEST);
        this.add(statusArea, BorderLayout.EAST);

        // Display Steps
        this.setSize(1450, 800);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Initialize all of the controls
     */
    private void createLists() {
        attackerTerritories = new JList<>();
        attackerTerritories.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        attackerTerritories.setLayoutOrientation(JList.VERTICAL);
        attackerTerritories.setVisibleRowCount(-1);

        adjacentTerritories = new JList<>();
        adjacentTerritories.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        adjacentTerritories.setLayoutOrientation(JList.VERTICAL);
        adjacentTerritories.setVisibleRowCount(-1);

        attackerScroller = new JScrollPane(attackerTerritories);
        adjacentScroller = new JScrollPane(adjacentTerritories);
    }

    private void createStatusArea() {
        statusText = new JTextArea();
        statusScroller = new JScrollPane(statusText);
    }

    private void createButtons() {
        button = new JButton(" ");
    }

    /**
     * Initialize all of the menus
     */
    private void createMenus() {
        menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        quit = new JMenuItem("Quit");
        nextPhase = new JMenuItem("Next Phase");

        fileMenu.add(quit);

        menuBar.add(fileMenu);
        menuBar.add(nextPhase);
    }

    /**
     * Updates the GUI when a attack has finished.
     *
     * @param ae Event that occurred
     */
    @Override
    public void attackUpdate(RiskEvent ae) {
        String outcome = "The attacker lost: " + ae.getAttackerLosses() + " armies\n" +
                "The defender lost: " + ae.getDefenderLosses() + " armies\n";

        if (ae.isNewTerritoryRuler()) {
            outcome += "The attacker won the territory";
        }

        if (ae.isNewContinentRuler()) {
            outcome += " and the Continent";
        }

        JOptionPane.showMessageDialog(this, outcome, "Outcome", JOptionPane.INFORMATION_MESSAGE);
        updateAttackerTerritories();
    }

    /**
     * Updates the gui when there is a new player.
     */
    @Override
    public void passUpdate() {
        JOptionPane.showMessageDialog(this,
                "It is now " + gameActions.getActivePlayer() + " turn.",
                "New Turn!",
                JOptionPane.INFORMATION_MESSAGE);
        currentPhase = -1;
        updateAttackerTerritories();
    }

    /**
     * Updates the gui when the status pages needs updating.
     */
    @Override
    public void updateStatus() {
        updateStatusArea();
    }

    @Override
    public void movementUpdate(RiskEvent ae) {
        if (ae.getEventID() == 0) {
            JOptionPane.showMessageDialog(this, "Successful moved armies", "Movement", JOptionPane.INFORMATION_MESSAGE);
            updateAttackerTerritories();
            updateStatus();
        } else {
            JOptionPane.showMessageDialog(this, "Not enough units", "Movement", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void addArmyUpdate(RiskEvent ae) {
        if (ae.getEventID() == 0) {
            JOptionPane.showMessageDialog(this, "Successfully added armies", "Add Army", JOptionPane.INFORMATION_MESSAGE);
            updateAttackerTerritories();
            updateStatus();
        } else {
            JOptionPane.showMessageDialog(this, "Try adding less units. You have " + gameActions.getCurrentPlayersArmies() + " armies left to add", "Add Army", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
