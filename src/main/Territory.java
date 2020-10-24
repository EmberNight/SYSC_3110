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
        ruler = "";
        adjacentTerritories = new HashMap<>();
    }

    public String getContinentName(){
        return continentName;
    }

    public void setAdjacentTerritory(Territory territory, String key){
        adjacentTerritories.put(key, territory);
    }

    public Territory getAdjacentTerritory(String key){
        return adjacentTerritories.get(key);
    }

    public boolean isAdjacent(String adjacent) {
        return adjacentTerritories.get(adjacent) != null;
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
        this.army = Math.max(army, 0); // Set whatever is higher army or 0
    }

    public void addArmy(int army){
        this.army += Math.abs(army);
    }

    public void printStatus(){
        System.out.println("    Territory: " + getName());
        System.out.println("        Army Count: " + getArmy());
        System.out.println("        Ruler: " + getRuler());
        System.out.println("        AdjacentTerritories: ");
        System.out.print("            ");
        for (String i : adjacentTerritories.keySet()){
            System.out.print(adjacentTerritories.get(i).getName() + ", ");
        }
        System.out.println();
    }
}

