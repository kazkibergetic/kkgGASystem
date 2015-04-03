package params;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * NumericAttributesParameterParser.
 *
 * @author Maxim Rybakov
 */
public class NumericAttributesParameterParser {

    public List<Integer> getNumericAttributes(String parameter){
        if (parameter == null || parameter.trim().isEmpty()){
            return Collections.emptyList();
        }
        List<Integer> result = new ArrayList<>();
        String[] numberIntervals = parameter.replaceAll(" ", "").split("[,]");
        for (String numberInterval : numberIntervals) {
            String[] borders = numberInterval.split("[-]");
            //no interval
            if (borders.length == 1){
                result.add(Integer.parseInt(borders[0]));
            } else if (borders.length == 2){
                int a = Integer.parseInt(borders[0]);
                int b = Integer.parseInt(borders[1]);
                if (a > b){
                    throw new IllegalStateException("Wrong range of interval");
                }
                for (int number = a; number <= b; ++number) {
                    result.add(number);
                }
            } else {
                throw new IllegalStateException("Wrong value of parameter");
            }
        }
        return result;
    }
}
