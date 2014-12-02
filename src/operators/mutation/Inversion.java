/**
 * 
 */
package operators.mutation;

import java.util.Collections;

import chromosome.ChromosomeRepresentationInterface;

/**
 * @author or13uw
 *
 */
public class Inversion implements MutationInterface{

	/* (non-Javadoc)
	 * @see operators.mutation.MutationInterface#performMutation(chromosome.ChromosomeRepresentationInterface)
	 */
	@Override
	public ChromosomeRepresentationInterface performMutation(
			ChromosomeRepresentationInterface ind) {
		
		
		Collections.reverse(ind.getChromosome());
		// TODO Auto-generated method stub
		return ind;
	}

}
