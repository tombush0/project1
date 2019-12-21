package main.Interfaces;

import main.AnimalMech.Animal;
import main.MapMech.Vector2d;

public interface IPositionChangeObserver {
    void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal);

}
