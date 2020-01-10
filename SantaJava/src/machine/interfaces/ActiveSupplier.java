package machine.interfaces;
import machine.data.Direction;

/**
 * The interface Active supplier.
 */
public interface ActiveSupplier {

    /**
     * Supply.
     *
     * @param consumer  the consumer
     * @param outputDir the output dir
     */
    void supply(PassiveConsumer consumer, Direction outputDir);

}
