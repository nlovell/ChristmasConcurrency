package machine;

import machine.components.MachinePart;
import machine.components.passive.Conveyor;
import machine.components.passive.Present;
import machine.components.passive.Sack;
import machine.components.threaded.Hopper;
import machine.components.threaded.Turntable;
import machine.data.Constants;

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
    }

    private void startStuff(){
        for(Hopper hopper : hoppers) {
            hopper.run();
        }
        for(Turntable turntable : turntables){
            turntable.run();
        }

        Constants.cout("Session begins!");
        long a = System.currentTimeMillis();

        //A simple timer loop of variable length
        /**
         * The Session.
         */
        int session = 0;
        for (session = 0; session < sessionLength; session++) {
            Constants.cout(String.valueOf(session));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for(Hopper hopper : hoppers) {
            hopper.setStop();
        }

        int remaining = giftsInSystem();
        while(remaining > 0){
            try {
                Thread.sleep(MIN_TIME);
                Constants.cout(remaining + "unsorted gifts are present in the system.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        for(Turntable turntable : turntables){
            turntable.setStop();
        }

        Constants.cout("Session over!");
        long b = System.currentTimeMillis();
        long c = b - a;
    }

    private int giftsInSystem() {
        int remaining = 0;

        for(Conveyor conveyor : conveyors){
            remaining = remaining + conveyor.giftsInConveyor();
        }

        for(Turntable turntable : turntables){
            if(turntable.hasPresent()){
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
            Sack[] convSacks = getParts(conv[3].split(" "), mySacks);
            arr[i] = new Conveyor(conv[0], Integer.parseInt(conv[1]), convSacks);
            i++;
        }

        return arr;
    }

    private <P extends MachinePart> P[] getParts(final String[] ids, final P[] parts) {

        MachinePart[] output = new MachinePart[ids.length];
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

        return (P[]) output;
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
        return new Hopper[0];
    }

    /**
     * Make presents present [ ].
     *
     * @param presents the presents
     * @param sacks    the sacks
     * @return the present [ ]
     */
    private Present[] makePresents(String[][] presents, Sack[] sacks) {
        return null;
    }

    /**
     * Make turntables turntable [ ].
     *
     * @param turntables the turntables
     * @param conveyors  the conveyors
     * @param sacks      the sacks
     * @return the turntable [ ]
     */
    private Turntable[] makeTurntables(String[][] turntables, Conveyor[] conveyors, Sack[] sacks) {
        return null;
    }


/*
    public void establishMachine() {


        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        conout("Session begins!");
        long a = System.currentTimeMillis();

        //A simple timer loop of variable length
        for (session = 0; session < sessionLength; session++) {
            conout(session);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        conout("Session over!");
        long b = System.currentTimeMillis();
        long c = b - a;

        conout(b + " - " + a + " = " + c);
    }


 */

//TODO  The simulation should run for one “session” – the length of the session representing the time set on the
//      machine by the elf. At the end of the session, the hoppers should immediately cease adding Presents to the
//      input hoppers. Ideally, any Presents currently in the machine should continue to be sorted into the
//      appropriate sack if this is possible, before the machine finally shuts down.

}
