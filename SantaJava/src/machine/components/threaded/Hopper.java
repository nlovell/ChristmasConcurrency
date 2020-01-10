package machine.components.threaded;

import machine.components.MachinePart;
import machine.components.passive.Conveyor;
import machine.components.passive.Present;
import machine.components.passive.Sack;
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
    private volatile boolean running = true;
    private Sack[] sacks;

    /**
     * Instantiates a new Hopper.
     *
     * @param hopperID      the hopper id
     * @param connectedBelt the connected belt
     * @param capacity      the capacity of the hopper
     * @param speed         the output speed of the hopper
     */
    public Hopper(int hopperID, Conveyor connectedBelt, int capacity, int speed) {
        super(hopperID);
        this.connectedBelt = connectedBelt;
        this.capacity = capacity;
        this.speed = speed;
    }

    @Override
    public void run() {
        try {
            do {
                Thread.sleep(1000 * speed);
                Present gift = new Present(sacks, 1, 99);
                connectedBelt.consume(gift);

            } while (running);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    public void setSacks(Sack[] sacks) {
        this.sacks = sacks;
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
