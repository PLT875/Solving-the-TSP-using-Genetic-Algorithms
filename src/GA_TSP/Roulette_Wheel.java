package GA_TSP;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Peter Tran
 */

// Implements the roulette wheel required for each generation //

public class Roulette_Wheel
{
    private double [] cumulative_prob_ofSelection;
    private double totalFitness;
    private int index_ofParent;      // Changes When pickChromosome is Called //
    private double randomProb;       
    

    public Roulette_Wheel(ArrayList<Chromosome> population){
        randomProb = 0.0;
        index_ofParent = -1;
        totalFitness = 0.0;
        cumulative_prob_ofSelection = new double[population.size() + 1];

        // Calculate Total Fitness //
        for(Chromosome ch: population){
           totalFitness = totalFitness + ch.getFitness();
        }
        
        int next_index = 1; // current  Index //
        int prev_index = 0; // previous Index //
        cumulative_prob_ofSelection[0]= 0.0;

        // If totalFitness = 0.0 then shouldn't divide by 0.0 //
        if(totalFitness == 0.0){
            for(Chromosome chrom: population){
                cumulative_prob_ofSelection[next_index] = 0.0;
                next_index++;
            }
        }

        else {
        // Now Calculate Cumulative Fitness of Chromosomes //
            for(Chromosome chrom: population){
                double selectionProb = chrom.getFitness()/totalFitness;
                cumulative_prob_ofSelection[next_index] =
                           cumulative_prob_ofSelection[prev_index] + selectionProb;
                prev_index++;
                next_index++;
            }
        }
    }

    // Chromosome is Picked, Index of Parent is Calculated //.
    public void pickChromosome(){
        Random double_generator = new Random();  
        randomProb = double_generator.nextDouble();
        index_ofParent = getLocation_ofParent(randomProb);
    }

    private int getLocation_ofParent(double randomDouble){
        int location_ofParent = 0; // Default - Required if RW Entries are 0.0
                                   // as explained earlier //
        for(int index = 0; index < cumulative_prob_ofSelection.length - 1; index++){
            if((randomDouble >= cumulative_prob_ofSelection[index]) &&
               (randomDouble <= cumulative_prob_ofSelection[index + 1])){
                location_ofParent = index;
                break;
            }
        }
        return location_ofParent;
    }
    
    public int get_indexOfParent(){ return index_ofParent; }

    public double [] get_rouletteWheel(){ return cumulative_prob_ofSelection; }

    // For Testing Purposes Only
    public double get_totalFitness(){ return totalFitness; }

    // For Testing Purposes Only //
    public double get_randomNo(){ return randomProb; }
    
}
