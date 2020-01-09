package machine.components.threaded;

import machine.components.MachinePart;
import machine.components.passive.Conveyor;
import machine.data.Direction;
import machine.interfaces.ActiveSupplier;
import machine.interfaces.PassiveConsumer;

/**
 * The type Hopper.
 */
public class Hopper extends MachinePart implements ActiveSupplier, Runnable {

    private final Conveyor connectedBelt;
    private final int capacity;
    private final int speed;
    private boolean running = true;

    /**
     * Instantiates a new Hopper.
     *
     * @param hopperID      the hopper id
     * @param connectedBelt the connected belt
     * @param capacity      the capacity of the hopper
     * @param speed         the output speed of the hopper
     */
    Hopper(int hopperID, Conveyor connectedBelt, int capacity, int speed) {
        super(hopperID);
        this.connectedBelt = connectedBelt;
        this.capacity = capacity;
        this.speed = speed;
    }

    @Override
    public void run() {
        do{

        } while (running);
    }


    //TODO: Hoppers have a collection of presents.
    //TODO: Hoppers are associated with a conveyor belt, and have a speed of working.
    //TODO: According to its pre-set speed of working, at appropriate intervals until it is empty,
    //      a hopper will attempt to place presents onto the conveyor belt – as long as there is space on the belt.


    //<editor-fold desc="Getter methods">
    /**
     * Gets capacity.
     *
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Gets connected belt.
     *
     * @return the connected belt
     */
    public Conveyor getConnectedBelt() {
        return connectedBelt;
    }


    /**
     * Gets speed.
     *
     * @return the speed
     */
    public int getSpeed() {
        return speed;
    }


    @Override
    public void supply(PassiveConsumer consumer, Direction outputDir) {

    }

    public void setStop() {
        this.running = false;
    }
    //</editor-fold>
}
