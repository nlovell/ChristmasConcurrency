package parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A way to parse standard UCan't
 * <p>
 * Author: [redacted]
 * Created: 20/12/2019
 */
public class FileParser {

    private boolean printEnabled;

    private ArrayList<String[]> conveyors = new ArrayList<>();
    private ArrayList<String[]> hoppers = new ArrayList<>();
    private ArrayList<String[]> presents = new ArrayList<>();
    private ArrayList<String[]> sacks = new ArrayList<>();
    private ArrayList<String[]> turntables = new ArrayList<>();


    /**
     * Instantiates a new File parser.
     *
     * @param file           the file
     * @param outputSource   boolean to enable output of the source file line-for-line to the console
     * @param enablePrinting boolean to enable log-style printing
     * @return an ArrayList of ArrayLists containing an Array of Strings defining the entire machine.
     */
    public ArrayList<ArrayList<String[]>> parseFile(String file,
                                                    final boolean outputSource,
                                                    final boolean enablePrinting) {
        this.printEnabled = enablePrinting;

        outputTitle();
        togglePrint("Attempting to parse file: " + file);

        String[] fileToParse = new String[0];
        try {
            fileToParse = openFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (outputSource) {
            togglePrint("----------------------------");
            togglePrint("Source file:\r\n");
            for (String lineToParse : fileToParse) {
                togglePrint("    " + lineToParse);
            }
            togglePrint("\r\nEnd of source.");
        }

        for (String lineToParse : fileToParse) {
            if (lineToParse != null)
                regexFilter(lineToParse);
        }

        ArrayList<ArrayList<String[]>> machine = new ArrayList<>();
        machine.add(conveyors);
        machine.add(hoppers);
        machine.add(sacks);
        machine.add(turntables);


        machine.add(presents);

        for (ArrayList<String[]> elem : machine) {
            for (String[] part : elem) {
                togglePrint(Arrays.toString(part));
            }
        }

        return machine;
    }

    /**
     * Accesses the file and reads it line-by-line into an of strings
     *
     * @param path the filepath for the file
     * @return an array of strings representing the file
     * @throws IOException for an unsuccessful attempt at reading a file
     */
    private String[] openFile(String path) throws IOException {
        int numberOfLines = countLines(path);
        String[] textData = new String[numberOfLines];

        try (FileReader fileReader = new FileReader(path);
             BufferedReader textReader = new BufferedReader(fileReader)) {
            //Reads lines into the array
            for (int i = 0; i < numberOfLines; i++) {
                textData[i] = textReader.readLine();
            }
            togglePrint("File length: " + numberOfLines);
        }
        return textData;

    }

    private int countLines(String path) throws IOException {
        int numberOfLines = 0;

        try (FileReader fileReader = new FileReader(path);
             BufferedReader textCounter = new BufferedReader(fileReader)) {

            //Gets the length of the file, in lines
            while (textCounter.readLine() != null) {
                numberOfLines++;
            }
        }
        return numberOfLines;
    }

    /**
     * Filters for each line
     *
     * @param theData the data to filter
     */
    private void regexFilter(String theData) {
        if (!Regexp.otherReg.matcher(theData).matches())
            togglePrint("----------------------------");
        if (Regexp.turntable.matcher(theData).matches()) {
            String[] turntable = parseTurntable(theData);
            turntables.add(turntable);
        } else if (Regexp.conveyor.matcher(theData).matches()) {
            String[] conv = parseConveyor(theData);
            conveyors.add(conv);
        } else if (Regexp.sack.matcher(theData).matches()) {
            String[] sack = parseSack(theData);
            sacks.add(sack);
        } else if (Regexp.hopper.matcher(theData).matches()) {
            String[] hopper = parseHopper(theData);
            hoppers.add(hopper);
        } else if (Regexp.present.matcher(theData).matches()) {
            parsePresent(theData);
        } else {
            if (!Regexp.otherReg.matcher(theData).matches())
            togglePrint("This data format is unrecognised.");
        }
    }

    /**
     * Parse conveyor string into it's composite attributes using regular expressions.
     *
     * @param conveyor the string defining a conveyor
     */
    private String[] parseConveyor(final String conveyor) {
        String[] conveyorDetails = new String[3];

        Matcher idMat = Regexp.conveyor.matcher(conveyor);
        idMat.find();
        togglePrint("Conveyor " + idMat.group(1) + " found in parsed machine.data.");
        conveyorDetails[0] = idMat.group(1);

        Pattern pattern = Regexp.conveyor;
        Matcher matcher = pattern.matcher(conveyor);

        while (matcher.find()) {
            togglePrint("      length: " + matcher.group(2));
            conveyorDetails[1] = matcher.group(2);
            togglePrint("destinations: " + Arrays.toString(stringArrayGenerator(matcher.group(3), " ")));
            conveyorDetails[2] = matcher.group(3);
        }
        togglePrint(Arrays.toString(conveyorDetails));
        return conveyorDetails;
    }

    /**
     * Parse hopper string into it's composite attributes using regular expressions.
     *
     * @param hopper the string defining a hopper
     */
    private String[] parseHopper(final String hopper) {
        String[] hopperDetails = new String[4];
        Matcher matcher = Regexp.hopper.matcher(hopper);
        while (matcher.find()) {
            togglePrint("Hopper " + matcher.group(1) + " found in parsed machine.data.");
            hopperDetails[0] = matcher.group(1);
            togglePrint("connected to: " + matcher.group(2));
            hopperDetails[1] = matcher.group(2);
            togglePrint("    capacity: " + matcher.group(3));
            hopperDetails[2] = matcher.group(3);
            togglePrint("       speed: " + matcher.group(4));
            hopperDetails[3] = matcher.group(4);
        }
        togglePrint(Arrays.toString(hopperDetails));
        return hopperDetails;
    }


    /**
     * Parse sack string into it's composite attributes using regular expressions.
     *
     * @param sack the string defining a sack
     */
    private String[] parseSack(final String sack) {
        String[] sackDetails = new String[3];
        Matcher matcher = Regexp.sack.matcher(sack);

        while (matcher.find()) {
            outputFound("Sack", matcher.group(1));
            sackDetails[0] = matcher.group(1);

            togglePrint("    capacity: " + matcher.group(2));
            sackDetails[1] = matcher.group(2);

            String[] ages = stringArrayGenerator(matcher.group(3), "-");
            togglePrint("        ages: " + Arrays.toString(ages));
            sackDetails[2] = matcher.group(3);
        }
        return sackDetails;
    }

    /**
     * Parse present string into it's composite attributes using regular expressions.
     *
     * @param turntable the string defining a turntable
     */
    private String[] parseTurntable(final String turntable) {
        String[] turntableDetails = new String[5];
        //N E S W

        Matcher idMat = Regexp.turntable.matcher(turntable);
        idMat.find();
        outputFound("Turntable", idMat.group(1));
        turntableDetails[0] = idMat.group(1);

        Matcher matcher = Regexp.turntableProp.matcher(turntable);

        while (matcher.find()) {
            togglePrint("-------------------");
            togglePrint(matcher.group());
            togglePrint(" orientation: " + matcher.group(1));
            togglePrint("        type: " + matcher.group(2));

            if (!matcher.group(2).equals("null")) {
                togglePrint("   output id: " + matcher.group(3));
                int out;
                switch(matcher.group(1)){
                    case "N":
                        out = 1;
                        break;
                    case "E":
                        out = 2;
                        break;
                    case "S":
                        out = 3;
                        break;
                    case "W":
                        out = 4;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + matcher.group(1));
                }
                turntableDetails[out] = matcher.group(2) + " " + matcher.group(3);
            }
        }
        return turntableDetails;
    }

    /**
     * Parse present string into it's composite attributes using regular expressions.
     *
     * @param present the string defining a turntable
     */
    private String[] parsePresent(final String present) {
        Matcher idMat = Regexp.present.matcher(present);
        idMat.find();
        outputFound("Present", idMat.group(1));

        Matcher matcher = Regexp.presentProp.matcher(present);

        while (matcher.find()) {
            togglePrint("-------------------");
            togglePrint(matcher.group());
            //togglePrint("   age range: " + matcher.group(1));
            //togglePrint("        type: " + matcher.group(2));

            //if (!matcher.group(2).equals("null"))
            //togglePrint("   output id: " + matcher.group(3));
        }
        return new String[0];
    }

    /**
     * Splits a string into an array, based on a single-character delimiter
     *
     * @param input     the input to delimit
     * @param delimiter the character to split the string on
     * @return the delimited string array
     */
    private String[] stringArrayGenerator(final String input, final String delimiter) {
        return input.split(delimiter);
    }

    /**
     * Outputs the title to the console. Totally unnecessary. But it looks pretty.
     */
    private void outputTitle() {
        togglePrint("\u001B[31m  _____ _ _        ___                            _   \r\n |  ___(_) | ___  "
                + "|_ _|_ __ ___  _ __   ___  _ __| |_ \n | |_  | | |/ _ \\  | || '_ ` _ \\| '_ \\ / _ \\| '__| __|\r\n"
                + " |  _| | | |  __/  | || | | | | | |_) | (_) | |  | |_ \n |_|   |_|_|\\___| |___|_| |_| |_| .__/ \\__"
                + "_/|_|   \\__|\n                                |_| \u001B[0m");
    }

    /**
     * Calls println if printEnabled is true
     *
     * @param printableLine the line to print
     */
    private void togglePrint(final String printableLine) {
        boolean shouldPrint = false;
        if (this.printEnabled) {
            shouldPrint = true;
        }

        if (shouldPrint)
            System.out.println(printableLine);
    }

    private void outputFound(String type, String id) {
        togglePrint(type + " " + id + " found in parsed data.");
    }
}