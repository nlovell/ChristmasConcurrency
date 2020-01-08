package machine.components.threaded;

import machine.interfaces.ActiveConsumer;
import machine.interfaces.ActiveSupplier;
import machine.interfaces.PassiveConsumer;
import machine.interfaces.PassiveSupplier;

public class Turntable implements ActiveSupplier, ActiveConsumer, Runnable {

    @Override
    public void run() {}

    //TODO When a turntable detects that a gift is waiting at one of its input ports (e.g. by polling all connected
    //      input conveyor belts in turn), the table will turn to receive the gift. Having determined the gift’s
    //      destination sack (by interrogating the gift), the table will then turn to line up with the appropriate
    //      output port and eject the gift if it is able to do so.
    //TODO The turntable has 2 alignments – North-South, and East-West. Presents can be moved in either
    //  direction by the turntable, so it should only ever need to move through 90 degrees from one alignment
    //  to the other. Eg. If a present was moving from West to East, the turntable would not need to rotate
    //  with the Present on it.
    //> The thread should sleep for a certain amount of time to simulate the time taken in turning. It
    //  should also sleep to represent moving the present on or off the turntable.
    //      >> It should take 0.5 seconds to rotate 90 degrees.
    //      >> It should take 0.75 seconds to move a present either on or off a turntable
    //> To keep things simple, as part of the configuration, each turntable will have a set of destination
    //  sacks associated with each of its output ports (as well as knowing the identities of any attached
    //  conveyor belts / sacks) so that, for example if turntable “B” in the configuration illustrated on
    //  page 2 receives a gift destined for sack “4”, it will know to eject the gift to the South.
}
