package machine.components.passive;

import machine.data.AgeRange;
import machine.components.MachinePart;
import machine.interfaces.PassiveConsumer;

import java.util.Arrays;

/**
 * The type Sack.
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
    int fullness = 0;
    /**
     * The Ages.
     */
    final AgeRange ages;


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
     * Replace sack.
     */
    public void replaceSack() {
        synchronized(presents) {
            Arrays.fill(presents, null);
            fullness = 0;
        }

    }

    /**
     * Is space boolean.
     *
     * @return the boolean
     */
    public boolean isSpace() {
        return fullness < capacity;
    }

    @Override
    public boolean consume(final Present gift) {
        synchronized(presents) {
            if (isSpace()) {
                presents[fullness] = gift;
                fullness++;
                //cout("Sack " + this.getId() + " has " + (capacity-fullness) + " slots spare.");
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
        if(age == this.ages){
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
     * Get fullness int.
     *
     * @return the int
     */
    public int getFullness(){
        return fullness;
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