package colony.garden;

import colony.organisms.PlantFactory;
import colony.organisms.PlantType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GardenTest {
    private Garden garden;

    @BeforeEach
    void setUp() {
        garden = Garden.Builder.newBuilder()
                .create2x2Grid()
                .build();
    }

    @Test
    void testGardenCreation() {
        assertNotNull(garden);
        assertEquals(4, garden.size());
    }

    @Test
    void testGetPlot() {
        Plot plot = garden.getPlot("Northwest");
        assertNotNull(plot);
        assertEquals("Northwest", plot.getName());
    }

    @Test
    void testGetInvalidPlot() {
        assertThrows(IllegalArgumentException.class, () -> {
            garden.getPlot("InvalidPlot");
        });
    }

    @Test
    void testPlotNeighbors() {
        Plot northwest = garden.getPlot("Northwest");
        assertFalse(northwest.getNeighbors().isEmpty());
    }

    @Test
    void test3x3Grid() {
        Garden largeGarden = Garden.Builder.newBuilder()
                .create3x3Grid()
                .build();
        assertEquals(9, largeGarden.size());
    }

    @Test
    void testPlotIsOpen() {
        Plot plot = garden.getPlot("Northwest");
        assertTrue(plot.isOpen());
        plot.plantSeed(PlantFactory.createPlant(PlantType.Sunflower));
        assertFalse(plot.isOpen());
    }

    @Test
    void testUprootPlant() {
        Plot plot = garden.getPlot("Northwest");
        plot.plantSeed(PlantFactory.createPlant(PlantType.Sunflower));
        assertFalse(plot.isOpen());
        plot.uprootPlant(plot.getPlant());
        assertTrue(plot.isOpen());
    }
}

