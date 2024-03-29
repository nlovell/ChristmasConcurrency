package net.nlovell.machine.components.threaded;

import net.nlovell.machine.components.MachinePart;
import net.nlovell.machine.components.passive.Conveyor;
import net.nlovell.machine.components.passive.Present;
import net.nlovell.machine.data.Direction;
import net.nlovell.machine.interfaces.ActiveSupplier;
import net.nlovell.machine.interfaces.PassiveConsumer;
import net.nlovell.machine.data.Constants;

import static net.nlovell.clog.LogConstants.CLOG_DEBUG;
import static net.nlovell.clog.Log.clogger;

/**
 * The type Hopper.
 */
public class Hopper extends MachinePart implements ActiveSupplier, Runnable {

    private final Conveyor connectedBelt;
    private final int capacity;
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
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep((1000 * speed)/ Constants.SPEED_MULT);
                int current = getCurrent();
                if (current > 0) {
                    if (connectedBelt.consume(gifts[current -1])) {
                        gifts[current - 1] = null;
                    }
                } else {
                    //Stops hoppers running after they're empty.
                    clogger(CLOG_DEBUG, "Hopper " + getId() + " has ran out of gifts, and has stopped running.");
                    setStop();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } while (running); //TODO syncronise or Thread.interupted()
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
        int cur = 0;
        for(Present gift : gifts){
            if(gift != null){
                cur++;
            }
        }
        return cur;
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
    public boolean supply(PassiveConsumer consumer, Direction outputDir) {
        return false;
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