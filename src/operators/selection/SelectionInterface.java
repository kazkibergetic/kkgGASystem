/**
 * 
 */
package operators.selection;

import chromosome.ChromosomeRepresentationInterface;
import evolver.Population;
import evolver.RunEvolutionContext;

/**
 * @author or13uw
 *
 */
public interface SelectionInterface {

	
	ChromosomeRepresentationInterface performSelection(RunEvolutionContext runEvolutionContext,  Population p);
}
