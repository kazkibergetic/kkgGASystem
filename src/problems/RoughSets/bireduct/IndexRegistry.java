package problems.RoughSets.bireduct;

import java.util.HashMap;
import java.util.Map;

/**
 * IndexRegistry.
 *
 * @author Maxim Rybakov
 */
public class IndexRegistry {
    private Map<Integer,Integer> originalByCurrentIndexes = new HashMap<>();

    public void registerOneDirectMapping(int first, int second) {
        int originalIndex = getOriginalIndex(second);
        originalByCurrentIndexes.put(first, originalIndex);
    }

    public void registerMapping(int first, int second) {
        int originalIndex1 = getOriginalIndex(first);
        int originalIndex2 = getOriginalIndex(second);
        boolean hasChild1 = first != originalIndex1;
        boolean hasChild2 = second != originalIndex2;
        if (!hasChild2){
            originalByCurrentIndexes.put(originalIndex2, originalIndex1);
        } else {
            originalByCurrentIndexes.put(second, originalIndex1);
        }
        if (!hasChild1){
            originalByCurrentIndexes.put(originalIndex1, originalIndex2);
        } else {
            originalByCurrentIndexes.put(first, originalIndex2);
        }
    }

    public int getOriginalIndex(int current){
        Integer result = originalByCurrentIndexes.get(current);
        return result != null? result: current;
    }

    public int getCurrentIndex(int original){
        for (Map.Entry<Integer, Integer> entry : originalByCurrentIndexes.entrySet()) {
            if(entry.getValue() == original) {
                return entry.getKey();
            }
        }
        return original;
    }
}
