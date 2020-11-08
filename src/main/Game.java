import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Game extends JFrame {
    private GameBoard gameBoard;
    private Actions actions;

    private JMenuBar menuBar;
    private JMenuItem quit, pass, startGame;
    private JList<Territory> attackerTerritories, defenderTerritories;
    private JScrollPane attackerScroller, defenderScroller, statusScroller;
    private JButton attackButton;
    private JTextArea statusText;

    private ArrayList<Player> playersList;

    public Game(String label) {
        super(label);

        playersList = initializeStatus();
        gameBoard = new GameBoard();
        actions = new Actions(playersList, gameBoard);

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
        //attackButton.addActionListener(e -> actions.attack());
        quit.addActionListener(e -> System.exit(0));
        pass.addActionListener(e -> actions.pass());
        startGame.addActionListener(e -> initializeStatus());
    }

    private void updateDefenderTerritories() {
        defenderTerritories.setListData(gameBoard.getAttackableTerritoryList(attackerTerritories.getSelectedValue()));
    }

    private void updateAttackerTerritories() {
        attackerTerritories.setListData(gameBoard.getRulerTerritoryList(actions.getActivePlayer()));
    }

    private void updateStatusArea() {
        statusText.setText(gameBoard.printBoardStatus());
    }

    private ArrayList<Player> initializeStatus() {
        JOptionPane option = new JOptionPane();

        int numOfPlayers = Integer.parseInt(option.showInputDialog(this,
                "Enter Number Of Players (2-6)",
                "Start Game",
                JOptionPane.INFORMATION_MESSAGE));
        ArrayList<Player> playersList = new ArrayList<>();

        for (int i = 1; i <= numOfPlayers; i++){
            playersList.add(new Player("Player " + i));
        }

        return playersList;
    }

    /**
     * Takes all of the elements and creates the frame.
     */
    private void buildFrame() {
        this.setLayout(new BorderLayout());
        this.add(menuBar, BorderLayout.PAGE_START);
        this.add(attackerScroller, BorderLayout.WEST);
        this.add(attackButton, BorderLayout.PAGE_END);
        this.add(defenderScroller, BorderLayout.CENTER);
        this.add(statusScroller, BorderLayout.EAST);

        // Display Steps
        this.setSize(1500, 800);
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

        JMenu menu = new JMenu("menu");
        quit = new JMenuItem("Quit");
        pass = new JMenuItem("Pass");
        JMenu fileMenu = new JMenu("File");
        startGame = new JMenuItem("Play Game");

        menuBar.add(fileMenu);
        fileMenu.add(startGame);
        menuBar.add(menu);
        menuBar.add(pass);

        menu.add(quit);
    }
}
