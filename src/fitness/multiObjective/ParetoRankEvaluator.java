package fitness.multiObjective;


import chromosome.ChromosomeRepresentationInterface;
import evolver.RunEvolutionContext;
import params.ClassInitialization;
import params.Parameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author anthony
 */
public class ParetoRankEvaluator implements RankEvaluator {

    protected static List<String> ranks = new CopyOnWriteArrayList<>();
    ClassInitialization ci = new ClassInitialization();
    protected int populationSize = Parameters.getPopulationSize();

    /**
     * complete ranking of dual objectives
     * <p/>
     * #sample ranked data
     * 2,1000 #Rank:1
     * 4,600  #Rank:1
     * 8,400  #Rank:1
     * 7,800  #Rank:2
     * 9,500  #Rank:2
     */

    @SuppressWarnings("unchecked")
    public List<Double> paretoCalculations() {
        List<Double> calculatedRanks = new ArrayList<>();
        List<List<Double>> originalObjectives = parseObjectiveSets(new ArrayList<>(ranks));
        int objectiveCount = originalObjectives.get(0).size();
        List<List<Double>> objectiveSets = new ArrayList<>(originalObjectives);

        int rank = 1;

        //replace with a more efficient later
        for (int i = 0; i < populationSize; i++) {
            calculatedRanks.add(Double.POSITIVE_INFINITY); //fill with dump values to enable set in second loop
        }

        List<Double> emptyObjective = Collections.nCopies(objectiveCount, Double.POSITIVE_INFINITY);

        while (Collections.frequency(objectiveSets, emptyObjective) != objectiveSets.size()) {
            List<Integer> nonDominatedIndividualIndexes = findNonDominatedIndividualIndexes(objectiveSets);
            for (Integer j : nonDominatedIndividualIndexes) {
                calculatedRanks.set(j, (double) rank);
                objectiveSets.set(j, emptyObjective);
            }
            rank++; //increment ranking number e.g. rank 0, rank 1, e.t.c
        }
        ranks.clear();
        //System.out.println("#counting "+count);

        return calculatedRanks;
    }

    protected List<Integer> findNonDominatedIndividualIndexes(List<List<Double>> objectiveSets) {
        List<Integer> index = new ArrayList<>();
        for (int i = 0; i < objectiveSets.size(); i++) {
            List<Double> selectedObjectives = objectiveSets.get(i);

            if (isNotDominatedByAnyIndividual(selectedObjectives, objectiveSets)) {
                index.add(i);
            }
        }
        return index;
    }

    protected boolean isNotDominatedByAnyIndividual(List<Double> selectedObjectives, List<List<Double>> objectiveSets) {
        boolean notDominated = true;

        int objectivesCount = objectiveSets.get(0).size();
        for (List<Double> objectiveSet : objectiveSets) {
            boolean notDominatedByOneIndividual = false;
            boolean notAllEqual = false;
            for (int j = 0; j < objectivesCount; j++) {
                Double selectedObjective = selectedObjectives.get(j);
                Double currentObjective = objectiveSet.get(j);

                notAllEqual = notAllEqual || !(selectedObjective.equals(currentObjective));
                notDominatedByOneIndividual = notDominatedByOneIndividual || (selectedObjective < currentObjective);
            }
            notDominated = notDominated && (notDominatedByOneIndividual || !notAllEqual);
        }
        return notDominated;
    }

    protected List<List<Double>> parseObjectiveSets(List<String> originalObjectiveSets) {
        List<List<Double>> originalObjectives = new ArrayList<>();
        for (String s : originalObjectiveSets) {
            List<String> originalObjectiveList = Arrays.asList(s.split("\\s*,\\s*"));

            List<Double> individualObjective = new ArrayList<>();
            for (String str : originalObjectiveList) {
                individualObjective.add(Double.parseDouble(str));
            }
            originalObjectives.add(individualObjective);
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
        //chromosome.setChromosome(TSPproblem.getOptimalTour());
        if (ranks.size() == Parameters.getPopulationSize()) {
            rankResults = paretoCalculations();
        } else {
            throw new Exception("Number of ranks is not equal to number of populations.");
        }

        for (int i = 0; i < chromosomes.size(); i++) {

            int position = ranksCopy.indexOf(chromosomes.get(i).getMetaData());

            //System.out.println(position + " >>> "+ i);
            chromosomes.get(i).setFitness(rankResults.get(position));


        }


    }
}