package GA_TSP;

/**
 * @author Peter Tran
 */

import java.util.ArrayList;
import java.util.Random;
import Crossover.Path_Crossover;
import Crossover.PMX;
import Crossover.OX;
import Crossover.CX;

public class Genetic_Algorithm_TSP 
{
    private final Graph graph; // 1st Argument is the file containing the graph //
    private final int population_Size;                   // 2nd Argument  //
    private final double cumulative_copying_Prob;        // 3rd Agrument  //
    private final double cumulative_crossover_Prob;      // 4th Argument  //
    private final double cumulative_mutation_Prob;       // 5th Argument  //
    private final int max_numIterations;                 // 6th Argument  //
    private final String crossover_Method;               // 7th Argument  //
    private int generationNumber;                
    private ArrayList<Chromosome> population;         // population, updated every gen //
    private ArrayList<Chromosome> selectionPool;      // selection pool //
    private ArrayList<Chromosome> next_generation;
    private ArrayList<Generation_Info> generationInfo;    // Generation Information //         
    private final int crossover_MethodID;
    private int chromosomeID_Counter;
    private double av_circuitCost_PerGen;
    private double cheapest_circuitCost_PerGen;
    private int number_CopiesPerGen;
    private int number_MatesPerGen;
    private int number_MutesPerGen;
    private double costliest_circuitCost_PerGen;
    
    public Genetic_Algorithm_TSP(String file_withGraph, int population_Size,
                                 double copying_Prob,   double crossover_Prob,
                                 double mutation_Prob,  int max_numIterations,
                                 String crossover_Method){
        graph = new Graph(file_withGraph);
        this.population_Size = population_Size;
        
        // Set Cumulative Values for Copying, Crossover, Mutation Probabilities //
        cumulative_copying_Prob   = copying_Prob;
        cumulative_crossover_Prob = cumulative_copying_Prob + crossover_Prob;
        cumulative_mutation_Prob  = cumulative_crossover_Prob + mutation_Prob;

        this.max_numIterations = max_numIterations;
        this.crossover_Method  = crossover_Method;
        generationNumber = 0;
        population  = new ArrayList<Chromosome>();
        selectionPool = new ArrayList<Chromosome>();
        next_generation = new ArrayList<Chromosome>();
        generationInfo  = new ArrayList<Generation_Info>();
        crossover_MethodID = set_crossoverMethodID();
        chromosomeID_Counter = 0;
        av_circuitCost_PerGen = 0.0;
        cheapest_circuitCost_PerGen = 0.0;
        number_CopiesPerGen = 0;
        number_MatesPerGen = 0;
        number_MutesPerGen = 0;
        costliest_circuitCost_PerGen = 0;
           
        // Create Initial Population //
        while(chromosomeID_Counter != population_Size){
            int[] randCircuit = graph.random_hamiltonianCircuit();
            double circuit_cost = graph.calculateCost(randCircuit);
            Chromosome chromosome = new Chromosome(chromosomeID_Counter, randCircuit,
                                                           circuit_cost, 0.0);
            population.add(chromosome);
            chromosomeID_Counter++;
        }
        insertionSort_Population();
        calculate_costliest_circuitCost_PerGen();
        updateAll_chromosomesFitness_PerGen();
        calculate_av_circuitCost_PerGen();
        calculate_cheapest_circuitCost_PerGen();
        Generation_Info genInfo = new Generation_Info(generationNumber, 
                                                      av_circuitCost_PerGen,
                                                      cheapest_circuitCost_PerGen,
                                                      costliest_circuitCost_PerGen,
                                                      number_CopiesPerGen,
                                                      number_MatesPerGen,
                                                      number_MutesPerGen);
        generationInfo.add(genInfo);
    }
    
    private void calculate_costliest_circuitCost_PerGen(){
       // Chromsome with Most Expensive circuit is
       // first element of ArrayList<Chromosome> population.
       // (Assumming that population has been sorted using insertionSort(...).
       costliest_circuitCost_PerGen = population.get(0).getWeight();
    }
    
    private void updateAll_chromosomesFitness_PerGen(){
       for(Chromosome chrom: population){
            double fitness = (costliest_circuitCost_PerGen) - chrom.getWeight();
            chrom.setFitness_PerGen(fitness);
       }
    }

    private int set_crossoverMethodID(){
        int xoverMethodID = -1;
        String pmx_Cross  = "pmx"; // 1 //
        String  ox_Cross  = "ox";  // 2 //
        String  cx_Cross  = "cx";  // 3 //
        
        if(crossover_Method.equals(pmx_Cross))    {  xoverMethodID = 1; }

        else if(crossover_Method.equals(ox_Cross)){  xoverMethodID = 2; }
        else if(crossover_Method.equals(cx_Cross)){  xoverMethodID = 3; }
        
        else { System.out.println("Unknown Crossover Method: " + xoverMethodID);
               System.exit(0);
        }
        
        return xoverMethodID;
    }

    public ArrayList<Chromosome> get_population(){
        return population;
    }

    public ArrayList<Chromosome> get_matingPairs(){ 
        return selectionPool;
    }
    
    public ArrayList<Chromosome> get_next_generation(){
        return next_generation;
    }

    public ArrayList<Generation_Info> get_generationInfo(){ 
        return generationInfo;
    }

    public int get_max_numIterations(){ return max_numIterations; }
    
    public void selection(){
        Roulette_Wheel rws = new Roulette_Wheel(population);
        int num_spins = 0;
        int num_spinsRequired = population_Size - 1;
        // Due to Elitism = 1, num_spinsRequired is population_Size - 1 //
        while(num_spins < num_spinsRequired){
            rws.pickChromosome();
            selectionPool.add(population.get(rws.get_indexOfParent()));
            num_spins++;
        }
    }

    public void evolve_Population(){
        // Perform Elitism  //
        elitism();
        Random randDouble = new Random();
        // Generate random double [0.0, 1.0] //
        for(int index = 0; index < selectionPool.size(); index = index + 2){
            double evolChoice = randDouble.nextDouble();

            // Copy Option //
            if((evolChoice >= 0.0)&&(evolChoice <= cumulative_copying_Prob)){
                copy(selectionPool.get(index), selectionPool.get(index + 1));
                number_CopiesPerGen = number_CopiesPerGen + 2;
            }

            // Recombination (Crossover) Option //
            else if((evolChoice >= cumulative_copying_Prob)
                                &&(evolChoice <= cumulative_crossover_Prob)){
                crossover(selectionPool.get(index), selectionPool.get(index + 1));
                number_MatesPerGen = number_MatesPerGen + 2;
            }
            
            // Mutation Option //
            else if((evolChoice >= cumulative_crossover_Prob)
                                &&(evolChoice <= cumulative_mutation_Prob)){
                mutate(selectionPool.get(index), selectionPool.get(index + 1));
                number_MutesPerGen = number_MutesPerGen + 2;
            }
        }
    }

    private void elitism(){
        // Save Top Chromosome in Current Generation //
        next_generation.add(population.get(population_Size - 1));
        
    }

    private void copy(Chromosome chrom1, Chromosome chrom2){
        next_generation.add(chrom1);
        next_generation.add(chrom2);
    }

    private void crossover(Chromosome chrom1, Chromosome chrom2){
        Path_Crossover xover_method = get_requiredCrossover(crossover_MethodID,
                                                       chrom1.getHamiltonianCircuit(),
                                                       chrom2.getHamiltonianCircuit());

        int[] offspring1_Circuit = xover_method.get_offspring1();
        int[] offspring2_Circuit = xover_method.get_offspring2();
        // Create offspring chromosomes, fitness is initially set to 0.0,
        // but will be updated when all Chromosome making up the 
        // new generation has been determined.
        Chromosome offspring1 = new Chromosome(chromosomeID_Counter++,
                                               offspring1_Circuit,
                                               graph.calculateCost(offspring1_Circuit),
                                               0.0);
        Chromosome offspring2 = new Chromosome(chromosomeID_Counter++,
                                               offspring2_Circuit,
                                               graph.calculateCost(offspring2_Circuit),
                                               0.0);
        next_generation.add(offspring1);
        next_generation.add(offspring2);
    }
    
    private Path_Crossover get_requiredCrossover(int crossover_MethodID,
                                                 int[] parent1_Circuit,
                                                 int[] parent2_Circuit){
        Path_Crossover xOverType = null;
        if (crossover_MethodID == 1){
            Path_Crossover pmx_Type = new PMX(parent1_Circuit, parent2_Circuit);
            xOverType = pmx_Type;
        }
       
        else if (crossover_MethodID == 2){
            Path_Crossover ox_Type = new OX(parent1_Circuit, parent2_Circuit);
            xOverType = ox_Type;
        }

        else if (crossover_MethodID == 3){
            Path_Crossover cx_Type = new CX(parent1_Circuit, parent2_Circuit);
            xOverType = cx_Type;
        }
   
        return xOverType;
    }

    private void mutate(Chromosome chrom1, Chromosome chrom2){
        chrom1.mutateCircuit_Inversion();
        chrom2.mutateCircuit_Inversion();
        next_generation.add(chrom1);
        next_generation.add(chrom2);
    }

    private void reinsertion(){
        population = null;
        population = next_generation;
        next_generation = new ArrayList<Chromosome>();
        selectionPool = new ArrayList<Chromosome>();
    }

    private void calculate_av_circuitCost_PerGen(){
        double totalcircuitCost = 0.0;
        for(Chromosome chrom : population){
            totalcircuitCost = totalcircuitCost + chrom.getWeight();
        }
        av_circuitCost_PerGen =  Math.round(totalcircuitCost/population.size());
    }

    private void calculate_cheapest_circuitCost_PerGen(){
        cheapest_circuitCost_PerGen = population.get(population_Size - 1).getWeight();
    }

    private void insertionSort_Population(){
    // Reference http://www.dreamincode.net/code/snippet516.htm
    // Sorts population, such that Individual with
    // cheapest circuit weight is at the bottom.
        
        double temp;
        int index;
        for(int position = 1; position < population.size(); position++){
            if(population.get(position).getWeight()
                           > population.get(position - 1).getWeight())
            {
               temp = population.get(position).getWeight();
               Chromosome chromTemp = population.get(position);
               index = position;
                 do {
                       population.set(index, population.get(index - 1));
                       index--;
                    }
                 while((index > 0) && (population.get(index-1).getWeight() < temp));
               population.set(index, chromTemp);
            }
        }
    }

    private static int string_toInteger(String args){
        String argument = args;
        int int_ofString = Integer.parseInt(argument);
        return int_ofString;
    }

    private static double string_toDouble(String args){
        String argument = args;
        double double_ofString = Double.parseDouble(argument);
        return double_ofString;
    }

    private void print_Population(){
        System.out.println("");
        System.out.println("Generation Number: " + generationNumber);
        System.out.println("");
        System.out.println("");
        for(Chromosome ch: population){
            System.out.print("  Circuit: ");
            ch.printCircuit(); 
            System.out.print("  Weight: " + ch.getWeight());
            System.out.print("  Fitness: " + ch.getFitness());
            System.out.print("  ID: " + ch.getChromosomeID());
            System.out.println("");
        }
        System.out.println("Copies in Generation: "+ number_CopiesPerGen);
        System.out.println("Mates in Generation: " + number_MatesPerGen);
        System.out.println("Mutations in Generation: " + number_MutesPerGen);
        System.out.println("Average Circuit Weight: " + av_circuitCost_PerGen);
        System.out.println("BEST CHROMOSOME (ID): " +
                           population.get(population.size()-1).getChromosomeID());
    }

    private void reset_numberOf_Copies_Mates_Mutes(){
        number_CopiesPerGen = 0;
        number_MatesPerGen  = 0;
        number_MutesPerGen  = 0;
    }

    public static void main(String[] args){
        String dataFile     = args[0];
        String popArgument  = args[1];
        String copyRate     = args[2];
        String xoverRate    = args[3];
        String muteRate     = args[4];
        String numGenerations   = args[5];
        String crossover_Method = args[6];
        
        Genetic_Algorithm_TSP gaTSP = new Genetic_Algorithm_TSP(dataFile,
                                                    string_toInteger(popArgument),
                                                    string_toDouble(copyRate),
                                                    string_toDouble(xoverRate),
                                                    string_toDouble(muteRate),
                                                    string_toInteger(numGenerations),
                                                    crossover_Method);
        System.out.println("Graph: " + dataFile);
        System.out.println("Population Size: " + popArgument);
        System.out.println("Copying Rate: "   + copyRate);
        System.out.println("Crossover Prob: " + xoverRate);
        System.out.println("Mutation Prob: " + muteRate);
        System.out.println("Number of Generations to Run: " + numGenerations);
        System.out.println("Crossover Method: " + crossover_Method);
        gaTSP.print_Population();
        int iter = 0;
        while(iter < gaTSP.get_max_numIterations())
        {
            gaTSP.generationNumber++;
            gaTSP.selection();
            gaTSP.evolve_Population();
            gaTSP.reinsertion();
            gaTSP.insertionSort_Population();
            
            gaTSP.calculate_costliest_circuitCost_PerGen();
            gaTSP.updateAll_chromosomesFitness_PerGen();
            gaTSP.calculate_av_circuitCost_PerGen();
            gaTSP.calculate_cheapest_circuitCost_PerGen();
            gaTSP.print_Population();
            Generation_Info genInfo = new Generation_Info(gaTSP.generationNumber,
                                                       gaTSP.av_circuitCost_PerGen,
                                                       gaTSP.cheapest_circuitCost_PerGen,
                                                       gaTSP.costliest_circuitCost_PerGen,
                                                       gaTSP.number_CopiesPerGen,
                                                       gaTSP.number_MatesPerGen,
                                                       gaTSP.number_MutesPerGen);
            gaTSP.get_generationInfo().add(genInfo);
            gaTSP.reset_numberOf_Copies_Mates_Mutes();
            iter++;
        }
        System.out.println(" Generation Statistics: ");
        System.out.println(" Column 1: Gen Number - ");
        System.out.println(" Column 2: Av Circuit Cost/Gen - ");
        System.out.println(" Column 3: Cheapest Circuit Cost/Gen - ");
        System.out.println(" Column 4: Most Expensive Circuit Cost/Gen - ");
        System.out.println(" Column 5: Number Copies/Gen - ");
        System.out.println(" Column 6: Number Mates/Gen - ");
        System.out.println(" Column 7: Number Mutes/Gen - ");
        
        for(Generation_Info genInfo : gaTSP.get_generationInfo()){
            genInfo.print_Generation_Info();
            System.out.println("");
        }

     }
       
}


