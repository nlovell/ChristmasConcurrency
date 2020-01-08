package machine.interfaces;

import machine.components.passive.Present;
import machine.components.passive.Sack;

public interface PassiveConsumer {
    boolean consume(Present gift);

    Sack[] getDestinations();
}
