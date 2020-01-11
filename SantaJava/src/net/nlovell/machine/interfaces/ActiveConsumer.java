package net.nlovell.machine.interfaces;

import net.nlovell.machine.data.Direction;

/**
 * The interface Active consumer.
 */
public interface ActiveConsumer {

    /**
     * Consume.
     *
     * @param supplier the supplier
     * @param inputDir the input dir
     */
    void consume(PassiveSupplier supplier, Direction inputDir);
}
