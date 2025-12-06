package colony.organisms;

import colony.garden.Plot;
import colony.strategy.PlantStrategy;


public class Plant{
    protected PlantStrategy strategy;
    protected Boolean isAlive = true;
    protected String name;
    protected Double health;
    protected Double oxygenOutput;
    public PlantFactory plantFactory = new PlantFactory();

    protected Plot currentLocation;

    public Plot getCurrentLocation() {
        return currentLocation;
    }

    public boolean isLiving() {
        return isAlive;
    }

    public Plant(PlantType type) {
        Plant plant = plantFactory.createPlant(type);

        this.name = plant.name;
        this.strategy = plant.strategy;
        this.health = plant.health;
        this.oxygenOutput = plant.oxygenOutput;
    }

    public Plant(String name, PlantStrategy strategy, Double initialHealth, Double oxygenOutput) {
        this.name = name;
        this.strategy = strategy;
        this.health = initialHealth;
        this.oxygenOutput = oxygenOutput;
    }

    public void sprout(Plot plot) {
        if (getCurrentLocation() != null) {
            if (getCurrentLocation().equals(plot)) {
                return;
            }
            getCurrentLocation().uprootPlant(this);
        }
        this.currentLocation = plot;
    }

    public Boolean isWeed() {
        return false;
    }

    public Double photosynthesize() {
        return strategy.photosynthesize(this, getCurrentLocation());
    }

    public Double getOxygenOutput() {
        return oxygenOutput;
    }

    public void die(){
        this.isAlive = false;
    }

    public void loseHealth(double damage) {
        this.health -= damage;
        if (health <= 0) {
            this.die();
        }
    }
}
