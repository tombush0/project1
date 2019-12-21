package main.Launching;

import java.io.FileReader;

import org.json.simple.parser.*;
import org.json.simple.JSONObject;

public class Json {
    static int width;
    static int height;
    static int startEnergy;
    static int moveEnergy;
    static int grassEnergy;
    static double jungleRatio;
    static int animalsStartingNumber;
    static int grassPerDay;
    public static boolean debug = false;

    static void readJSON() throws Exception {
        Object obj = new JSONParser().parse(new FileReader("./parameters.json"));
        JSONObject parameters = (JSONObject) obj;

        try {
            Json.width = (int) (long) parameters.get("width") ;
            Json.height = (int) (long) parameters.get("height");
            Json.startEnergy = (int) (long) parameters.get("startEnergy");
            Json.moveEnergy = (int) (long) parameters.get("moveEnergy");
            Json.grassEnergy = (int) (long) parameters.get("grassEnergy");
            Json.jungleRatio = (double) parameters.get("jungleRatio");
            Json.animalsStartingNumber = (int) (long) parameters.get("startingAnimalsNumber");
            Json.grassPerDay = (int) (long) parameters.get("grassPerDay");
        } catch (ArithmeticException a) {
            System.out.println(a);
        }
    }
}
