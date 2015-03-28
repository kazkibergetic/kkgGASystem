package fitness.multiObjective;


import chromosome.ChromosomeRepresentationInterface;
import evolver.RunEvolutionContext;
import params.ClassInitialization;
import params.Parameters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ParetoRankEvaluator.
 *
 * @author Maxim
 */
public class ParetoRankEvaluator implements RankEvaluator {

    protected static List<String> ranks = new CopyOnWriteArrayList<>();
    ClassInitialization ci = new ClassInitialization();
    protected int populationSize = Parameters.getPopulationSize();

    public List<Double> paretoCalculations() {
        List<Double> calculatedRanks = new ArrayList<>();
        if (!ranks.isEmpty()){
            Map<Integer, List<Double>> originalObjectives = parseObjectiveSets(ranks);
            List<Integer> excludedObjects = new ArrayList<>();

            int objectCount = originalObjectives.keySet().size();
            calculatedRanks.addAll(Collections.nCopies(populationSize, Double.POSITIVE_INFINITY));

            int rank = 1;
            while (excludedObjects.size() != objectCount) {
                List<Integer> notDominatedIndexes = findNotDominatedObjectives(originalObjectives, excludedObjects);
                for (int i : notDominatedIndexes) {
                    calculatedRanks.set(i, (double) rank);
                    excludedObjects.add(i);
                }
                ++rank;
            }
            ranks.clear();
        }
        return calculatedRanks;
    }

    private List<Integer> findNotDominatedObjectives(Map<Integer, List<Double>> originalObjectives, List<Integer> excludedObjects) {
        List<Integer> notDominatedIndexes = new ArrayList<>();

        for (int i : originalObjectives.keySet()) {
            if (!excludedObjects.contains(i)) {
                List<Double> candidate = originalObjectives.get(i);
                boolean dominated = false;
                for (int j : originalObjectives.keySet()) {
                    if (i != j && !excludedObjects.contains(j)) {
                        List<Double> objectToCompare = originalObjectives.get(j);
                        if (isDominated(candidate, objectToCompare)) {
                            dominated = true;
                            break;
                        }
                    }
                }
                if (!dominated){
                    notDominatedIndexes.add(i);
                }
            }
        }
        return notDominatedIndexes;
    }

    protected boolean isDominated(List<Double> candidate, List<Double> objectToCompare) {
        boolean notDominated = false;
        boolean equals = true;
        for (int i = 0; i < candidate.size(); ++i) {
            Double a = candidate.get(i);
            Double b = objectToCompare.get(i);
            if (a < b) {
                notDominated = true;
            }
            equals = equals && a.equals(b);
        }
        return !equals && !notDominated;
    }

    protected Map<Integer, List<Double>> parseObjectiveSets(List<String> originalObjectiveSets) {
        Map<Integer, List<Double>> originalObjectives = new HashMap<>();

        for (int i = 0 ; i< originalObjectiveSets.size(); ++i) {
            String[] numbers = originalObjectiveSets.get(i).split(",");

            List<Double> individualObjective = new ArrayList<>();
            for (String number : numbers) {
                individualObjective.add(Double.parseDouble(number));
            }
            originalObjectives.put(i, individualObjective);
        }
        return originalObjectives;
    }

    @Override
    public void preEvaluateFitness() {
    }


    @Override
    public double evaluateFitness(RunEvolutionContext runEvolutionContext, ChromosomeRepresentationInterface chromosome) throws Exception {
        String result = ci.getProblem().evaluateFitness(runEvolutionContext, chromosome);

        if (result.split(",").length > 1) {
            ranks.add(result);
            chromosome.setMetaData(result);
        } else {
            throw new Exception("Fitness value is in a wrong format!");
        }

        return Double.POSITIVE_INFINITY;
    }


    /* (non-Javadoc)
     * @see fitness.FitnessEvaluator#postEvaluateFitness(java.util.ArrayList)
     */
    @Override
    public void postEvaluateFitness(List<ChromosomeRepresentationInterface> chromosomes) throws Exception {
        List<Double> rankResults;
        List<String> ranksCopy = new ArrayList<String>(ranks);
        if (ranks.size() == Parameters.getPopulationSize()) {
            rankResults = paretoCalculations();
        } else {
            throw new Exception("Number of ranks is not equal to number of populations.");
        }

        for (ChromosomeRepresentationInterface chromosome : chromosomes) {
            int position = ranksCopy.indexOf(chromosome.getMetaData());
            chromosome.setFitness(rankResults.get(position));
        }
    }
}