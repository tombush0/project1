package main.AnimalMech;

import main.MapMech.Rand;

import java.util.Arrays;
import java.util.Random;

public class Genom {
    private Genes[] genes;

    public Genom(Genes[] genes){
        this.genes = genes;
        checkGenom();
    }

    public Genom(){
        this.genes = new Genes[32];
        for(int i=0; i<32; i++){
            this.genes[i] = Genes.fromNumerical(Rand.get(8));
        }
        checkGenom();
    }

    public Genom mutate(Genom other){
        Random r1 = new Random();
        Random r2 = new Random();
        int a = Rand.get(32), b = Rand.get(32);
        Genes[] newGenes = this.genes.clone();
        if (-Math.min(-a, -b) - Math.min(a, b) >= 0)
            System.arraycopy(other.genes, Math.min(a,b), newGenes, Math.min(a,b), -Math.min(-a,-b) - Math.min(a,b));
        Genom newGenom = new Genom(newGenes);
        newGenom.checkGenom();
        return newGenom;
    }


    private void checkGenom(){
        boolean[] check = new boolean[8];
        for(int k=0; k<32; k++)
            for(int j=0; j<8; j++) check[j] = false;
        for(Genes gen : this.genes) check[gen.getNumerical()] = true;
        for(int i=0; i<8; i++){
            if(!check[i]){
                this.genes[i] = Genes.fromNumerical(i);
                check[i] = true;
                Arrays.sort(this.genes);
            }
        }
    }

    public Genes[] getGenes(){
        return this.genes;
    }
}


