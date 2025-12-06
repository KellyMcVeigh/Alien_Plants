package colony.strategy;

import colony.garden.Plot;
import colony.organisms.Plant;

public class PoisonousStrategy extends PlantStrategy {
    Double poisonValue = 2.0;

    @Override
    public double photosynthesize(Plant plant, Plot currentPlot) {
        return super.photosynthesize(plant, currentPlot);
    }

    @Override
    public void playDay(Plant plant, Plot currentPlot) {
        if (currentPlot.hasWeeds()){
            for (Plant weed : currentPlot.getWeeds()){
                weed.loseHealth(poisonValue);
            }
        }
    }
}
