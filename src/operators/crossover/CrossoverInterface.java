/**
 * 
 */
package operators.crossover;

import java.util.ArrayList;

import chromosome.ChromosomeRepresentationInterface;
import exceptions.ChromomesInequalityException;

/**
 * @author or13uw
 *
 */
public interface CrossoverInterface {

	
	
	ArrayList<ChromosomeRepresentationInterface> performCrossover(ChromosomeRepresentationInterface parent1, ChromosomeRepresentationInterface parent2) throws ChromomesInequalityException;

}
