package machine.components.passive;

import machine.data.AgeRange;
import machine.data.*;

/**
 * The type Present.
 */
public class Present {

    final private Sack[] suitableSacks;
    final private AgeRange ageRange;

    /**
     * Instantiates a new Present.
     *
     * @param suitableSacks the type of toy
     * @param ageMin    the age min
     * @param ageMax    the age max
     */
    public Present(Sack[] suitableSacks, int ageMin, int ageMax) {
        this.suitableSacks = suitableSacks;
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

    public Sack[] getSuitableSacks() {
        return suitableSacks;
    }

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
}
