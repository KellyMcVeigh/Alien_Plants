package colony;

import colony.garden.Garden;
import colony.observer.OxygenReporter;
import colony.organisms.PlantFactory;
import colony.organisms.PlantType;

public class Main {
    public static void main(String[] args) {
        Garden garden = Garden.Builder.newBuilder()
                .create2x2Grid()
                .build();

        Colony colony = new Colony(garden);
        colony.addOxygenObserver(new OxygenReporter());

        garden.getPlot("Northwest").plantSeed(PlantFactory.createPlant(PlantType.Sunflower));
        garden.getPlot("Southeast").plantSeed(PlantFactory.createPlant(PlantType.Rose));

        colony.startGame();
    }
}

