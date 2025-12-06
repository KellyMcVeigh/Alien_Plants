# Alien Plants

> **Note:** This README was AI-generated.

A garden simulation game demonstrating object-oriented design patterns in Java. Manage an alien colony's garden to produce enough oxygen to survive!

## Game Overview

You are managing a garden in an alien colony. Your goal is to reach **50 oxygen** to win the game. Each turn, your plants photosynthesize to produce oxygen, but beware of weeds that spread and compete for resources!

### How to Play

Each turn you can:
1. **Uproot a plant** - Remove a plant from a plot
2. **Uproot a weed** - Remove a weed from a plot
3. **Purchase a plant** - Buy and plant a new plant (costs oxygen)
4. **Skip turn** - Do nothing

### Plant Types

| Plant | Oxygen Output | Cost | Special |
|-------|---------------|------|---------|
| Sunflower | 2.0 | 5 | - |
| Rose | 1.5 | 3 | - |
| Nightshade | 0.5 | 2 | Poisons weeds |
| Foxglove | 0.75 | 2 | Poisons weeds |
| Lily of the Valley | 0.25 | 1 | Poisons weeds |

### Weeds
Weeds spread each turn and compete with your plants. Use poisonous plants to combat them!

## Design Patterns Used

### 1. Strategy Pattern
Plants use different strategies for their behavior:
- `PlantStrategy` - Base strategy with default behavior
- `PoisonousStrategy` - Damages weeds in the same plot
- `WeedStrategy` - Spreads to neighboring plots

### 2. Factory Pattern
- `PlantFactory` - Creates plants based on `PlantType`
- `PlotFactory` - Creates garden plots

### 3. Builder Pattern
- `Garden.Builder` - Fluent builder for creating garden layouts (2x2, 3x3 grids)

### 4. Observer Pattern
- `OxygenObserver` - Interface for observing oxygen changes
- `OxygenReporter` - Logs oxygen changes to console

## Project Structure

```
src/
├── main/java/colony/
│   ├── Colony.java           # Main game logic
│   ├── Main.java             # Entry point
│   ├── garden/
│   │   ├── Garden.java       # Garden with Builder
│   │   ├── Plot.java         # Individual garden plot
│   │   └── PlotFactory.java
│   ├── organisms/
│   │   ├── Plant.java        # Base plant class
│   │   ├── Weed.java         # Weed subclass
│   │   ├── PlantType.java    # Plant type enum
│   │   └── PlantFactory.java
│   ├── strategy/
│   │   ├── PlantStrategy.java
│   │   ├── PoisonousStrategy.java
│   │   └── WeedStrategy.java
│   └── observer/
│       ├── OxygenObserver.java
│       └── OxygenReporter.java
└── test/java/colony/
    ├── ColonyTest.java
    ├── garden/GardenTest.java
    ├── organisms/PlantTest.java
    └── strategy/StrategyTest.java
```

## Building and Running
 - Run Main.java

