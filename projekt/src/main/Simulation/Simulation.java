package main.Simulation;


import main.AnimalMech.Animal;
import main.Interfaces.IMapElement;
import main.MapMech.Grass;
import main.MapMech.Rand;
import main.MapMech.Vector2d;

import java.util.*;

class Simulation {

    private static void movement(List<Animal> animals, Field map){
        List<Animal> animals1 = new ArrayList<>(animals);
        for (Animal animal : animals1) {
            animal.move(map);
        }
//        System.out.println("Moved animals");
    }


    private static void consumption(List<Animal> animals, Map<Vector2d, LinkedList<IMapElement>> hashmap, Field map){
        for(Animal animal: animals){
            LinkedList<IMapElement> location = hashmap.get(animal.getPosition());
            if( location.getFirst() instanceof Grass){
                location.removeFirst();
                map.statistics.removeGrass();
                List<Animal> strongestAnimal = new LinkedList<>();
                int maxEnergy = 0;
                for(IMapElement candidate: location){
                    if(((Animal) candidate).energy > maxEnergy){
                        maxEnergy =  ((Animal) candidate).energy;
                        strongestAnimal.clear();
                        strongestAnimal.add((Animal) candidate);
                    }
                    else if (((Animal) candidate).energy == maxEnergy){
                        strongestAnimal.add((Animal) candidate);
                    }
                }
                for(Animal consumer : strongestAnimal){
                    consumer.energy += Grass.energy/strongestAnimal.size();
                }
            }
        }
    }

    private static void procreation(List<Animal> animals, Map<Vector2d, LinkedList<IMapElement>> hashmap, Field map){
        boolean[][] procreated = new boolean[map.width+1][map.height+1];
        for( boolean[] row : procreated) Arrays.fill(row, false);
        LinkedList<Animal> children = new LinkedList<>();
        Set<Vector2d> childrenPositions = new HashSet<>();

        for(Animal animal : animals){
            animal.inMap(map);

            if(!procreated[animal.getPosition().x][animal.getPosition().y]){
                procreated[animal.getPosition().x][animal.getPosition().y] = true;
                LinkedList<IMapElement> animalsHere;
                animalsHere = hashmap.get(animal.getPosition());
                if(animalsHere.size() > 1) {
                    animalsHere.sort(new Comparator<IMapElement>() {
                        @Override
                        public int compare (IMapElement a, IMapElement b){
                            return -(((Animal) a).energy - ((Animal) b).energy);
                        }
                    });
                    Animal strongest = (Animal) animalsHere.get(0);
                    Animal secondStrongest = (Animal) animalsHere.get(1);
                    if(secondStrongest.energy > 0.5*Animal.startEnergy){
                        Vector2d childPosition = strongest.getPosition().randAround();
                        for(int i=0; i<40; i++){
                            if( !map.isOccupied(childPosition) && !childrenPositions.contains(childPosition)) break;
                            childPosition = strongest.getPosition().randAround();
                        }

                        Animal babyAnimal = new Animal(childPosition, strongest.getGenom().mutate(secondStrongest.getGenom()));
                        babyAnimal.energy += strongest.getKidEnergy() + secondStrongest.getKidEnergy();
                        childrenPositions.add(childPosition);
                        children.add(babyAnimal);
                    }
                }
            }
        }
        for( Animal kid : children){
            kid.inMap(map);
            map.placeAnimal(kid);
        }
    }


    private static void makeWorldGreen(Field map){
        for(int i = 0; i< map.grassPerDay; i++) {
            int count1 = (map.width * map.height), count2 = (map.width * map.height);
            for(int j=0; j<count1; j++) {
                Vector2d pos = new Vector2d(Rand.get(map.width+1)%(map.width+1), Rand.get(map.height+1)%(map.height+1));
                if (pos.precedes(map.jungleUpRight) && pos.follows(map.jungleLowLeft)) {
                    if (map.placeGrass(new Grass(pos))) {
                        break;
                    }
                }
            }
            for(int j=0; j<count2; j++) {
                Vector2d pos = new Vector2d(Rand.get(map.width + 1), Rand.get(map.height+1));
                if (!(pos.precedes(map.jungleUpRight) && pos.follows(map.jungleLowLeft))) {
                    if (map.placeGrass(new Grass(pos))) {
                        break;
                    }
                }
            }
        }
    }

    static void day(List<Animal> animals, Map<Vector2d, LinkedList<IMapElement>> hashmap, Field map){
        movement(animals, map);
        consumption(animals, hashmap, map);
        procreation(animals, hashmap, map);
        makeWorldGreen(map);

    }
}
