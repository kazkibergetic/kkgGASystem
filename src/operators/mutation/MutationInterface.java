/**
 * 
 */
package operators.mutation;

import chromosome.ChromosomeRepresentationInterface;
import evolver.RunEvolutionContext;

/**
 * @author or13uw
 *
 */
public interface MutationInterface {

	
	ChromosomeRepresentationInterface performMutation(RunEvolutionContext runEvolutionContext, ChromosomeRepresentationInterface ind);
}
