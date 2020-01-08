package machine.components.passive;

import machine.ChristmasMachine;
import machine.interfaces.PassiveConsumer;
import machine.interfaces.PassiveSupplier;

public class Conveyor implements PassiveSupplier, PassiveConsumer {

    final int ID;
    final int length;
    final int[] destinations;
    Present[] presents;
    final ChristmasMachine machine;

    public Conveyor(int id, int length, int[] destinations, ChristmasMachine machine) {
        ID = id;
        this.length = length;
        this.destinations = destinations;
        this.machine = machine;
        this.presents = new Present[this.length];
        //this.machine = ChristmasMachine.getInstance();
    }

    boolean insertPresent(Present giftToInsert){
        //TODO: this is a critical region.
        if(isSpace()){
            for(int i = 0; i<length-1; i++){
                if(presents[i]==null){
                    presents[i] = giftToInsert;
                    return true;
                }
            }
        }
        return false;
    }

    Present removePresent(){
        Present temp = null;
        //TODO: this is a critical region.
        if(presents[0] != null){
            //move present 0 to the temp buffer
            temp = presents[0];
            presents[0] = null;

            for(int i = 1; i<length-1; i++){
                presents[i-1] = presents[i];
            }
            return temp;
        }

        return null;
    }

    boolean isSpace() {
        //TODO: this is a critical region.
        for (Present present : presents)
            if(present == null)
                return true;
        return false;
    }

//o In the scenario, the belts are acting as passive buffers (shared memory space) between the
//various hoppers, turntables and sacks. In other words, you have something like several
//Producer/Consumer scenarios chained together.
//TODO  A belt will act a little like a queue data structure. The belt class should be implemented as a fixed size
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
