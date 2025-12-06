package colony.garden;

import colony.organisms.Plant;

import java.util.*;

// this code is modified from the in class polymorphia maze
public class Garden {
    private List<Plot> plots;

    private Garden() {
    }

    public int size() {
        return plots.size();
    }

    public String toString() {
        return String.join("\n\n", plots.stream().map(Object::toString).toList());
    }

    public List<Plot> getPlots() {
        return List.copyOf(plots);
    }

    public Boolean hasLivingPlants() {
        return plots.stream().anyMatch(Plot::hasLivingPlant);
    }

    public List<Plant> getLivingPlants() {
        List<Plant> plants = new ArrayList<>();
        for (Plot plot : plots) {
            if (plot.hasLivingPlant()) {
                plants.add(plot.getPlant());
            }
        }
        return plants;
    }

    public Plot getPlot(String plotName) {
        return plots.stream()
                .filter(plot -> plot.getName().equals(plotName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No plot with name " + plotName));
    }

    public Boolean hasOpenPlot(){
        for (Plot plot : plots) {
            if (plot.isOpen()) {
                return true;
            }
        }
        return false;
    }

    public static class Builder {
        // static Logger logger = org.slf4j.LoggerFactory.getLogger(Builder.class);
        private final Random rand = new Random();
        public static String[] grid2x2PlotNames = new String[]{"Northwest", "Northeast", "Southwest", "Southeast"};
        public static String[] grid3x3PlotNames = new String[]{"Northwest", "North", "Northeast", "West", "Center", "East", "Southwest", "South", "Southeast"};

        final Garden garden = new Garden();
        Map<String, Plot> plotMap = new HashMap<>();
        private boolean useBidirectionalConnections = true;
        private int currentPlotIndex = 0;
        private final PlotFactory plotFactory;

        private Builder(PlotFactory plotFactory) {
            this.plotFactory = plotFactory;
        }

        private Plot nextPlot() {
            return garden.getPlots().get(currentPlotIndex++ % garden.getPlots().size());
        }

        private Plot getRandomPlot() {
            return garden.plots.get(rand.nextInt(garden.plots.size()));
        }

        public Builder createGridOfPlots(int rows, int columns, String[] plotNames) {
            Plot[][] plotGrid = new Plot[rows][columns];
            List<Plot> plots = new ArrayList<>();
            // Notice -- don't use i and j. Use row and column -- they are better
            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    Plot newPlot = plotFactory.createPlot(plotNames[row * columns + column]);
                    plotGrid[row][column] = newPlot;
                    plots.add(newPlot);
                }
            }
            garden.plots = plots;

            // Now connect the plots
            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    Plot currentPlot = plotGrid[row][column];
                    Plot neighbor;
                    if (row > 0) {
                        neighbor = plotGrid[row - 1][column];
                        currentPlot.addNeighbor(neighbor, useBidirectionalConnections);
                    }
                    if (column > 0) {
                        neighbor = plotGrid[row][column - 1];
                        currentPlot.addNeighbor(neighbor, useBidirectionalConnections);
                    }
                }
            }
            return this;
        }

        public Builder create2x2Grid() {
            return createGridOfPlots(2, 2, grid2x2PlotNames);
        }

        public Builder create3x3Grid() {
            return createGridOfPlots(3, 3, grid3x3PlotNames);
        }
    }
}
