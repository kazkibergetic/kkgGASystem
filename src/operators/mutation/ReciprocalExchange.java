/**
 * 
 */
package operators.mutation;

import java.util.Random;

import chromosome.ChromosomeRepresentationInterface;

/**
 * @author or13uw
 *
 */
public class ReciprocalExchange implements MutationInterface {

	/* (non-Javadoc)
	 * @see operators.mutation.MutationInterface#performMutation(chromosome.ChromosomeRepresentationInterface)
	 */
	@Override
	public ChromosomeRepresentationInterface performMutation(
			ChromosomeRepresentationInterface ind) {
		// TODO Auto-generated method stub
		
		
				Random rand = new Random();
				
				int position1 = rand.nextInt(ind.getSize());
				int position2 = rand.nextInt(ind.getSize());
				
				int position1Value = (Integer) ind.getGene(position1);
				ind.setGene(position1, ind.getGene(position2));
				ind.setGene(position2, position1Value);
				
	            return ind;  

	      
	}

}
