package net.nlovell.machine.interfaces;

import net.nlovell.machine.components.passive.Sack;
import net.nlovell.machine.data.AgeRange;
import net.nlovell.machine.components.passive.Present;

/**
 * The interface Passive consumer.
 */
public interface PassiveConsumer {
    /**
     * Consume boolean.
     *
     * @param gift the gift
     * @return the boolean
     */
    boolean consume(Present gift);

    /**
     * Get destinations sack [ ].
     *
     * @return the sack [ ]
     */
    Sack[] getDestinations();

    /**
     * Search int.
     *
     * @param age the age
     * @return the int
     */
    int search(AgeRange age);
}
