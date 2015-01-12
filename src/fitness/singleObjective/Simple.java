/**
 * 
 */
package fitness.singleObjective;


import java.util.ArrayList;

import params.ClassInitialization;
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
		String result;
		result = ci.getProblem().evaluateFitness(chromosome);
		if(result.split(",").length==1)
		{
			return Double.parseDouble(result);
		}
		else
		{
			new Exception("Fitness value is in a wrong format!");
		}
		
		return 0d;
	}
	
	public  void preEvaluateFitness() {
	}

	public void postEvaluateFitness() {
	}

	/* (non-Javadoc)
	 * @see fitness.FitnessEvaluationInterface#postEvaluateFitness(java.util.ArrayList)
	 */
	@Override
	public void postEvaluateFitness(
			ArrayList<ChromosomeRepresentationInterface> chromosomes) {
		// TODO Auto-generated method stub
		
	}

}
