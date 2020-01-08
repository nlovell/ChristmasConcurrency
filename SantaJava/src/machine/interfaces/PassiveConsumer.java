package machine.interfaces;

import data.AgeRange;
import machine.components.passive.Present;
import machine.components.passive.Sack;

public interface PassiveConsumer {
    boolean consume(Present gift);

    int search(AgeRange age);
}
