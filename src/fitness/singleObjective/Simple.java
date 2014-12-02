/**
 * 
 */
package fitness.singleObjective;

import java.util.Random;

import params.ClassInitialization;
import problems.ProblemInterface;
import problems.TSP.TSPproblem;
import chromosome.ChromosomeRepresentationInterface;
import fitness.FitnessEvaluationInterface;

/**
 * @author or13uw
 *
 */
public class Simple implements FitnessEvaluationInterface{

	
	/* (non-Javadoc)
	 * @see fitness.FitnessEvaluationInterface#evaluateFitness(chromosome.ChromosomeRepresentationInterface)
	 */
	
	ClassInitialization ci = new ClassInitialization();
	
	@Override
	public double evaluateFitness(ChromosomeRepresentationInterface  chromosome) {
		// TODO Auto-generated method stub
		
		
		//chromosome.setChromosome(TSPproblem.getOptimalTour());
		double totalDistance = 0;
		totalDistance = ci.getProblem().evaluateFitness(chromosome);
		
		return totalDistance;
	}

}
