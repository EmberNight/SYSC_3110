import javax.swing.*;
import java.io.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImportExport {
    private JFrame win;
    public static void saveGame(Risk baseGame) {
        // Emmitt
        // Serialization
        //Step One. Serialize Risk object
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
    public void importCustomMap(File fileName) {
        // Tanner
        // Use xml or jason with the PAX parser}
    }
}
