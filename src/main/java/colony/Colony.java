package colony;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.slf4j.Logger;

import colony.garden.Garden;
import colony.garden.Plot;
import colony.observer.OxygenObserver;
import colony.organisms.Plant;
import colony.organisms.PlantFactory;
import colony.organisms.PlantType;

public class Colony {
    static Logger logger = org.slf4j.LoggerFactory.getLogger(Colony.class);

    private static final double WIN_OXYGEN_THRESHOLD = 50.0;

    Double oxygen = 0.0;
    Garden garden;
    Integer turnCount = 0;
    final Random rand = new Random();
    private final List<OxygenObserver> oxygenObservers = new ArrayList<>();
    private final Scanner scanner;

    public Colony(Garden garden) {
        this.garden = garden;
        this.scanner = new Scanner(System.in);
    }

    public Colony(Garden garden, Scanner scanner) {
        this.garden = garden;
        this.scanner = scanner;
    }

    public void addOxygenObserver(OxygenObserver observer) {
        oxygenObservers.add(observer);
    }

    public void removeOxygenObserver(OxygenObserver observer) {
        oxygenObservers.remove(observer);
    }

    private void notifyOxygenObservers(double previousOxygen, double newOxygen) {
        double change = newOxygen - previousOxygen;
        for (OxygenObserver observer : oxygenObservers) {
            observer.onOxygenChange(previousOxygen, newOxygen, change);
        }
    }

    private void addOxygen(double amount) {
        double previousOxygen = oxygen;
        oxygen += amount;
        notifyOxygenObservers(previousOxygen, oxygen);
    }

    private List<Plant> getLivingPlants() {
        return garden.getLivingPlants();
    }

    public Double getOxygen() {
        return oxygen;
    }

    public Garden getGarden() {
        return garden;
    }

    public void purchasePlant(PlantType type, String plotName, Double cost) {
        double previousOxygen = oxygen;
        oxygen -= cost;
        notifyOxygenObservers(previousOxygen, oxygen);
        Plant plant = PlantFactory.createPlant(type);
        garden.getPlot(plotName).plantSeed(plant);
        logger.info("Planted {} in plot {}", type, plotName);
    }

    public boolean hasWon() {
        return oxygen >= WIN_OXYGEN_THRESHOLD;
    }

    public boolean hasLost() {
        return !garden.hasLivingPlants() && !garden.hasOpenPlot();
    }

    public void startGame() {
        logger.info("=== Welcome to Alien Plants! ===");
        logger.info("Goal: Reach {} oxygen to win!", WIN_OXYGEN_THRESHOLD);
        logger.info("Current oxygen: {}\n", oxygen);

        while (!hasWon() && !hasLost()) {
            playTurn();
        }

        endGame();
    }

    public void endGame() {
        if (hasWon()) {
            logger.info("\n=== CONGRATULATIONS! YOU WON! ===");
            logger.info("You reached {} oxygen in {} turns!", String.format("%.2f", oxygen), turnCount);
        } else {
            logger.info("\n=== GAME OVER ===");
            logger.info("All plants have died. Final oxygen: {}", String.format("%.2f", oxygen));
        }
    }

    public void playTurn() {
        if (turnCount == 0) {
            logger.info("\nStarting play...\n");
        }
        turnCount += 1;
        logger.info("=== Turn {} ===", turnCount);
        logger.info("Current oxygen: {}", String.format("%.2f", oxygen));

        List<Plant> plants = getLivingPlants();
        while (!plants.isEmpty()) {
            int index = rand.nextInt(plants.size());
            Plant plant = plants.get(index);
            addOxygen(plant.photosynthesize());
            plant.playDay();
            plants.remove(index);
        }

        displayGardenStatus();
        handlePlayerAction();

        logger.info("End of turn. Oxygen: {}\n", String.format("%.2f", oxygen));
    }

    private void displayGardenStatus() {
        logger.info("\n--- Garden Status ---");
        for (Plot plot : garden.getPlots()) {
            String status;
            if (plot.isOpen()) {
                status = "Empty";
            } else if (plot.hasLivingPlant()) {
                status = "Plant: " + plot.getPlant();
            } else {
                status = "Dead plant";
            }
            int weedCount = plot.getWeeds().size();
            logger.info("{}: {} | Weeds: {}", plot.getName(), status, weedCount);
        }
        logger.info("---------------------\n");
    }

    private void handlePlayerAction() {
        logger.info("Choose an action:");
        logger.info("1. Uproot a plant");
        logger.info("2. Uproot a weed");
        if (garden.hasOpenPlot()) {
            logger.info("3. Purchase and plant a new plant");
        }
        logger.info("4. Skip turn");
        System.out.print("Enter choice: ");

        int choice = 4;
        try {
            choice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            logger.info("Invalid input, skipping turn.");
            return;
        }

        switch (choice) {
            case 1:
                uprootPlantAction();
                break;
            case 2:
                uprootWeedAction();
                break;
            case 3:
                if (garden.hasOpenPlot()) {
                    purchasePlantAction();
                } else {
                    logger.info("No open plots available!");
                }
                break;
            case 4:
                logger.info("Skipping turn...");
                break;
            default:
                logger.info("Invalid choice, skipping turn.");
        }
    }

    private void uprootPlantAction() {
        logger.info("Available plots with plants:");
        List<Plot> plotsWithPlants = new ArrayList<>();
        for (Plot plot : garden.getPlots()) {
            if (plot.hasLivingPlant()) {
                plotsWithPlants.add(plot);
                logger.info("  - {}", plot.getName());
            }
        }

        if (plotsWithPlants.isEmpty()) {
            logger.info("No plants to uproot!");
            return;
        }

        System.out.println("Enter plot name: ");
        String plotName = scanner.nextLine().trim();

        try {
            Plot plot = garden.getPlot(plotName);
            if (plot.hasLivingPlant()) {
                plot.uprootPlant(plot.getPlant());
                logger.info("Uprooted plant from {}", plotName);
            } else {
                logger.info("No plant in that plot!");
            }
        } catch (IllegalArgumentException e) {
            logger.info("Invalid plot name!");
        }
    }

    private void uprootWeedAction() {
        logger.info("Available plots with weeds:");
        List<Plot> plotsWithWeeds = new ArrayList<>();
        for (Plot plot : garden.getPlots()) {
            if (plot.hasWeeds()) {
                plotsWithWeeds.add(plot);
                logger.info("  - {} ({} weeds)", plot.getName(), plot.getWeeds().size());
            }
        }

        if (plotsWithWeeds.isEmpty()) {
            logger.info("No weeds to uproot!");
            return;
        }

        System.out.println("Enter plot name: ");
        String plotName = scanner.nextLine().trim();

        try {
            Plot plot = garden.getPlot(plotName);
            if (plot.hasWeeds()) {
                Plant weed = plot.getWeeds().get(0);
                plot.uprootWeed(weed);
                logger.info("Uprooted a weed from {}", plotName);
            } else {
                logger.info("No weeds in that plot!");
            }
        } catch (IllegalArgumentException e) {
            logger.info("Invalid plot name!");
        }
    }

    private void purchasePlantAction() {
        logger.info("Available plants to purchase:");
        logger.info("  1. Sunflower (Cost: 5 oxygen, Output: 2.0)");
        logger.info("  2. Rose (Cost: 3 oxygen, Output: 1.5)");
        logger.info("  3. Nightshade (Cost: 2 oxygen, Output: 0.5, Poisonous)");
        logger.info("  4. Foxglove (Cost: 2 oxygen, Output: 0.75, Poisonous)");
        logger.info("  5. Lily of the Valley (Cost: 1 oxygen, Output: 0.25, Poisonous)");
        System.out.print("Enter plant number: ");

        int plantChoice;
        try {
            plantChoice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            logger.info("Invalid input!");
            return;
        }

        PlantType type;
        double cost;
        switch (plantChoice) {
            case 1:
                type = PlantType.Sunflower;
                cost = 5.0;
                break;
            case 2:
                type = PlantType.Rose;
                cost = 3.0;
                break;
            case 3:
                type = PlantType.Nightshade;
                cost = 2.0;
                break;
            case 4:
                type = PlantType.Foxglove;
                cost = 2.0;
                break;
            case 5:
                type = PlantType.LilyOfTheValley;
                cost = 1.0;
                break;
            default:
                logger.info("Invalid plant choice!");
                return;
        }

        if (oxygen < cost) {
            logger.info("Not enough oxygen! Need {} but have {}", cost, String.format("%.2f", oxygen));
            return;
        }

        logger.info("Available open plots:");
        for (Plot plot : garden.getPlots()) {
            if (plot.isOpen()) {
                logger.info("  - {}", plot.getName());
            }
        }
        System.out.println("Enter plot name: ");
        String plotName = scanner.nextLine().trim();

        try {
            Plot plot = garden.getPlot(plotName);
            if (plot.isOpen()) {
                purchasePlant(type, plotName, cost);
            } else {
                logger.info("That plot is not open!");
            }
        } catch (IllegalArgumentException e) {
            logger.info("Invalid plot name!");
        }
    }
}
