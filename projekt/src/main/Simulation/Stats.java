
package main.Simulation;

import main.AnimalMech.Genes;
import main.AnimalMech.Genom;

import java.util.Arrays;

public class Stats {
    private int animalNumber;
    private int grassNumber;
    private int[] genomOccurence = new int[8];

    Stats(){
        Arrays.fill(genomOccurence, 0);
        this.animalNumber = 0;
        this.grassNumber = 0;
    }


    void addAnimal(){
        this.animalNumber++;
    }

    void addGrass(){
        this.grassNumber++;
    }

    public void removeAnimal(){ this.animalNumber-- ;}

    void removeGrass(){ this.grassNumber-- ;}


    void analizeGenom(Genom genom){
        Arrays.fill(this.genomOccurence, 0);
        int[] animalGenes = new int[8];
        Arrays.fill(animalGenes, 0);
        Genes[] genes = genom.getGenes();
        for(int i=0; i<32; i++){
            animalGenes[genes[i].getNumerical()]++;
        }
        int mostFrequentCount = 0;
        int mostFrequent = 0;
        for(int i=0; i<8; i++){
            if(animalGenes[i] > mostFrequentCount){
                mostFrequentCount = animalGenes[i];
                mostFrequent = i;
            }
        }
        this.genomOccurence[mostFrequent] ++;
    }

    private Genes getMostPopularGene() {
        int mostFrequentCount = 0;
        int mostFrequent = 0;
        for(int i=0; i<8; i++){
            if(this.genomOccurence[i] > mostFrequentCount){
                mostFrequentCount = genomOccurence[i];
                mostFrequent = i;
            }
        }
        return Genes.fromNumerical(mostFrequent);
    }

    @Override
    public String toString() {
        String a = String.valueOf(this.animalNumber);
        String b = String.valueOf(this.grassNumber);
        String c = String.valueOf(this.getMostPopularGene());
        return "Animals:" + a + "  Grass:" + b + "  Best gene:" + c;
    }
}
