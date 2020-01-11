package clog;

import static clog.constants.*;

public class log {

    /*--------------------------------------------------------
       CLOG - the simple [C]onstant [LOG]ger, with log levels.
         Toggle these values between true/false to enable
         and disable different levels of detail in the
         console output of the program.
     --------------------------------------------------------*/

    /**
     * Console Logging with basic levels.
     *
     * @param clog_level the clog level
     * @param input      the string to print
     */
    public static void clogger(final String clog_level, final String input) {
        boolean output = false;
        switch (clog_level) {
            case constants.CLOG_OUTPUT:
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
