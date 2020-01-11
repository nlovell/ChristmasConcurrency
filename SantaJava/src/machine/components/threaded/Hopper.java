package machine.components.threaded;

import machine.components.MachinePart;
import machine.components.passive.Conveyor;
import machine.components.passive.Present;
import machine.components.passive.Sack;
import machine.data.Direction;
import machine.interfaces.ActiveSupplier;
import machine.interfaces.PassiveConsumer;

import static clog.constants.CLOG_DEBUG;
import static clog.log.clogger;

/**
 * The type Hopper.
 */
public class Hopper extends MachinePart implements ActiveSupplier, Runnable {

    private final Conveyor connectedBelt;
    private final int capacity;
    private int current = 0;
    private final int speed;
    private volatile boolean running = true;
    private Present[] gifts;

    /**
     * Instantiates a new Hopper.
     *
     * @param hopperID      the hopper id
     * @param connectedBelt the connected belt
     * @param capacity      the capacity of the hopper
     * @param speed         the output speed of the hopper
     */
    public Hopper(int hopperID, Conveyor connectedBelt, int capacity, int speed, Present[] gifts) {
        super(hopperID);
        this.connectedBelt = connectedBelt;
        this.capacity = capacity;
        this.speed = speed;
        this.gifts = gifts;

        for(Present gift : gifts){
            if(gift != null){
                current++;
            }
        }
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(1000 * speed);
                if (current > 0) {
                    if (connectedBelt.consume(gifts[1])) {
                        gifts[1] = null;
                        current--;
                    }
                } else {
                    //Stops hoppers running after they're empty.
                    clogger(CLOG_DEBUG, "Hopper " + getId() + " has ran out of gifts, and has stopped running.");
                    setStop();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } while (running);
    }

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
     * Gets the current number of gifts in the hopper
     *
     * @return gift count
     */
    public int getCurrent() {
        return current;
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

    /**
     * Sets stop.
     */
    public void setStop() {
        this.running = false;
    }

    //</editor-fold>

    @Override
    public String toString() {
        return "Hopper{" +
                "connectedBelt=" + connectedBelt.getId() +
                ", capacity=" + capacity +
                ", speed=" + speed +
                ", running=" + running +
                '}';
    }
}
