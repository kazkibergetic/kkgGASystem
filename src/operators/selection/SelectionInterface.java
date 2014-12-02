/**
 * 
 */
package operators.selection;

import chromosome.ChromosomeRepresentationInterface;
import evolver.Population;

/**
 * @author or13uw
 *
 */
public interface SelectionInterface {

	
	ChromosomeRepresentationInterface performSelection(Population p);
}
