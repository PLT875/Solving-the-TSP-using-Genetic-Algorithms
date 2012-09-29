package Crossover;

import Crossover.OX;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

import GA_TSP.Graph;

/**
 *
 * @author Peter Tran
 */
public class OX_Test {

    private OX oxTest;

    private int [] parent1_Test = { 2, 4, 6, 8, 1, 3, 5, 7 };
    private int [] parent2_Test = { 3, 4, 5, 6, 7, 8, 1, 2 };
    

    private OX oxTestRandom;
    private Graph graphTester;
    
    @BeforeClass
    public void setUp() {
        oxTest = new OX(parent1_Test, parent2_Test);
        graphTester = new Graph("tsp_graphs/tube_9stations.txt");
    }

    @Test
    public void crossOver_Test() {
        System.out.println("");
        System.out.println("");
        System.out.println("Test - OX");
        System.out.println("----------");
        System.out.println("Parents");
        for(int x = 0; x < parent1_Test.length; x++){
            System.out.print(" " + parent1_Test[x]);
        }

        System.out.println("");
        for(int y = 0; y < parent1_Test.length; y++){
            System.out.print(" " + parent2_Test[y]);
        }

        System.out.println("");
        System.out.println("OX Test with cutPoints at  "
               + oxTest.get_cutPoint1() + "  and  " + oxTest.get_cutPoint2());

        int [] result1 = oxTest.get_offspring1();
        for(int i = 0; i < oxTest.get_offspring1().length; i++){
            System.out.print(" " + result1[i]);
        }

        System.out.println();
        int [] result2 = oxTest.get_offspring2();
        for(int x = 0; x< oxTest.get_offspring2().length; x++){
            System.out.print(" " + result2[x]);
        }
        
        System.out.println("");
        
    }

    @Test // Test for crossOver methods robustness,
          // can it run for consecutive repetitions. //
    public void random_crossOver_Test(){
        System.out.println("");
        System.out.println("");
        
        System.out.println("Random OX Test");
        System.out.println("--------------");
        System.out.println("");
        int iter = 0;
        while(iter != 5){
            int[] parent1 = graphTester.random_hamiltonianCircuit();
            int[] parent2 = graphTester.random_hamiltonianCircuit();
            oxTestRandom = new OX(parent1, parent2);
            oxTestRandom.printOffspring(oxTestRandom.get_offspring1(),
                                        oxTestRandom.get_offspring2());

            System.out.println("Cutpoints " + oxTestRandom.get_cutPoint1() +
                                            " and "
                                            + oxTestRandom.get_cutPoint2());
            iter++;
        }

    }
    

    @AfterClass
    public void cleanUp() {
        oxTest = null;
        oxTestRandom = null;
        graphTester = null;
    }
}

