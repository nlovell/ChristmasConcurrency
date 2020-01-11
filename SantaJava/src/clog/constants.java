package clog;

public class constants {

        /*--------------------------------------------------------
       CLOG - the simple [C]onstant [LOG]ger, with log levels.
         Toggle these values between true/false to enable
         and disable different levels of detail in the
         console output of the program.
     --------------------------------------------------------*/

    /** The constant to enable CLOG logging for OUTPUT lines. */
    final static boolean TOGGLE_CLOG_OUTPUT = true;

    /** The constant to enable CLOG logging for DEBUG lines. */
    final static boolean TOGGLE_CLOG_DEBUG = true;

    /** The constant to enable CLOG logging for finer DEBUG lines. */
    final static boolean TOGGLE_CLOG_FINE_DEBUG = false;

    /** The constant to enable CLOG logging for ERROR lines. */
    final static boolean TOGGLE_CLOG_ERROR = false;

    /** The constant to enable CLOG logging for OBJECT lines. */
    final static boolean TOGGLE_CLOG_OBJECT = true;

    /** The constant to enable CLOG logging for PARSE lines. */
    final static boolean TOGGLE_CLOG_PARSE = true;

      /*--------------------------------------------------------
       The values to preface each string with:
     --------------------------------------------------------*/

    /** The constant value to preface an OUTPUT line with the CLOG clog.logger. */
    final static public String CLOG_OUTPUT  = "OUTPUT: ";

    /** The constant value to preface a DEBUG line with the CLOG clog.logger. */
    final static public String CLOG_DEBUG   = " DEBUG: ";

    /** The constant value to preface a DEBUG line with the CLOG clog.logger. */
    final static public String CLOG_FINE_DEBUG   = "FDEBUG: ";

    /** The constant value to preface an ERRORR line with the CLOG clog.logger. */
    final static public String CLOG_ERROR   = " ERROR: ";

    /** The constant value to preface an OBJECT line with the CLOG clog.logger. */
    final static public String CLOG_OBJECT  = "OBJECT: ";

    /** The constant value to preface a PARSE line with the CLOG clog.logger. */
    final static public String CLOG_PARSE   = " PARSE: ";
}
