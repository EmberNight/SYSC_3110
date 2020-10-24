import javax.management.remote.rmi.RMIConnectionImpl;
import java.util.*;

public class GameBoard {

    private Map<String, Continent> continentMap;
    private Map<String, Territory> territoryMap;
    private CircularLinkedList players;
    private List<String> randomTerritories;

    public GameBoard(){
        continentMap = new HashMap<>();
        createBoard();
        this.players = players;
        randomTerritories = new ArrayList<>();
    }

    public void createBoard(){

        // create continents
        Continent northAmerica = new Continent("North America", 5);
        Continent southAmerica = new Continent("South America", 2);
        Continent europe = new Continent("Europe", 5);
        Continent africa = new Continent("Africa", 3);
        Continent asia = new Continent("Asia", 7);
        Continent australia = new Continent("Australia", 2);

        //add continents to continentMap
        continentMap.put("North America", northAmerica);
        continentMap.put("South America", southAmerica);
        continentMap.put("Europe", europe);
        continentMap.put("Africa", africa);
        continentMap.put("Asia", asia);
        continentMap.put("Australia", australia);

        //create North America Territories
        Territory alaska = new Territory("Alaska", "North America");
        northAmerica.addTerritory(alaska);

        Territory northwestTerritories = new Territory("Northwest Territories", "North America");
        northAmerica.addTerritory(northwestTerritories);

        Territory alberta = new Territory("Alberta", "North America");
        northAmerica.addTerritory(alberta);

        Territory ontario = new Territory("Ontario", "North America");
        northAmerica.addTerritory(ontario);

        Territory easternCanada = new Territory("Eastern Canada", "North America");
        northAmerica.addTerritory(easternCanada);

        Territory greenland = new Territory("Greenland", "North America");
        northAmerica.addTerritory(greenland);

        Territory westernUnitedStates = new Territory("Western United States", "North America");
        northAmerica.addTerritory(westernUnitedStates);

        Territory easternUnitedStates = new Territory("Eastern United Staes", "North America");
        northAmerica.addTerritory(easternUnitedStates);

        Territory centralAmerica = new Territory("Central America", "North America");
        northAmerica.addTerritory(centralAmerica);

        // create South America Territories
        Territory venezuela = new Territory("Venezuela", "South America");
        southAmerica.addTerritory(venezuela);

        Territory brazil = new Territory("Brazil", "South America");
        southAmerica.addTerritory(brazil);

        Territory peru = new Territory("Peru", "South America");
        southAmerica.addTerritory(peru);

        Territory argentina = new Territory("Argentina", "South America");
        southAmerica.addTerritory(argentina);

        //creates Europe Territories
        Territory iceland = new Territory("Iceland", "Europe");
        europe.addTerritory(iceland);

        Territory scandinavia = new Territory("Scandinavia", "Europe");
        europe.addTerritory(scandinavia);

        Territory russia = new Territory("Russia", "Europe");
        europe.addTerritory(russia);

        Territory greatBritain = new Territory("Great Britain", "Europe");
        europe.addTerritory(greatBritain);

        Territory northernEurope = new Territory("Northern Europ", "Europe");
        europe.addTerritory(northernEurope);

        Territory westernEurope = new Territory("Western Europe", "Europe");
        europe.addTerritory(westernEurope);

        Territory southernEurope = new Territory("Souther Europe", "Europe");
        europe.addTerritory(southernEurope);

        // creates Africa Territories
        Territory northAfrica = new Territory("North Africa", "Africa");
        africa.addTerritory(northAfrica);

        Territory egypt = new Territory("Egypt", "Africa");
        africa.addTerritory(egypt);

        Territory eastAfrica = new Territory("East Africa", "Africa");
        africa.addTerritory(eastAfrica);

        Territory centralAfrica = new Territory("Cental Africa", "Africa");
        africa.addTerritory(centralAfrica);

        Territory southAfrica = new Territory("South Africa", "Africa");
        africa.addTerritory(southAfrica);

        Territory madagascar = new Territory("Madagascar", "Africa");
        africa.addTerritory(madagascar);

        //creates Asia Territories
        Territory middleEast = new Territory("Middle East", "Asia");
        asia.addTerritory(middleEast);

        Territory afghanistan = new Territory("Afghanistan", "Asia");
        asia.addTerritory(afghanistan);

        Territory india = new Territory("India", "Asia");
        asia.addTerritory(india);

        Territory southeastAsia = new Territory("Southeast Asia", "Asia");
        asia.addTerritory(southeastAsia);

        Territory china = new Territory("China", "Asia");
        asia.addTerritory(china);

        Territory ural = new Territory("Ural", "Asia");
        asia.addTerritory(ural);

        Territory siberia = new Territory("Siberia", "Asia");
        asia.addTerritory(siberia);

        Territory yakutsk = new Territory("Yakutsk", "Asia");
        asia.addTerritory(yakutsk);

        Territory kamchatka = new Territory("Kamchatka", "Asia");
        asia.addTerritory(kamchatka);

        Territory irkutsk = new Territory("Irkutsk", "Asia");
        asia.addTerritory(irkutsk);

        Territory mongolia = new Territory("Mongolia", "Asia");
        asia.addTerritory(mongolia);

        Territory japan = new Territory("Japan", "Asia");
        asia.addTerritory(japan);


        // creates Australia Territories
        Territory indonesia = new Territory("Indonesia", "Australia");
        australia.addTerritory(indonesia);

        Territory newGuinea = new Territory("New Guinea", "Australia");
        australia.addTerritory(newGuinea);

        Territory westernAustralia = new Territory("Western Australia", "Australia");
        australia.addTerritory(westernAustralia);

        Territory easternAustralia = new Territory("Eastern Australia", "Australia");
        australia.addTerritory(easternAustralia);

        // set adjacencies for North America Territories
        alaska.setAdjacentTerritory(northwestTerritories, "Northwest Territories");
        alaska.setAdjacentTerritory(alberta, "Alberta");

        northwestTerritories.setAdjacentTerritory(alaska, "Alaska");
        northwestTerritories.setAdjacentTerritory(alberta, "Alberta");
        northwestTerritories.setAdjacentTerritory(ontario, "Ontario");
        northwestTerritories.setAdjacentTerritory(greenland, "Greenland");

        alberta.setAdjacentTerritory(alaska, "Alaska");
        alberta.setAdjacentTerritory(northwestTerritories, "Northwest Territories");
        alberta.setAdjacentTerritory(ontario, "Ontario");
        alberta.setAdjacentTerritory(westernUnitedStates, "Western United States");

        ontario.setAdjacentTerritory(northwestTerritories, "Northwest Territories");
        ontario.setAdjacentTerritory(alberta, "Alberta");
        ontario.setAdjacentTerritory(westernUnitedStates, "Western United States");
        ontario.setAdjacentTerritory(easternUnitedStates, "Eastern United States");
        ontario.setAdjacentTerritory(easternCanada, "Eastern Canada");
        ontario.setAdjacentTerritory(greenland, "Greenland");

        easternCanada.setAdjacentTerritory(greenland, "Greenland");
        easternCanada.setAdjacentTerritory(ontario, "Ontario");
        easternCanada.setAdjacentTerritory(easternUnitedStates, "Eastern United States");

        greenland.setAdjacentTerritory(northwestTerritories, "Northwest Territories");
        greenland.setAdjacentTerritory(ontario, "Ontario");
        greenland.setAdjacentTerritory(easternCanada, "Eastern Canada");
        greenland.setAdjacentTerritory(iceland, "Iceland");

        westernUnitedStates.setAdjacentTerritory(alberta, "Alberta");
        westernUnitedStates.setAdjacentTerritory(ontario, "Ontario");
        westernUnitedStates.setAdjacentTerritory(easternUnitedStates, "Eastern United States");
        westernUnitedStates.setAdjacentTerritory(centralAmerica, "Central America");

        easternUnitedStates.setAdjacentTerritory(westernUnitedStates, "Western United States");
        easternUnitedStates.setAdjacentTerritory(ontario, "Ontario");
        easternUnitedStates.setAdjacentTerritory(easternCanada, "Eastern Canada");
        easternUnitedStates.setAdjacentTerritory(centralAmerica, "Central America");

        centralAmerica.setAdjacentTerritory(westernUnitedStates, "Western United States");
        centralAmerica.setAdjacentTerritory(easternUnitedStates, "Eastern United States");
        centralAmerica.setAdjacentTerritory(venezuela, "Venezuela");

        // set adjacencies for South America Territories
        venezuela.setAdjacentTerritory(centralAmerica,"Central America");
        venezuela.setAdjacentTerritory(brazil, "Brazil");
        venezuela.setAdjacentTerritory(peru, "Peru");

        peru.setAdjacentTerritory(venezuela, "Venezuela");
        peru.setAdjacentTerritory(brazil, "Brazil");
        peru.setAdjacentTerritory(argentina, "Argentina");

        argentina.setAdjacentTerritory(peru, "Peru");
        argentina.setAdjacentTerritory(brazil, "Brazil");

        brazil.setAdjacentTerritory(venezuela, "Venezuela");
        brazil.setAdjacentTerritory(peru, "Peru");
        brazil.setAdjacentTerritory(argentina, "Argentina");
        brazil.setAdjacentTerritory(northAfrica, "North Africa");

        //set adjacencies for Africa Territories
        northAfrica.setAdjacentTerritory(brazil, "Brazil");
        northAfrica.setAdjacentTerritory(westernEurope, "Western Europe");
        northAfrica.setAdjacentTerritory(southernEurope, "Southern Europe");
        northAfrica.setAdjacentTerritory(egypt, "Egypt");
        northAfrica.setAdjacentTerritory(eastAfrica, "East Africa");
        northAfrica.setAdjacentTerritory(centralAfrica, "Central Africa");

        egypt.setAdjacentTerritory(northAfrica, "North Africa");
        egypt.setAdjacentTerritory(southernEurope, "Southern Europe");
        egypt.setAdjacentTerritory(middleEast, "Middle East");
        egypt.setAdjacentTerritory(eastAfrica, "East Africa");

        eastAfrica.setAdjacentTerritory(northAfrica, "North Africa");
        eastAfrica.setAdjacentTerritory(egypt, "Egypt");
        eastAfrica.setAdjacentTerritory(middleEast, "Middle East");
        eastAfrica.setAdjacentTerritory(centralAfrica, "Central Africa");
        eastAfrica.setAdjacentTerritory(southAfrica, "South Africa");
        eastAfrica.setAdjacentTerritory(madagascar, "Madagascar");

        centralAfrica.setAdjacentTerritory(northAfrica, "North Africa");
        centralAfrica.setAdjacentTerritory(eastAfrica, "East Africa");
        centralAfrica.setAdjacentTerritory(southAfrica, "South Africa");

        southAfrica.setAdjacentTerritory(centralAfrica, "Central Africa");
        southAfrica.setAdjacentTerritory(eastAfrica, "East Africa");
        southAfrica.setAdjacentTerritory(madagascar, "Madagascar");

        madagascar.setAdjacentTerritory(eastAfrica, "East Africa");
        madagascar.setAdjacentTerritory(southAfrica, "South Africa");

        // set adjacencies for Europe Territories
        iceland.setAdjacentTerritory(greenland, "Greenland");
        iceland.setAdjacentTerritory(greatBritain, "Great Britain");
        iceland.setAdjacentTerritory(scandinavia, "Scandinavia");

        greatBritain.setAdjacentTerritory(iceland, "Iceland");
        greatBritain.setAdjacentTerritory(scandinavia, "Scandinavia");
        greatBritain.setAdjacentTerritory(northernEurope, "Northern Europe");
        greatBritain.setAdjacentTerritory(westernEurope, "Western Europe");

        westernEurope.setAdjacentTerritory(greatBritain, "Great Britain");
        westernEurope.setAdjacentTerritory(northernEurope, "Northern Europe");
        westernEurope.setAdjacentTerritory(southernEurope, "Southern Europe");
        westernEurope.setAdjacentTerritory(northAfrica, "North Africa");

        southernEurope.setAdjacentTerritory(westernEurope, "Western Europe");
        southernEurope.setAdjacentTerritory(northernEurope, "Northern Europe");
        southernEurope.setAdjacentTerritory(russia, "Russia");
        southernEurope.setAdjacentTerritory(middleEast, "Middle East");
        southernEurope.setAdjacentTerritory(egypt,"Egypt");
        southernEurope.setAdjacentTerritory(northAfrica, "North Africa");

        northernEurope.setAdjacentTerritory(greatBritain, "Great Britain");
        northernEurope.setAdjacentTerritory(scandinavia, "Scandinavia");
        northernEurope.setAdjacentTerritory(russia, "Russia");
        northernEurope.setAdjacentTerritory(southernEurope, "Southern Europe");
        northernEurope.setAdjacentTerritory(westernEurope, "Western Europe");

        scandinavia.setAdjacentTerritory(iceland, "Iceland");
        scandinavia.setAdjacentTerritory(greatBritain, "Great Britain");
        scandinavia.setAdjacentTerritory(northernEurope, "Northern Europe");
        scandinavia.setAdjacentTerritory(russia, "Russia");

        russia.setAdjacentTerritory(scandinavia, "Scandinavia");
        russia.setAdjacentTerritory(northernEurope, "Northern Europe");
        russia.setAdjacentTerritory(southernEurope, "Southern Europe");
        russia.setAdjacentTerritory(middleEast, "Middle East");
        russia.setAdjacentTerritory(afghanistan, "Afghanistan");
        russia.setAdjacentTerritory(ural, "Ural");

        // create adjacencies for Asia Territories
        middleEast.setAdjacentTerritory(eastAfrica, "East Africa");
        middleEast.setAdjacentTerritory(egypt, "Egypt");
        middleEast.setAdjacentTerritory(southernEurope, "Southern Europe");
        middleEast.setAdjacentTerritory(russia, "Russia");
        middleEast.setAdjacentTerritory(afghanistan, "Afghanistan");
        middleEast.setAdjacentTerritory(india, "India");

        india.setAdjacentTerritory(afghanistan, "Afghanistan");
        india.setAdjacentTerritory(middleEast, "Middle East");
        india.setAdjacentTerritory(china, "China");
        india.setAdjacentTerritory(southeastAsia, "Southeast Asia");

        afghanistan.setAdjacentTerritory(russia, "Russia");
        afghanistan.setAdjacentTerritory(middleEast, "Middle East");
        afghanistan.setAdjacentTerritory(india, "India");
        afghanistan.setAdjacentTerritory(china, "China");
        afghanistan.setAdjacentTerritory(ural, "Ural");

        ural.setAdjacentTerritory(russia, "Russia");
        ural.setAdjacentTerritory(afghanistan, "Afghanistan");
        ural.setAdjacentTerritory(china, "China");
        ural.setAdjacentTerritory(siberia, "Siberia");

        siberia.setAdjacentTerritory(ural, "Ural");
        siberia.setAdjacentTerritory(china, "China");
        siberia.setAdjacentTerritory(mongolia, "Mongolia");
        siberia.setAdjacentTerritory(irkutsk, "Irkutsk");
        siberia.setAdjacentTerritory(yakutsk, "Yakutsk");

        yakutsk.setAdjacentTerritory(siberia, "Siberia");
        yakutsk.setAdjacentTerritory(irkutsk, "Irkutsk");
        yakutsk.setAdjacentTerritory(kamchatka, "Kamchatka");

        kamchatka.setAdjacentTerritory(yakutsk, "Yakutsk");
        kamchatka.setAdjacentTerritory(irkutsk,"Irkutsk");
        kamchatka.setAdjacentTerritory(mongolia, "Mongolia");
        kamchatka.setAdjacentTerritory(japan, "Japan");

        irkutsk.setAdjacentTerritory(siberia, "Siberia");
        irkutsk.setAdjacentTerritory(yakutsk, "Yakutsk");
        irkutsk.setAdjacentTerritory(kamchatka, "Kamchatka");
        irkutsk.setAdjacentTerritory(mongolia, "Mongolia");

        japan.setAdjacentTerritory(kamchatka, "Kamchatka");
        japan.setAdjacentTerritory(mongolia, "Mongolia");

        mongolia.setAdjacentTerritory(siberia, "Siberia");
        mongolia.setAdjacentTerritory(irkutsk, "Irkutsk");
        mongolia.setAdjacentTerritory(kamchatka, "Kamchatka");
        mongolia.setAdjacentTerritory(japan, "Japan");
        mongolia.setAdjacentTerritory(china, "China");

        china.setAdjacentTerritory(india, "India");
        china.setAdjacentTerritory(afghanistan, "Afghanistan");
        china.setAdjacentTerritory(ural, "Ural");
        china.setAdjacentTerritory(siberia, "Siberia");
        china.setAdjacentTerritory(mongolia, "Mongolia");
        china.setAdjacentTerritory(southeastAsia, "Southeast Asia");

        southeastAsia.setAdjacentTerritory(india, "India");
        southeastAsia.setAdjacentTerritory(china, "China");
        southeastAsia.setAdjacentTerritory(indonesia, "Indonesia");

        // set adjacencies for Australia Territories
        indonesia.setAdjacentTerritory(southeastAsia, "Southeast Asia");
        indonesia.setAdjacentTerritory(newGuinea, "New Guinea");
        indonesia.setAdjacentTerritory(westernAustralia, "Western Australia");

        newGuinea.setAdjacentTerritory(indonesia, "Indonesia");
        newGuinea.setAdjacentTerritory(westernAustralia, "Western Australia");
        newGuinea.setAdjacentTerritory(easternAustralia, "Eastern Australia");

        westernAustralia.setAdjacentTerritory(indonesia, "Indonesia");
        westernAustralia.setAdjacentTerritory(newGuinea, "New Guinea");
        westernAustralia.setAdjacentTerritory(easternAustralia, "Eastern Australia");

        easternAustralia.setAdjacentTerritory(newGuinea, "New Guinea");
        easternAustralia.setAdjacentTerritory(westernAustralia, "Western Australia");
    }

    public void createTestBoard(){
        Continent north_america = new Continent("North America", 3);
        Continent south_america = new Continent("South America", 4);
        Continent africa = new Continent("Africa", 5);
        Continent asia = new Continent("Asia", 4);

        continentMap.put("North America", north_america);
        continentMap.put("South America", south_america);
        continentMap.put("Africa", africa);
        continentMap.put("Asia", asia);

        Territory canada = new Territory("Canada", "North America");
        Territory usa = new Territory("United States", "North America");
        Territory mexico = new Territory("Mexico", "North America");

        Territory brazil = new Territory("Brazil", "South America");
        Territory argentina = new Territory("Argentina" , "South America");
        Territory peru = new Territory("Peru", "South America");

        Territory kenya = new Territory("Kenya", "Africa");
        Territory egypt = new Territory("Egypt", "Africa");
        Territory zimbabwe = new Territory("Zimbabwe", "Africa");

        Territory japan = new Territory("Japan", "Asia");
        Territory china = new Territory("China", "Asia");
        Territory russia = new Territory("Russia", "Asia");

        north_america.addTerritory(canada);
        north_america.addTerritory(usa);
        north_america.addTerritory(mexico);

        south_america.addTerritory(brazil);
        south_america.addTerritory(argentina);
        south_america.addTerritory(peru);

        africa.addTerritory(kenya);
        africa.addTerritory(egypt);
        africa.addTerritory(zimbabwe);

        asia.addTerritory(japan);
        asia.addTerritory(china);
        asia.addTerritory(russia);

        canada.setAdjacentTerritory(usa, "USA");
        canada.setAdjacentTerritory(mexico, "Mexico");

        usa.setAdjacentTerritory(canada, "Canada");
        usa.setAdjacentTerritory(mexico, "Mexico");

        mexico.setAdjacentTerritory(usa, "USA");
        mexico.setAdjacentTerritory(canada, "Canada");
        mexico.setAdjacentTerritory(zimbabwe, "Zimbabwe");

        zimbabwe.setAdjacentTerritory(egypt, "Egypt");
        zimbabwe.setAdjacentTerritory(china, "China");
        zimbabwe.setAdjacentTerritory(kenya, "Kenya");
        zimbabwe.setAdjacentTerritory(mexico, "Mexico");

        kenya.setAdjacentTerritory(zimbabwe, "Zimbabwe");
        kenya.setAdjacentTerritory(russia, "Russia");
        kenya.setAdjacentTerritory(brazil, "Brazil");

        brazil.setAdjacentTerritory(peru, "Peru");
        brazil.setAdjacentTerritory(kenya, "Kenya");
        brazil.setAdjacentTerritory(argentina, "Argentina");

        peru.setAdjacentTerritory(egypt, "Egypt");
        peru.setAdjacentTerritory(brazil, "Brazil");

        argentina.setAdjacentTerritory(brazil, "Brazil");

        egypt.setAdjacentTerritory(zimbabwe, "Zimbabwe");
        egypt.setAdjacentTerritory(peru, "Peru");

        china.setAdjacentTerritory(zimbabwe, "Zimbabwe");
        china.setAdjacentTerritory(japan, "Japan");
        china.setAdjacentTerritory(russia, "Russia");

        japan.setAdjacentTerritory(china, "China");

        russia.setAdjacentTerritory(china, "China");
        russia.setAdjacentTerritory(kenya, "Kenya");

    }

    public void printBoardStatus(){
        for (String i : continentMap.keySet()){
            continentMap.get(i).printStatus();
        }
    }
    public Territory getTerritory(String territoryName){
        return territoryMap.get(territoryName);
    }

    public boolean isAdjacentTerritory(Territory compareTo, Territory compare){
        for (String i : compareTo.getAdjacentTerritories().keySet()){
            if (compare.getAdjacentTerritories().get(i).equals(compareTo)){
                return true;
            }
        }
        return false;
    }

    private List<String> populateUnallocatedTerritories(){
        for (String i : territoryMap.keySet()){
            randomTerritories.add(i);
        }
        Collections.shuffle(randomTerritories);
        return randomTerritories;
    }

    public String getUnallocatedTerritory(){
        return randomTerritories.remove(0);
    }

    public int getArmy(String territoryName){
        return getTerritory(territoryName).getArmy();
    }

    public String getTerritoryRuler(String territoryName){
        return getTerritory(territoryName).getRuler();
    }

    public void setTerritoryRuler(String territoryName , String ruler){
        getTerritory(territoryName).setRuler(ruler);
    }

    public void addTerritoryArmy(String territoryName, int army){
        getTerritory(territoryName).setArmy(army);
    }

    public void removeTerritoryArmy(String territoryName){
        getTerritory(territoryName).setArmy(0);
    }

    public boolean setContinentRuler(Continent continent, String ruler){
        for (int i = 0; i < continent.getTerritories().size(); i++){
            if (!continent.getTerritories().get(i).getRuler().equals(ruler)){
                return false;
            }
        }
        return true;
    }

    public boolean isPlayerEliminated(String name){
        for (String i : territoryMap.keySet()){
            if (territoryMap.get(i).getRuler().equals(name)){
                return false;
            }
        }
        return true;
    }
}
