package machine.components.passive;

import machine.data.AgeRange;
import machine.components.MachinePart;
import machine.interfaces.PassiveConsumer;

import java.util.Arrays;

import static clog.constants.CLOG_DEBUG;

/**
 * The passive consumer Sack.
 * This is the final destination/consumer for all gifts.
 */
public class Sack extends MachinePart implements PassiveConsumer {

    /**
     * The Presents.
     */
    final Present[] presents;
    /**
     * The Capacity.
     */
    private final int capacity;
    /**
     * The Fullness.
     */
    private int fullness = 0;
    /**
     * The Ages.
     */
    final AgeRange ages;

    /**
     * The lifetime total of presents the sack in this slot has received.
     */
    private int lifetimeTotal = 0;


    /**
     * Instantiates a new Sack.
     *
     * @param sack_id  the sack id
     * @param capacity the capacity
     * @param ageMin   the age min;
     * @param ageMax   the age max
     */
    public Sack(int sack_id, int capacity, int ageMin, int ageMax) {
        super(sack_id);
        this.presents = new Present[capacity];
        this.capacity = capacity;

        this.ages = new AgeRange(ageMin, ageMax);
    }

    /**
     * Replace the sack and updatge the lifetimetotal.
     */
    public void replaceSack() {
        synchronized (presents) {
            for(Present present : presents){
                present.deliver();
                present = null;
            }
            Arrays.fill(presents, null);
            fullness = 0;
        }

    }

    /**
     * Returns true if there is space in the sack.
     *
     * @return the boolean
     */
    public boolean isSpace() {
        return fullness < capacity;
    }

    @Override
    public boolean consume(final Present gift) {
        synchronized (presents) {
            if (isSpace()) {
                presents[fullness] = gift;
                fullness++;
                lifetimeTotal++;
                clog.log.clogger(CLOG_DEBUG, "Sack " + this.getId() + " has " + (capacity-fullness) + " slots spare.");
                return true;
            }
        }
        return false;
    }

    @Override
    public Sack[] getDestinations() {
        return new Sack[]{this};
    }

    @Override
    public int search(final AgeRange age) {
        if (age == this.ages) {
            return 0;
        } else {
            return Integer.MAX_VALUE;
        }
    }

    /**
     * Gets capacity.
     *
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Return the total number of presents the Sack has stored.
     *
     * @return the int
     */
    public int getLifetimeTotal() {
        return lifetimeTotal;
    }

    @Override
    public String toString() {
        return "Sack{" + super.toString() +
                ", capacity=" + capacity +
                ", fullness=" + fullness +
                ", ages=" + ages +
                "}\n";
    }
}