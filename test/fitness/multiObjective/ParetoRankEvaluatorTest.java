package fitness.multiObjective;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * fitness.multiObjective.ParetoRankEvaluatorTest.
 *
 * @author Maxim Shchekotov
 */
public class ParetoRankEvaluatorTest extends ParetoRankEvaluator {

    @Before
    public void beforeTest() {

    }

    @Test
    public void paretoCalculatingForFourTest() {
        populationSize=5;
        String[] objectives = {"2.34,0.73,124,30", "0,1.32,340,100", "3.56,3.45,1200,1", "4.32,1.03,350,45", "5.27,0.89,350,15"};
        ranks = new ArrayList<>(Arrays.asList(objectives));

        List<Double> calculatedRanks = paretoCalculations();
        List<Double> expectedRanks = new ArrayList<>(Arrays.asList(1.0, 1.0, 1.0, 2.0, 1.0));
        Assert.assertEquals(expectedRanks, calculatedRanks);
    }

    @Test
     public void paretoCalculatingFromBookTest() {
        populationSize=5;
        String[] objectives = {"2.34,0.73", "0,1.32", "3.56,3.45", "4.32,1.03", "5.27,0.89"};
        ranks = new ArrayList<>(Arrays.asList(objectives));

        List<Double> calculatedRanks = paretoCalculations();
        List<Double> expectedRanks = new ArrayList<>(Arrays.asList(1.0, 1.0, 2.0, 2.0, 2.0));
        Assert.assertEquals(expectedRanks, calculatedRanks);
    }

    @Test
    public void paretoCalculatingFromBookWithFusionTest() {
        populationSize=5;
        String[] objectives = {"3.56,3.45", "2.34,0.73", "5.27,0.89", "0,1.32", "4.32,1.03" };
        ranks = new ArrayList<>(Arrays.asList(objectives));

        List<Double> calculatedRanks = paretoCalculations();
        List<Double> expectedRanks = new ArrayList<>(Arrays.asList(2.0, 1.0, 2.0, 1.0, 2.0));
        Assert.assertEquals(expectedRanks, calculatedRanks);
    }

    @Test
    public void paretoCalculatingFromBookWithReverseTest() {
        populationSize=5;
        String[] objectives = {"2.34,0.73", "0,1.32", "3.56,3.45", "4.32,1.03", "5.27,0.89"};

        ArrayList<String> objectivesList = new ArrayList<>(Arrays.asList(objectives));
        Collections.reverse(objectivesList);
        ranks = objectivesList;

        List<Double> calculatedRanks = paretoCalculations();
        List<Double> expectedRanks = new ArrayList<>(Arrays.asList(2.0, 2.0, 2.0, 1.0, 1.0));
        Assert.assertEquals(expectedRanks, calculatedRanks);
    }

    @Test
    public void paretoCalculatingFromCodeTest() {
        populationSize=5;
        String[] objectives = {"2,1000", "4,600", "8,400", "7,800", "9,500"};
        List<String> strings = Arrays.asList(objectives);
        ranks = new ArrayList<>(strings);

        List<Double> calculatedRanks = paretoCalculations();

        List<Double> expectedRanks = new ArrayList<>(Arrays.asList(1.0, 1.0, 1.0, 2.0, 2.0));
        Assert.assertEquals(expectedRanks, calculatedRanks);
    }


    @Test
    public void paretoCalculatingFromCodeWithFusionTest() {
        populationSize=5;
        String[] objectives = { "7,800","4,600", "8,400","9,500","2,1000"};
        List<String> strings = Arrays.asList(objectives);
        ranks = new ArrayList<>(strings);

        List<Double> calculatedRanks = paretoCalculations();

        List<Double> expectedRanks = new ArrayList<>(Arrays.asList(2.0, 1.0, 1.0, 2.0, 1.0));
        Assert.assertEquals(expectedRanks, calculatedRanks);
    }

    @Test
    public void paretoCalculatingFromCodeWithReverseTest() {
        populationSize=5;
        String[] objectives = { "9,500","7,800","8,400","4,600","2,1000"};
        List<String> strings = Arrays.asList(objectives);
        ranks = new ArrayList<>(strings);

        List<Double> calculatedRanks = paretoCalculations();

        List<Double> expectedRanks = new ArrayList<>(Arrays.asList(2.0, 2.0, 1.0, 1.0, 1.0));
        Assert.assertEquals(expectedRanks, calculatedRanks);
    }

    @Test
     public void paretoCalculatingWithTreeRanksTest() {
        populationSize=6;
        String[] objectives = {"8,600", "2,1000", "4,400",  "7,800", "9,900", "10,800"};
        ArrayList<String> objectivesList = new ArrayList<>(Arrays.asList(objectives));
        ranks = objectivesList;
        List<Double> calculatedRanks = paretoCalculations();
        List<Double> expectedRanks = new ArrayList<>(Arrays.asList(2.0, 1.0, 1.0, 2.0, 3.0, 3.0));
        Assert.assertEquals(expectedRanks, calculatedRanks);
    }

    @Test
    public void paretoCalculatingWithTreeRanksTest2() {
        populationSize=6;
        String[] objectives = {"2,1000", "4,400", "8,600", "7,800", "9,900", "10,800"};
        ArrayList<String> objectivesList = new ArrayList<>(Arrays.asList(objectives));
        Collections.reverse(objectivesList);
        ranks = objectivesList;
        List<Double> calculatedRanks = paretoCalculations();
        List<Double> expectedRanks = new ArrayList<>(Arrays.asList(3.0, 3.0, 2.0, 2.0, 1.0, 1.0));
        Assert.assertEquals(expectedRanks, calculatedRanks);
    }

    @Test
    public void isNotDominatedByAnyIndividualTest() {
        populationSize=5;
        String[] objectives = {"2.34,0.73,124,30", "0,1.32,340,100", "3.56,3.45,1200,1", "4.32,1.03,350,45", "5.27,0.89,350,15"};
        List<String> strings = Arrays.asList(objectives);
        List<List<Double>> objectiveSets = parseObjectiveSets(strings);
        List<Double> selectedIndividualObjectives = objectiveSets.get(0);
        boolean b = isNotDominatedByAnyIndividual(selectedIndividualObjectives,objectiveSets);

        Assert.assertEquals(true, b);
         selectedIndividualObjectives = objectiveSets.get(1);
         b = isNotDominatedByAnyIndividual(selectedIndividualObjectives,objectiveSets);

        Assert.assertEquals(true, b);

        selectedIndividualObjectives = objectiveSets.get(2);
        b = isNotDominatedByAnyIndividual(selectedIndividualObjectives,objectiveSets);

        Assert.assertEquals(true, b);

        selectedIndividualObjectives = objectiveSets.get(3);
        b = isNotDominatedByAnyIndividual(selectedIndividualObjectives,objectiveSets);

        Assert.assertEquals(false, b);

        selectedIndividualObjectives = objectiveSets.get(4);
        b = isNotDominatedByAnyIndividual(selectedIndividualObjectives,objectiveSets);

        Assert.assertEquals(true, b);
    }

    @Test
     public void findNonDominatedIndividualIndexesTest() {
        populationSize=5;
        String[] objectives = {"9,500","7,800","8,400","4,600","2,1000"};
        List<String> strings = Arrays.asList(objectives);
        List<List<Double>> objectiveSets = parseObjectiveSets(strings);
        List<Integer> calculatedIndexes = findNonDominatedIndividualIndexes(objectiveSets);

        List<Integer> expectedIndexes = new ArrayList<>(Arrays.asList(2,3,4));
        Assert.assertEquals(expectedIndexes, calculatedIndexes);
    }

    @Test
    public void findNonDominatedIndividualIndexesTest2() {
        populationSize=5;
        String[] objectives = {"2.34,0.73", "0,1.32", "3.56,3.45", "4.32,1.03", "5.27,0.89"};
        List<String> strings = Arrays.asList(objectives);
        List<List<Double>> objectiveSets = parseObjectiveSets(strings);
        List<Integer> calculatedIndexes = findNonDominatedIndividualIndexes(objectiveSets);

        List<Integer> expectedIndexes = new ArrayList<>(Arrays.asList(0,1));
        Assert.assertEquals(expectedIndexes, calculatedIndexes);
    }
}
