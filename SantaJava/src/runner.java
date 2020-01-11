import machine.ChristmasMachine;
import parser.FileParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static machine.data.Constants.*;
import static parser.Regexp.filePathValidator;

/**
 * The Runner class and the Parser classes aren't part of the actual "machine", so while they have access to
 * non-static arrays (eg arraylists) - these are not exposed to the classes related to the machine. They are parsed
 * into static arrays before being passed there.
 * <p>
 * How to run:
 * From the source directory, the following command can be used to run the Runner and log the output to a file
 * <p>
 * SantaJava\src> javac runner.java
 * SantaJava\src> java runner > output.txt
 * <p>
 * The resulting output file will contain every line normally output to the console.
 */
public class runner {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {

        String fileToParse = "";

        //Comment these lines out to override the default example.
        final String testfiledir =  "D:/repositories/Concurrency1/SantaJava/src/test_files/";
        fileToParse = testfiledir + "example_2.txt";
        // Or fill in your own value(s) here to automatically skip manual filename entry at runtime


        while (!filePathValidator.matcher(fileToParse).find()) {
            fileToParse = consoleReader();
            clog(CLOG_PARSE, fileToParse);
            assert fileToParse != null;
            clog(CLOG_PARSE, "Is input valid?: " + filePathValidator.matcher(fileToParse).find());
        }

        FileParser fp = new FileParser();
        ArrayList<ArrayList<String[]>> output = fp.parseFile(fileToParse, true, false);

        String[][] presents = new String[1][1];
        String[][] conveyors = parseArrayList(output.get(0), 3);
        String[][] hoppers = parseArrayList(output.get(1), 4);
        String[][] sacks = parseArrayList(output.get(2), 3);
        String[][] turntables = parseArrayList(output.get(3), 5);
        int timer = 30;

        ChristmasMachine machine = new ChristmasMachine(timer, conveyors, hoppers, presents, sacks, turntables);
        machine.runMachine();
    }

    private static void getAllFiles(File curDir) {

        File[] filesList = curDir.listFiles();
        for(File f : filesList){
            if(f.isDirectory())
                System.out.println(f.getName());
            if(f.isFile()){
                System.out.println(f.getName());
            }
        }

    }

    /**
     * Parse array list string [ ] [ ].
     *
     * @param data the data
     * @param size the size
     * @return the string [ ] [ ]
     */
    public static String[][] parseArrayList(ArrayList<String[]> data, int size) {
        String[][] parsedData = new String[data.size()][size];
        for (int i = 0; i < data.size(); i++) {
            parsedData[i] = data.get(i);
        }
        return parsedData;
    }


    /**
     * Console reader string.
     *
     * @return the string
     */
    static String consoleReader() {
        //Read console
        try {
            System.out.println("File to parse:");
            return new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
