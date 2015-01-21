/**
 * 
 */
package fitness;

import chromosome.ChromosomeRepresentationInterface;

import java.util.List;

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

	public void postEvaluateFitness(List<ChromosomeRepresentationInterface> chromosomes) throws Exception;
}
