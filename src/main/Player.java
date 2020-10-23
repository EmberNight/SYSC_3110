import java.util.ArrayList;
import java.util.List;

/**
 * @author Emmitt Luhning
 */
public class Player {

    private String name;
    private int numOfSoldiers;
    private int numOfTerritories;
    private ArrayList<Territory> ruledTerritories;

    public Player(String name, ArrayList<Territory> ruledTerritories) {
        this.ruledTerritories = ruledTerritories;
        this.name = name;
    }

    public void addRuledTerritory(Territory territory) {
        ruledTerritories.add(territory);
    }
    public void removeRuledTerritory(Territory territory) {
        for(int i = 0; i < ruledTerritories.size(); i++)
        {
            if(ruledTerritories.get(i) == territory){
                    ruledTerritories.remove(i);
             }
        }

    }
    public ArrayList getRuledTerritories() {
        return ruledTerritories;
    }

    public String getName() {
        return name;
    }

    public int getNumOfSoldiers() {
        return numOfSoldiers;
    }

    public void setNumOfSoldiers(int num) {
        numOfSoldiers = num;
    }

    public void setNumOfTerritories(int num) {
        numOfTerritories = num;
    }

    public int getNumOfTerritories(){
        return numOfTerritories;
    }
}

