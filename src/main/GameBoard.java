import java.util.ArrayList;

public class GameBoard {
    private final ArrayList<Continent> continents;
    private final int numOfPlayers;

    public GameBoard(int numOfPlayers){
        continents = new ArrayList<>();
        createBoard();
        this.numOfPlayers = numOfPlayers;
    }

    public void createBoard(){
        Continent north_america = new Continent("North America", 3);
        Continent south_america = new Continent("South America", 4);
        Continent africa = new Continent("Africa", 5);
        Continent asia = new Continent("Asia", 4);

        continents.add(north_america);
        continents.add(south_america);
        continents.add(africa);
        continents.add(asia);

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

        canada.setAdjacentTerritory(usa, "east");
        canada.setAdjacentTerritory(mexico, "south");

        usa.setAdjacentTerritory(canada, "west");
        usa.setAdjacentTerritory(mexico, "south");

        mexico.setAdjacentTerritory(usa, "north");
        mexico.setAdjacentTerritory(canada, "north-west");
        mexico.setAdjacentTerritory(zimbabwe, "south");

        zimbabwe.setAdjacentTerritory(egypt, "west");
        zimbabwe.setAdjacentTerritory(china, "east");
        zimbabwe.setAdjacentTerritory(kenya, "south");
        zimbabwe.setAdjacentTerritory(mexico, "north");

        kenya.setAdjacentTerritory(zimbabwe, "north");
        kenya.setAdjacentTerritory(russia, "east");
        kenya.setAdjacentTerritory(brazil, "west");

        brazil.setAdjacentTerritory(peru, "north");
        brazil.setAdjacentTerritory(kenya, "east");
        brazil.setAdjacentTerritory(argentina, "south");

        peru.setAdjacentTerritory(egypt, "east");
        peru.setAdjacentTerritory(brazil, "south");

        argentina.setAdjacentTerritory(brazil, "north");

        egypt.setAdjacentTerritory(zimbabwe, "east");
        egypt.setAdjacentTerritory(peru, "west");

        china.setAdjacentTerritory(zimbabwe, "west");
        china.setAdjacentTerritory(japan, "east");
        china.setAdjacentTerritory(russia, "south");

        japan.setAdjacentTerritory(china, "west");

        russia.setAdjacentTerritory(china, "north");
        russia.setAdjacentTerritory(kenya, "west");
    }

    public void printBoardStatus(){
        for (int i = 0; i < continents.size(); i++){
            continents.get(i).printStatus();
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
        for (int i = 0; i < continents.size(); i++) {
            if (continents.get(i).getRuler().equals(name)) {
                return false;
            }
            for (int j = 0; j < continents.get(i).getTerritories().size(); j++){
                if (continents.get(i).getTerritories().get(j).getRuler().equals(name)){
                    return false;
                }
            }
        }
        return true;
    }

}
