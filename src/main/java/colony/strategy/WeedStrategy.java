package colony.strategy;

import colony.garden.Plot;
import colony.organisms.Plant;
import colony.organisms.PlantFactory;
import colony.organisms.Weed;
import java.util.List;

public class WeedStrategy extends PlantStrategy {
    @Override
    public void playDay(Plant plant, Plot currentPlot) {
        if (plant instanceof Weed) {
            Weed weed = (Weed) plant;
            Plant newWeed = PlantFactory.createPlant(weed.getType());
            currentPlot.weedSprout(newWeed);
            List<Plot> neighbors = currentPlot.getNeighbors();
            for (Plot neighbor : neighbors){
                if (neighbor.hasLivingPlant()){
                    Plant weed1 = PlantFactory.createPlant(weed.getType());
                    neighbor.weedSprout(weed1);
                }
                else {
                    Plant weed1 = PlantFactory.createPlant(weed.getType());
                    neighbor.weedSprout(weed1);
                    Plant weed2 = PlantFactory.createPlant(weed.getType());
                    neighbor.weedSprout(weed2);
                }
            }
        }
    }
}
