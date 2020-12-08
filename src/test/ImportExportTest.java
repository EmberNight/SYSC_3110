import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for ImportExport
 *
 * @author Ashwin Stoparczyk
 */
public class ImportExportTest {

    @Test
    public void testSaveLoad(){ //Some dialogue box shows up stating that the attack phase started, click the button on it to advance the test. It does nothing to affect the test
        Risk baseGame = new Risk("Base game", true);

        ImportExport.saveGame(baseGame);

        Risk savedGame = new Risk("Saved game", true);
        ImportExport.loadGame(savedGame);

        //phase tests
        assertEquals(baseGame.getPhase(), savedGame.getPhase());

        //gameBoard tests
        assertEquals(baseGame.getGameBoard().getArmy("canada"), savedGame.getGameBoard().getArmy("canada")); //Test armies of Territories
        assertEquals(baseGame.getGameBoard().getArmy("united states"), savedGame.getGameBoard().getArmy("united states"));

        assertEquals(baseGame.getGameBoard().getContinent("North America").getTerritories().get(0).
                getRuler(),savedGame.getGameBoard().getContinent("North America").getTerritories().get(0).getRuler()); //Test Continent's Territories' rulers
        assertEquals(baseGame.getGameBoard().getContinent("North America").getTerritories().get(1).
                getRuler(),savedGame.getGameBoard().getContinent("North America").getTerritories().get(1).getRuler());

        //gameActions tests
        assertEquals(baseGame.getGameActions().getActivePlayer(), savedGame.getGameActions().getActivePlayer()); //Test active players
        assertEquals(baseGame.getGameActions().getCurrentPlayersArmies(), savedGame.getGameActions().getCurrentPlayersArmies()); //Test active player's armies

        //Territory tests
        assertEquals(baseGame.getAdjacentTerritory(), savedGame.getAdjacentTerritory()); //Test adjacent Territory
        assertEquals(baseGame.getAttackerTerritory(), savedGame.getAttackerTerritory()); //Test attacker Territory
    }

    @Test
    public void testCustomMap() {
        createTestMap();
        File testMapFile = new File("testMap.xml");
        GameBoard testBoard = ImportExport.importCustomMap(testMapFile);

        //Continent tests
        assertEquals(testBoard.getContinent("North America").getName(), "North America");
        assertEquals(testBoard.getContinent("North America").getValue(), 3);

        //Territory tests
        Territory canada = testBoard.getContinent("North America").getTerritories().get(0); //Canada
        assertEquals(canada.getName(), "canada");//Canada
        assertEquals(canada.getAdjacentTerritory("united states").getName(), "united states");

        Territory united = testBoard.getContinent("North America").getTerritories().get(1); //US
        assertEquals(united.getName(), "united states");
        assertEquals(united.getAdjacentTerritory("canada").getName(), "canada");
        assertEquals(united.getAdjacentTerritory("mexico").getName(), "mexico");

        Territory mexico = testBoard.getContinent("North America").getTerritories().get(2); //Mexico
        assertEquals(mexico.getName(), "mexico");
        assertEquals(mexico.getAdjacentTerritory("united states").getName(), "united states");
    }

    /**
     * Creates a small, testable map in an XML file called "testMap.xml"
     */
    public void createTestMap(){
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();

            // root element
            Element root = document.createElement("gameboard");
            document.appendChild(root);

            //continent
            Element continent = document.createElement("continent");
            root.appendChild(continent);
            Element continentName = document.createElement("continentName");
            continentName.appendChild(document.createTextNode("North America"));
            continent.appendChild(continentName);
            Element continentValue = document.createElement("continentValue");
            continentValue.appendChild(document.createTextNode("3"));
            continent.appendChild(continentValue);

            //canada
            Element canada = document.createElement("territory");
            continent.appendChild(canada);

            Element canadaName = document.createElement("territoryName");
            canadaName.appendChild(document.createTextNode("canada"));
            canada.appendChild(canadaName);

            Element canadaAdjacent = document.createElement("adjacentName");
            canadaAdjacent.appendChild(document.createTextNode("united states"));
            canada.appendChild(canadaAdjacent);

            //united states
            Element united = document.createElement("territory");
            continent.appendChild(united);

            Element unitedName = document.createElement("territoryName");
            unitedName.appendChild(document.createTextNode("united states"));
            united.appendChild(unitedName);

            Element unitedAdjacent1 = document.createElement("adjacentName");
            unitedAdjacent1.appendChild(document.createTextNode("canada"));
            united.appendChild(unitedAdjacent1);

            Element unitedAdjacent2 = document.createElement("adjacentName");
            unitedAdjacent2.appendChild(document.createTextNode("mexico"));
            united.appendChild(unitedAdjacent2);

            //mexico
            Element mexico = document.createElement("territory");
            continent.appendChild(mexico);

            Element mexicoName = document.createElement("territoryName");
            mexicoName.appendChild(document.createTextNode("mexico"));
            mexico.appendChild(mexicoName);

            Element mexicoAdjacent = document.createElement("adjacentName");
            mexicoAdjacent.appendChild(document.createTextNode("united states"));
            mexico.appendChild(mexicoAdjacent);

            //create XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File("testMap.xml"));

            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File");

        } catch (ParserConfigurationException p){
            p.printStackTrace();
        } catch (TransformerConfigurationException tc){
            tc.printStackTrace();
        } catch (TransformerException te){
            te.printStackTrace();
        }
    }
}
