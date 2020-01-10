package machine.interfaces;

import machine.data.Direction;

public interface ActiveConsumer {

    void consume(PassiveSupplier supplier, Direction inputDir);
}
