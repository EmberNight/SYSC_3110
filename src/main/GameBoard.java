import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameBoard {

    private Parser parser;
    private Map<String, Continent> continentMap;
    private Map<String, Territory> territoryMap;
    private CircularLinkedList players;
    private final static int numOfTerritories = 12;

    public GameBoard(CircularLinkedList players){
        continentMap = new HashMap<>();
        createBoard();
        this.players = players;

    }

    public void createBoard(){
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
            continentMap.get(i).printStatus();;
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

    public void allocateArmies(){

        if(players.getSize() == 2){
            Player player = players.getHead();
            for (int i = 0; i < players.getSize(); i++){
                player.setNumOfSoldiers(40);
                player.setNumOfTerritories(21);
                player = player.returnNextNode();
            }

        }
        if(players.getSize() == 3){
            Player player = players.getHead();
            for (int i = 0; i < players.getSize(); i++){
                player.setNumOfSoldiers(35);
                player.setNumOfTerritories(14);
                player = player.returnNextNode();
            }

        }
        if(players.getSize() == 4){
            Player player = players.getHead();
            for (int i = 0; i < players.getSize(); i++){
                player.setNumOfSoldiers(30);
                if (i == 0 || i == 1){
                    player.setNumOfTerritories(11);
                }
                else {
                    player.setNumOfTerritories(10);
                }
                player = player.returnNextNode();
            }

        }
        if(players.getSize() == 5){
            Player player = players.getHead();
            for (int i = 0; i < players.getSize(); i++){
                player.setNumOfSoldiers(25);
                if (i == 0 || i == 1){
                    player.setNumOfTerritories(9);
                }
                else{
                    player.setNumOfTerritories(8);
                }
                player = player.returnNextNode();
            }

        }
        if(players.getSize() == 6){
            Player player = players.getHead();
            for (int i = 0; i < players.getSize(); i++){
                player.setNumOfSoldiers(20);
                player.setNumOfTerritories(7);
                player = player.returnNextNode();
            }

        }

        int max = (int)Math.random() * 6;
        player = players.getHead();
        for(String i : territoryMap.keySet()){
            if (territoryMap.get(i).getRuler().equals(null)){
                for (int j = 0; j < max; j ++){
                    player = player.returnNextNode();
                }
                territoryMap.get(i).setRuler(player.getName());
                player.addRuledTerritory(territoryMap.get(i));
                territoryMap.get(i).setArmy(1);
                player.setNumOfSoldiers(player.getNumOfSoldiers() - 1);
                player.setNumOfTerritories(player.getNumOfTerritories() - 1);
            }
        }

        player = players.getHead();
        for (int i = 0; i < players.getSize(); i++){
            while(player.getNumOfSoldiers() > 0){
                int randomTerritoryIndex = (int)Math.random() *  player.getRuledTerritories.getSize();
                player.getRuledTerritories(randomTerritoryIndex).setArmy(player.getRuledTerritories(randomTerritoryIndex) + 1);
                player.setNumOfSoldiers(player.getNumOfSoldiers() - 1);
            }
        }

    }

    public int getArmy(Territory territory){
        return territory.getArmy();
    }

    public String getTerritoryRuler(Territory territory){
        return territory.getRuler();
    }

    public void setTerritoryRuler(Territory territory, String ruler){
        territory.setRuler(ruler);
    }

    public void addTerritoryArmy(Territory territory, int army){
        territory.setArmy(army);
    }

    public void removeTerritoryArmy(Territory territory){
        territory.setArmy(0);
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
