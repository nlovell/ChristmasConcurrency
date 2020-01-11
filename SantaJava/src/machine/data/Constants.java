package machine.data;

/**
 * The type Constants.
 */
public class Constants {
    /** The time taken to rotate a present on a turntable, in milliseconds. */
    final static public int ROTATE_TIME = 750;

    /**
     * Increase this number to make the machine run faster.
     * Any value other than 1 may cause timing issues.
     * */
    final static public int SPEED_MULT = 1;

    /** The time taken to  move a present to or from a turntable, in milliseconds. */
    final static public int MOVE_TIME = 500;

    /** Change how often the terminal outputs the results, in seconds. */
    final static public int OUTPUT_TIME = 5;

    /** The constant MIN_TIME. */
    final static public int MIN_TIME = Math.min(ROTATE_TIME, MOVE_TIME);
}