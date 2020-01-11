package net.nlovell.machine.components.threaded;

import net.nlovell.machine.components.MachinePart;
import net.nlovell.machine.components.passive.Present;
import net.nlovell.machine.components.passive.Sack;
import net.nlovell.machine.data.Direction;
import net.nlovell.machine.data.TurntableConnection;
import net.nlovell.machine.interfaces.ActiveConsumer;
import net.nlovell.machine.interfaces.ActiveSupplier;
import net.nlovell.machine.interfaces.PassiveConsumer;
import net.nlovell.machine.interfaces.PassiveSupplier;

import static net.nlovell.clog.Log.clogger;
import static net.nlovell.clog.LogConstants.CLOG_DEBUG;
import static net.nlovell.clog.LogConstants.CLOG_ERROR;
import static net.nlovell.machine.data.Constants.*;
import static net.nlovell.machine.data.Direction.*;

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
            try {
                if (current != null) {
                    if (!print) clogger(CLOG_DEBUG,
                            "Turntable " + this.getId() + " is attempting to move a present.\n" + this.toString());
                    print = true;
                    if (!supplyPresent()) {
                        Thread.sleep(50);
                    }
                } else {
                    if (print) clogger(CLOG_DEBUG,
                            "Turntable " + this.getId() + " is attempting to receive a present.");
                    print = false;

                    if (!consumePresent()) {
                        Thread.sleep(50);
                    }
                }
            } catch (InterruptedException e) {
                clogger(CLOG_ERROR, e.getMessage());
            }
        } while (running);
    }

    private boolean supplyPresent() {
        if (this.getId().equals("B")) {
            int i = 0;
        }
        TurntableConnection conn = giftRouter(current);
        return supply(conn.getConsumer(), conn.getDir());
    }

    TurntableConnection giftRouter(Present gift) {
        TurntableConnection conn;
        for (TurntableConnection connection : this.connections) {
            //If the current Connection direction is the same place the gift came from
            // and the connection is NOT an input and is NOT null...
            if (connection != null && connection.getDir() != lastDirectionMoved
                    && !connection.isInput()) {
                //Interrogate the gift for potential destinations
                Sack[] suitableSacks = gift.getSuitableSacks();
                if (suitableSacks == null) return null;

                for (Sack sack : suitableSacks) {
                    for (Sack dest : connection.getConsumer().getDestinations()) {
                        if (dest == sack && dest.isSpace()) {
                            return connection;
                        }
                    }
                }
            }
        }
        return null;
    }


    /**
     * Consume present.
     */
    boolean consumePresent() {
        for (TurntableConnection connection : connections) {
            if (connection != null && connection.getConsumer() == null) {
                PassiveSupplier supp = connection.getSupplier();
                if (supp != null) {

                    synchronized (supp) {
                        //peak at the present
                        Present peaked = supp.peak();
                        if (peaked != null && giftRouter(peaked) != null) {
                            //yoink
                            this.current = supp.supply();
                            if (this.current != null) {
                                //if i can, it's mine now
                                clogger(CLOG_DEBUG, "Turntable " + this.getId() + " has successfully received a present!");
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


    @Override
    public void consume(final PassiveSupplier supplier, final Direction inputDir) {
        rotate(inputDir);
        moveGiftDelay();
        current = supplier.supply();
        lastDirectionMoved = inputDir;
    }

    @Override
    public boolean supply(final PassiveConsumer consumer, final Direction outputDir) {
        rotate(outputDir);
        if (consumer.consume(current)) {
            current = null;
            return true;
        }
        return false;
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
            clogger(CLOG_DEBUG, "Turntable " + this.getId() + " is rotating.");
            Thread.sleep(ROTATE_TIME / SPEED_MULT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Move gift delay.
     */
    public void moveGiftDelay() {
        try {
            Thread.sleep(MOVE_TIME / SPEED_MULT);
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