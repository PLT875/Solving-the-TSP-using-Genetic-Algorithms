package Crossover;

/**
 * @author Peter Tran
 */
public interface Path_Crossover
{
    
    public int[] get_offspring1();
    public int[] get_offspring2();
    
    public int[] get_parent1();
    public int[] get_parent2();

    public void crossOver(int[] offSpring, int[] parentX, int[] parentY);

    // Used in Testing //
    public void printOffspring(int [] offspring1, int [] offspring2);
 
}
