package machine.components.passive;

import machine.data.AgeRange;

/**
 * The type Present.
 */
public class Present {

    final private Sack[] suitableSacks;
    final private AgeRange ageRange;
    final int hopperID;

    /**
     * Instantiates a new Present.
     *  @param suitableSacks the type of toy
     * @param ageMin        the age min
     * @param ageMax        the age max
     * @param hopperID
     */
    public Present(Sack[] suitableSacks, int ageMin, int ageMax, final int hopperID) {
        this.suitableSacks = suitableSacks;
        this.hopperID = hopperID;
        this.ageRange = new AgeRange(ageMin, ageMax);
    }

    /**
     * Get age range.
     *
     * @return the int [ ]
     */
    public AgeRange getAgeRange(){
        return ageRange;
    }

    /**
     * Get suitable sacks sack [ ].
     *
     * @return the sack [ ]
     */
    public Sack[] getSuitableSacks() {
        return suitableSacks;
    }

    /**
     * Get suitable sack i ds string [ ].
     *
     * @return the string [ ]
     */
    public String[] getSuitableSackIDs(){
        String[] sacks = new String[suitableSacks.length];
        int i = 0;
        for(Sack sack : suitableSacks){
            sacks[i++] = sack.getId();
        }

        return sacks;
    }

    @Override
    public String toString() {
        return "Present{" +
                "ageRange=" + ageRange +
                '}';
    }

    public int getHopperID(){
        return hopperID;
    }

    public void deliver(){
        try {
            this.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
