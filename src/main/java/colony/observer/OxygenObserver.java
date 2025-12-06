package colony.observer;

public interface OxygenObserver {
    void onOxygenChange(double previousOxygen, double newOxygen, double change);
}

