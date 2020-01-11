package machine.components.threaded;

import machine.components.MachinePart;
import machine.components.passive.Present;
import machine.components.passive.Sack;
import machine.data.Direction;
import machine.data.TurntableConnection;
import machine.interfaces.ActiveConsumer;
import machine.interfaces.ActiveSupplier;
import machine.interfaces.PassiveConsumer;
import machine.interfaces.PassiveSupplier;

import static machine.data.Constants.*;
import static machine.data.Direction.*;

/**
 * The type Turntable.
 */
public class Turntable extends MachinePart implements ActiveSupplier, ActiveConsumer, Runnable {


    private final TurntableConnection[] connections;
    private Present current;
    private volatile boolean running = true;
    private Direction lastDirectionMoved;

    /**
     * Instantiates a new Turntable.
     *
     * @param id          the id
     * @param connections the connections
     */
    public Turntable(final String id, final TurntableConnection[] connections) {
        super(id);
        this.connections = connections;
        //All turntables are initialised to face North/South
        lastDirectionMoved = N;
    }

    @Override
    public void run() {
        boolean print = true;
        do {
            if (current != null) {
                if (!print) clog(CLOG_DEBUG,
                        "Turntable " + this.getId() + " is attempting to move a present.\n" + this.toString());
                print = true;
                supplyPresent();
            } else {
                if (print) clog(CLOG_DEBUG,
                        "Turntable " + this.getId() + " is attempting to receive a present.");
                print = false;
                consumePresent();
            }
        } while (running);
    }

    private void supplyPresent() {
        for (TurntableConnection connection : connections) {
            //If the current Connection direction is the same place the gift came from
            // and the connection is NOT an input and is NOT null...
            if (connection != null && connection.getDir() != lastDirectionMoved
                    && !connection.isInput()) {
                //Interrogate the gift for potential destinations
                Sack[] suitableSacks = current.getSuitableSacks();
                for (Sack sack : suitableSacks) {
                    for (Sack dest : connection.getConsumer().getDestinations()) {
                        if (dest == sack && dest.isSpace()) {
                            supply(connection.getConsumer(), connection.getDir());
                            return;
                        }
                    }
                }
            }
        }
    }


    /**
     * Consume present.
     */
    void consumePresent() {
        for (TurntableConnection connection : connections) {
            if (connection != null && connection.getConsumer() == null) {
                PassiveSupplier supp = connection.getSupplier();
                if (supp != null) {
                    this.current = supp.supply();
                    if (this.current != null) {
                        clog(CLOG_DEBUG, "Turntable " + this.getId() + " has successfully received a present!");
                        break;
                    }
                }
            }
        }
    }


    @Override
    public void consume(final PassiveSupplier supplier, final Direction inputDir) {
        rotate(inputDir);
        moveGiftDelay();
        current = supplier.supply();
        lastDirectionMoved = inputDir;
    }

    @Override
    public void supply(final PassiveConsumer consumer, final Direction outputDir) {
        rotate(outputDir);
        if (consumer.consume(current))
            current = null;
    }

    public void rotate(final Direction direction) {
        //if the gift is coming from North or South
        if (direction == N || direction == S) {
            //and ISN'T going North or South
            if (lastDirectionMoved != N && lastDirectionMoved != S) {
                //Rotate the turntable
                rotateDelay();
                lastDirectionMoved = E;
            }
        }  //Otherwise the gift is coming from East or West
        else {
            //And needs to be rotated
            if (lastDirectionMoved != E && lastDirectionMoved != W) {
                rotateDelay();
                lastDirectionMoved = N;
            }
        }
    }

    /**
     * Rotate delay.
     */
    public void rotateDelay() {
        try {
            clog(CLOG_DEBUG, "Turntable " + this.getId() + " is rotating.");
            Thread.sleep(ROTATE_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Move gift delay.
     */
    public void moveGiftDelay() {
        try {
            Thread.sleep(MOVE_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets stop.
     */
    public void setStop() {
        this.running = false;
    }


    /**
     * Has present boolean.
     *
     * @return the boolean
     */
    public boolean hasPresent() {
        if (current != null) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {

        //This section stops the need to import the Java Utils Arrays.
        StringBuilder conns = new StringBuilder("[");
        for (TurntableConnection connection : connections) {
            if (connection != null)
                conns.append(connection.toString()).append(", ");
            else
                conns.append("{connection  null}, ");
        }
        conns.delete(conns.length() - 2, conns.length());
        conns.append("]");

        return "Turntable{" +
                "current=" + current +
                ", connections=" + conns +
                ", running=" + running +
                '}';
    }
}
