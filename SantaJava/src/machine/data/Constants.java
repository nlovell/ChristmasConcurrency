package machine.data;

/**
 * The type Constants.
 */
public class Constants {
    /**
     * The time taken to rotate a present on a turntable, in milliseconds.
     */
    final static public int ROTATE_TIME = 750;

    /**
     * The time taken to  move a present to or from a turntable, in milliseconds.
     */
    final static public int MOVE_TIME = 500;

    /**
     * The constant MIN_TIME.
     */
    final static public int MIN_TIME = Math.min(ROTATE_TIME, MOVE_TIME);

    /**
     * Cout.
     *
     * @param input the input
     */
    public static void cout(String input){
        System.out.println(input);
    }

    /**
     * Cout.
     *
     * @param input the input
     */
    public static void cout(int input){
        cout(String.valueOf(input));
    }
}
