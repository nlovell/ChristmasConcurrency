package parser;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

/**
 * The type Regexp.
 */
public class Regexp {
    /**
     * Regex for finding Conveyor strings in the input file
     */
    public static final Pattern conveyor =
            compile("^([0-9]+) (?: ?length ([1-9]+)|:? ?destinations ([0-9 ]+))+$");

    /**
     * Regex for finding Hopper strings in the input file
     */
    public static final Pattern hopper =
            compile("^([0-9]+) (?: ?belt ([1-9])+| ?capacity ([0-9]+)| (?:speed ([0-9]+))){3}$");

    /**
     * Regex for finding Sack strings in the input file
     */
    public static final Pattern sack =
            compile("^([0-9]+)(?: ?capacity ([0-9]+)| ?age ([0-9\\-]+))+$");

    /**
     * Regex for finding Turntable strings in the input file
     */
    public static final Pattern turntable =
            compile("^([A-Z]+)( ?[NEWS] ((ib|ob|os|) [0-9]|null)){4}");


    /**
     * The constant turntableProp.
     */
    public static final Pattern turntableProp =
            compile("([NEWS]) (ib|ob|os|null)(?: )([0-9])?");

    /**
     * Regex for finding Other strings in the input file
     */
    public static final Pattern otherReg = compile("^([A-Z]+|[0-9]+$)");

    /**
     * Regex for finding Present strings in the input file
     */
    public static final Pattern present =
            compile("^PRESENTS ([0-9])(?:\\n?\\r?)?([0-9])((?:\\n?\\r?[0-9]+-[0-9]+)+)$");

    /**
     * The constant presentProp.
     */
    public static final Pattern presentProp =
            compile("^[0-9]+-[0-9]+$");

    /**
     * Regex for validating a Windows filepath
     */
    public static final Pattern filePathValidator =
            compile("^[A-Za-z]:(?:[\\\\/.](?:[A-Z0-9a-z]*)).*(?<!([\\\\/ ]))$");
}
