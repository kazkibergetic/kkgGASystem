package evolver;

import chromosome.ChromosomeRepresentationInterface;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.List;
import java.util.Map;

/**
 * ProblemResultCache.
 *
 * @author Max
 */
public class ProblemResultCache {
    private Cache<ChromosomeRepresentationInterface, String> chromosomeResults = CacheBuilder.newBuilder().build();
    private Cache<ChromosomeRepresentationInterface, Map<Integer, List<List<String>>>> discretizationResults = CacheBuilder.newBuilder().build();
    private Cache<Integer, String> discretizationIntervals = CacheBuilder.newBuilder().build();

    public void putResults(ChromosomeRepresentationInterface chromosome, String result){
        chromosomeResults.put(chromosome, result);
    }

    public void putResults(ChromosomeRepresentationInterface chromosome, Map<Integer, List<List<String>>> result){
        discretizationResults.put(chromosome, result);
    }

    public void putDiscretizationIntervals(Integer column, String result){
        discretizationIntervals.put(column, result);
    }

    public String getResults(ChromosomeRepresentationInterface chromosome){
        return chromosomeResults.getIfPresent(chromosome);
    }

    public Map<Integer, List<List<String>>> getDiscretizationResults(ChromosomeRepresentationInterface chromosome) {
        return discretizationResults.getIfPresent(chromosome);
    }

    public String getDiscretizationIntervals(Integer column) {
        return discretizationIntervals.getIfPresent(column);
    }

    public void removeResults(ChromosomeRepresentationInterface chromosome){
        chromosomeResults.invalidate(chromosome);
        discretizationResults.invalidate(chromosome);
    }

    public void removeDiscretizationIntervals(){
        discretizationIntervals.cleanUp();
    }
}
