package GA_TSP;
/**
 * @author Peter Tran
 */
import java.util.ArrayList;
import java.util.Random;

public class Graph
{
    private double[][] weighted_Graph;
    
    private Graph_Reader graph_Reader;

    public Graph(String filename){
        graph_Reader = new Graph_Reader(filename);
        graph_Reader.create_Graph();
        weighted_Graph = graph_Reader.get_weightedGraph();
    }

    public int [] random_hamiltonianCircuit(){

        int [] hamiltonianCircuit = new int[weighted_Graph.length];
        ArrayList<Integer> nodes_notPicked = new ArrayList<Integer>();

        // Add all Nodes (labelled 0 to |N-1|) //
        // to nodes_notPicked: ArrayList<Integer> //

        for(int start_Node = 0; start_Node < weighted_Graph.length; start_Node++){
            nodes_notPicked.add(start_Node);
        }

        // Randomly pick a node from nodes_notPicked
        // Add this node to hamiltonianCircuit[nodePosition]
        
        for(int nodePosition = 0; nodePosition < weighted_Graph.length; nodePosition++){
            Random rand_nextNode = new Random();
            int nodeSelect = rand_nextNode.nextInt(nodes_notPicked.size());
            hamiltonianCircuit[nodePosition] = nodes_notPicked.get(nodeSelect);
            nodes_notPicked.remove(nodeSelect);
        }
        
        return hamiltonianCircuit;
    }

    public double calculateCost(int [] hamiltonianCircuit){
        double cost = 0.0;
        for(int currentNode = 0;
                currentNode < hamiltonianCircuit.length - 1; currentNode++){
            
            cost = cost + get_edgeWeight(hamiltonianCircuit[currentNode],
                                         hamiltonianCircuit[currentNode + 1]);
        }
        // Find edge weight from last node in sequence back to start
        // and add to cost
        cost = cost + get_edgeWeight(hamiltonianCircuit[(hamiltonianCircuit.length) - 1],
                                     hamiltonianCircuit[0]);
        return cost;
    }

     private double get_edgeWeight(int first_nodeNumber, int second_nodeNumber){
        double weight = weighted_Graph[first_nodeNumber][second_nodeNumber];
        return weight;
    }
}
