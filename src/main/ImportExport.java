import java.io.*;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;

import java.util.*;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Provides methods for saved games and custom maps
 *
 * @author Tanner trautrim
 * @author Emmitt Luhning
 * @author Ashwin Stoparczyk
 */
public class ImportExport {
    /**
     * Saves the current board state as a file
     * Copy of normal method, exists so that save file won't be overwritten when test is run
     *
     * @param baseGame The Risk game to be saved
     */
    public static void saveGame(Risk baseGame, File fileName) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
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
     *
     * @param baseGame The Risk game to be overwritten by the saved game
     */
    public static void loadGame(Risk baseGame, File fileName) {
        Risk loadedGame;
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
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

        loadedGame.getGameActions().setActionView(baseGame);
        baseGame.loadGame(loadedGame.getPhase(), loadedGame.getGameBoard(), loadedGame.getGameActions());
    }

    /**
     * Imports a custom map, in XML format, to be used as a new GameBoard
     *
     * @param fileName The file containing the custom map
     */
    public static GameBoard importCustomMap(File fileName) {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp;

        try {
            sp = spf.newSAXParser();
        } catch (Exception e) {
            return null;
        }

        Map<String, Continent> continentMap = new HashMap<>();
        Map<String, Territory> territoryMap = new HashMap<>();
        Map<String, ArrayList<String>> adjacentTerritories = new HashMap<>();

        DefaultHandler dh = new DefaultHandler() {
            private Continent continent;
            private Territory territory;
            int mode = 0;


            // Sets how the other functions should behave
            public void startElement(String u, String ln, String qName, Attributes a) {
                if ("continent".equals(qName)) {
                    // If the xml tag is equal to continent it creates a continent
                    continent = new Continent();
                } else if ("continentName".equals(qName)) {
                    // If the xml tag is equal to continentName if set the mode so that in the
                    // characters function it will set the last added continent's name
                    mode = 1;
                } else if ("continentValue".equals(qName)) {
                    // If the xml tag is equal to continentValue if set the mode so that in the
                    // characters function it will set the last added continent's value
                    mode = 2;
                } else if ("territoryName".equals(qName)) {
                    // If the xml tag is equal to territoryName if set the mode so that in the
                    // characters function it will create a territory and added it to the last
                    // added continent.
                    mode = 3;
                } else if ("adjacentName".equals(qName)) {
                    // If the xml tag is equal to adjacentName if set the mode so that in the
                    // characters function it will add a adjacent territory to the last
                    // added territory.
                    mode = 4;
                }

            }

            // Ends the continent phase
            public void endElement(String uri, String localName, String qName) {
                if ("continent".equals(qName)) {
                    continentMap.put(continent.getName(), continent);
                }
                mode = 0;
            }

            // Handles attributes
            public void characters(char[] ch, int start, int length) {
                switch (mode) {
                    case 1: // Sets the last added continent's name
                        continent.setName(new String(ch, start, length));
                        break;
                    case 2: // Sets the last added continent's value
                        String num = new String(ch, start, length);
                        continent.setValue(Integer.parseInt(num));
                        break;
                    case 3: // Creates a territory and adds it to the last continent added.
                        territory = new Territory(new String(ch, start, length), continent.getName());
                        continent.addTerritory(territory);
                        territoryMap.put(territory.getName(), territory);
                        // Used to store territories temporarily, so that a real territory can be made later
                        adjacentTerritories.put(territory.getName(), new ArrayList<>());
                        break;
                    case 4: // Adds an adjacent territory to the last added territory.
                        adjacentTerritories.get(territory.getName()).add(new String(ch, start, length));
                }
            }
        };

        try {
            sp.parse(fileName, dh);
        } catch (Exception e) {
            return null;
        }


        // After all territories have been created by the XML sort out the adjacent territories
        ArrayList<String> territoryList = new ArrayList<>();
        for (String key : territoryMap.keySet()) {
            while (!adjacentTerritories.get(key).isEmpty()) {
                territoryList.add(adjacentTerritories.get(key).get(0).toLowerCase()); // Used to Check if all territories can be reached!
                String t = adjacentTerritories.get(key).remove(0).toLowerCase();
                Territory adjacent = territoryMap.get(t);

                if (adjacent != null) {
                    territoryMap.get(key).setAdjacentTerritory(adjacent, adjacent.getName());
                }
            }
        }

        // Check if all territories can be reached.
        for (int i = 0; i < territoryList.size(); i++) {
            if (Collections.frequency(territoryList, territoryList.get(i)) > 1) {
                territoryList.remove(i);
                i--;
            }
        }

        if (territoryList.size() != territoryMap.keySet().size()) {
            return null;
        }

        // Check if all Continents can be reached.
        for (String s : continentMap.keySet()) {
            boolean isIsland = true;
            Continent continent = continentMap.get(s);
            for (Territory t : continent.getTerritories()) {
                if (continent.getName().equals(t.getContinentName())) {
                    isIsland = false;
                    break;
                }
            }
            if (isIsland) {
                return null;
            }
        }

        return new GameBoard(continentMap, territoryMap);
    }
}
