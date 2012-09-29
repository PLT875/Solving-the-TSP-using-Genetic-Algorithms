package Crossover;

import GA_TSP.Graph;
import Crossover.CX;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

/**
 * @author Peter Tran
 */
public class CX_Test {
    private CX cxTest;

    private int [] parent1_Test = { 1, 2, 3, 4, 5, 6, 7, 8 };
    private int [] parent2_Test = { 4, 6, 8, 7, 5, 3, 1, 2 };
    
    private CX cxTestRandom;
    private Graph graphTester;
   
    @BeforeClass
    public void setUp() {
        // code that will be invoked before this test starts
             cxTest = new CX(parent1_Test, parent2_Test);
        graphTester = new Graph("tsp_graphs/tube_9stations.txt");
    }
    
   
    @Test 
    public void random_crossOver_Test(){
        System.out.println("");
        System.out.println("CX Test");
        System.out.println("-------");
        int iter = 0;
        while(iter != 5){
            int[] parent1 = graphTester.random_hamiltonianCircuit();
            int[] parent2 = graphTester.random_hamiltonianCircuit();
            cxTestRandom = new CX(parent1, parent2);
            cxTestRandom.printOffspring(cxTestRandom.get_offspring1(),
                                        cxTestRandom.get_offspring2());
            System.out.println("");
            iter++;
        }
    }

    @Test
    public void cxProduction_Test(){
            int [] expectedOffspring1_Result = { 1, 6, 8, 4, 5, 3, 7, 2};
            int [] offspring1_Result = cxTest.get_offspring1();

            for(int index = 0; index < offspring1_Result.length; index++){
                assertEquals(offspring1_Result[index],
                                        expectedOffspring1_Result[index]);
            }

            int [] expectedOffspring2_Result = { 4, 2, 3, 7, 5, 6, 1, 8};
            int [] offspring2_Result = cxTest.get_offspring2();

            for(int index = 0; index < offspring1_Result.length; index++){
                assertEquals(offspring2_Result[index],
                                        expectedOffspring2_Result[index]);
            }
            
    }

    @AfterClass
    public void cleanUp() {
        cxTest = null;
        graphTester = null;
        cxTestRandom = null;
        
    }
}
