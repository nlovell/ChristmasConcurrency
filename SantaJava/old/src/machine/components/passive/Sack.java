package machine.components.passive;

import machine.data.AgeRange;
import machine.components.MachinePart;
import machine.interfaces.PassiveConsumer;

import java.util.Arrays;

/**
 * The type Sack.
 */
public class Sack extends MachinePart implements PassiveConsumer {

    @Override
    public String toString() {
        return "Sack{" + super.toString() +
                ", capacity=" + capacity +
                ", fullness=" + fullness +
                ", ages=" + ages +
                "}\n";
    }

    /**
     * The Presents.
     */
    final Present[] presents;
    /**
     * The Capacity.
     */
    final int capacity;
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
     * @param capacity the capacity
     * @param sack_id  the sack id
     * @param ageMin   the age min
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
    void replaceSack() {
        Arrays.fill(presents, null);
        fullness = 0;
    }

    /**
     * Is space boolean.
     *
     * @return the boolean
     */
    boolean isSpace() {
        return fullness < capacity;
    }

    @Override
    public boolean consume(final Present gift) {
        if(isSpace()){
            presents[fullness] = gift;
            fullness++;
            return true;
        }

        return false;
    }

    @Override
    public int search(final AgeRange age) {
        if(age == this.ages){
            return 0;
        } else {
            return Integer.MAX_VALUE;
        }
    }
}