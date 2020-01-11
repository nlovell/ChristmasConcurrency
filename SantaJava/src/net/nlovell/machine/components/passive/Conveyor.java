package net.nlovell.machine.components.passive;

import net.nlovell.clog.Log;
import net.nlovell.machine.data.AgeRange;
import net.nlovell.machine.components.MachinePart;
import net.nlovell.machine.interfaces.PassiveConsumer;
import net.nlovell.machine.interfaces.PassiveSupplier;

import java.util.Arrays;

import static net.nlovell.clog.LogConstants.CLOG_DEBUG;

/**
 * The type Conveyor. //TODO
 */
public class Conveyor extends MachinePart implements PassiveSupplier, PassiveConsumer {

    private final Sack[] destinations;
    private final Present[] presents;

    private int head = 0;
    private int tail = 0;
    private final int length;

    /**
     * Instantiates a new Conveyor.
     *
     * @param id           the id
     * @param length       the length
     * @param destinations the destinations
     */
    public Conveyor(String id, int length, Sack[] destinations) {
        super(id);
        this.length = length;
        this.destinations = destinations;
        this.presents = new Present[this.length];
    }

    private boolean isSpace() {
        return giftsInConveyor() != length;
    }

    private boolean isEmpty() {
        synchronized (this) {
            return head == tail && presents[head] == null;
        }
    }

    private boolean isFull() {
        return head == tail && !isEmpty();
    }

    private void incrementTail() {
        tail++;
        if (tail >= length) {
            tail = 0;
        }
    }

    private void incrementHead() {
        head++;
        if (head >= length) {
            head = 0;
        }
    }

    /**
     * Gifts in conveyor int.
     *
     * @return the int //todo what?
     */
    public int giftsInConveyor() {
        // head and tail can be the same value when both full or empty.
        // this if deals with it.
        if (isFull())
            return length;

        return (tail + (length - head)) % length;

        // modulus maths handles tail-before-head positions in the queue, eg:
        //
        // head H = 3;
        // tail T = 1;
        // length = (6);
        //
        // these values can be depicted as follows:
        //               T   H
        //     array: [o| | |o|o|o]
        // positions:  0 1 2 3 4 5 (6)
        //
        // using these values in the function, it will return int 4 as expected
    }

    @Override //todo remove
    public int search(final AgeRange age) {
        for (Sack sack : destinations) {
            if (sack.search(age) == 0) {
                return destinations.length;
            }
        }
        return Integer.MAX_VALUE;
    }

    /**
     * When the belt receives a present
     *
     * @return boolean
     */
    @Override
    public boolean consume(final Present gift) {
        synchronized (this) {
            if (isSpace()) {
                Log.clogger(CLOG_DEBUG, this.toString());
                Log.clogger(CLOG_DEBUG, "Conveyor " + super.getId() + " received a gift!");
                presents[tail] = gift;
                incrementTail();
                return true;
            }
        }

        return false;
    }

    /**
     * When the belt supplies a present
     *
     * @return the present
     */
    @Override
    public Present supply() {
        Present result = null;

        synchronized (this) {
            if (isEmpty()) {
                return null;
            } else {
                final Present gift = presents[head];
                presents[head] = null;
                Log.clogger(CLOG_DEBUG, "Conveyor " + super.getId() + " supplied a gift!");
                incrementHead();
                result = gift;
            }
        }

        return result;
    }

    @Override
    public Sack[] getDestinations() {
        return destinations;
    }

    @Override
    public Present peak(){
        synchronized (this) {
            return presents[head];
        }
    }

    @Override
    public String toString() {
        String[] sacks = new String[destinations.length];
        int i = 0;
        for (Sack destination : destinations) {
            sacks[i++] = destination.getId();
        }
        return "Conveyor{" + super.toString() +
                ", destinations=" + Arrays.toString(sacks) +
                ", presents=" + giftsInConveyor() +
                '}';
    }
}
