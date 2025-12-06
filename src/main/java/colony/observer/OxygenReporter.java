package colony.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OxygenReporter implements OxygenObserver {
    private static final Logger logger = LoggerFactory.getLogger(OxygenReporter.class);

    @Override
    public void onOxygenChange(double previousOxygen, double newOxygen, double change) {
        if (change > 0) {
            logger.info("Oxygen increased by {} (from {} to {})", 
                String.format("%.2f", change), 
                String.format("%.2f", previousOxygen), 
                String.format("%.2f", newOxygen));
        } else if (change < 0) {
            logger.info("Oxygen decreased by {} (from {} to {})", 
                String.format("%.2f", Math.abs(change)), 
                String.format("%.2f", previousOxygen), 
                String.format("%.2f", newOxygen));
        }
    }
}

