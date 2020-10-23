import java.util.List;

public class Player {

    private String name;
    private int numOfSoldiers;
    private int numOfTerritories;
    private List<Territory> ruledTerriries;

    public Players(String name) {
        this.name = name;
    }

    public void addRuledTerritory(Territory territory) {
        ruledTerritories.add(territory);
    }

    public List returnTerritories() {
        return ruledTerritories;
    }

    public String getName() {
        return name;
    }

    public int getNumOfSoldiers() {
        return numOfSoldiers;
    }

    public void setNumOfSoliders(int num) {
        numOfSoldiers = num;
    }

    public void setNumOfTerritories(int num) {
        numOfTerritories = num;
    }

    public int getNumOfTerritories(){
        return numOfTerritories;
    }
}

