package machine;

import machine.components.passive.Conveyor;
import machine.components.passive.Present;
import machine.components.passive.Sack;
import machine.components.threaded.Hopper;
import machine.components.threaded.Turntable;

import java.io.IOException;

public class ChristmasMachine {
    final int sessionLength;
    int session = 0;

    public ChristmasMachine(final int sessionLength,
                            final String[][] conveyorData,
                            final int[][] hopperData,
                            final String[][] presentData,
                            final String[][] sackData,
                            final String[][] turntableData) {
        this.sessionLength = sessionLength;

        Sack[] sacks = makeSacks(sackData);
        Present[] presents = makePresents(presentData, sacks);
        Conveyor[] conveyors = makeConveyors(conveyorData, sacks);
        Hopper[] hoppers = makeHoppers(hopperData, conveyors, presents);
        makeTurntables(turntableData, conveyors, sacks);
    }

    Sack[] makeSacks(final String[][] sacks) {

        return null;
    }

    Conveyor[] makeConveyors(final String[][] conveyors, final Sack[] mySacks) {
        Conveyor[] arr = new Conveyor[conveyors.length];

        int i = 0;

        for(String[] conv : conveyors){
            Sack[] convSacks = getParts(conv[3].split(" "), mySacks);
            arr[i] = new Conveyor(conv[0], Integer.parseInt(conv[1]), convSacks);
            i++;
        }

        return arr;
    }

    private <P extends MachinePart> P[] getParts(final String[] ids, final P[] parts) {

        MachinePart[] output = new MachinePart[ids.length];
        int i = 0;

        for(P part : parts){
            for(String id : ids) {
                if(part.getId().equals(id)){
                    output[i] = part;
                    i++;
                    break;
                }
            }
        }

        return (P[]) output;
    }

    Hopper[] makeHoppers(int[][] hoppers, Conveyor[] conveyors, Present[] presents) {
        return new Hopper[0];
    }

    Present[] makePresents(String[][] presents, Sack[] sacks) {
        return null;
    }

    Turntable[] makeTurntables(String[][] turntables, Conveyor[] conveyors, Sack[] sacks) {
        return null;
    }


/*
    public void establishMachine() {


        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Session begins!");
        long a = System.currentTimeMillis();

        //A simple timer loop of variable length
        for (session = 0; session < sessionLength; session++) {
            System.out.println(session);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Session over!");
        long b = System.currentTimeMillis();
        long c = b - a;

        System.out.println(b + " - " + a + " = " + c);
    }


 */

//TODO  The simulation should run for one “session” – the length of the session representing the time set on the
//      machine by the elf. At the end of the session, the hoppers should immediately cease adding Presents to the
//      input hoppers. Ideally, any Presents currently in the machine should continue to be sorted into the
//      appropriate sack if this is possible, before the machine finally shuts down.

}
