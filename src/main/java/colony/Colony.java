package colony;

import colony.garden.Garden;
import colony.organisms.Plant;
import org.slf4j.Logger;

import java.util.List;
import java.util.Random;

public class Colony {
    static Logger logger = org.slf4j.LoggerFactory.getLogger(Colony.class);

    Double oxygen = 0;
    Garden garden;
    Integer turnCount = 0;
    final Random rand = new Random();

    public Colony(Garden garden) {
        this.garden = garden;
    }

    private List<Plant> getLivingPlants() {
        return garden.getLivingPlants();
    }

    public void purchasePlant(Plant plant, Double cost){
        oxygen -= cost;
        sow(plant);
    }

    public void sow(Plant plant){
        // prompt user for a plot #
        name = input;
        garden.getPlot(name).plantSeed(plant);
    }

    public void playTurn() {
        if (turnCount == 0) {
            logger.info("\nStarting play...\n");
        }
        turnCount += 1;

        // Process all the plots
        List<Plant> plants = getLivingPlants();
        while (!plants.isEmpty()) {
            int index = rand.nextInt(plants.size());
            Plant plant = plants.get(index);
            oxygen += plant.photosynthesize();
            plants.remove(index);
        }

        //player can uproot a plant
        //player can uproot a weed
        if (garden.hasOpenPlot()) {
            //if player has open plot, player can purchase a new plant and plant it
        }

    }
}
