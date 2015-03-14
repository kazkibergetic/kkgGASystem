/**
 * 
 */
package operators.crossover;

import chromosome.ChromosomeRepresentationInterface;
import evolver.RunEvolutionContext;
import exceptions.ChromomesInequalityException;

import java.util.List;

/**
 * @author or13uw
 *
 */
public interface CrossoverInterface {

	
	
	List<ChromosomeRepresentationInterface> performCrossover(RunEvolutionContext runEvolutionContext, ChromosomeRepresentationInterface parent1, ChromosomeRepresentationInterface parent2) throws ChromomesInequalityException;

}
