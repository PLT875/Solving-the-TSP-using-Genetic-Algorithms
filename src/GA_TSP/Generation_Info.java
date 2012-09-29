package GA_TSP;

/**
 * @author Peter Tran
 */
public class Generation_Info
{
    private int generationNo;
    
    private double  average_CircuitCost;
    private double  cheapest_CircuitCost;
    private double  mostExpensive_CircuitCost;
    private int     number_ofCopies;
    private int     number_ofMates;
    private int     number_ofMutes; // Mutations //

    public Generation_Info(int generationNo, double average_CircuitCost,
                                             double cheapest_CircuitCost,
                                             double mostExpensive_CircuitCost,
                                             int number_ofCopies,
                                             int number_ofMates,
                                             int number_ofMutes){
        
        this.generationNo = generationNo;
        this.average_CircuitCost  = average_CircuitCost;
        this.cheapest_CircuitCost = cheapest_CircuitCost;
        this.mostExpensive_CircuitCost = mostExpensive_CircuitCost;
        this.number_ofCopies = number_ofCopies;
        this.number_ofMates = number_ofMates;
        this.number_ofMutes = number_ofMutes;
    }

    public void print_Generation_Info(){
        System.out.print(" " + generationNo);
        System.out.print(" " + average_CircuitCost);
        System.out.print(" " + cheapest_CircuitCost);
        System.out.print(" " + mostExpensive_CircuitCost);
        System.out.print(" " + number_ofCopies);
        System.out.print(" " + number_ofMates);
        System.out.print(" " + number_ofMutes);
    }
}
