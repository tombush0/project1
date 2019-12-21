package main.AnimalMech;
import main.Interfaces.IPositionChangeObserver;
import main.MapMech.Rand;
import main.Simulation.Field;
import main.Interfaces.IMapElement;
import main.MapMech.Vector2d;

import java.util.LinkedList;
import java.util.Random;


public class Animal implements IMapElement {

    public static int startEnergy;
    public static int moveEnergy;
    public int energy;
    private Genom genom;
    private MapDirection direction;
    private Vector2d position;
    private LinkedList<IPositionChangeObserver> observers = new LinkedList<>();


    private Animal(Vector2d startingPosition){
        this.energy = startEnergy;
        this.position = startingPosition;
        Random r = new Random();
        this.direction = MapDirection.fromNumerical(r.nextInt(8));
//        System.out.println("Before creating genom");
        this.genom = new Genom();
    }

    public Animal(Vector2d startingPosition, Genom givenGenom){
        this.energy = 0;
        this.position = startingPosition;
        Random r = new Random();
        this.direction = MapDirection.fromNumerical(r.nextInt(8));
        this.genom = givenGenom;
    }

    public Genom getGenom(){
        return this.genom;
    }

    @Override
    public Vector2d getPosition() {
        return this.position;
    }

    public void inMap(Field map) {
        int x = this.position.x % (map.width + 1);
        int y = this.position.y % (map.height + 1);
        if (x < 0) x += map.width + 1;
        if (y < 0) y += map.height + 1;
        this.position = new Vector2d(x, y);
    }
//    public void changePosition(Vector2d newPosition){
//        this.position = newPosition;
//    }

//    public void changeEnergy(int energy){
//        this.energy += energy;
//    }

    public int getKidEnergy(){
        int givenEnergy = (int) Math.round(0.25 * this.energy);
        this.energy -= givenEnergy;
        return givenEnergy;
    }

//    @Override
//    public String toString() {
//        String symbol;
//        symbol = (this.energy >= 0.5 * this.startEnergy) ? "X" : "x";
//        return symbol;
//    }

    @Override
    public String toString() {
        return String.valueOf('A');
    }

    public static void makeAnimal(Field map){
        Vector2d pos = new Vector2d(Rand.get(map.width), Rand.get(map.height));
        for(int i=0; i< 100 && map.isOccupied(pos); i++){
            int x = Rand.get(map.width) % (map.width +1);
            int y = Rand.get(map.height) % (map.height +1);
            if(x < 0) x += map.width + 1;
            if(y < 0) y += map.height +1;
            pos = new Vector2d(x,y);
        }
        map.placeAnimal(new Animal(pos));
    }

    public void move(Field map){
        this.energy -= Animal.moveEnergy;
        if(this.energy <= 0){
            this.die(map);
        }
        else{
            Random r = new Random();
            this.direction = this.direction.rotate(this.genom.getGenes()[r.nextInt(32)].getNumerical());
            Vector2d oldPos = this.position;
            this.position = this.position.add(this.direction.toUnitVector());
            this.inMap(map);
            this.positionChange(oldPos, this.position);
        }
    }

    private void die(Field map){
        map.statistics.removeAnimal();
        this.positionChange(this.position, new Vector2d(-5, -5));
    }

    public void addObserver(IPositionChangeObserver observer){
        this.observers.add(observer);
    }

    private void removeObserver(IPositionChangeObserver observer){
        this.observers.remove(observer);
    }

    private void positionChange(Vector2d oldPosition, Vector2d newPosition){
        for(IPositionChangeObserver observer: this.observers)
            observer.positionChanged(oldPosition, newPosition, this);
    }
}