package net.nlovell.machine;

import net.nlovell.machine.components.passive.Sack;
import net.nlovell.machine.data.Constants;

import java.util.Random;

import static net.nlovell.clog.LogConstants.CLOG_DEBUG;
import static net.nlovell.clog.Log.clogger;

/**
 * Elves run as threads, and are assigned to empty the sacks when they're full.
 */
public class Elf implements Runnable{

    private final Sack elfSack;
    private final int replaceTime;
    private volatile boolean running = true;
    private final String elfID;

    /**
     * Instantiates a new Elf.
     *
     * @param elfSack     the elf sack
     * @param replaceTime the replace time
     */
    public Elf(final Sack elfSack, final int replaceTime){
        this.elfSack = elfSack;
        this.replaceTime = replaceTime * this.elfSack.getCapacity();

        Random r = new Random();
        elfID = (char) (65 + r.nextInt(25)) + String.format("%03d", r.nextInt(999));
    }

    @Override
    public void run() {
        do {
            if (!elfSack.isSpace()) {
                try {
                    Thread.sleep(replaceTime/ Constants.SPEED_MULT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                elfSack.replaceSack();
                clogger(CLOG_DEBUG, "Sack " + elfSack.getId() + " was full, and has been replaced by an elf.");
            }
        } while(running);
    }

    /**
     * Set stop.
     */
    public void setStop(){
        running = false;
    }

    public String getElfID() {
        return elfID;
    }

}
