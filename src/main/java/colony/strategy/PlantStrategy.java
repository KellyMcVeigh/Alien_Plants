package colony.strategy;

import colony.garden.Plot;
import colony.organisms.Plant;

public class PlantStrategy {
    public double photosynthesize(Plant plant, Plot currentPlot){
        return plant.getOxygenOutput();
    };
}
