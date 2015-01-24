/**
 * 
 */
package operators.crossover;

import chromosome.ChromosomeRepresentationInterface;
import exceptions.ChromomesInequalityException;

import java.util.List;

/**
 * @author or13uw
 *
 */
public interface CrossoverInterface {

	
	
	List<ChromosomeRepresentationInterface> performCrossover(ChromosomeRepresentationInterface parent1, ChromosomeRepresentationInterface parent2) throws ChromomesInequalityException;

}
