package evolver;

import chromosome.ChromosomeRepresentationInterface;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * ProblemResultCache.
 *
 * @author Max
 */
public class ProblemResultCache {
    private Cache<ChromosomeRepresentationInterface, String> chromosomeResults = CacheBuilder.newBuilder().build();

    public void putResults(ChromosomeRepresentationInterface chromosome, String result){
        chromosomeResults.put(chromosome, result);
    }

    public String getResults(ChromosomeRepresentationInterface chromosome){
        return chromosomeResults.getIfPresent(chromosome);
    }

    public void removeResults(ChromosomeRepresentationInterface chromosome){
        chromosomeResults.invalidate(chromosome);
    }
}
