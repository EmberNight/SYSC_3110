import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {
    private final GameBoard gameBoard;
    private final Actions actions;

    private JMenuBar menuBar;
    private JMenuItem quit, pass;
    private JList<Territory> attackerTerritories, defenderTerritories;
    private JScrollPane attackerScroller, defenderScroller;
    private JButton attackButton;

    public Game(String label, Actions actions, GameBoard gameBoard) {
        super(label);
        this.gameBoard = gameBoard;
        this.actions = actions;

        createAttackLists();
        createMenus();
        createButtons();
        assignFunctions();
        buildFrame();
    }

    /**
     * Creates all the function events.
     */
    private void assignFunctions() {
        attackerTerritories.addListSelectionListener(e -> updateDefenderTerritories());
        attackButton.addActionListener(e -> actions.attack());
        quit.addActionListener(e -> System.exit(0));
        pass.addActionListener(e -> actions.pass());
    }

    private void updateDefenderTerritories() {
        defenderTerritories.setListData(gameBoard.getAttackableTerritoryList(attackerTerritories.getSelectedValue()));
    }

    /**
     * Takes all of the elements and creates the frame.
     */
    private void buildFrame() {
        JPanel j = new JPanel();
        j.setLayout(new BorderLayout());
        this.setLayout(new BorderLayout());
        this.add(menuBar, BorderLayout.PAGE_START);
        this.add(j, BorderLayout.WEST);
        j.add(attackerScroller, BorderLayout.CENTER);
        j.add(attackButton, BorderLayout.PAGE_END);
        this.add(defenderScroller, BorderLayout.CENTER);

        // Display Steps
        this.setSize(600, 600);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Initialize all of the controls
     */
    private void createAttackLists() {
        attackerTerritories = new JList<>();
        attackerTerritories.setListData(gameBoard.getRulerTerritoryList(actions.getActivePlayer()));
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

        menuBar.add(menu);
        menuBar.add(pass);

        menu.add(quit);
    }
}
