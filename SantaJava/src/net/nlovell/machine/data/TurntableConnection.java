package net.nlovell.machine.data;

import net.nlovell.machine.components.passive.Conveyor;
import net.nlovell.machine.components.passive.Sack;
import net.nlovell.machine.interfaces.PassiveConsumer;
import net.nlovell.machine.interfaces.PassiveSupplier;


/**
 * The type Turntable connection.
 */
public class TurntableConnection {


    /** True for input **/
    private final boolean isInput;
    private final Direction dir;
    private final PassiveConsumer consumer;
    private final PassiveSupplier supplier;


    /**
     * Instantiates a new Turntable connection.
     *
     * @param dir     the dir
     * @param isInput the is input
     * @param belt    the belt
     */
    public TurntableConnection(Direction dir, boolean isInput, Conveyor belt){
        this.isInput = isInput;
        this.dir = dir;
        if(isInput) {
            this.supplier = belt;
            this.consumer = null;
        } else {
            this.supplier = null;
            this.consumer = belt;
        }
    }

    /**
     * Instantiates a new Turntable connection.
     *
     * @param dir  the dir
     * @param sack the sack
     */
    public TurntableConnection(Direction dir, Sack sack){
        this.dir = dir;
        this.consumer = sack;

        this.isInput = false;
        this.supplier = null;
    }

    /**
     * Gets consumer.
     *
     * @return the consumer
     */
    public PassiveConsumer getConsumer() {
        return consumer;
    }

    /**
     * Gets supplier.
     *
     * @return the supplier
     */
    public PassiveSupplier getSupplier() {
        return supplier;
    }

    /**
     * Gets dir.
     *
     * @return the dir
     */
    public Direction getDir() {
        return dir;
    }

    /**
     * Is input boolean.
     *
     * @return the boolean
     */
    public boolean isInput() {
        return isInput;
    }

    @Override
    public String toString() {
        return "{" +
                "isInput=" + isInput +
                ", dir=" + dir +
                '}';
    }
}
