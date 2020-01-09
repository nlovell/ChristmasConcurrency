package machine.components.passive;

import machine.data.AgeRange;
import machine.components.MachinePart;
import machine.interfaces.PassiveConsumer;
import machine.interfaces.PassiveSupplier;

import java.util.Arrays;

public class Conveyor extends MachinePart implements PassiveSupplier, PassiveConsumer {

    private final Sack[] destinations;
    private final Present[] presents;

    private int head = 0;
    private int tail = 0;
    private final int length;

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
        //TODO thread safeify this - mutex this fucker

        synchronized (presents) {
            if (isSpace()) {
                System.out.println(this.toString());
                System.out.println("Conveyor " + super.getId() + " received a gift!");
                presents[tail] = gift;
                incrementTail();
                return true;
            }
        }

        return false;
    }


    /**
     * When the belt supplies a present
     * @return the present
     */
    @Override
    public Present supply() {
        Present result = null;
        //TODO thread safeify this - mutex this fucker
        if (!isEmpty()) {
            Present gift;
            gift = presents[head];
            presents[head] = null;
            incrementHead();
            result = gift;
        }
        return result;
    }

    @Override
    public String toString() {
        return "Conveyor{" + super.toString() +
                "destinations=" + Arrays.toString(destinations) +
                ", presents=" + Arrays.toString(presents) +
                ", head=" + head +
                ", tail=" + tail +
                ", length=" + length +
                '}';
    }

//o In the scenario, the belts are acting as passive buffers (shared memory space) between the
//various hoppers, turntables and sacks. In other words, you have something like several
//Producer/Consumer scenarios chained together.
//TODO  A belt will act a little like a queue machine.data structure. The belt class should be implemented as a fixed size
//      array to store the gift objects as they pass onto and off it (capacity according to the configuration of the
//      machine). DO NOT use a Java collection instead of an array.
//o One simple way to implement the belt is as a circular buffer, enforcing a strict ordering to the
//addition and removal of gifts.
//o If you are really stuck with implementing the conveyor belt, you could build it with capacity for
//only one gift, so that you didn't need to use an array at all – just a single variable. You would
//lose lots of marks for doing this, but it might allow you to get the program working and gain
//other marks elsewhere. It is better to have a simpler but working program than a confused
//mess!
//TODO  You will need to provide a method for a Hopper to check if there is space on the belt before attempting
//      to add a Present (to the “input” belts), and another for the turntables to see whether a gift is available
//      for them or not.
//TODO  It is important that your program is thread-safe, and you need to take care to ensure that your
//      conveyor belts can’t be corrupted (e.g. when two turntables try to access one at the same time)

}
