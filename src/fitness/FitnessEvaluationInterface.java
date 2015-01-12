/**
 * 
 */
package fitness;

import java.util.ArrayList;

import chromosome.ChromosomeRepresentationInterface;

/**
 * @author or13uw
 *
 */
public interface FitnessEvaluationInterface {

	
	
	
	

	/**
	 * @param chromosome
	 * @return
	 * @throws Exception 
	 */
	double evaluateFitness(ChromosomeRepresentationInterface chromosome) throws Exception;
	public void preEvaluateFitness();

	public void postEvaluateFitness(ArrayList<ChromosomeRepresentationInterface> chromosomes) throws Exception;
}
