package machine.components.threaded;

import machine.data.Direction;
import machine.data.TurntableConnection;
import machine.components.MachinePart;
import machine.components.passive.Present;
import machine.interfaces.ActiveConsumer;
import machine.interfaces.ActiveSupplier;
import machine.interfaces.PassiveConsumer;
import machine.interfaces.PassiveSupplier;

import java.util.Arrays;

import static machine.data.Constants.MOVE_TIME;
import static machine.data.Constants.ROTATE_TIME;
import static machine.data.Direction.*;

public class Turntable extends MachinePart implements ActiveSupplier, ActiveConsumer, Runnable {


    private Present current;
    private final TurntableConnection[] connections;
    private boolean running = true;


    public Turntable(final String id, final TurntableConnection[] connections) {
        super(id);
        this.connections = connections;
    }

    @Override
    public void run() {
        do {
            if (current != null) {
                supplyPresent();
            } else {
                consumePresent();
            }

        } while (running);
    }

    void supplyPresent() {
        int minPath = Integer.MAX_VALUE;
        TurntableConnection minCon = null;

        for (TurntableConnection connection : connections) {
            int currentPath = connection.search(current.getAgeRange());
            if (currentPath < minPath) {
                minPath = currentPath;
                minCon = connection;
            }

            if (minPath < Integer.MAX_VALUE) {
                supply(minCon.getConsumer(), connection.getDir());
            } else {
                throw new IllegalArgumentException("Age range not found in network for gift " + current.getAgeRange());
            }
        }
    }


    void consumePresent() {
        for (TurntableConnection connection : connections) {
            PassiveSupplier supp = connection.getSupplier();
            if (supp != null) {
                this.current = supp.supply();
                if (this.current != null) {
                    break;
                }
            }
        }
    }


    @Override
    public void consume(final PassiveSupplier supplier, final Direction inputDir) {
        moveGiftDelay();
        current = supplier.supply();
        current.setLastDirectionMoved(inputDir);
    }

    @Override
    public void supply(final PassiveConsumer consumer, final Direction outputDir) {
        //TODO must be atomic!

        Direction moved = current.getLastDirectionMoved();
        //if the gift is coming from North or South
        if (outputDir == N || outputDir == S) {
            //and ISN'T going North or South
            if (moved != N && moved != S) {
                //Rotate the turntable
                rotateDelay();
            }
        } else {
            if (moved != E && moved != W) {
                rotateDelay();
            }
        }

        consumer.consume(current);
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

    //TODO When a turntable detects that a gift is waiting at one of its input ports (e.g. by polling all connected
    //      input conveyor belts in turn), the table will turn to receive the gift. Having determined the gift’s
    //      destination sack (by interrogating the gift), the table will then turn to line up with the appropriate
    //      output port and eject the gift if it is able to do so.
    //TODO The turntable has 2 alignments – North-South, and East-West. Presents can be moved in either
    //  direction by the turntable, so it should only ever need to move through 90 degrees from one alignment
    //  to the other. Eg. If a present was moving from West to East, the turntable would not need to rotate
    //  with the Present on it.
    //> The thread should sleep for a certain amount of time to simulate the time taken in turning. It
    //  should also sleep to represent moving the present on or off the turntable.
    //      >> It should take 0.5 seconds to rotate 90 degrees.
    //      >> It should take 0.75 seconds to move a present either on or off a turntable
    //> To keep things simple, as part of the configuration, each turntable will have a set of destination
    //  sacks associated with each of its output ports (as well as knowing the identities of any attached
    //  conveyor belts / sacks) so that, for example if turntable “B” in the configuration illustrated on
    //  page 2 receives a gift destined for sack “4”, it will know to eject the gift to the South.
}
