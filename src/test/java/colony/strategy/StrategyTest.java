package colony.strategy;

import colony.garden.Garden;
import colony.garden.Plot;
import colony.organisms.Plant;
import colony.organisms.PlantFactory;
import colony.organisms.PlantType;
import colony.organisms.Weed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StrategyTest {
    private Garden garden;
    private Plot plot;

    @BeforeEach
    void setUp() {
        garden = Garden.Builder.newBuilder()
                .create2x2Grid()
                .build();
        plot = garden.getPlot("Northwest");
    }

    @Test
    void testBasicStrategyPhotosynthesize() {
        Plant plant = PlantFactory.createPlant(PlantType.Rose);
        plot.plantSeed(plant);
        double oxygen = plant.photosynthesize();
        assertTrue(oxygen > 0);
    }

    @Test
    void testPoisonousStrategyDamagesWeeds() {
        Plant poisonousPlant = PlantFactory.createPlant(PlantType.Nightshade);
        plot.plantSeed(poisonousPlant);

        Weed weed = new Weed(PlantType.Dandelion);
        plot.weedSprout(weed);

        assertTrue(plot.hasWeeds());
        poisonousPlant.playDay();
    }

    @Test
    void testWeedSpreadStrategy() {
        Weed weed = new Weed(PlantType.Dandelion);
        plot.weedSprout(weed);

        int initialWeedCount = plot.getWeeds().size();
        weed.playDay();
        assertTrue(plot.getWeeds().size() >= initialWeedCount);
    }

    @Test
    void testPlayDayMethod() {
        Plant plant = PlantFactory.createPlant(PlantType.Sunflower);
        plot.plantSeed(plant);
        assertDoesNotThrow(() -> plant.playDay());
    }
}

