/**kkgGASystem 2014
 * @author Oleg Rybkin, Computer Science, Brock University 
 */
package problems;

import chromosome.ChromosomeRepresentationInterface;
import evolver.RunEvolutionContext;

import java.io.File;
import java.util.ArrayList;


/**
 * @author or13uw
 *
 */
public interface ProblemInterface  {

	
	public static ArrayList<Points> points = new ArrayList<Points> ();
	
	 /**
     * initialize the problem, read dataset
     */	
	public void initialize(RunEvolutionContext runEvolutionContext, File file);
	
	 /**
     * returns number of points to create chromosome in GA
     * @return number of points in order to create a chromosome
     */	
	public int getSizeOfChromosome();
	
	
	 /**
     * returns points to create chromosome
     * @return points to create chromosome
     */	
	public ArrayList<Points> getPoints();
	
	 /**
     * evaluates fitness of the provided chromosome
     * @param chromosome : supplied by GA system. permutation if attributes to find reduct
     * @return fitness value of the provided chromosome
     */	
	public String evaluateFitness(RunEvolutionContext runEvolutionContext, ChromosomeRepresentationInterface  chromosome);
}
