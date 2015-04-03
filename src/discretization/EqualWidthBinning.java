package discretization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * EqualWidthBinning.
 *
 * @author Maxim Rybakov
 */
public class EqualWidthBinning implements DiscretizationMethod<String> {

    private int binNumber;

    public EqualWidthBinning(int binNumber) {
        this.binNumber = binNumber;
    }

    /**
     * Splits original data to several non-numeric columns using EqualWidthBinning algorithm.
     * @param originalData data to discretize
     * @return list of new non-numeric columns
     */
    @Override
    public List<List<String>> discretize(List<String> originalData) {
        List<List<String>> result = new ArrayList<>(binNumber);
        if (!originalData.isEmpty()) {
            List<Double> values = new ArrayList<>(originalData.size());
            for (String string : originalData) {
                values.add(Double.parseDouble(string));
            }
            Double max = Collections.max(values);
            Double min = Collections.min(values);
            double width = (max - min) / binNumber;

            for(int i = 1; i <= binNumber; ++i){
                List<String> column = new ArrayList<>(originalData.size());
                result.add(column);
                for (Double value : values) {
                    double leftRange = min + width * (i - 1);
                    double rightRange = min + width * i;
                    boolean inside = leftRange <= value && value < rightRange;
                    if (!inside && i == binNumber && leftRange < value && value <= rightRange){
                        inside = true;
                    }
                    column.add(inside? "1": "0");
                }
            }
        }
        return result;
    }
}
