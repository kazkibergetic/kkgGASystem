/**
 *
 */
package operators.mutation;

import chromosome.ChromosomeRepresentationInterface;
import evolver.RunEvolutionContext;
import params.Parameters;

import java.util.Random;

/**
 * @author or13uw
 */
public class ReciprocalExchange implements MutationInterface {

    /* (non-Javadoc)
     * @see operators.mutation.MutationInterface#performMutation(chromosome.ChromosomeRepresentationInterface)
     */
    @Override
    public ChromosomeRepresentationInterface performMutation(RunEvolutionContext runEvolutionContext, ChromosomeRepresentationInterface ind) {
        Random rand = runEvolutionContext.getRandom();

        int position1 = rand.nextInt(ind.getSize());
        int position2 = rand.nextInt(ind.getSize());

        ChromosomeRepresentationInterface clone = ind.clone();

        clone.setGene(position1, ind.getGene(position2));
        clone.setGene(position2, ind.getGene(position1));

        return clone;
    }

}
