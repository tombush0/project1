package main.AnimalMech;

import main.MapMech.Vector2d;

public enum MapDirection {
    N, NE, E, SE, S, SW, W, NW;


    public String toString() {
        switch(this) {
            case N: return "N";
            case NE: return "NE";
            case E: return "E";
            case SE: return "SE";
            case S: return "S";
            case SW: return "SW";
            case W: return "W";
            default: return "NW";
        }
    }

    public int getNumerical(){
        return this.ordinal();
    }

    public static MapDirection fromNumerical(int direction){
        return MapDirection.values()[direction];
    }

    public MapDirection rotate(int degrees){
        return fromNumerical((this.getNumerical() + degrees)%8);
    }

    public Vector2d toUnitVector() {
        switch(this){
            case N: return new Vector2d(0, 1);
            case NE: return new Vector2d(1, 1);
            case E: return new Vector2d(1, 0);
            case SE: return new Vector2d(1, -1);
            case S: return new Vector2d(0, -1);
            case SW: return new Vector2d(-1, -1);
            case W: return new Vector2d(-1, 0);
            default: return new Vector2d(-1, 1);
        }
    }


}