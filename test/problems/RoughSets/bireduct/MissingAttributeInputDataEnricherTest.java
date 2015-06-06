package problems.RoughSets.bireduct;

import org.junit.Assert;
import org.junit.Test;
import params.Parameters;

import java.util.Arrays;
import java.util.List;

/**
 * MissingAttributeInputDataEnricherTest.
 *
 * @author Maxim Shchekotov
 */
public class MissingAttributeInputDataEnricherTest extends Parameters {

    private MissingAttributeInputDataEnricher enricher = new MissingAttributeInputDataEnricher();

    @Test
    public void testOneLineWithOneMissingValue() {
        String[] strings = {"1,3,5,7,8,9,1,8,5,7",
                "1,3,?,4,2,2,?,4,8,2",
                "3,1,8,0,1,2,5,5,4,4",
                "1,3,7,4,2,2,2,2,8,3",
                "3,5,?,7,4,8,3,2,4,4",
                "1,3,4,7,4,5,3,8,4,3"};
        Parameters.missingAttributeSymbol = "?";
        List<String> lines = Arrays.asList(strings);
        List<String> enrichedLines = enricher.enrichData(lines);
        Assert.assertEquals("1,3,7,4,2,2,2,4,8,2", enrichedLines.get(1));
        Assert.assertEquals("3,5,4,7,4,8,3,2,4,4", enrichedLines.get(4));
    }

    @Test
    public void testOneLineWithTwoMissingValues() {
        String[] strings = {
                "9,2,5,?,8,9,1,0,5,7",
                "1,3,8,4,2,2,6,4,8,2",
                "3,1,8,0,1,2,5,5,4,4",
                "1,3,7,4,2,2,2,9,9,9",
                "3,5,0,7,4,8,3,2,4,4",
                "1,3,4,7,4,5,3,8,4,3"};
        Parameters.missingAttributeSymbol = "?";
        List<String> lines = Arrays.asList(strings);
        List<String> enrichedLines = enricher.enrichData(lines);
        Assert.assertEquals("9,2,5,4,8,9,1,0,5,7",enrichedLines.get(0));

    }

    @Test
    public void testLinesWithoutMissingValues() {
        String[] strings = {
                "9,2,5,7,8,9,1,0,5,7",
                "1,3,8,4,2,2,6,4,8,2",
                "3,1,8,0,1,2,5,5,4,4",
                "1,3,7,4,2,2,2,9,9,9",
                "3,5,0,7,4,8,3,2,4,4",
                "1,3,4,7,4,5,3,8,4,3"};
        Parameters.missingAttributeSymbol = "?";
        List<String> lines = Arrays.asList(strings);
        List<String> enrichedLines = enricher.enrichData(lines);
        Assert.assertEquals("9,2,5,7,8,9,1,0,5,7",enrichedLines.get(0));
    }

    @Test
    public void testLinesWithSameMissingValues() {
        String[] strings = {
                "1,3,5,7,8,9,1,8,5,7",
                "1,3,?,4,2,2,?,4,8,2",
                "3,1,8,0,1,2,5,5,4,4",
                "1,3,?,4,2,2,2,2,8,3",
                "3,5,?,7,4,8,3,2,4,4",
                "1,3,4,7,4,5,3,8,4,3"};
        Parameters.missingAttributeSymbol = "?";
        List<String> lines = Arrays.asList(strings);
        List<String> enrichedLines = enricher.enrichData(lines);
        Assert.assertEquals("1,3,5,4,2,2,1,4,8,2",enrichedLines.get(1));
        Assert.assertEquals("3,5,4,7,4,8,3,2,4,4",enrichedLines.get(4));

    }

    @Test
    public void testOnRealNumbersData(){
        String[] strings = {
                "123,3,5,7,8,9,1,8,5,7",
                "1,1,?,4,2,2,1,4,8,2"
        };
        Parameters.missingAttributeSymbol = "?";
        List<String> lines = Arrays.asList(strings);
        List<String> enrichedLines = enricher.enrichData(lines);
        Assert.assertEquals("1,1,5,4,2,2,1,4,8,2", enrichedLines.get(1));
    }

    @Test
    public void testOnRealStringData(){
        String[] strings = {
                "x_1,sunny,hot,high,weak,no",
                "x_2,sunny,hot,high,strong,no",
                "x_3,?,hot,high,weak,yes"
        };
        Parameters.missingAttributeSymbol = "?";
        List<String> lines = Arrays.asList(strings);
        List<String> enrichedLines = enricher.enrichData(lines);
        Assert.assertEquals("x_3,sunny,hot,high,weak,yes", enrichedLines.get(2));
    }
}
