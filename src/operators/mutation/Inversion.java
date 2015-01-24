/**
 * 
 */
package operators.mutation;

import chromosome.ChromosomeRepresentationInterface;

import java.util.Collections;

/**
 * @author or13uw
 *
 */
public class Inversion implements MutationInterface{

	/* (non-Javadoc)
	 * @see operators.mutation.MutationInterface#performMutation(chromosome.ChromosomeRepresentationInterface)
	 */
	@Override
	public ChromosomeRepresentationInterface performMutation(ChromosomeRepresentationInterface ind) {
        ChromosomeRepresentationInterface clone = ind.clone();
        Collections.reverse(clone.getChromosome());
		return clone;
	}

}
