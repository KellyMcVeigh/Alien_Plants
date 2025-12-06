package colony.strategy;

import colony.garden.Plot;
import colony.organisms.Plant;
import colony.organisms.PlantFactory;
import colony.organisms.Weed;

import java.util.List;

public class WeedStrategy extends PlantStrategy {
    public void dayPassing(Weed plant, Plot currentPlot) {
        Plant weed = PlantFactory.createPlant(plant.getType());
        currentPlot.weedSprout(weed);
        List<Plot> neighbors = currentPlot.getNeighbors();
        for (Plot neighbor : neighbors){
            if (neighbor.hasLivingPlant()){
                Plant weed1 = PlantFactory.createPlant(plant.getType());
                neighbor.weedSprout(weed1);
            }
            else {
                Plant weed1 = PlantFactory.createPlant(plant.getType());
                neighbor.weedSprout(weed1);
                Plant weed2 = PlantFactory.createPlant(plant.getType());
                neighbor.weedSprout(weed2);
            }
        }
    }
}
