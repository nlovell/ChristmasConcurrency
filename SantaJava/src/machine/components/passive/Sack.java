package machine.components.passive;

import machine.data.AgeRange;
import machine.components.MachinePart;
import machine.interfaces.PassiveConsumer;

import java.util.Arrays;

public class Sack extends MachinePart implements PassiveConsumer {

    final Present[] presents;
    final int capacity;
    int fullness = 0;
    final AgeRange ages;


    public Sack(int capacity, int sack_id, int ageMin, int ageMax) {
        super(sack_id);
        this.presents = new Present[capacity];
        this.capacity = capacity;

        this.ages = new AgeRange(ageMin, ageMax);
    }

    void replaceSack() {
        Arrays.fill(presents, null);
        fullness = 0;
    }

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