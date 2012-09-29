package GA_TSP;
/*
 * @author Peter Tran
 */
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.ArrayList;

public class Graph_Reader
{
    private int num_Rows;                       //  number of lines in the file //
    private double[][] weighted_graph;
    private ArrayList<String> store_lineList;   //  stores each line of a file as a string  //
    private ArrayList<Double> distance_betweenNodes;

    // The constructor creates a 2D matrix weighted_graph //
    // Each line in the file is store as a String in "store_lineList" //

    public Graph_Reader(String filename){
        num_Rows = 0;
        store_lineList = new ArrayList<String>();
        distance_betweenNodes = new ArrayList<Double>(); // TEMP //
        try {
            FileInputStream fileInputStream = new FileInputStream(filename);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            BufferedReader bufferedReader   = new BufferedReader
                                             (new InputStreamReader(dataInputStream));
            // Read Line by Line
            String stringLine;
            while((stringLine = bufferedReader.readLine()) != null){
                store_lineList.add(stringLine);
                num_Rows++;
            }
            dataInputStream.close();
            
        }
        catch(Exception exception){
            System.out.println("ERROR: " + exception.getMessage());
        }
        
        weighted_graph = new double[num_Rows][num_Rows];
    }

    public void create_Graph(){
        // Each string in store_lineList can be broken up into words //
        // i.e. in the Graph's context each word is a distance between a pair of nodes //
        
        
        ArrayList<Object> temp = new ArrayList<Object>();
        for(int i = 0; i < store_lineList.size(); i++){
            StringTokenizer lineParser = new StringTokenizer(store_lineList.get(i));
            while(lineParser.hasMoreTokens()){
                temp.add(lineParser.nextElement());
            }
        }

        // these words as store as Double in "temp" ArrayList //
        for(int x = 0; x < temp.size(); x++){
            Object obj = temp.get(x);
            String str_distance = obj.toString();
            double distance = Double.parseDouble(str_distance);
            distance_betweenNodes.add(distance);
        }

        // fill weighted_graph with "temp" entries //
        int tempIndex = 0;
        for(int row = 0; row < weighted_graph.length; row++){
            for(int column = 0; column < weighted_graph.length; column++){
               weighted_graph[row][column] = distance_betweenNodes.get(tempIndex);
                tempIndex++;
            }
        } 
    }
    
    public double[][] get_weightedGraph(){
        return weighted_graph;
    }
    
}
