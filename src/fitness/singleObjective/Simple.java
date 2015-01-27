/**
 *
 */
package fitness.singleObjective;


import chromosome.ChromosomeRepresentationInterface;
import evolver.RunEvolutionContext;
import fitness.FitnessEvaluator;
import params.ClassInitialization;

import java.util.List;

/**
 * @author or13uw
 */
public class Simple implements FitnessEvaluator {

    ClassInitialization ci = new ClassInitialization();

    @Override
    public double evaluateFitness(RunEvolutionContext runEvolutionContext, ChromosomeRepresentationInterface chromosome) {
        String result = ci.getProblem().evaluateFitness(runEvolutionContext, chromosome);
        if (result.split(",").length == 1) {
            return Double.parseDouble(result);
        } else {
            throw new IllegalStateException("Fitness value is in a wrong format!");
        }
    }

    @Override
    public void preEvaluateFitness() {
    }

    @Override
    public void postEvaluateFitness(List<ChromosomeRepresentationInterface> chromosomes) {

    }

}
