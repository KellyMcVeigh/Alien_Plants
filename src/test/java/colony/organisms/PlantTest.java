package colony.organisms;

import colony.garden.Garden;
import colony.garden.Plot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlantTest {
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
    void testPlantCreation() {
        Plant plant = PlantFactory.createPlant(PlantType.Sunflower);
        assertNotNull(plant);
        assertTrue(plant.isLiving());
    }

    @Test
    void testPlantPhotosynthesize() {
        Plant plant = PlantFactory.createPlant(PlantType.Rose);
        plot.plantSeed(plant);
        double oxygen = plant.photosynthesize();
        assertTrue(oxygen > 0);
    }

    @Test
    void testPlantDies() {
        Plant plant = PlantFactory.createPlant(PlantType.Sunflower);
        assertTrue(plant.isLiving());
        plant.die();
        assertFalse(plant.isLiving());
    }

    @Test
    void testPlantLoseHealth() {
        Plant plant = PlantFactory.createPlant(PlantType.Dandelion);
        plot.plantSeed(plant);
        plant.loseHealth(100);
        assertFalse(plant.isLiving());
    }

    @Test
    void testWeedIsWeed() {
        Weed weed = new Weed(PlantType.Dandelion);
        assertTrue(weed.isWeed());
    }

    @Test
    void testPlantIsNotWeed() {
        Plant plant = PlantFactory.createPlant(PlantType.Sunflower);
        assertFalse(plant.isWeed());
    }
}

