package machine;

import machine.components.MachinePart;
import machine.components.passive.Conveyor;
import machine.components.passive.Present;
import machine.components.passive.Sack;
import machine.components.threaded.Hopper;
import machine.components.threaded.Turntable;
import machine.data.Constants;
import machine.data.Direction;
import machine.data.TurntableConnection;

import java.lang.reflect.Array;

import static machine.data.Constants.MIN_TIME;
import static machine.data.Constants.cout;

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
        this.sessionLength = sessionLength;

        Sack[] sacks = makeSacks(sackData);
        Present[] presents = makePresents(presentData, sacks);

        conveyors = makeConveyors(conveyorData, sacks);
        hoppers = makeHoppers(hopperData, conveyors, presents);

        turntables = makeTurntables(turntableData, conveyors, sacks);

        System.out.println("------------- ~fin~ -------------");

    }

    public void startStuff() {
        for (Hopper hopper : hoppers) {
            new Thread(hopper).start();
        }
        for (Turntable turntable : turntables) {
            turntable.run();
        }

        Constants.cout("Session begins!");
        long a = System.currentTimeMillis();

        //A simple timer loop of variable length
        for (int session = 0; session < sessionLength; session++) {
            Constants.cout(String.valueOf(session));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (Hopper hopper : hoppers) {
            hopper.setStop();
        }

        int remaining = giftsInSystem();
        while (remaining > 0) {
            try {
                Thread.sleep(MIN_TIME);
                Constants.cout(remaining + "unsorted gifts are present in the system.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        for (Turntable turntable : turntables) {
            turntable.setStop();
        }

        Constants.cout("Session over!");
        long b = System.currentTimeMillis();
        long c = b - a;
    }

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

    /**
     * Make sacks sack [ ].
     *
     * @param sacks the sacks
     * @return the sack [ ]
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
            System.out.println(arr[i].toString() + '\n');

            i++;

        }

        return arr;
    }

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

    private <P extends MachinePart> P getPart(final String id, final P[] parts) {

        for (P part : parts) {
            if (part.getId().equals(id)) {
                return part;
            }
        }

        return null;
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

            arr[i] = new Hopper(Integer.parseInt(hopper[0]), getPart(hopper[1], conveyors),
                    Integer.parseInt(hopper[2]), Integer.parseInt(hopper[3]));

            System.out.println(arr[i].toString() + '\n');
            i++;
        }

        return arr;
    }

    /**
     * Make presents present [ ].
     *
     * @param presents the presents
     * @param sacks    the sacks
     * @return the present [ ]
     */
    private Present[] makePresents(String[][] presents, Sack[] sacks) {
        //TODO make gifts
        return null;
    }

    /**
     * Make turntables turntable [ ].
     *
     * @param turntables the turntables
     * @param belts  the conveyors
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

            System.out.println(arr[i].toString() + '\n');
            i++;
        }

        return arr;
    }

//TODO  The simulation should run for one “session” – the length of the session representing the time set on the
//      machine by the elf. At the end of the session, the hoppers should immediately cease adding Presents to the
//      input hoppers. Ideally, any Presents currently in the machine should continue to be sorted into the
//      appropriate sack if this is possible, before the machine finally shuts down.

}
