package colony.garden;

import colony.organisms.Plant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Plot {
    static private final Random rand = new Random();

    private final String name;
    private final List<Plot> neighbors = new ArrayList<>();
    private Plant plant = null;
    private final List<Plant> plants = new ArrayList<>();

    public Plot(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Plant getPlant() {
        return plant;
    }

    public boolean hasWeeds() {
        return plants.stream().anyMatch(Plant::isWeed);
    }

    public List<Plant> getWeeds() {
        return plants.stream()
                .filter(Plant::isWeed)
                .toList();
    }

    public void plantSeed(Plant plant) {
        if (plant != null){
            uprootPlant(this.plant);
        }
        this.plant = plant;
        plant.sprout(this);
        plants.add(plant);
    }

    public void weedSprout(Plant plant) {
        plant.sprout(this);
        plants.add(plant);
    }

    public void uprootPlant(Plant plant) {
        this.plant = null;
        plants.remove(plant);
    }

    public void uprootWeed(Plant weed) {
        plants.remove(weed);
    }

    public Boolean hasLivingPlant() {
        if(plant == null) return false;
        else return getPlant().isLiving();
    }

    public boolean isOpen(){
        return plant == null;
    }

    void addNeighbor(Plot neighbor) {
        // Make sure we are never a neighbor of ourselves
        if (this != neighbor) {
            this.neighbors.add(neighbor);
        }
    }

    void addNeighbor(Plot neighbor, boolean bidirectional) {
        this.addNeighbor(neighbor);
        if (bidirectional) {
            neighbor.addNeighbor(this);
        }
    }

    public List<Plot> getNeighbors() {
        return neighbors;
    }
}
