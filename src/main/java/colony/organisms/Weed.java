package colony.organisms;

public class Weed extends Plant{
    PlantType type;
    public Weed(PlantType type) {
        super(type);
        this.type = type;
    }

    public PlantType getType() {
        return type;
    }

    @Override
    public Boolean isWeed() {
        return true;
    }
}
