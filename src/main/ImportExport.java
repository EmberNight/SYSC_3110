import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class ImportExport {
    public void saveGame(File fileName) {
        // Emmit
        // Serialization
    }
    public void loadGame(File fileName) {
        // Ashwin
        // Serialization
    }

    public GameBoard importCustomMap(File fileName) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();
        Map<String, Continent> continentMap = new HashMap<>();
        Map<String, Territory> territoryMap = new HashMap<>();
        Map<String, ArrayList<String>> adjacentTerritories = new HashMap<>();

        DefaultHandler dh = new DefaultHandler() {
            private Continent continent;
            private Territory territory;
            int mode = 0;


            public void startElement(String u, String ln, String qName, Attributes a) {
                System.out.println("Start: " + qName + "\n");
                if ("continent".equals(qName))  {
                    continent = new Continent();
                } else if ("continentName".equals(qName)) {
                    mode = 1;
                } else if ("continentValue".equals(qName)) {
                    mode = 2;
                } else if ("territoryName".equals(qName)) {
                    mode = 3;
                } else if ("adjacentName".equals(qName)) {
                    mode = 4;
                }

            }

            public void endElement(String uri, String localName, String qName) {
                System.out.println("End: " + qName + "\n");
                if ("continent".equals(qName))  {
                    continentMap.put(continent.getName(), continent);
                }
                mode = 0;
            }

            public void characters(char[] ch, int start, int length) {
                System.out.println("char: " + new String(ch, start, length) + "\n");

                switch(mode) {
                    case 1:
                        continent.setName(new String(ch, start, length));
                        break;
                    case 2:
                        String num = new String(ch, start, length);
                        continent.setValue(Integer.parseInt(num));
                        break;
                    case 3:
                        territory = new Territory(new String(ch, start, length), continent.getName());
                        continent.addTerritory(territory);
                        territoryMap.put(territory.getName(), territory);
                        adjacentTerritories.put(territory.getName(), new ArrayList<>());
                        break;
                    case 4:
                        adjacentTerritories.get(territory.getName()).add(new String(ch, start, length));
                }
            }
        };

        sp.parse(fileName, dh);

        for(String key : territoryMap.keySet()) {
            while (!adjacentTerritories.get(key).isEmpty()) {
                String t = adjacentTerritories.get(key).remove(0).toLowerCase();
                Territory adjacent = territoryMap.get(t);

                if (adjacent != null) {
                    territoryMap.get(key).setAdjacentTerritory(adjacent, adjacent.getName());
                }
            }
        }

        return new GameBoard(continentMap, territoryMap);
    }
}
