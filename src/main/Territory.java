import java.util.*;
public class Territory {

    private String ruler;
    private String continentName;
    private String name;
    private int army;
    private Map<String, Territory> adjacentTerritories;

    public Territory(String name, String continentName){
        this.name = name;
        this.continentName = continentName;
        army = 0;
        String ruler = "No one";
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
        this.army = army;
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

