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
     * Change how often the terminal outputs the results, in seconds.
     */
    final static public int OUTPUT_TIME = 5;

    /**
     * The constant MIN_TIME.
     */
    final static public int MIN_TIME = Math.min(ROTATE_TIME, MOVE_TIME);

    /**
     * Shortcut for System.out.println.
     *
     * @param input the input
     */
    public static void cout(String input) {
        System.out.println(input);
    }

    /**
     * Shortcut for System.out.println.
     *
     * @param input the input
     */
    public static void cout(int input) {
        cout(String.valueOf(input));
    }

    /**
     * Console Logging with basic levels
     *
     * @param input
     */
    public static void clog(final String input) {
        final String type = input.substring(0, CLOG_DEBUG.length());
        boolean output = false;
        switch (type) {
            case CLOG_DEBUG:
                if (CLOG_DEBUG_TOGGLE) output = true;
                break;
            case CLOG_OUTPUT:
                if (CLOG_OUTPUT_TOGGLE) output = true;
                break;
            case CLOG_ERROR:
                if (CLOG_ERROR_TOGGLE) output = true;
                break;
            case CLOG_OBJECT:
                if (CLOG_OBJECT_TOGGLE) output = true;
                break;
            case CLOG_PARSE:
                if (CLOG_PARSE_TOGGLE) output = true;
                break;
        }

        if(output){
            System.out.println(input);
        }
    }

    final static public String CLOG_DEBUG   = " DEBUG: ";
    final static public String CLOG_OUTPUT  = "OUTPUT: ";
    final static public String CLOG_ERROR   = " ERROR: ";
    final static public String CLOG_OBJECT  = "OBJECT: ";
    final static public String CLOG_PARSE   = " PARSE: ";

    final static private boolean CLOG_DEBUG_TOGGLE = false;
    final static private boolean CLOG_OUTPUT_TOGGLE = true;
    final static private boolean CLOG_ERROR_TOGGLE = false;
    final static private boolean CLOG_OBJECT_TOGGLE = false;
    final static private boolean CLOG_PARSE_TOGGLE = false;

}
