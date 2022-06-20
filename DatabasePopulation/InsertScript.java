package diabetes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
//Emil Pontoppidan Rasmussen, s204441
public class InsertScript {
    private static String[] time;
    private static String[] unformattedTime;
    private static String[][] measurements;
    private static String[] measurementLine;
    private static String type;
    private static Scanner input;

    public static void main(String[] args) throws Exception {
        input = new Scanner(System.in);

        System.out.println("What type of measurement?");
        type = input.nextLine();

        readingTime();

        System.out.println("What txt do you want to read from?");
        readingMeasurements();

        System.out.println("What txt do you want to write to?");
        writeMeasurement();

    }

    private static void readingTime() throws FileNotFoundException {
        unformattedTime = new String[8065];
        time = new String[8065];
        String[] placeHolder = new String[4];
        File file = new File("/Users/emilpontoppidanrasmussen/Desktop/time-kopi.txt");
        Scanner console = new Scanner(file);

        while (console.hasNextLine()) {
            String s = console.nextLine();
            unformattedTime = s.split(",");

            for(int i = 0; i < 8065; i++){
                placeHolder = unformattedTime[i].split("[- ]");
                //has to be an if statement if we add more months than january
                time[i] = placeHolder[2] + "-" + "01" + "-" + placeHolder[0] + " " + placeHolder[3];
            }
        }
    }

    private static void readingMeasurements() throws FileNotFoundException {
        String readingFile = input.nextLine();

        measurements = new String[20][8065];
        measurementLine = new String[8065];

        File file = new File(readingFile);

        Scanner console = new Scanner(file);

        int i = 0;
        while (console.hasNextLine() && i < 20) {
            String s = console.nextLine();
            measurementLine = s.split(",");

            for(int j = 0; j < 8065 - 1; j++){
                measurements[i][j] = measurementLine[j];
            }
            i++;
        }
    }

    private static void writeMeasurement() throws IOException {
        String writeFile = input.nextLine();

        FileWriter myWriter  = new FileWriter(writeFile);

        int measurementId = 0;

        myWriter.write("INSERT INTO measurement (measurement_id, measurement_name, time, value, patient_id) VALUES" + "\n");
        for(int i = 0; i < 20; i++){
            for(int j = 0; j < 8065 - 1; j++){ //-1 when extracting other than CGM
                if (i == 19 && j == 8064 - 1){ //-1 when extracting other than CGM
                    myWriter.write("(" + measurementId + "," + "'" + type + "'" + "," + "'" + time[j] + "'" + "," + measurements[i][j] + "," + i + ")" + ";");
                    measurementId++;
                }else{
                    myWriter.write("(" + measurementId + "," + "'" + type + "'" + "," + "'" + time[j] + "'" + "," + measurements[i][j] + "," + i + ")" + "," + "\n");
                    measurementId++;
                }
            }
        }
        myWriter.close();
    }

}
