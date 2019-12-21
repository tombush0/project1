package main.MapMech;


import java.util.Random;

public class Rand {

    public static int get(int i){
        Random a = new Random();
        return a.nextInt(i);
    }
}
