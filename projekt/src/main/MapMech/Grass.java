package main.MapMech;

import main.Interfaces.IMapElement;

public class Grass implements IMapElement {
    private Vector2d pos;
    public static int energy;

    public Grass(Vector2d position){
        this.pos = position;
    }

    @Override
    public Vector2d getPosition(){
        return pos;
    }

    @Override
    public String toString(){
        return "*";
    }
}
