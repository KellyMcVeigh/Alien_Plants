package colony;

import colony.garden.Garden;
import colony.observer.OxygenObserver;
import colony.organisms.PlantFactory;
import colony.organisms.PlantType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ColonyTest {
    private Garden garden;
    private Colony colony;

    @BeforeEach
    void setUp() {
        garden = Garden.Builder.newBuilder()
                .create2x2Grid()
                .build();
        colony = new Colony(garden);
    }

    @Test
    void testColonyInitialOxygen() {
        assertEquals(0.0, colony.getOxygen());
    }

    @Test
    void testWinCondition() {
        assertFalse(colony.hasWon());
    }

    @Test
    void testPurchasePlantReducesOxygen() {
        colony.purchasePlant(PlantType.Rose, "Northwest", 3.0);
        assertEquals(-3.0, colony.getOxygen());
    }

    @Test
    void testPurchasePlantAddsPlantToGarden() {
        colony.purchasePlant(PlantType.Sunflower, "Northwest", 5.0);
        assertTrue(garden.getPlot("Northwest").hasLivingPlant());
    }

    @Test
    void testOxygenObserverNotified() {
        final double[] observedChange = {0.0};
        colony.addOxygenObserver(new OxygenObserver() {
            @Override
            public void onOxygenChange(double previousOxygen, double newOxygen, double change) {
                observedChange[0] = change;
            }
        });

        colony.purchasePlant(PlantType.Rose, "Northwest", 3.0);
        assertEquals(-3.0, observedChange[0]);
    }

    @Test
    void testGardenHasOpenPlot() {
        assertTrue(garden.hasOpenPlot());
        garden.getPlot("Northwest").plantSeed(PlantFactory.createPlant(PlantType.Sunflower));
        assertTrue(garden.hasOpenPlot());
    }

    @Test
    void testGardenHasLivingPlants() {
        assertFalse(garden.hasLivingPlants());
        garden.getPlot("Northwest").plantSeed(PlantFactory.createPlant(PlantType.Sunflower));
        assertTrue(garden.hasLivingPlants());
    }
}

