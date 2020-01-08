package machine.components.passive;

import data.AgeRange;
import machine.interfaces.PassiveConsumer;
import machine.interfaces.PassiveSupplier;

import java.util.Arrays;

public class Sack implements PassiveConsumer {

    final Present presents[];
    final int sack_id;
    final int capacity;
    final AgeRange ages;


    public Sack(int capacity, int sack_id, int capacity1, int agemin, int agemax) {
        this.presents = new Present[capacity];
        this.sack_id = sack_id;
        this.capacity = capacity1;

        this.ages = new AgeRange(agemin, agemax);
    }

    void replaceSack() {
        Arrays.fill(presents, null);
    }

    boolean isSpace() {
        for (Present present : presents) {
            if (present == null) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void consume(PassiveSupplier source) {
        //TODO: consume stuff
    }
}
