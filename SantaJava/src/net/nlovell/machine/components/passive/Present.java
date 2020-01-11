package net.nlovell.machine.components.passive;

import net.nlovell.machine.data.AgeRange;

/**
 * The type Present.
 */
public class Present {
    /**
     *
     */
    final private Sack[] suitableSacks;
    final private AgeRange ageRange;
    /**
     * The Hopper id.
     */
    final int hopperID;

    /**
     * Instantiates a new Present.
     *
     * @param suitableSacks the type of toy
     * @param ageRange      the agerange
     * @param hopperID      the hopper id
     */
    public Present(Sack[] suitableSacks, AgeRange ageRange, final int hopperID) {
        this.suitableSacks = suitableSacks;
        this.hopperID = hopperID;
        this.ageRange = ageRange;
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


    /**
     * Get hopper id int.
     *
     * @return the int
     */
    public int getHopperID(){
        return hopperID;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("|");
        for(Sack sack : suitableSacks){
            sb.append(sack.getId()).append("|");
        }
        return "Present{" +
                "ageRange=" + ageRange +
                "suitable sacks=" + sb +
                '}';
    }
}
