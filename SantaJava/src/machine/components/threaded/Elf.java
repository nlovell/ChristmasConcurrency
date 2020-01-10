package machine.components.threaded;

import machine.components.passive.Sack;

/**
 * Elves run as threads, and are assigned to empty the sacks when they're full.
 */
public class Elf implements Runnable{

    private final Sack elfSack;
    private final int replaceTime;
    private volatile boolean running = true;


    public Elf(final Sack elfSack, final int replaceTime){
        this.elfSack = elfSack;
        this.replaceTime = replaceTime * this.elfSack.getCapacity();
    }

    @Override
    public void run() {
        do {
            if (!elfSack.isSpace()) {
                try {
                    Thread.sleep(replaceTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                elfSack.replaceSack();
                //cout("Sack " + elfSack.getId() + " was full, and has been replaced by an elf.");

            }
        } while(running);
    }

    public void setStop(){
        running = false;
    }
}
