/**
 * 
 */
package operators.selection;

import java.util.Random;

import evolver.RunEvolutionContext;
import params.Parameters;
import chromosome.ChromosomeRepresentationInterface;
import evolver.Population;

/**
 * @author or13uw
 *
 */
public class TournamentSelection implements SelectionInterface {

    /* (non-Javadoc)
         * @see operators.selection.SelectionInterface#performSelection(evolver.Population)
         */
	@Override
	public ChromosomeRepresentationInterface performSelection(RunEvolutionContext runEvolutionContext, Population p) {
        Random rand = runEvolutionContext.getRandom();
        //	System.out.println("Selection");
        int randomNumber = rand.nextInt(p.getChromosomes().size());
        double minFitness = p.getChromosomes().get(randomNumber).getFitness();
        int indMinFitnessId = randomNumber;
        for (int i = 0; i < Parameters.getTournamentSize() - 1; i++) {
            //System.out.println(randomNumber);
            randomNumber = rand.nextInt(p.getChromosomes().size());
            if (p.getChromosomes().get(randomNumber).getFitness() < minFitness) {
                minFitness = p.getChromosomes().get(randomNumber).getFitness();
                indMinFitnessId = randomNumber;
            }
        }
        //	System.out.println("minF "+indMinFitnessId);
        //	System.out.println(p.getChromosomes().get(indMinFitnessId).getChromosome());
        return p.getChromosomes().get(indMinFitnessId);


    }

}
