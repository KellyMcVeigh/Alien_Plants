package colony.organisms;

import colony.strategy.PlantStrategy;
import colony.strategy.PoisonousStrategy;
import colony.strategy.WeedStrategy;

public class PlantFactory {

    public static Plant createPlant(PlantType type) {
        PlantStrategy strategy;
        String name = type.toString();
        double startingHealth;
        double oxygenOutput;

        switch(type) {
            // Normal plants
            case Sunflower:
                oxygenOutput = 2.0;
            case Rose:
                oxygenOutput = 1.5;
                startingHealth = 10;
                strategy = new PlantStrategy();
                break;

            // Poisonous plants
            case Nightshade:
                oxygenOutput = 0.5;
            case Foxglove:
                oxygenOutput = 0.75;
            case LilyOfTheValley:
                oxygenOutput = 0.25;
                startingHealth = 5;
                strategy = new PoisonousStrategy();
                break;

            // Weeds
            case Dandelion:
                oxygenOutput = 0.1;
            case Clover:
                oxygenOutput = 0.1;
                startingHealth = 3;
                strategy = new WeedStrategy();
                break;

            default:
                oxygenOutput = 1.5;
                startingHealth = 10;
                strategy = new PlantStrategy();
        }

        return new Plant(name, strategy, startingHealth, oxygenOutput);
    }
}
