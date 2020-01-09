package machine.components;

/**
 * The type Machine part.
 */
public abstract class MachinePart {
    @Override
    public String toString() {
        return "MachinePart{" +
                "ID='" + ID + '\'' +
                '}';
    }

    /**
     * The Id.
     */
    final String ID;

    /**
     * Instantiates a new Machine part.
     *
     * @param id the id
     */
    public MachinePart(final String id) {
        this.ID = id;
    }

    /**
     * Instantiates a new Machine part.
     *
     * @param id the id
     */
    public MachinePart(final int id){
        this.ID = String.valueOf(id);
    }

    /**
     * Get id string.
     *
     * @return the string
     */
    public String getId(){
        return ID;
    }
}