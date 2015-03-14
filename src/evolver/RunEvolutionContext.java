package evolver;

import output.statistics.FitnessResults;

import java.util.Random;
import java.util.concurrent.ExecutorService;

/**
 * RunEvolutionContext.
 *
 * @author Maxim Shchekotov
 */
public class RunEvolutionContext {
    private boolean rankOption;
    private ProblemResultCache problemResultCache;
    private FitnessResults fitnessOutput;
    private ExecutorService executorService;
    private Random random;

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public ProblemResultCache getProblemResultCache() {
        return problemResultCache;
    }

    public void setProblemResultCache(ProblemResultCache problemResultCache) {
        this.problemResultCache = problemResultCache;
    }

    public FitnessResults getFitnessOutput() {
        return fitnessOutput;
    }

    public void setFitnessOutput(FitnessResults fitnessOutput) {
        this.fitnessOutput = fitnessOutput;
    }

    public boolean isRankOption() {
        return rankOption;
    }

    public void setRankOption(boolean rankOption) {
        this.rankOption = rankOption;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }
}
