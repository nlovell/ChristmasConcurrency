package net.nlovell.machine;

import net.nlovell.machine.components.MachinePart;
import net.nlovell.machine.components.passive.Conveyor;
import net.nlovell.machine.components.passive.Present;
import net.nlovell.machine.components.passive.Sack;
import net.nlovell.machine.components.threaded.Hopper;
import net.nlovell.machine.components.threaded.Turntable;
import net.nlovell.machine.data.AgeRange;
import net.nlovell.machine.data.Direction;
import net.nlovell.machine.data.TurntableConnection;
import net.nlovell.machine.data.Constants;

import java.lang.reflect.Array;
import java.util.Arrays;

import static net.nlovell.clog.LogConstants.*;
import static net.nlovell.clog.Log.clogger;

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

        clogger(CLOG_OBJECT, "-----------New Christmas Machine established with the following elements-----------\n");

        this.sessionLength = sessionLength;

        sacks = makeSacks(sackData);
        elves = makeElves(sacks);
        presents = makePresents(presentData, sacks);

        conveyors = makeConveyors(conveyorData, sacks);
        hoppers = makeHoppers(hopperData, conveyors, presents);

        turntables = makeTurntables(turntableData, conveyors, sacks);

        clogger(CLOG_OBJECT, "-----------------------------------------------------------------------------------\n");

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
            clogger(CLOG_OBJECT, arr[i].toString());

            i++;
        }
        return arr;
    }

    private Elf[] makeElves(final Sack[] sacks) {
        Elf[] elves = new Elf[sacks.length];
        int i = 0;

        for (Sack sack : sacks) {
            elves[i] = new Elf(sack, 10);
            clogger(CLOG_OBJECT, elves[i].toString());
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
        Present[] arr = new Present[presents.length];
        int i = 0;
        //id min max
        for (String[] gift : presents) {
            AgeRange age = new AgeRange(Integer.parseInt(gift[1]), Integer.parseInt(gift[2]));
            clogger(CLOG_FINE_DEBUG, "Target age: " + age);

            Sack[] mySacks = new Sack[0];
            int j = 0;
            for(Sack sack : sacks){
                AgeRange sackAge = sack.getAges();
                clogger(CLOG_FINE_DEBUG,   "Sack " + sack.getId() + " has sack age: " + sackAge );
                boolean test = sackAge.contains(age);
                if(test){
                    mySacks = Arrays.copyOf(mySacks, j+1);
                    mySacks[j] = sack;
                    j++;
                }
            }
            int obs = mySacks.length;
            if(mySacks.length > 0) {
                arr[i] = new Present(mySacks, age, Integer.parseInt(gift[0]));
                clogger(CLOG_OBJECT, arr[i].toString());
                i++;
            } else {
                clogger(CLOG_ERROR, "Invalid data provided. Present was not made with this data: " + gift.toString());
            }
        }
        return arr;
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
            clogger(CLOG_OBJECT, arr[i].toString());
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
        Hopper[] arr = new Hopper[hoppers.length];

        int i = 0;

        for (String[] hopper : hoppers) {
            int hopperID = Integer.parseInt(hopper[0]);
            Present[] gifts = new Present[Integer.parseInt(hopper[2])];
            int j = 0;
            for (Present gift : presents) {
                if(gift == null){
                    clogger(CLOG_ERROR, "Null gift tried to be added to new hoppper. Skipping.");
                } else if (gift.getHopperID() == hopperID) {
                    gifts[j] = gift;
                    j++;
                }
            }

            arr[i] = new Hopper(hopperID, getPart(hopper[1], conveyors),
                    gifts.length, Integer.parseInt(hopper[3]), gifts);

            clogger(CLOG_OBJECT, arr[i].toString());
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

            clogger(CLOG_OBJECT, arr[i].toString());
            i++;
        }

        return arr;
    }

    //</editor-fold>


    /**
     * Converts current unix time in ms, to a timestamp as a string
     *
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

        clogger(CLOG_OUTPUT, " Session begins at " + timestamp() +
                ", and will last for " + sessionLength + " seconds.");
        long startTime = System.currentTimeMillis();

        int timer = 0;
        final int target = 1000;
        //A simple timer loop of variable length
        do {
            try {
                Thread.sleep(target/ Constants.SPEED_MULT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timer++;
            if (timer % Constants.OUTPUT_TIME == 0) {
                timedLogger(startTime);
            }
        } while (timer < sessionLength);

        clogger(CLOG_OUTPUT, " Session over at " + timestamp() + '\n');
        long hopperStopTime = System.currentTimeMillis();
        clogger(CLOG_OUTPUT, " Halting hopper outputs, and waiting for system to tidy up. \n");

        endMachine();

        clogger(CLOG_OUTPUT, " Session over!");
        long endTime = System.currentTimeMillis();

        clogger(CLOG_OUTPUT, " System totally halted at " + timestamp() + " (" + (endTime - hopperStopTime) + "ms after stop command)");
        clogger(CLOG_OUTPUT, " " + (String.format("Total time: %ds", ((endTime - startTime) / 1000L))));

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
        if (elves.length == 1) {
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

        clogger(CLOG_OUTPUT, " " + elfString);
    }

    /**
     *
     */
    private void endMachine() {
        for (Hopper hopper : hoppers) {
            hopper.setStop();
        }

        int remaining = giftsInSystem();
        clogger(CLOG_OUTPUT, remaining + " unsorted gifts are present in the system.");
        while (remaining > 0) {

            try {
                Thread.sleep(100/ Constants.SPEED_MULT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int temp = giftsInSystem();
            if (remaining > temp) {
                remaining = temp;
                clogger(CLOG_OUTPUT, remaining + " unsorted gifts are present in the system.");
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
     * @param startTime the time the machine started
     */
    private void timedLogger(Long startTime) {
        clogger(CLOG_OUTPUT, " Output time - " + timestamp() + " (" + timeSince(startTime) + "ms since start)");
        int giftCount = 0;
        for (Hopper hopper : hoppers) {
            giftCount = giftCount + hopper.getCurrent();
        }

        clogger(CLOG_OUTPUT, "               Hoppers cumulatively contain " + giftCount + " gifts.");
        giftCount = 0;

        for (Sack sack : sacks) {
            giftCount = giftCount + sack.getLifetimeTotal();
        }
        clogger(CLOG_OUTPUT, "               " + giftCount + " presents have been deposited into sacks.\n");
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
     * @param ids   the array of IDs of the elements
     * @param parts the array of parts to search in
     * @param clazz the class type of the parts
     * @param <P>   the type parameter
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
     * Get the number of remaining gifts in transit within the Christmas Machine.
     *
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
