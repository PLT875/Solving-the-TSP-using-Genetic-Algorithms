package GA_TSP;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author Peter Tran
 */

public class Chromosome_mutateTest {

    private Chromosome chromosome_mutateTest1;
    private Chromosome chromosome_mutateTest2;
    private int[] hamiltonian_circuitTest1 = { 1, 3, 5, 6, 7, 2, 4, 8, 0 };
    private int[] hamiltonian_circuitTest2 = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

    @BeforeClass
    public void setUp() {
        chromosome_mutateTest1 =
                new Chromosome(1, hamiltonian_circuitTest1, 0.0, 0.0);

        chromosome_mutateTest2 =
                new Chromosome(2, hamiltonian_circuitTest2, 0.0, 0.0);
        // not interested in weights and fitness, only the hamiltonian circuit
        // to test mutation method.
    }

    // Regression Testing (Repeat Manually) //

    @Test
    public void mutate_SwapTest() {
        System.out.println("");
        System.out.println("Mutate Test Swap");
        System.out.println("----------------");
        System.out.println("");

        for(int index = 0; index < hamiltonian_circuitTest1.length; index++){
            System.out.print(" " + hamiltonian_circuitTest1[index]);
        }
        
        chromosome_mutateTest1.mutateCircuit_SingleSwap();
        System.out.println(" Before");
        
        int[] result = chromosome_mutateTest1.getHamiltonianCircuit();

        for(int index = 0; index < result.length; index++){
            System.out.print(" " + result[index]);
        }
        System.out.println(" After");
        System.out.println(""); 
    }

    // Regression Testing (Repeat Manually) //
    @Test
    public void mutate_InversionTest(){
        System.out.println("");
        System.out.println("Mutate Test Inversion");
        System.out.println("---------------------");
        System.out.println("");

        for(int index = 0; index < hamiltonian_circuitTest2.length; index++){
            System.out.print(" " + hamiltonian_circuitTest2[index]);
        }
        
        chromosome_mutateTest2.mutateCircuit_Inversion();
        System.out.println(" Before");

        int[] result = chromosome_mutateTest2.getHamiltonianCircuit();
        
        for(int index = 0; index < result.length; index++){
            System.out.print(" " + result[index]);
        }
        System.out.println(" After");
        System.out.println("");
    }
    

    @AfterClass
    public void cleanUp() {
        chromosome_mutateTest1 = null;
        chromosome_mutateTest2 = null;
    }

}
