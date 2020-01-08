package parser;

import java.util.regex.Pattern;

public class Constants {
    /** Regex for finding Conveyor strings in the input file **/
    public static final Pattern conveyor=Pattern.compile("^([0-9]+) (?: ?length ([1-9]+)|:? ?destinations ([0-9 ]+))+$");

    /** Regex for finding Hopper strings in the input file **/
    public static final String hopper="^([0-9]+) (?: ?belt ([1-9])+| ?capacity ([0-9]+)| (?:speed ([0-9]+))){3}$";

    /** Regex for finding Sack strings in the input file **/
    public static final String sack="^([0-9]+)(?: ?capacity ([0-9]+)| ?age ([0-9\\-]+))+$";

    /** Regex for finding Turntable strings in the input file **/
    public static final String turntable="^([A-Z]+)( ?[NEWS] ((ib|ob|os|) [0-9]|null)){4}";
    public static final String turntableProp="([NEWS]) (ib|ob|os|null)(?: )([0-9])?";

    /** Regex for finding Other strings in the input file **/
    public static final String otherReg = "^([A-Z]+|[0-9]+$)";

    /** Regex for finding Present strings in the input file **/
    public static final String present="^PRESENTS ([0-9])(?:\\n?\\r?)?([0-9])((?:\\n?\\r?[0-9]+-[0-9]+)+)$";
    public static final String presentProp="^[0-9]+-[0-9]+$";

    /** Regex for validating a Windows filepath **/
    public static final String filePathValidator = "^[A-Za-z]:(?:(?:\\\\|\\/|\\.)(?:[A-Z0-9a-z]*)).*(?<!(\\\\|\\/| ))$";
}
