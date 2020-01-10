package machine.components.passive;

import machine.data.AgeRange;
import machine.components.MachinePart;
import machine.interfaces.PassiveConsumer;
import machine.interfaces.PassiveSupplier;

import java.util.Arrays;

/**
 * The type Conveyor.
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
        //this.machine = ChristmasMachine.getInstance();
    }

    private boolean isSpace() {
        return giftsInConveyor() != length;
        //return tail != head - 1 && (tail != 0 || head != length);
    }

    private boolean isEmpty(){
        return head == tail && presents[head] == null;
    }

    private boolean isFull(){
        return head == tail && !isEmpty();
    }

    private void incrementTail(){
        tail++;
        if(tail >= length){
            tail = 0;
        }
    }

    private void incrementHead(){
        head++;
        if(head >= length){
            head = 0;
        }
    }

    /**
     * Gifts in conveyor int.
     *
     * @return the int
     */
    public int giftsInConveyor(){
        // head and tail can be the same value when both full or empty.
        // this if deals with it.
        if(isFull())
            return length;

        return (tail + (length - head)) % length;

        // fancy maths handles tail-before-head positions in the queue, eg:
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

    @Override
    public int search(final AgeRange age) {
        for(Sack sack : destinations){
            if(sack.search(age) == 0){
                return destinations.length;
            }
        }
        return Integer.MAX_VALUE;
    }

    // T
    // H
    //[o|o|o]


    /**
     * When the belt receives a present
     * @return boolean
     */
    @Override
    public boolean consume(final Present gift) {
        synchronized (presents) {
            if (isSpace()) {
                //cout(this.toString());
                //cout("Conveyor " + super.getId() + " received a gift!");
                presents[tail] = gift;
                incrementTail();
                return true;
            }
        }

        return false;
    }

    @Override
    public Sack[] getDestinations() {
        return destinations;
    }


    /**
     * When the belt supplies a present
     * @return the present
     */
    @Override
    public Present supply() {
        Present result = null;

        synchronized (presents) {
            if (!isEmpty()) {
                Present gift;
                gift = presents[head];
                presents[head] = null;
                //cout("Conveyor " + super.getId() + " supplied a gift!");

                incrementHead();
                result = gift;
            }
            return result;
        }
    }

    @Override
    public String toString() {
        String[] sacks = new String[destinations.length];
        int i = 0;
        for(Sack destination : destinations) {
            sacks[i++] = destination.getId();
        }
        return "Conveyor{" + super.toString() +
                ", destinations=" + Arrays.toString(sacks) +
                ", presents=" + giftsInConveyor() +
                '}';
    }
}
