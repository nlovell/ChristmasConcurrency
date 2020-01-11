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

    /*--------------------------------------------------------
       CLOG - the simple [C]onstant [LOG]ger, with log levels.
         Toggle these values between true/false to enable
         and disable different levels of detail in the
         console output of the program.
     --------------------------------------------------------*/

    /** The constant to enable CLOG logging for OUTPUT lines. */
    final static private boolean CLOG_OUTPUT_TOGGLE = true;

    /** The constant to enable CLOG logging for DEBUG lines. */
    final static private boolean CLOG_DEBUG_TOGGLE  = false;

    /** The constant to enable CLOG logging for ERROR lines. */
    final static private boolean CLOG_ERROR_TOGGLE  = false;

    /** The constant to enable CLOG logging for OBJECT lines. */
    final static private boolean CLOG_OBJECT_TOGGLE = true;

    /** The constant to enable CLOG logging for PARSE lines. */
    final static private boolean CLOG_PARSE_TOGGLE  = false;

      /*--------------------------------------------------------
       The values to preface each string with:
     --------------------------------------------------------*/

    /** The constant value to preface an OUTPUT line with the CLOG logger. */
    final static public String CLOG_OUTPUT  = "OUTPUT: ";

    /** The constant value to preface a DEBUG line with the CLOG logger. */
    final static public String CLOG_DEBUG   = " DEBUG: ";

    /** The constant value to preface an ERRORR line with the CLOG logger. */
    final static public String CLOG_ERROR   = " ERROR: ";

    /** The constant value to preface an OBJECT line with the CLOG logger. */
    final static public String CLOG_OBJECT  = "OBJECT: ";

    /** The constant value to preface a PARSE line with the CLOG logger. */
    final static public String CLOG_PARSE   = " PARSE: ";

    /**
     * Console Logging with basic levels.
     *
     * @param clog_level the clog level
     * @param input      the string to print
     */
    public static void clog(final String clog_level, final String input) {
        boolean output = false;
        switch (clog_level) {
            case CLOG_OUTPUT:
                if (CLOG_OUTPUT_TOGGLE)
                    output = true;
                break;
            case CLOG_DEBUG:
                if (CLOG_DEBUG_TOGGLE)
                    output = true;
                break;
            case CLOG_ERROR:
                if (CLOG_ERROR_TOGGLE)
                    output = true;
                break;
            case CLOG_OBJECT:
                if (CLOG_OBJECT_TOGGLE)
                    output = true;
                break;
            case CLOG_PARSE:
                if (CLOG_PARSE_TOGGLE)
                    output = true;
                break;
        }

        if(output){
            System.out.println(clog_level + input);
        }
    }
}