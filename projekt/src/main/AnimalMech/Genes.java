package main.AnimalMech;


public enum Genes {
    F, FR, R, RB, B, BL, L, FL;

    public int getNumerical(){
            return this.ordinal();
            }

    public static Genes fromNumerical(int gene){
            return Genes.values()[gene];
            }
}
