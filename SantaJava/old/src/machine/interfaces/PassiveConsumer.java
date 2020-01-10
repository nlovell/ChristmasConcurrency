package machine.interfaces;

import machine.data.AgeRange;
import machine.components.passive.Present;

public interface PassiveConsumer {
    boolean consume(Present gift);

    int search(AgeRange age);
}
