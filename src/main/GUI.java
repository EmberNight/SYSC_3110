import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI {

    public Actions actions;
    public GameBoard gameboard;

    public GUI(){
        JFrame frame = new JFrame("Risk");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = frame.getContentPane();

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem startGame = new JMenuItem("Play Game");
        fileMenu.add(startGame);

        ScrollPane scroll = new ScrollPane();
        TextArea text = new TextArea();
        text.setEditable(false);
        scroll.add(text);

        contentPane.add(scroll);


        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane option = new JOptionPane();

                int numOfPlayers = Integer.parseInt(option.showInputDialog((Component) e.getSource(),
                        "Enter Number Of Players (2-6)",
                        "Start Game",
                        JOptionPane.INFORMATION_MESSAGE));
                ArrayList<Player> playersList = new ArrayList<>();
                for (int i = 1; i <= numOfPlayers; i++){
                    playersList.add(new Player("Player " + i));
                }
                gameboard = new GameBoard();
                actions = new Actions(playersList, gameboard);
                String string = actions.printStatus();
                text.setText(string);
            }
        });
        frame.setJMenuBar(menuBar);
        frame.setSize(550, 350);
        frame.setVisible(true);
    }
}
