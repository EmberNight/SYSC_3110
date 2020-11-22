import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * GUI implementation
 *
 * @author Tanner trautrim
 * @author Jordan Peterkin
 */
public class Risk extends JFrame implements RiskView {
    private final GameBoard gameBoard;
    private final GameActions gameActions;

    private JMenuBar menuBar;
    private JMenuItem quit, pass, nextPhase;
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

        currentPhase = 0;
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
        buildFrame();

        updateStatusArea();
        beginGame();
    }

    /**
     * Creates all the function events.
     */
    private void assignFunctions() {
        attackerTerritories.addListSelectionListener(e -> updateTerritories());
        adjacentTerritories.addListSelectionListener(e -> updateAdjacentTerritory());
        quit.addActionListener(e -> System.exit(0));
        pass.addActionListener(e -> gameActions.pass());
        nextPhase.addActionListener(e -> updatePhase());
        button.addActionListener(e -> updatePhase());
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

    private void addArmies(){

        if (attackerTerritory == null) {
            JOptionPane.showMessageDialog(this, "Must select the territory you want to add to", "Addition Cancelled", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int numArmies;
        try {
            numArmies = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Number of units to add. You have " + gameActions.getCurrentAddableArmies() + " armies to add.", "Add Armies", JOptionPane.INFORMATION_MESSAGE));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Addition Cancelled", "Bad input.", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        gameActions.addArmies(attackerTerritory.getName(), numArmies);
        if (gameActions.getCurrentAddableArmies() == 1) {
            titleAction.setText("Phase: Add Armies (" + gameActions.getCurrentAddableArmies() + " army left to add)");
        }
        else{
            titleAction.setText("Phase: Add Armies (" + gameActions.getCurrentAddableArmies() + " armies left to add)");
        }
    }

    public void beginGame(){
        JOptionPane.showMessageDialog(this, "Press the Start Game button at the bottom left to begin", "The game is about to begin!", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Advances the state of the game.
     */
    private void updatePhase() {
        updateAttackerTerritories();

        switch (currentPhase) {

            case 0:
                allocateTroopPhase();
                currentPhase = 1;
                return;

            case 1:
                startAttackPhase();
                currentPhase = 2;
                return;

            case 2:
                startMovementPhase();
                currentPhase = 0;
                gameActions.changeTurns();
        }
    }

    private void allocateTroopPhase(){
        JOptionPane.showMessageDialog(this,
                "The army allocation phase has started, you have " + gameActions.getStartAddableArmies() + " armies to add",
                "Add Troops!",
                JOptionPane.INFORMATION_MESSAGE);
        titleAction.setText("Phase: Add Armies (" + gameActions.getCurrentAddableArmies() + " armies left to add)");
        button.setText("Add Armies");

        if (button.getActionListeners().length != 0) {
            button.removeActionListener(button.getActionListeners()[0]);
        }
        button.addActionListener(e -> addArmies());
    }

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

        int attackDice = 0;
        while (attackDice > 3 || attackDice < 1) {
            try {
                attackDice = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Number Dice (1-3) 0 To stop attack", "Attacker", JOptionPane.INFORMATION_MESSAGE));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Attack Cancelled", "Attack Cancelled", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (attackDice == 0) {
                JOptionPane.showMessageDialog(this, "Attack Cancelled", "Attack Cancelled", JOptionPane.INFORMATION_MESSAGE);
                return;
            } else if (attackDice > attackerTerritory.getArmy() - 1) {
                JOptionPane.showMessageDialog(this, "The attacker does not have enough armies for " + attackDice + "dice", "Troop Warning", JOptionPane.INFORMATION_MESSAGE);
                attackDice = 0;
            }
        }

        int defendDice = 0;
        while (defendDice > 2 || defendDice < 1) {
            try {
                defendDice = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Number Dice (1-2)", "Defender", JOptionPane.INFORMATION_MESSAGE));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Defender input bad value.", "Attack Cancelled", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (defendDice > adjacentTerritory.getArmy()) {
                JOptionPane.showMessageDialog(this, "The defender does not have enough armies for " + attackDice + "dice", "Troop Warning", JOptionPane.INFORMATION_MESSAGE);
                defendDice = 0;
            }
        }

        gameActions.attack(attackerTerritory.getName(), adjacentTerritory.getName(), attackDice, defendDice);
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
        if (currentPhase == 2) {
            adjacentTerritories.setListData(gameBoard.getAttackableTerritoryList(attackerTerritories.getSelectedValue(), gameActions.getActivePlayer()));
        } else {
            adjacentTerritories.setListData(gameBoard.getFriendlyTerritoryList(attackerTerritories.getSelectedValue(), gameActions.getActivePlayer()));
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
        }
        ArrayList<Player> playersList = new ArrayList<>();

        for (int i = 1; i <= numOfPlayers; i++) {
            playersList.add(new Player("Player " + i));
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
        button = new JButton("Start Game");
    }

    /**
     * Initialize all of the menus
     */
    private void createMenus() {
        menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        quit = new JMenuItem("Quit");
        pass = new JMenuItem("Pass");
        nextPhase = new JMenuItem("Next Phase");

        fileMenu.add(quit);

        menuBar.add(fileMenu);
        menuBar.add(pass);
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
        currentPhase = 0;
        updatePhase();
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
            JOptionPane.showMessageDialog(this, "Try adding less units. You have " + gameActions.getCurrentAddableArmies() + " armies left to add", "Add Army", JOptionPane.INFORMATION_MESSAGE);
        }
    }


}
