package GA_TSP;
/**
 * @author Peter Tran
 */
import java.util.Random;

public class Chromosome
{
    private int chromosomeID;           // uniquely identify a chromosome //
    private int[] hamiltonianCircuit;   // a Hamiltonian Circuit //
    private double weight;              // weight of circuit //
    private double fitness;             // chromosome fitness //

    public Chromosome(int chromosomeID, int [] hamiltonianCircuit,
                                        double weight,  double fitness){
        this.chromosomeID = chromosomeID;
        this.hamiltonianCircuit = hamiltonianCircuit;
        this.weight = weight;
        this.fitness = fitness;
    }

    public int getChromosomeID(){
        return chromosomeID;
    }
    
    public int [] getHamiltonianCircuit(){
        return hamiltonianCircuit;
    }

    public double getWeight(){
        return weight;
    }

    public double getFitness(){
        return fitness;
    }

    public void setFitness_PerGen(double fitness){
        this.fitness = fitness;
    }

    // Mutation by single swap //
    public void mutateCircuit_SingleSwap(){
        Random randInt = new Random();
        int index1st = randInt.nextInt(hamiltonianCircuit.length);
        int index2nd = randInt.nextInt(hamiltonianCircuit.length);
        int temp = hamiltonianCircuit[index1st];
        hamiltonianCircuit[index1st] = hamiltonianCircuit[index2nd];
        hamiltonianCircuit[index2nd] = temp;
    }

    public void mutateCircuit_Inversion(){
        // Aim say p1 = { 0, 1, 2, 3, 4, 5, 6} //
        // mutate  p1 = { 0, 1, 5, 4, 3, 2, 6} //
        // i.e reverse ordering of nodes between 2 cutpoints //
        Random randInt = new Random();
        int index1st = randInt.nextInt(hamiltonianCircuit.length);
        int index2nd = randInt.nextInt(hamiltonianCircuit.length);
        while(index2nd == index1st){
            index2nd = randInt.nextInt(hamiltonianCircuit.length);
        }
        
        if(index1st > index2nd){
            int temp = index1st;
            index1st = index2nd;
            index2nd = temp;
        }


        while(true){
            int test = index2nd - index1st;
            // case p1 = { 0, 1, 2, 3, 4, 5, 6, 7 } reverse from 2 to 5 //
            // when 3 and 4 is reached difference is 1, perform final swap then stop //
            if(test == 1){ 
                 int temp = hamiltonianCircuit[index1st];
                 hamiltonianCircuit[index1st] = hamiltonianCircuit[index2nd];
                 hamiltonianCircuit[index2nd] = temp;
                 break;
            }

            else if(test == 2){
            // case p1 = { 0, 1, 2, 3, 4, 5, 6, 7 } reverse from 2 to 6 //
            // when 3 and 5 is reached difference is 2, perform final swap then stop //
                int temp = hamiltonianCircuit[index1st];
                hamiltonianCircuit[index1st] = hamiltonianCircuit[index2nd];
                hamiltonianCircuit[index2nd] = temp;
                break;
            }
            
            int temp = hamiltonianCircuit[index1st];
            hamiltonianCircuit[index1st] = hamiltonianCircuit[index2nd];
            hamiltonianCircuit[index2nd] = temp;
            index1st++;
            index2nd--;
        }
         
    }
    
    public void printCircuit(){
        for(int i = 0; i < hamiltonianCircuit.length; i++){
            System.out.print(" " +hamiltonianCircuit[i]);
        }
    }
}
