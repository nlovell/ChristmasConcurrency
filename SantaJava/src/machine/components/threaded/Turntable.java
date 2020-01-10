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

import java.util.Arrays;

import static machine.data.Constants.*;
import static machine.data.Direction.*;

public class Turntable extends MachinePart implements ActiveSupplier, ActiveConsumer, Runnable {


    private final TurntableConnection[] connections;
    private Present current;
    private volatile boolean running = true;
    private Direction lastDirectionMoved;


    public Turntable(final String id, final TurntableConnection[] connections) {
        super(id);
        this.connections = connections;
    }

    @Override
    public void run() {
        boolean print = true;
        do {
            if (current != null) {
               // if (!print) cout("Turntable " + this.getId() + " is attempting to move a present.\n" + this.toString());
                print = true;
                supplyPresent();
            } else {
                //if (print) cout("Turntable " + this.getId() + " is attempting to receive a present.");
                print = false;
                consumePresent();
            }
        } while (running);
    }

    private void supplyPresent() {
        for (TurntableConnection connection : connections) {
            //If the current Connection direction is the same place the gift came from
            if (connection.getDir() != lastDirectionMoved) {

                //If the connection is NOT an input
                if (!connection.isInput()) {

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
    }


    void consumePresent() {
        for (TurntableConnection connection : connections) {
            if (connection != null && connection.getConsumer() == null) {
                PassiveSupplier supp = connection.getSupplier();
                if (supp != null) {
                    this.current = supp.supply();
                    if (this.current != null) {
                        //cout("Turntable " + this.getId() + " has successfully received a present!");
                        break;
                    }
                }
            }
        }
    }


    @Override
    public void consume(final PassiveSupplier supplier, final Direction inputDir) {
        moveGiftDelay();
        current = supplier.supply();
        lastDirectionMoved = inputDir;
    }

    @Override
    public void supply(final PassiveConsumer consumer, final Direction outputDir) {
        //TODO must be atomic!
        //if the gift is coming from North or South
        if (outputDir == N || outputDir == S) {
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

        if (consumer.consume(current))
            current = null;
    }

    public void rotateDelay() {
        try {
            Thread.sleep(ROTATE_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void moveGiftDelay() {
        try {
            Thread.sleep(MOVE_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setStop() {
        this.running = false;
    }


    public boolean hasPresent() {
        if (current != null) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "Turntable{" +
                "current=" + current +
                ", connections=" + Arrays.toString(connections) +
                ", running=" + running +
                '}';
    }
}
