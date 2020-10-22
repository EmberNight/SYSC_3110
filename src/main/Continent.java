import java.util.*;

public class Continent {

    private final ArrayList<Territory> territories;
    private final String name;
    private final int value;
    private String ruler;

    // can pass constructor no territories or arrayList of territories
    public Continent(String name, int value){
        territories = new ArrayList<>();
        this.value = value;
        ruler = "No Ruler";
        this.name = name;
    }
    public Continent(ArrayList<Territory>territories, String name, int value){
        this.territories = territories;
        this.value = value;
        ruler = "No Ruler";
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public String getRuler(){
        return ruler;
    }

    public void setRuler(String ruler){
        this.ruler = ruler;
    }

    public ArrayList<Territory> getTerritories(){
        return territories;
    }

    public void addTerritory(Territory territory){
        territories.add(territory);
    }

    public void printStatus(){
        System.out.println("Continent: " + getName());
        System.out.println("Territories: ");
        for (int i = 0; i < territories.size(); i++){
            territories.get(i).printStatus();
        }
    }
}
