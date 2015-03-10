package params;

import chromosome.ChromosomeRepresentationInterface;
import operators.crossover.CrossoverInterface;
import operators.mutation.MutationInterface;
import operators.selection.SelectionInterface;
import fitness.FitnessEvaluator;

/**
 * @author or13uw
 *
 */
public class Parameters {

	protected static int runs;
	protected static int generations;
	protected static int population_size;
	protected static double crossover_probability;
	protected static double mutation_probability;
	protected static int chromosome_size;
	protected static int tournament_size;
	protected static int processors;
	protected static long seed;

	protected static CrossoverInterface crossover_operator;
	protected static MutationInterface mutation_operator;
	protected static SelectionInterface selection_operator;
	
	protected static FitnessEvaluator fitness_evaluation_operator;
	
	public static ChromosomeRepresentationInterface chromosome;
	
	static String stat_out;
	
	static String input_folder;
	
	static String file_extension;

    static int elitism_size;

    static int best_individuals_out;
	
	/** Receives # of experiments (runs)
	 * @return # of runs
	 */
	public static int getNumberOfRuns()
	{
		return runs;
	}
	
	/**Receives # of generations
	 * @return # of generations
	 */
	public static int getNumberOfGenerations()
	{
		return generations;
	}
	
	/** Receives the size of population
	 * @return population size
	 */
	public static int getPopulationSize()
	{
		return population_size;
	}
	
	/** Receives the crossover probability
	 * @return crossover probability
	 */
	public static double getCrossoverProbability()
	{
		return crossover_probability;
	}
	
	
	public static int getNumberOfProcessors()
	{
		return processors;
	}
	
	/** Receives the mutation probability
	 * @return mutation probability
	 */
	public static double getMutationProbability()
	{
		return mutation_probability;
	}
	
	/** Receives the size of chromosomes
	 * @return chromosome size
	 */
	/*public static int getChromosomeSize()
	{
		return chromosome_size;
	}
	*/
	/** Receives the number of chromosomes to participate in tournament selection
	 * @return tournament size
	 */
	public static int getTournamentSize()
	{
		return tournament_size;
	}

    /**
     * @return seed for all randoms
     */
    public static long getSeed() {
        return seed;
    }

    /** Receives the statistics output folder
	 * @return statistics output folder name
	 */
	public static String getOutputFolder()
	{
		return stat_out;
	}
	
	public static String getFilesExtension()
	{
		return file_extension;
	}
	
	public static String getInputFolder()
	{
		return input_folder;
	}

    public static int getElitismSize() {
        return elitism_size;
    }

    public static int getBestIndividualsOut() {
        return best_individuals_out;
    }
}
