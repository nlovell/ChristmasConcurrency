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

        ArrayList<String[]> conveyors = output.get(0);
        ArrayList<String[]>  hoppers;
        ArrayList<String[]> presents;
        ArrayList<String[]> sacks ;
        ArrayList<String[]> turntables;

        String[][] conveyor = new String[conveyors.size()][3];
        for(int i = 0; i  < conveyors.size(); i++){
             conveyor[i] = conveyors.get(i);
        }

        System.out.println(Arrays.toString(conveyor[0]));


       // ChristmasMachine xmasMach = new ChristmasMachine(new String[]{"120"});
       // xmasMach.establishMachine();


        /*
        do {
            fileToParse = consoleReader("File to parse");
            System.out.println(fileToParse);
            System.out.println("Is input valid?: " + fileToParse.matches(Constants.filePathValidator));
        } while(!fileToParse.matches(Constants.filePathValidator));

        FileParser fp = new FileParser(fileToParse, true, true); */
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
}
