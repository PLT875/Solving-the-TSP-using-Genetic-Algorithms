package GA_TSP;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

/**
 *
 * @author Peter Tran
 */
public class Graph_Test {
    private Graph graph;
    private Graph benchmark;
    
    @BeforeClass
    public void setUp() {
            graph = new Graph("tsp_graphs/tube_9stations.txt");
        benchmark = new Graph("tsp_graphs/bays29.txt");
    }

    @Test
    public void testCalculateCost(){
        System.out.println("");
        System.out.println("Test Calculate Cost - tube_9stations");
        System.out.println("------------------------------------");

        int[] example_hamiltonianCircuit   = { 0, 1, 3, 6, 8, 7, 5, 4, 2 };
        assertEquals(graph.calculateCost(example_hamiltonianCircuit), 17.0);

        int[] example_hamiltonianCircuit2  = { 3, 4 ,6, 8, 7, 5, 2, 0, 1 };
        assertEquals(graph.calculateCost(example_hamiltonianCircuit2), 20.0);   
    }
    
    @Test
    public void testCalculateCost_bays29(){
        // Test the Hamiltonian Circuit Cost is Correct //
        // Use the Optimal Hamiltonian Circuit supplied as a test //
        System.out.println("");
        System.out.println("Test Calculate Cost Optimal bay29 Circuit");
        System.out.println("-----------------------------------------");
        int [] opt_benchmark = { 0, 27, 5,  11,  8,   4, 25, 28,  2, 1, 19,  9,  3, 14,
                            17, 16, 13, 21, 10, 18,  24,  6, 22, 26, 7, 23, 15, 12, 20 };
        
        assertEquals(benchmark.calculateCost(opt_benchmark), 2020.0);

        
        int [] opt_benchmark2 = {21, 16, 13, 17, 14, 24,  6, 22, 26, 7, 23, 0, 27, 5, 
                              11, 8,  4, 25, 28,  2,  1, 20, 19,  9, 3, 12,15, 18, 10 };
        
        assertEquals(benchmark.calculateCost(opt_benchmark2), 2096.0);
    }
    
    @AfterClass
    public void cleanUp() {
        graph = null;
    }
}
