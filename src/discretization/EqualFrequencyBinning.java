package discretization;

import evolver.RunEvolutionContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * EqualFrequencyBinning.
 *
 * @author Maxim Rybakov
 */
public class EqualFrequencyBinning implements DiscretizationMethod<String> {

    private int binNumber;

    public EqualFrequencyBinning(int binNumber) {
        this.binNumber = binNumber;
    }
    /**
     * Splits original data to several non-numeric columns using EqualFrequencyBinning algorithm.
     * @param originalData data to discretize
     * @return list of new non-numeric columns
     */
    @Override
    public List<List<String>> discretize(RunEvolutionContext runEvolutionContext, int attributeIndex, List<String> originalData) {
        List<List<String>> result = new ArrayList<>(binNumber);
        if (!originalData.isEmpty()) {
            int actualBinNumber = binNumber;
            int groupSize = originalData.size() / actualBinNumber;
            if (groupSize == 0){
                actualBinNumber = originalData.size();
                groupSize = originalData.size() / actualBinNumber;
            }
            boolean equalSize = originalData.size() % actualBinNumber == 0;

            List<Item> values = new ArrayList<>(originalData.size());
            int i = 0;
            for (String string : originalData) {
                values.add(new Item(i++, Double.parseDouble(string)));
            }
            Collections.sort(values);

            StringBuilder intervalsInfo = new StringBuilder();

            for(int j = 1; j <= actualBinNumber; ++j){
                List<String> column = new ArrayList<>(Collections.nCopies(originalData.size(), ""));
                result.add(column);
                int leftRange = groupSize * (j - 1);
                int rightRange = groupSize * j;
                if (j == actualBinNumber && !equalSize){
                    rightRange = values.size();
                }
                for(i = 0; i < values.size(); ++i) {
                    boolean inside = leftRange <= i && i < rightRange;
                    Item item = values.get(i);
                    int originalIndex = item.getOriginalIndex();
                    column.set(originalIndex, inside? "1": "0");
                }

                intervalsInfo.append(j)
                        .append(j == 1 ? " (" : " [")
                        .append(j == 1 ? "-inf" : values.get(leftRange).getValue())
                        .append(" , ")
                        .append(j == binNumber ? "+inf" : values.get(rightRange - 1).getValue())
                        .append(j == binNumber ? ") " : "] ")
                        .append("\n");
            }

            runEvolutionContext.getProblemResultCache().putDiscretizationIntervals(attributeIndex, intervalsInfo.toString());
        }
        return result;
    }

    private static class Item implements Comparable<Item> {
        private int originalIndex;
        private double value;

        public Item(int originalIndex, double value) {
            this.originalIndex = originalIndex;
            this.value = value;
        }

        public int getOriginalIndex() {
            return originalIndex;
        }

        public double getValue() {
            return value;
        }

        @Override
        public int compareTo(Item rhs) {
            return Double.compare(value, rhs.getValue());
        }
    }
}
