package evolver;

import output.statistics.FitnessResults;

/**
 * RunEvolutionContext.
 *
 * @author Maxim Shchekotov
 */
public class RunEvolutionContext {
    private boolean rankOption;
    private ProblemResultCache problemResultCache;
    private FitnessResults fitnessOutput;

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
}
