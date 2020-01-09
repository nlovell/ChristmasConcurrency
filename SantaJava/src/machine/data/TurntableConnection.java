package machine.data;

import machine.components.passive.Conveyor;
import machine.components.passive.Sack;
import machine.interfaces.PassiveConsumer;
import machine.interfaces.PassiveSupplier;



public class TurntableConnection {

    /** True for input **/
    private final boolean isInput;
    private final Direction dir;
    private final PassiveConsumer consumer;
    private final PassiveSupplier supplier;


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

    public Direction getDir() {
        return dir;
    }
}
