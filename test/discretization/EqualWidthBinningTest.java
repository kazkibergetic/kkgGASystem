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
 * EqualWidthBinningTest.
 *
 * @author Maxim Rybakov
 */
public class EqualWidthBinningTest {

    private RunEvolutionContext runEvolutionContext;

    @Before
    public void before(){
        runEvolutionContext = new RunEvolutionContext();
        runEvolutionContext.setProblemResultCache(new ProblemResultCache());
    }

    @Test
    public void testForEmptyList(){
        EqualWidthBinning equalWidthBinning = new EqualWidthBinning(3);
        assertTrue(equalWidthBinning.discretize(runEvolutionContext, 0, Collections.<String>emptyList()).isEmpty());
    }

    @Test
    public void testForDoubleList(){
        EqualWidthBinning equalWidthBinning = new EqualWidthBinning(3);
        assertThat(equalWidthBinning.discretize(runEvolutionContext, 0, Arrays.asList("1", "2", "3")),  is(Arrays.asList(
                Arrays.asList("1","0","0"),
                Arrays.asList("0","1","0"),
                Arrays.asList("0","0","1")
        )));
        assertThat(equalWidthBinning.discretize(runEvolutionContext, 0, Arrays.asList("3", "1", "2")),  is(Arrays.asList(
                Arrays.asList("0","1","0"),
                Arrays.asList("0","0","1"),
                Arrays.asList("1","0","0")
        )));

        assertThat(equalWidthBinning.discretize(runEvolutionContext, 0, Arrays.asList("1", "2", "3", "4")),  is(Arrays.asList(
                Arrays.asList("1","0","0","0"),
                Arrays.asList("0","1","0","0"),
                Arrays.asList("0","0","1","1")
        )));

        assertThat(equalWidthBinning.discretize(runEvolutionContext, 0, Arrays.asList("-10", "-20", "-30", "10")),  is(Arrays.asList(
                Arrays.asList("0","1","1","0"),
                Arrays.asList("1","0","0","0"),
                Arrays.asList("0","0","0","1")
        )));
    }
}
