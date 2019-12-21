package main.Launching;

import main.AnimalMech.Animal;
import main.MapMech.Grass;
import main.Simulation.Field;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class World {
    public static void main(String[] args) {
        try {

            Json.readJSON();

            Field map = new Field(Json.height, Json.width, Json.jungleRatio, Json.grassPerDay);
            Grass.energy = Json.grassEnergy;
            Animal.startEnergy = Json.startEnergy;
            Animal.moveEnergy = Json.moveEnergy;
            int animalsNumber = Json.animalsStartingNumber;


            for (int i = 0; i < animalsNumber; i++) {
                Animal.makeAnimal(map);
            }

            Runnable animateDay = new Runnable() {
                @Override
                public void run() {
                    map.day();
                    System.out.println(map);
                    System.out.println(map.statistics.toString());
                }
            };

            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            executor.scheduleAtFixedRate(animateDay, 0, 500, TimeUnit.MILLISECONDS);
        } catch (IllegalArgumentException a) {
            System.out.println(a);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}