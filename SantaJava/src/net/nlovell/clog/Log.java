package net.nlovell.clog;

import static net.nlovell.clog.LogConstants.*;

public class Log {

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
            case LogConstants.CLOG_OUTPUT:
                if (TOGGLE_CLOG_OUTPUT)
                    output = true;
                break;
            case CLOG_DEBUG:
                if (TOGGLE_CLOG_DEBUG)
                    output = true;
                break;
            case CLOG_FINE_DEBUG:
                if (TOGGLE_CLOG_FINE_DEBUG)
                    output = true;
                break;
            case CLOG_ERROR:
                if (TOGGLE_CLOG_ERROR)
                    output = true;
                break;
            case CLOG_OBJECT:
                if (TOGGLE_CLOG_OBJECT)
                    output = true;
                break;
            case CLOG_PARSE:
                if (TOGGLE_CLOG_PARSE)
                    output = true;
                break;
        }

        if(output){
            System.out.println(clog_level + input);
        }
    }
}
