import java.util.*;
public class Territory {

    private final String continentName;
    private final String name;
    private final Map<String, Territory> adjacentTerritories;
    private String ruler;
    private int army;

    public Territory(String name, String continentName){
        this.name = name;
        this.continentName = continentName;
        army = 0;
        ruler = "No Ruler";
        adjacentTerritories = new HashMap<>();
    }

    public String getContinentName(){
        return continentName;
    }

    public void setAdjacentTerritory(Territory territory, String key){
        adjacentTerritories.put(key, territory);
    }

    public Territory getAdjacentTerritory(String direction){
        return adjacentTerritories.get(direction);
    }

    public String getName(){
        return name;
    }

    public int getArmy(){
        return army;
    }

    public String getRuler(){
        return ruler;
    }

    public void setRuler(String newRuler){
        ruler = newRuler;
    }

    public void setArmy(int army){
        if (army < 0) {
            this.army = 0;
        } else {
            this.army = army;
        }
    }

    public void printStatus(){
        System.out.println("    Territory: " + getName());
        System.out.println("        Army Count: " + getArmy());
        System.out.println("        Ruler: " + getRuler());
        System.out.println("      AdjacentTerritories: ");
        for (String i : adjacentTerritories.keySet()){
            System.out.println("          " + i + " " + adjacentTerritories.get(i).getName());
        }
    }
}

