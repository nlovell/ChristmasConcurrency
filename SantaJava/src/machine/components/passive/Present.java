package machine.components.passive;

import data.AgeRange;

/**
 * The type Present.
 */
public class Present {

    final private String typeOfToy;
    final private AgeRange ageRange;

    /**
     * Instantiates a new Present.
     *
     * @param typeOfToy the type of toy
     * @param ageMin    the age min
     * @param ageMax    the age max
     */
    public Present(String typeOfToy, int ageMin, int ageMax) {
        this.typeOfToy = typeOfToy;
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
     * Gets type of toy.
     *
     * @return the type of toy
     */
    public String getTypeOfToy() {
        return typeOfToy;
    }

    //TODO  Present objects are created at the start of the simulation and loaded into the hopper to simulate the
    //      toys to be distributed
    //TODO  As well as constructor and destructor methods, a Present must have a method to allow the Sorting
    //      machine to read its destination
}
