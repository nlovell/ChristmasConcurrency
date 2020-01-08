package data;

import machine.components.passive.Conveyor;
import machine.components.passive.Sack;

public class TurntableConnection {

    /** True for input **/
    final boolean isInput;
    final Direction dir;
    final Sack sack;
    final Conveyor conveyor;

    enum Direction {
        N,
        E,
        W,
        S
    }

    public TurntableConnection(Direction dir, boolean isInput, Conveyor belt){
        this.isInput = isInput;
        this.dir = dir;
        this.conveyor = belt;

        this.sack = null;
    }

    public TurntableConnection(Direction dir, Sack sack){
        this.dir = dir;
        this.sack = sack;

        this.isInput = false;
        this.conveyor= null;
    }

    public Sack getSack() {
        return sack;
    }

    public Conveyor getConveyor() {
        return conveyor;
    }
}
