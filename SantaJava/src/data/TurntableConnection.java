package data;

import machine.components.passive.Conveyor;
import machine.components.passive.Sack;
import machine.interfaces.PassiveConsumer;
import machine.interfaces.PassiveSupplier;

public class TurntableConnection {

    /** True for input **/
    final boolean isInput;
    final Direction dir;
    final PassiveConsumer consumer;
    final PassiveSupplier supplier;


    enum Direction {
        N,
        E,
        W,
        S
    }

    public TurntableConnection(Direction dir, boolean isInput, Conveyor belt){
        this.isInput = isInput;
        this.dir = dir;
        if(isInput) {
            this.supplier = belt;
            this.consumer = null;
        } else {
            this.supplier = null;
            this.consumer = belt;
        }
    }

    public TurntableConnection(Direction dir, Sack sack){
        this.dir = dir;
        this.consumer = sack;

        this.isInput = false;
        this.supplier = null;
    }

    public PassiveConsumer getConsumer() {
        return consumer;
    }

    public PassiveSupplier getSupplier() {
        return supplier;
    }

    public int search(final AgeRange age) {
      if(!isInput){
          if(consumer != null){
            return consumer.search(age);
          }
      }
      return Integer.MAX_VALUE;
    }
}
