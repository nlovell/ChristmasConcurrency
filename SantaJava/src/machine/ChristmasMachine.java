package machine;

import java.io.IOException;

public class ChristmasMachine {
    int sessionLength;
    int session = 0;
    public String testString = "this is a test";
    public Hopper[] hoppers = new Hopper[3];
    private static ChristmasMachine machine = null;

    public static ChristmasMachine getInstance()
    {
        if (machine == null)
            machine = new ChristmasMachine();

        return machine;
    }

    private ChristmasMachine() {
        this.sessionLength = sessionLength;
        System.out.println("Length: " + this.sessionLength);

        //initialise hoppers
        hoppers[0] = new Hopper(1, 2,3, 4);
        hoppers[1] = new Hopper(9, 8,7, 6);
        hoppers[2] = new Hopper(2, 8,7, 6);

        hoppers[0].run();
        hoppers[1].run();
        hoppers[2].run();


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
