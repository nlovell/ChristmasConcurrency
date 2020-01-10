package machine.interfaces;

import machine.components.passive.Sack;
import machine.data.AgeRange;
import machine.components.passive.Present;

public interface PassiveConsumer {
    boolean consume(Present gift);
    Sack[] getDestinations();

    int search(AgeRange age);
}
