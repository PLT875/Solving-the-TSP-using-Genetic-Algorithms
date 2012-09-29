package GA_TSP;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
/**
 *
 * @author Peter Tran
 */
public class Graph_Reader_Test {
    private Graph_Reader gr1;
    private Graph_Reader gr2;
    private Graph_Reader tube9;
    private Graph_Reader bays29;   // best result is 2020, graph is TSP benchmark //
    
    @BeforeClass
    public void setUp() {
           gr1 = new Graph_Reader("tsp_graphs/tspadata1.txt");
           gr2 = new Graph_Reader("tsp_graphs/tspadata2.txt");
         tube9 = new Graph_Reader("tsp_graphs/tube_9stations.txt");
        bays29 = new Graph_Reader("tsp_graphs/bays29.txt");

        tube9.create_Graph();
        gr1.create_Graph();
        bays29.create_Graph();
    }

    @Test
    public void test_Size9(){
        System.out.println("");
        System.out.println("Test a certain graph has array dimensions of 9 by 9");
        System.out.println("-------------------------------------------------");
        assertEquals(tube9.get_weightedGraph().length, 9);
    }

    @Test
    public void test_Size17() {
        System.out.println("");
        System.out.println("Test a certain graph has array dimensions of 17 by 17");
        System.out.println("-------------------------------------------");
        assertEquals(gr1.get_weightedGraph().length, 17);
    }

    @Test
    public void test_size_bays29() {
        System.out.println("");
        System.out.println("Test bays29 has array dimensions of 29 by 29");
        System.out.println("--------------------------------------------");
        assertEquals(bays29.get_weightedGraph().length, 29);
    }
    
    @Test
    public void size9_printTest(){
        System.out.println("");
        System.out.println("Print Map Size 9");
        System.out.println("----------------");
        double[][] temp = tube9.get_weightedGraph();
        for(int y = 0; y < temp.length; y++){
            for(int x = 0; x < temp.length; x++){
                System.out.print(" " + temp[y][x]);
            }
            System.out.println("");
        }
    }

    @Test
    public void test_Size48(){
        System.out.println("");
        System.out.println("Test a certain graph has array dimensions of 48 by 48");
        System.out.println("-----------------------------------------------------");
        assertEquals(gr2.get_weightedGraph().length, 48);
    }

    @AfterClass
    public void cleanUp() {
          gr1 = null;
          gr2 = null;
        tube9 = null;
       bays29 = null;

    }
}
