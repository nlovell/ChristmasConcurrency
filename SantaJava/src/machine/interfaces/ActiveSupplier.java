package machine.interfaces;
import machine.data.Direction;

public interface ActiveSupplier {

    void supply(PassiveConsumer consumer, Direction outputDir);

}
