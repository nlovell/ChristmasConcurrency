package machine.components.threaded;

import machine.MachinePart;
import machine.interfaces.ActiveSupplier;
import machine.interfaces.PassiveSupplier;

/**
 * The type Hopper.
 */
public class Hopper extends MachinePart implements ActiveSupplier, Runnable {

    private final int hopperID;
    private final int connectedBelt;
    private final int capacity;
    private final int speed;
    /**
     * Instantiates a new Hopper.
     * @param hopperID      the hopper id
     * @param connectedBelt the connected belt
     * @param capacity      the capacity of the hopper
     * @param speed         the output speed of the hopper
     */
    Hopper(int hopperID, int connectedBelt, int capacity, int speed) {
        this.hopperID = hopperID;
        this.connectedBelt = connectedBelt;
        this.capacity = capacity;
        this.speed = speed;

        //System.out.println(ChristmasMachine.getInstance().testString);
    }

    @Override
    public void run() {
    }


    //TODO: Hoppers have a collection of presents.
    //TODO: Hoppers are associated with a conveyor belt, and have a speed of working.
    //TODO: According to its pre-set speed of working, at appropriate intervals until it is empty, a hopper will attempt to place presents onto the conveyor belt – as long as there is space on the belt.


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
    public int getConnectedBelt() {
        return connectedBelt;
    }

    /**
     * Gets hopper id.
     *
     * @return the hopper id
     */
    public int getHopperID() {
        return hopperID;
    }

    /**
     * Gets speed.
     *
     * @return the speed
     */
    public int getSpeed() {
        return speed;
    }
    //</editor-fold>
}
