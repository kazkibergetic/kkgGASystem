package problems.RoughSets.bireduct;

import java.util.ArrayList;
import java.util.List;

/**
 * IndexRegistry.
 *
 * @author Maxim Rybakov
 */
public class IndexRegistry {

    private static class Permutation{
        int fromIndex;
        int toIndex;

        public Permutation(int fromIndex, int toIndex) {
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
        }

        public int getOriginalIndex(int current){
            if (toIndex == current) {
                current = fromIndex;
            }
            return current;
        }

        public int getCurrentIndex(int original){
            if (fromIndex == original) {
                original = toIndex;
            }
            return original;
        }
    }

    private static class SimultaneousPermutation extends Permutation{
        public SimultaneousPermutation(int fromIndex, int toIndex) {
            super(fromIndex, toIndex);
        }

        public int getOriginalIndex(int current){
            if (toIndex == current) {
                return fromIndex;
            }
            if (fromIndex == current) {
                return toIndex;
            }
            return current;
        }

        public int getCurrentIndex(int original){
            if (toIndex == original) {
                return fromIndex;
            }
            if (fromIndex == original) {
                return toIndex;
            }
            return original;
        }
    }

    private List<Permutation> permutations = new ArrayList<>();

    public void registerMapping(int from, int to) {
        permutations.add(new SimultaneousPermutation(from, to));
    }

    public int getOriginalIndex(int current){
        for(int i = permutations.size() - 1; i >=0; --i){
            Permutation permutation = permutations.get(i);
            current = permutation.getOriginalIndex(current);
        }
        return current;
    }

    public int getCurrentIndex(int original){
        for(Permutation permutation: permutations){
            original = permutation.getCurrentIndex(original);
        }
        return original;
    }

    public void moveIndexesByValue(int first, int max, int count){
        for(int i = max - 1; i >= first + 1; --i){
            permutations.add(new Permutation(i, i + count));
        }
        for (int i = 1; i <= count; ++i) {
            permutations.add(new Permutation(first, first + i));
        }
    }
}
