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
    private JMenuItem quit, pass;
    private JList<Territory> attackerTerritories, defenderTerritories;
    private JScrollPane attackerScroller, defenderScroller, statusScroller;
    private JButton attackButton;
    private JTextArea statusText;

    private Territory attackerTerritory;
    private Territory defenderTerritory;

    public Risk(String label) {
        super(label);

        attackerTerritory = null;
        defenderTerritory = null;

        ArrayList<Player> playersList = initializeStatus();
        gameBoard = new GameBoard();
        gameActions = new GameActions(this, playersList, gameBoard);

        createMenus();
        createButtons();
        createStatusArea();
        createAttackLists();
        assignFunctions();
        buildFrame();

        updateStatusArea();
        updateAttackerTerritories();
    }

    /**
     * Creates all the function events.
     */
    private void assignFunctions() {
        attackerTerritories.addListSelectionListener(e -> updateDefenderTerritories());
        defenderTerritories.addListSelectionListener(e -> updateDefenderTerritory());
        attackButton.addActionListener(e -> performAttack());
        quit.addActionListener(e -> System.exit(0));
        pass.addActionListener(e -> gameActions.pass());
    }

    /**
     * Performs an attack and checks the inputs for errors.
     */
    private void performAttack() {
        if (attackerTerritory == null || defenderTerritory == null) {
            JOptionPane.showMessageDialog(this,"Must select attacking and defending territories","Attack Cancelled", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int attackDice = 0;
        while (attackDice > 3 || attackDice < 1) {
            try {
                attackDice = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Number Dice (1-3) 0 To stop attack", "Attacker", JOptionPane.INFORMATION_MESSAGE));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,"Attack Cancelled","Attack Cancelled", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (attackDice == 0) {
                JOptionPane.showMessageDialog(this,"Attack Cancelled","Attack Cancelled", JOptionPane.INFORMATION_MESSAGE);
                return;
            } else if (attackDice > attackerTerritory.getArmy()-1) {
                JOptionPane.showMessageDialog(this,"The attacker does not have enough armies for " + attackDice + "dice","Troop Warning", JOptionPane.INFORMATION_MESSAGE);
                attackDice = 0;
            }
        }

        int defendDice = 0;
        while (defendDice > 2 || defendDice < 1) {
            try {
                defendDice = Integer.parseInt(JOptionPane.showInputDialog(this,"Enter Number Dice (1-2)","Defender", JOptionPane.INFORMATION_MESSAGE));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,"Defender input bad value.","Attack Cancelled", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (defendDice > defenderTerritory.getArmy()) {
                JOptionPane.showMessageDialog(this,"The defender does not have enough armies for " + attackDice + "dice","Troop Warning", JOptionPane.INFORMATION_MESSAGE);
                defendDice = 0;
            }
        }

        gameActions.attack(attackerTerritory.getName(), defenderTerritory.getName(), attackDice, defendDice);
    }

    /**
     * Updates the selected defender territory.
     */
    private void updateDefenderTerritory() {
        defenderTerritory = defenderTerritories.getSelectedValue();
    }

    /**
     * Updates the defender territory list.
     */
    private void updateDefenderTerritories() {
        defenderTerritories.setListData(gameBoard.getAttackableTerritoryList(attackerTerritories.getSelectedValue(), gameActions.getActivePlayer()));
        defenderTerritory = null;
        attackerTerritory = attackerTerritories.getSelectedValue();
    }

    /**
     * Updates the attackers territory list.
     */
    private void updateAttackerTerritories() {
        attackerTerritories.setListData(gameBoard.getRulerTerritoryList(gameActions.getActivePlayer()));
        attackerTerritory = null;
        defenderTerritory = null;
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
                numOfPlayers = Integer.parseInt(JOptionPane.showInputDialog(this,
                        "Enter Number Of Players (max " + (6 - numOfPlayers) + ")",
                        "Start Game",
                        JOptionPane.INFORMATION_MESSAGE));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Bad input value.", "Game Closing", JOptionPane.INFORMATION_MESSAGE);
                System.exit(-1);
            }
        }
        ArrayList<Player> playersList = new ArrayList<>();

        int realPlayers = 0;
        for (int i = 1; i <= numOfPlayers; i++){
            playersList.add(new Player("Player " + i, false));
            realPlayers = i;
        }
        for (int i = realPlayers; i <= realPlayers + numOfAI; i++){
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

        JTextArea titleStatus = new JTextArea("Game Status");
        JTextArea titleAction = new JTextArea("Attack");

        titleStatus.setEditable(false);
        titleStatus.setBackground(actionArea.getBackground());

        titleAction.setEditable(false);
        titleAction.setBackground(actionArea.getBackground());

        actionArea.add(titleAction, BorderLayout.PAGE_START);
        actionArea.add(attackerScroller, BorderLayout.WEST);
        actionArea.add(defenderScroller, BorderLayout.EAST);
        actionArea.add(attackButton, BorderLayout.PAGE_END);

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
    private void createAttackLists() {
        attackerTerritories = new JList<>();
        attackerTerritories.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        attackerTerritories.setLayoutOrientation(JList.VERTICAL);
        attackerTerritories.setVisibleRowCount(-1);

        defenderTerritories = new JList<>();
        defenderTerritories.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        defenderTerritories.setLayoutOrientation(JList.VERTICAL);
        defenderTerritories.setVisibleRowCount(-1);

        attackerScroller = new JScrollPane(attackerTerritories);
        defenderScroller = new JScrollPane(defenderTerritories);
    }

    private void createStatusArea() {
        statusText = new JTextArea();
        statusScroller = new JScrollPane(statusText);
    }

    private void createButtons() {
        attackButton = new JButton("Attack");
    }

    /**
     * Initialize all of the menus
     */
    private void createMenus() {
        menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        quit = new JMenuItem("Quit");
        pass = new JMenuItem("Pass");

        fileMenu.add(quit);

        menuBar.add(fileMenu);
        menuBar.add(pass);
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

        JOptionPane.showMessageDialog(this,outcome,"Outcome", JOptionPane.INFORMATION_MESSAGE);
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
        updateAttackerTerritories();
    }

    /**
     * Updates the gui when the status pages needs updating.
     */
    @Override
    public void updateStatus() {
        updateStatusArea();
    }
}
