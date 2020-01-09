import machine.ChristmasMachine;
import parser.FileParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Runner class and the Parser classes aren't part of the actual "machine", so while they have access to
 *  non-static arrays (eg arraylists) - these are not exposed to the classes related to the machine. They are parsed
 *  into static arrays before being passed there.
 */
public class runner {
    public static void main(String args[]){

        String fileToParse;
        FileParser fp =  new FileParser();
        ArrayList<ArrayList<String[]>> output = fp.parseFile("C:/test/example.txt", true, true);

       String[][] presents = new String[1][1];
        String[][] turntables = new String[1][1];

        int timer = 60;
        String[][] conveyors = parseArrayList(output.get(0), 3);
        String[][] hoppers = parseArrayList(output.get(1), 4);
        String[][] sacks = parseArrayList(output.get(2), 3);

        System.out.println("----------------------------");

        System.out.println(Arrays.toString(conveyors[0]));
        System.out.println(Arrays.toString(hoppers[0]));
        System.out.println(Arrays.toString(sacks[0]));



        ChristmasMachine machine = new ChristmasMachine(timer, conveyors, hoppers, presents, sacks, turntables);

        machine.startStuff();
    }

    public static String[][] parseArrayList(ArrayList<String[]> data, int size){
        String[][] parsedData = new String[data.size()][size];
        for(int i = 0; i  < data.size(); i++){
            parsedData[i] = data.get(i);
        }
        return parsedData;
    }



    static String consoleReader(String reason) {
        //Read console
        try  {
            System.out.println("Console Input | " + reason);
            return new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {

        }
    }

    // ChristmasMachine xmasMach = new ChristmasMachine(new String[]{"120"});
    // xmasMach.establishMachine();


        /*
        do {
            fileToParse = consoleReader("File to parse");
            conout(fileToParse);
            conout("Is input valid?: " + fileToParse.matches(Constants.filePathValidator));
        } while(!fileToParse.matches(Constants.filePathValidator));

        FileParser fp = new FileParser(fileToParse, true, true); */
}
