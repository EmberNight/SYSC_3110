import javax.swing.*;
import java.io.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Provides methods for saved games and custom maps
 * @author Tanner trautrim
 * @author Emmitt Luhning
 * @author Ashwin Stoparczyk
 */
public class ImportExport {
    private JFrame win;

    /**
     * Saves the current board state as a file
     * @param baseGame The Risk game to be saved
     */
    public static void saveGame(Risk baseGame) {
        try {
            FileOutputStream fileOut = new FileOutputStream("gameSave.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(baseGame);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Loads a board state from a file
     * @param baseGame The Risk game to be overwritten by the saved game
     */
    public static void loadGame(Risk baseGame) {
        Risk loadedGame;
        try {
            FileInputStream fileIn = new FileInputStream("gameSave.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            loadedGame = (Risk) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
            return;
        }
        baseGame.loadGame(loadedGame.getPhase(), loadedGame.getGameBoard(), loadedGame.getGameActions(), loadedGame.getPlayerList(),
                loadedGame.getAdjacentTerritory(), loadedGame.getAttackerTerritory());
    }

    /**
     * Saves the current board state as a file
     * Copy of normal method, exists so that save file won't be overwritten when test is run
     * @param baseGame The Risk game to be saved
     */
    public static void saveGameTest(Risk baseGame) {
        try {
            FileOutputStream fileOut = new FileOutputStream("gameSaveTest.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(baseGame);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Loads a board state from a file
     * Copy of normal method, exists so that save file won't be overwritten when test is run
     * @param baseGame The Risk game to be overwritten by the saved game
     */
    public static void loadGameTest(Risk baseGame) {
        Risk loadedGame;
        try {
            FileInputStream fileIn = new FileInputStream("gameSaveTest.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            loadedGame = (Risk) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
            return;
        }
        baseGame.loadGame(loadedGame.getPhase(), loadedGame.getGameBoard(), loadedGame.getGameActions(), loadedGame.getPlayerList(),
                loadedGame.getAdjacentTerritory(), loadedGame.getAttackerTerritory());
    }

    /**
     * Imports a custom map, in XML format, to be used as a new GameBoard
     * @param fileName The file containing the custom map
     */
    public void importCustomMap(File fileName) {
        // Tanner
        // Use xml or jason with the PAX parser}
    }
}
