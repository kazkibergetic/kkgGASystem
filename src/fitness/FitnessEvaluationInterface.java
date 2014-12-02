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
	 */
	double evaluateFitness(ChromosomeRepresentationInterface chromosome);
}
