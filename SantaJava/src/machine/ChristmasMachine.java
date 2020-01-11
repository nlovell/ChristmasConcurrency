package machine;

import machine.components.MachinePart;
import machine.components.passive.Conveyor;
import machine.components.passive.Present;
import machine.components.passive.Sack;
import machine.components.threaded.Hopper;
import machine.components.threaded.Turntable;
import machine.data.Direction;
import machine.data.TurntableConnection;

import java.lang.reflect.Array;

import static clog.constants.*;
import static machine.data.Constants.OUTPUT_TIME;

/**
 * The type Christmas machine.
 */
public class ChristmasMachine {
    /**
     * The Session length.
     */
    private final int sessionLength;

    private Hopper[] hoppers;
    private Turntable[] turntables;
    private Conveyor[] conveyors;
    private Sack[] sacks;
    private Elf[] elves;
    private Present[] presents;

    /**
     * Instantiates a new Christmas machine.
     *
     * @param sessionLength the session length
     * @param conveyorData  the conveyor data
     * @param hopperData    the hopper data
     * @param presentData   the present data
     * @param sackData      the sack data
     * @param turntableData the turntable data
     */
    public ChristmasMachine(final int sessionLength,
                            final String[][] conveyorData,
                            final String[][] hopperData,
                            final String[][] presentData,
                            final String[][] sackData,
                            final String[][] turntableData) {

        clog.log.clogger(CLOG_OBJECT, "-----------New Christmas Machine established with the following elements-----------\n");

        this.sessionLength = sessionLength;

        sacks = makeSacks(sackData);
        elves = makeElves(sacks);
        presents = makePresents(presentData, sacks);

        conveyors = makeConveyors(conveyorData, sacks);
        hoppers = makeHoppers(hopperData, conveyors, presents);

        turntables = makeTurntables(turntableData, conveyors, sacks);

        clog.log.clogger(CLOG_OBJECT, "-----------------------------------------------------------------------------------\n");

    }
    //<editor-fold desc="Maker functions">

    /**
     * Make an array of sacks.
     *
     * @param sacks the String[][] defining a collection of sacks
     * @return the sack array[] to return
     */
    private Sack[] makeSacks(final String[][] sacks) {
        Sack[] arr = new Sack[sacks.length];
        int i = 0;

        for (String[] sack : sacks) {
            //cap id minmax
            int ageMin = Integer.parseInt(sack[2].split("-")[0]);
            int ageMax = Integer.parseInt(sack[2].split("-")[1]);

            arr[i] = new Sack(Integer.parseInt(sack[0]), Integer.parseInt(sack[1]), ageMin, ageMax);
            i++;
        }
        return arr;
    }

    private Elf[] makeElves(final Sack[] sacks) {
        Elf[] elves = new Elf[sacks.length];
        int i = 0;

        for (Sack sack : sacks) {
            elves[i] = new Elf(sack, 10);
            i++;
        }
        return elves;
    }


    /**
     * Make presents present [].
     *
     * @param presents the presents
     * @param sacks    the sacks
     * @return the present []
     */
    private Present[] makePresents(String[][] presents, Sack[] sacks) {
        for(String[] present : presents) {
            clog.log.clogger(CLOG_DEBUG, present.toString());
        }
        return null;
    }

    /**
     * Make conveyors conveyor [ ].
     *
     * @param conveyors the conveyors
     * @param mySacks   the my sacks
     * @return the conveyor [ ]
     */
    private Conveyor[] makeConveyors(final String[][] conveyors, final Sack[] mySacks) {
        Conveyor[] arr = new Conveyor[conveyors.length];

        int i = 0;

        for (String[] conv : conveyors) {
            Sack[] convSacks = getParts(conv[2].split(" "), mySacks, Sack.class);
            arr[i] = new Conveyor(conv[0], Integer.parseInt(conv[1]), convSacks);
            clog.log.clogger(CLOG_OBJECT, arr[i].toString() + '\n');

            i++;
        }

        return arr;
    }


    /**
     * Make hoppers hopper [ ].
     *
     * @param hoppers   the hoppers
     * @param conveyors the conveyors
     * @param presents  the presents
     * @return the hopper [ ]
     */
    private Hopper[] makeHoppers(String[][] hoppers, Conveyor[] conveyors, Present[] presents) {
        //TODO: make hoppers use actual gifts
        Hopper[] arr = new Hopper[hoppers.length];

        int i = 0;

        for (String[] hopper : hoppers) {

            arr[i] = new Hopper(Integer.parseInt(hopper[0]), getPart(hopper[1], conveyors),
                    Integer.parseInt(hopper[2]), Integer.parseInt(hopper[3]));

            clog.log.clogger(CLOG_OBJECT, arr[i].toString() + '\n');
            i++;
        }

        return arr;
    }

    /**
     * Make turntables turntable [ ].
     *
     * @param turntables the turntables
     * @param belts      the conveyors
     * @param sacks      the sacks
     * @return the turntable [ ]
     */
    private Turntable[] makeTurntables(String[][] turntables, Conveyor[] belts, Sack[] sacks) {
        Turntable[] arr = new Turntable[turntables.length];

        int i = 0;

        for (String[] turntable : turntables) {
            TurntableConnection[] conns = new TurntableConnection[4];
            //NESW
            for (int j = 1; j <= 4; j++) {
                if (turntable[j] != null) {
                    Direction dir = Direction.values()[j - 1];
                    String getVal = turntable[j].split(" ")[1];

                    if (turntable[j].split(" ")[0].equals("os")) {
                        conns[j - 1] = new TurntableConnection(dir, getPart(getVal, sacks));
                    } else {
                        boolean input = !turntable[j].split("b")[0].equals("o");
                        conns[j - 1] = new TurntableConnection(dir, input, getPart(getVal, belts));
                    }
                }
            }

            arr[i] = new Turntable(turntable[0], conns);

            clog.log.clogger(CLOG_OBJECT, arr[i].toString() + '\n');
            i++;
        }

        return arr;
    }

    //</editor-fold>


    /**
     * Converts current unix time in ms, to a timestamp as a string
     * @return a HH:MM:SS timestamp
     */
    private static String timestamp() {
        long seconds = System.currentTimeMillis() / 1000;
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60)) % 24;
        return String.format("%d:%02d:%02d", h, m, s);
    }

    /**
     * Kicks off the operation of the machine.
     */
    public void runMachine() {
        startMachine();

        clog.log.clogger(CLOG_OUTPUT, " Session begins at " + timestamp() +
                ", and will last for " + sessionLength + " seconds.");
        long startTime = System.currentTimeMillis();

        int timer = 0;
        final int target = 1000;
        //A simple timer loop of variable length
        do {
            try {
                Thread.sleep(target);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timer++;
            if (timer % OUTPUT_TIME == 0) {
                timedLogger(startTime);
            }
        } while (timer < sessionLength);

        clog.log.clogger(CLOG_OUTPUT, " Session over at " + timestamp() + '\n');
        long hopperStopTime = System.currentTimeMillis();
        clog.log.clogger(CLOG_OUTPUT, " Halting hopper outputs, and waiting for system to tidy up. \n");

        endMachine();

        clog.log.clogger(CLOG_OUTPUT, " Session over!");
        long endTime = System.currentTimeMillis();

        clog.log.clogger(CLOG_OUTPUT, " System totally halted at " + timestamp() + " (" + (endTime - hopperStopTime) + "ms after stop command)");
        clog.log.clogger(CLOG_OUTPUT, " " + (String.format("Total time: %ds", (0L + (endTime - startTime) / 1000))));

        timedLogger(endTime);
    }

    /**
     *
     */
    private void startMachine() {

        for (Hopper hopper : hoppers) {
            new Thread(hopper).start();
        }
        for (Turntable turntable : turntables) {
            new Thread(turntable).start();
        }

        StringBuilder elfString = new StringBuilder();
        if(elves.length == 1){
            elfString.append("Meet the Elf running this machine - it's ").append(elves[0].getElfID());
        } else {
            elfString.append("Meet the Elves running this machine - there's ");

            for (Elf elf : elves) {
                if (elf != elves[elves.length - 1])
                    elfString.append(elf.getElfID()).append(", ");
                else
                    elfString.append("and ").append(elf.getElfID()).append("!");

                new Thread(elf).start();
            }
        }

        clog.log.clogger(CLOG_OUTPUT, " " + elfString);
    }

    /**
     *
     */
    private void endMachine() {
        for (Hopper hopper : hoppers) {
            hopper.setStop();
        }

        int remaining = giftsInSystem();
        while (remaining > 0) {

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int temp = giftsInSystem();
            if (remaining > temp) {
                remaining = temp;
                clog.log.clogger(CLOG_OUTPUT, remaining + " unsorted gifts are present in the system.");
            }
        }

        for (Turntable turntable : turntables) {
            turntable.setStop();
        }

        for (Elf elf : elves) {
            elf.setStop();
        }
    }

    /**
     *
     * @param startTime the time the machine started
     */
    private void timedLogger(Long startTime) {
        clog.log.clogger(CLOG_OUTPUT, " Output time - " + timestamp() + " (" + timeSince(startTime) + "ms since start)");
        int giftCount = 0;
        for (Hopper hopper : hoppers) {
            giftCount = giftCount + hopper.getCurrent();
        }

        clog.log.clogger(CLOG_OUTPUT, "               Hoppers cumulatively contain " + giftCount + " gifts.");
        giftCount = 0;

        for (Sack sack : sacks) {
            giftCount = giftCount + sack.getLifetimeTotal();
        }
        clog.log.clogger(CLOG_OUTPUT, "               " + giftCount + " presents have been deposited into sacks.\n");
    }

    /**
     * Time since string.
     *
     * @param time the original time to reference
     * @return the time in milliseconds since param time
     */
    private long timeSince(long time) {
        return System.currentTimeMillis() - time;
    }


    //<editor-fold desc="Part getters">

    /**
     * Gets a part.
     *
     * @param <P>   the type parameter
     * @param id    the id of the part
     * @param parts the array of parts to search in
     * @return the part
     */
    private static <P extends MachinePart> P getPart(final String id, final P[] parts) {

        for (P part : parts) {
            if (part.getId().equals(id)) {
                return part;
            }
        }

        return null;
    }

    /**
     * Get an array that references specific parts of the machine based on ID and class-type.
     *
     * @param ids the array of IDs of the elements
     * @param parts the array of parts to search in
     * @param clazz the class type of the parts
     * @param <P> the type parameter
     * @return an array of MachineParts
     */
    private <P extends MachinePart> P[] getParts(final String[] ids, final P[] parts, Class<P> clazz) {

        @SuppressWarnings("unchecked") final P[] output = (P[]) Array.newInstance(clazz, ids.length);

        //MachinePart[] output = new MachinePart[ids.length];
        int i = 0;

        for (P part : parts) {
            for (String id : ids) {
                if (part.getId().equals(id)) {
                    output[i] = part;
                    i++;
                    break;
                }
            }
        }

        return output;
    }

    /**
     * Get the number of remaining gifts in the Christmas Machine.
     * @return the number of gifts in the Christmas Machine
     */
    private int giftsInSystem() {
        int remaining = 0;

        for (Conveyor conveyor : conveyors) {
            remaining = remaining + conveyor.giftsInConveyor();
        }

        for (Turntable turntable : turntables) {
            if (turntable.hasPresent()) {
                remaining++;
            }
        }

        return remaining;
    }
    //</editor-fold>
}
