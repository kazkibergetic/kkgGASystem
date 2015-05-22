package fitness.multiObjective;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * SumOfRanksTest.
 *
 * @author Max
 */
public class SumOfRanksTest {

    private SumOfRanks sumOfRanks = new SumOfRanks();

    @Test
    public void testExample() {
        String[] objectives = {"1,2", "2,3", "1,3", "3,4", "2,4", "1,0"};
        List<Double> expectedRanks = Arrays.asList(0.21764, 0.37647, 0.27647, 0.53529, 0.43529, 0.15882);

        List<String> ranks = Arrays.asList(objectives);
        List<Double> calculatedRanks = sumOfRanks.sorCalculations(ranks);

        for (int i = 0; i < expectedRanks.size(); ++i) {
            assertEquals(expectedRanks.get(i), calculatedRanks.get(i), 1e-5);
        }
    }

}
