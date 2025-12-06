package colony.garden;

public class PlotFactory {
    Plot createPlot(String name) {
        return new Plot(name);
    }
}
