package GA_TSP;

import java.util.ArrayList;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * @author Peter Tran
 */

public class Genetic_Algorithm_TSP_Test {

    private Genetic_Algorithm_TSP ga_TSP;  // Test Object //
   
    private Roulette_Wheel roulette_wheel; // Test Object //
    
    @BeforeClass
    public void setUp() {
        ga_TSP = new Genetic_Algorithm_TSP("tsp_graphs/tube_9stations.txt",
                                            15, 0.3, 0.6, 0.1, 20, "pmx");
        
        roulette_wheel = new Roulette_Wheel(ga_TSP.get_population());

    }
  
    @Test
    public void testChromosomes_inInitialPopulation(){
        // Simply Print out a Chromosomes' Attributes //
        System.out.println("");
        System.out.println("Test Chromosome");
        System.out.println("-----------------");
        ArrayList<Chromosome> chrom1 = ga_TSP.get_population();
         for(int index = 0; index <chrom1.size(); index++){
                printCircuit(chrom1.get(index).getHamiltonianCircuit());
                System.out.print(" Weight " + chrom1.get(index).getWeight());
                System.out.print(" Fitness " + chrom1.get(index).getFitness());
                System.out.print(" ID: " + chrom1.get(index).getChromosomeID());
                System.out.println("");
         }
         System.out.println("");
    }

    @Test
    public void testPopulationSize(){
        assertEquals(ga_TSP.get_population().size(), 15);
    }

    @Test
    public void testRouletteWheel(){
      // Simply Print out the Roulette Wheel //
      System.out.println("");
      System.out.println("Test Roulette Wheel");
      System.out.println("-------------------");
      System.out.println("Total Fitness: " + roulette_wheel.get_totalFitness());
       double [] temp = roulette_wheel.get_rouletteWheel();
       for(int index = 0; index < temp.length; index++){
            System.out.println(index+ ": " + temp[index]);
       }
       System.out.println("");
       // Test Roulette Wheel (represented as array) is the right length
       // to accomodate to cumulative probabilities from [0.0 to 1.0]
       // the length will be population size + 1.
       assertEquals(roulette_wheel.get_rouletteWheel().length, 16);
    }

   @Test
    public void testselectParents(){
        // Inspect print outs to see if the random number generated results
        // in the right Chromosome being picked. //
        roulette_wheel.pickChromosome();
        System.out.println("");
        System.out.println("Test Select Parents");
        System.out.println("-------------------");
        System.out.println("Refer to Roulette Wheel produced in testRouletteWheel");
        System.out.println("Random No.1 for Parent 1 Selection: " +
                                            roulette_wheel.get_randomNo());
        System.out.println("Random No.1 refers to Population Index: " +
                                            roulette_wheel.get_indexOfParent());
        System.out.println("Parent(ID): " + ga_TSP.get_population().
                              get(roulette_wheel.get_indexOfParent()).getChromosomeID());
        System.out.println("");
    }
    
    public void printCircuit(int [] hamiltonianCircuit){
        for(int i = 0; i < hamiltonianCircuit.length; i++){
            System.out.print(" " + hamiltonianCircuit[i]);
        }
    }

    @AfterClass
    public void cleanUp() {
        ga_TSP = null;
        roulette_wheel = null;
    }
}
