package machine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChristmasMachine {
    int sessionLength;
    int session = 0;
    public String testString = "this is a test";



    public ChristmasMachine(int sessionLength,
                            String[][] conveyors,
                            int[][] hoppers,
                            String[][] presents,
                            String[][] sacks,
                            String[][] turntables) {

        makeConveyors(conveyors);
        makeHoppers(hoppers);
        makeSacks(sacks);
        makePresents(presents);
        makeTurntables(turntables);
    }

    public void makeConveyors(String[][] conveyors){}
    public void makeHoppers( int[][] hoppers){}
    void makePresents(String[][] presents){}
    void makeSacks(String[][] sacks){}
    void makeTurntables(String[][] turntables){}

    public void establishMachine(){


        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Session begins!");
        long a  = System.currentTimeMillis();

        //A simple timer loop of variable length
        for(session = 0; session < sessionLength; session++)
        {
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

    public void setSessionLength(int session) {
        this.sessionLength = session;
    }


//TODO  The simulation should run for one “session” – the length of the session representing the time set on the
//      machine by the elf. At the end of the session, the hoppers should immediately cease adding Presents to the
//      input hoppers. Ideally, any Presents currently in the machine should continue to be sorted into the
//      appropriate sack if this is possible, before the machine finally shuts down.

}
