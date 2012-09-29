package Crossover;

import Crossover.Path_Crossover;
import Crossover.PMX;
import Crossover.CX;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author Peter Tran
 */
public class Crossover_Test {
    
    private PMX pmxTest;
    private Path_Crossover crossover_type;
    private int [] parent1_Test = { 3, 2, 1, 8, 7, 6, 5, 4 };
    private int [] parent2_Test = { 1, 3, 5, 7, 8, 4, 2, 6 };

    @BeforeClass
    public void setUp() {
        pmxTest = new PMX(parent1_Test, parent2_Test);
        crossover_type = new CX(parent1_Test, parent2_Test);
    }

    @Test
    public void getParent1_Test(){
        System.out.println("");
        System.out.println("");
        System.out.println("Test - Print Parent 1");
        System.out.println("---------------------");
        System.out.println("Parent 1");
        int [] temp = pmxTest.get_parent1();
        for(int i = 0; i < pmxTest.get_parent1().length; i++){
            System.out.print(" " + temp[i]);
        }
    }

    @Test
    public void getParent2_Test(){
        System.out.println("");
        System.out.println("");
        System.out.println("Test - Print Parent 2");
        System.out.println("---------------------");
        System.out.println("Parent 2");
        int [] temp = pmxTest.get_parent2();
        for(int i = 0; i < pmxTest.get_parent2().length; i++){
            System.out.print(" " + temp[i]);
        }
    }

    @Test
    public void test_interfaceCrossover(){
        System.out.println("");
        System.out.println("Test");
        System.out.println("Interface Crossover Test - Printing Offspring Only");
        System.out.println("--------------------------------------------------");

        int [] offspring1 = crossover_type.get_offspring1();
        int [] offspring2 = crossover_type.get_offspring2();

        System.out.println("");

        for(int index = 0; index < offspring1.length; index++){
            System.out.print(" " + offspring1[index]);
        }

        System.out.println("");

        for(int index2= 0; index2 < offspring2.length; index2++){
            System.out.print(" " + offspring2[index2]);
        }
        
        System.out.println("");

    }

    @AfterClass
    public void cleanUp() {
       pmxTest = null;
       crossover_type = null;
    }
}
