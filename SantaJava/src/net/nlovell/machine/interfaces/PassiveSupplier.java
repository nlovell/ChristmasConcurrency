package net.nlovell.machine.interfaces;

import net.nlovell.machine.components.passive.Present;

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
