package discretization;

import evolver.ProblemResultCache;
import evolver.RunEvolutionContext;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * EqualFrequencyBinningTest.
 *
 * @author Maxim Rybakov
 */
public class EqualFrequencyBinningTest {

    private RunEvolutionContext runEvolutionContext;

    @Before
    public void before(){
        runEvolutionContext = new RunEvolutionContext();
        runEvolutionContext.setProblemResultCache(new ProblemResultCache());
    }

    @Test
    public void testForEmptyList() {
        EqualFrequencyBinning equalFrequencyBinning = new EqualFrequencyBinning(3);
        assertTrue(equalFrequencyBinning.discretize(runEvolutionContext, 0, Collections.<String>emptyList()).isEmpty());
    }

    @Test
    public void testForSmallVectorSize() {
        EqualFrequencyBinning equalFrequencyBinning = new EqualFrequencyBinning(3);
        assertThat(equalFrequencyBinning.discretize(runEvolutionContext, 0, Arrays.asList("1", "2")), is(Arrays.asList(
                Arrays.asList("1", "0"),
                Arrays.asList("0", "1")
        )));
    }

    @Test
    public void testForDoubleList() {
        EqualFrequencyBinning equalFrequencyBinning = new EqualFrequencyBinning(3);
        assertThat(equalFrequencyBinning.discretize(runEvolutionContext, 0, Arrays.asList("1", "2", "3")), is(Arrays.asList(
                Arrays.asList("1", "0", "0"),
                Arrays.asList("0", "1", "0"),
                Arrays.asList("0", "0", "1")
        )));
        assertThat(equalFrequencyBinning.discretize(runEvolutionContext, 0, Arrays.asList("3", "1", "2")), is(Arrays.asList(
                Arrays.asList("0", "1", "0"),
                Arrays.asList("0", "0", "1"),
                Arrays.asList("1", "0", "0")
        )));

        assertThat(equalFrequencyBinning.discretize(runEvolutionContext, 0, Arrays.asList("1", "2", "3", "4")), is(Arrays.asList(
                Arrays.asList("1", "0", "0", "0"),
                Arrays.asList("0", "1", "0", "0"),
                Arrays.asList("0", "0", "1", "1")
        )));

        assertThat(equalFrequencyBinning.discretize(runEvolutionContext, 0, Arrays.asList("-10", "-20", "-30", "10")), is(Arrays.asList(
                Arrays.asList("0", "0", "1", "0"),
                Arrays.asList("0", "1", "0", "0"),
                Arrays.asList("1", "0", "0", "1")
        )));
    }
}
