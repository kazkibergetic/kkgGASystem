package fitness.multiObjective;


import chromosome.ChromosomeRepresentationInterface;
import evolver.RunEvolutionContext;
import params.ClassInitialization;
import params.Parameters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Oleg Rybkin
 */
public class SumOfRanks implements RankEvaluator {
    protected static List<String> ranks = new CopyOnWriteArrayList<>();
    private ClassInitialization ci = new ClassInitialization();

    @Override
    public double evaluateFitness(RunEvolutionContext runEvolutionContext, ChromosomeRepresentationInterface chromosome) throws Exception {
        String result = ci.getProblem().evaluateFitness(runEvolutionContext, chromosome);
        if (result.split("[,]").length > 1) {
            ranks.add(result);
            chromosome.setMetaData(result);
        } else {
            throw new Exception("Fitness value is in a wrong format!");
        }

        return Double.POSITIVE_INFINITY;
    }

    public List<Double> sorCalculations(List<String> ranks) {
        List<DoublePair> inputValues = new ArrayList<>();
        for (int j = 0; j < ranks.size(); ++j) {
            String[] values = ranks.get(j).split("[,]");
            inputValues.add(new DoublePair(Double.parseDouble(values[0]), Double.parseDouble(values[1]), j));
        }
        Collections.sort(inputValues, DoublePair.orderByLeft);
        double lastValue = Double.NaN;
        int currentRank = 0;
        for (DoublePair value : inputValues) {
            if (value.left == lastValue) {
                value.rank1 = currentRank;
            } else {
                ++currentRank;
                value.rank1 = currentRank;
                lastValue = value.left;
            }
        }
        Collections.sort(inputValues, DoublePair.orderByRight);
        lastValue = Double.NaN;
        currentRank = 0;
        for (DoublePair value : inputValues) {
            if (value.right == lastValue) {
                value.rank2 = currentRank;
            } else {
                ++currentRank;
                value.rank2 = currentRank;
                lastValue = value.right;
            }
        }
        double sumOfRanks1 = 0;
        double sumOfRanks2 = 0;
        for (DoublePair value : inputValues) {
            sumOfRanks1 += value.rank1;
            sumOfRanks2 += value.rank2;
        }
        Collections.sort(inputValues, DoublePair.orderByInitialPosition);
        List<Double> resultRanks = new ArrayList<>();
        for (DoublePair value : inputValues) {
            double finalRank = value.rank1 / sumOfRanks1 + value.rank2 / sumOfRanks2;
            resultRanks.add(finalRank);
        }
        return resultRanks;
    }

    @Override
    public void preEvaluateFitness() {
    }

    @Override
    public void postEvaluateFitness(List<ChromosomeRepresentationInterface> chromosomes) throws Exception {
        List<Double> rankResults;
        ArrayList<String> ranksCopy = new ArrayList<String>(ranks);
        if (ranks.size() == Parameters.getPopulationSize()) {
            rankResults = sorCalculations(ranksCopy);
            ranks.clear();
        } else {
            throw new Exception("Number of ranks is not equal to number of populations.");
        }

        for (ChromosomeRepresentationInterface chromosome : chromosomes) {
            int position = ranksCopy.indexOf(chromosome.getMetaData());
            chromosome.setFitness(rankResults.get(position));
        }
    }

    private static class DoublePair {
        double left;
        double right;
        int initialRankPosition;
        int rank1;
        int rank2;

        public DoublePair(double left, double right, int initialRankPosition) {
            this.left = left;
            this.right = right;
            this.initialRankPosition = initialRankPosition;
        }

        private static final Comparator<DoublePair> orderByLeft = new Comparator<DoublePair>() {
            @Override
            public int compare(DoublePair o1, DoublePair o2) {
                return Double.compare(o1.left, o2.left);
            }
        };

        private static final Comparator<DoublePair> orderByRight = new Comparator<DoublePair>() {
            @Override
            public int compare(DoublePair o1, DoublePair o2) {
                return Double.compare(o1.right, o2.right);
            }
        };

        private static final Comparator<DoublePair> orderByInitialPosition = new Comparator<DoublePair>() {
            @Override
            public int compare(DoublePair o1, DoublePair o2) {
                return Integer.compare(o1.initialRankPosition, o2.initialRankPosition);
            }
        };

        @Override
        public String toString() {
            return "DoublePair{" +
                    "left=" + left +
                    ", right=" + right +
                    ", initialRankPosition=" + initialRankPosition +
                    ", rank1=" + rank1 +
                    ", rank2=" + rank2 +
                    '}';
        }
    }
}
