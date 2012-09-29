package Crossover;

import Crossover.PMX;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

import GA_TSP.Graph;

/**
 *
 * @author Peter Tran
 */
public class PMX_Test {

    private PMX pmxTest;
    
    private int [] parent1_Test = { 2, 4, 6, 8, 1, 3, 5, 7 };
    private int [] parent2_Test = { 3, 4, 5, 6, 7, 8, 1, 2 };
    private PMX pmxTestRandom;
    private Graph graphTester;

   
    @BeforeClass
    public void setUp() {
        // code that will be invoked before this test starts
        pmxTest = new PMX(parent1_Test, parent2_Test);
        graphTester = new Graph("tsp_graphs/tube_9stations.txt");
    }


    @Test
    public void segment_Test(){
        System.out.println(" ");
        System.out.println("PMX Segment Test 1");
        System.out.println("------------------");
        System.out.println(" Cutpoint1 " + pmxTest.get_cutPoint1());
        System.out.println(" Cutpoint2 " + pmxTest.get_cutPoint2());

        int [] temp = pmxTest.get_segment1();
        for(int index = 0; index < temp.length; index++){
            System.out.print(" " + temp[index]);
        }
        System.out.println(" ");
        System.out.println("PMX Segment Test 2");
        System.out.println("------------------");
        System.out.println(" Cutpoint1 " + pmxTest.get_cutPoint1());
        System.out.println(" Cutpoint2 " + pmxTest.get_cutPoint2());

        int [] temp2 = pmxTest.get_segment2();
        for(int index = 0; index < temp2.length; index++){
            System.out.print(" " + temp2[index]);
        }
        
        System.out.println(" ");
    }

    @Test
    public void crossOver_Test() {
        System.out.println("");
        System.out.println("PMX Test");
        System.out.println("--------");
        System.out.println("PMX Test with cutPoints at "
                             + pmxTest.get_cutPoint1() + " and "
                             + pmxTest.get_cutPoint2());
        
        System.out.println("Parents");
        for(int x = 0; x < parent1_Test.length; x++){
            System.out.print(" " +parent1_Test[x]);
        }
        System.out.println("");
        for(int y = 0; y < parent1_Test.length; y++){
            System.out.print(" " +parent2_Test[y]);
        }
        System.out.println("");

        System.out.println("Offspring");
        System.out.println("");
        int [] temp = pmxTest.get_offspring1();
        for(int i = 0; i < pmxTest.get_offspring1().length; i++){
            System.out.print(" " + temp[i]);
        }
        
        System.out.println();
        int [] temp2 = pmxTest.get_offspring2();
        for(int x = 0; x< pmxTest.get_offspring2().length; x++){
            System.out.print(" " + temp2[x]);
        }
        
        System.out.println("");
    }

    
    @Test
    public void random_crossOver_Test(){
        System.out.println("");
        System.out.println("Random PMX Test");
        System.out.println("---------------");
        int iter = 0;
        while(iter != 5){
            int[] parent1 = graphTester.random_hamiltonianCircuit();
            int[] parent2 = graphTester.random_hamiltonianCircuit();
            pmxTestRandom = new PMX(parent1, parent2);
            pmxTestRandom.printOffspring(pmxTestRandom.get_offspring1(),
                                         pmxTestRandom.get_offspring2());
            
            System.out.println("Cutpoints " + pmxTestRandom.get_cutPoint1() +
                                            " and "
                                            + pmxTestRandom.get_cutPoint2());

            iter++;
        }

    }

    

    @Test
    public void printOffspring_Test(){
        pmxTest.printOffspring(pmxTest.get_offspring1(),
                               pmxTest.get_offspring2());
    }

    
    @AfterClass
    public void cleanUp() {
        // code that will be invoked after this test ends
        pmxTest = null;
        pmxTestRandom = null;
        graphTester = null;
    }
}
