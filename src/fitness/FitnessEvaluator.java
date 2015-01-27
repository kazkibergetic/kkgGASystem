/**
 * 
 */
package fitness;

import chromosome.ChromosomeRepresentationInterface;
import evolver.RunEvolutionContext;

import java.util.List;

/**
 * @author or13uw
 *
 */
public interface FitnessEvaluator {

    void preEvaluateFitness();
    /**
	 * @param chromosome
	 * @return
	 * @throws Exception
	 */
	double evaluateFitness(RunEvolutionContext runEvolutionContext, ChromosomeRepresentationInterface chromosome) throws Exception;

	void postEvaluateFitness(List<ChromosomeRepresentationInterface> chromosomes) throws Exception;
}
