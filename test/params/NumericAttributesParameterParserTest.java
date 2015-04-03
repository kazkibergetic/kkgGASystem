package params;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * NumericAttributesParameterParserTest.
 *
 * @author Maxim Rybakov
 */
public class NumericAttributesParameterParserTest {

    private NumericAttributesParameterParser parameterParser = new NumericAttributesParameterParser();

    @Test
    public void testParseEmptyParameter(){
        assertTrue(parameterParser.getNumericAttributes("").isEmpty());
        assertTrue(parameterParser.getNumericAttributes(null).isEmpty());
        assertTrue(parameterParser.getNumericAttributes("  ").isEmpty());
    }

    @Test
    public void testParseSimpleNumbers(){
        assertThat(parameterParser.getNumericAttributes("1"), is(Arrays.asList(1)));
        assertThat(parameterParser.getNumericAttributes("1,2"), is(Arrays.asList(1,2)));
        assertThat(parameterParser.getNumericAttributes("1,2,7,8"), is(Arrays.asList(1,2,7,8)));
    }

    @Test
    public void testParseIntervalsOfNumbers(){
        assertThat(parameterParser.getNumericAttributes("1-3"), is(Arrays.asList(1,2,3)));
        assertThat(parameterParser.getNumericAttributes("4-5,6-6,10-14"), is(Arrays.asList(4,5,6,10,11,12,13,14)));
    }

    @Test
    public void testParseIntervalsOfNumbersAndSimpleNumbers(){
        assertThat(parameterParser.getNumericAttributes("1-3,7,8-10,67,78,79-80"), is(Arrays.asList(1,2,3,7,8,9,10,67,78,79,80)));
    }
}
