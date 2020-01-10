package machine.interfaces;

import machine.components.passive.Present;

/**
 * The interface Passive supplier.
 */
public interface PassiveSupplier {

    /**
     * Supply present.
     *
     * @return the present
     */
    Present supply();

}
