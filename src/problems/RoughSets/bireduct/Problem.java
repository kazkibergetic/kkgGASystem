package problems.RoughSets.bireduct;

import chromosome.ChromosomeRepresentationInterface;
import com.google.common.base.Charsets;
import discretization.DiscretizationMethod;
import evolver.ProblemResultCache;
import evolver.RunEvolutionContext;
import output.DisplayInfo;
import params.ClassInitialization;
import params.Parameters;
import problems.Points;
import problems.ProblemInterface;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author or13uw
 */
public class Problem implements ProblemInterface {

    /**
     * Points for GA System
     */
    public static ArrayList<Points> points;
    /**
     * Dataset for a problem with all values
     */
    private static List<List<String>> tableByRows;
    private static List<List<String>> tableByColumns;
    private static Map<Integer, List<List<String>>> discretizationByOriginalColumn;
    /**
     * Number of values in the dataset
     */
    private static int numRows, numColumns, numConditionalAttributes;

    /**
     * Stores the history of changing indexes in original data
     * to be able to recover them after all the calculations
     */
    private static IndexRegistry indexRegistry;

    private static DiscretizationMethod<String> discretizationMethod;

    @Override
    public void initialize(RunEvolutionContext runEvolutionContext, File file) {
        numRows = 0;
        numColumns = 0;
        numConditionalAttributes = 0;
        indexRegistry = new IndexRegistry();
        points = new ArrayList<Points>();
        tableByRows = new ArrayList<>();
        tableByColumns = new ArrayList<>();
        discretizationByOriginalColumn = new HashMap<>();
        discretizationMethod = new ClassInitialization().getDiscretizationMethod();
        try {
            readDataSet(runEvolutionContext, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * reads dataset from the specified file, writes values into table data
     * structure
     *
     * @param file : file to read dataset from
     */
    private void readDataSet(RunEvolutionContext runEvolutionContext, File file) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(file.getAbsolutePath()), Charsets.UTF_8);
        if (Parameters.isMissingAttributeEnable()) {
            MissingAttributeInputDataEnricher enricher = new MissingAttributeInputDataEnricher();
            lines = enricher.enrichData(lines);
            enricher.writeMissingAttributesChanges(runEvolutionContext);
        }
        String delimiter = Parameters.getDelimiter();
        for (String line : lines) {
            line = line.trim();
            if (line.length() > 0) {
                String[] items = line.split(delimiter);
                tableByRows.add(numRows++, new ArrayList<>(Arrays.asList(items)));
            }
        }

        int columns = tableByRows.get(0).size();
        for (int i = 0; i < columns; ++i) {
            List<String> column = new ArrayList<>(numRows);
            for (int j = 0; j < numRows; ++j) {
                column.add(tableByRows.get(j).get(i));
            }
            tableByColumns.add(column);
        }

        boolean nameColumnExists = Parameters.isNameColumnExists();
        int nameColumnPosition = Parameters.getNameColumnPosition();
        int decisionColumnPosition = Parameters.getDecisionColumnPosition();
        reorderNameNadDecisionAttributes(indexRegistry, tableByColumns, nameColumnExists, nameColumnPosition, decisionColumnPosition);
        List<Integer> numericAttributes = Parameters.getNumericAttributes();
        discretizeNumericAttributes(runEvolutionContext, indexRegistry, tableByColumns, numericAttributes);

        numRows = tableByColumns.get(0).size();
        numColumns = tableByColumns.size();
        numConditionalAttributes = numColumns - 2;

        tableByRows.clear();
        for (int i = 0; i < numRows; ++i) {
            List<String> row = new ArrayList<>();
            for (int j = 0; j < numColumns; ++j) {
                row.add(tableByColumns.get(j).get(i));
            }
            tableByRows.add(row);
        }

        printFirstValues();
        createPoints(numConditionalAttributes + numRows);
    }

    private void discretizeNumericAttributes(RunEvolutionContext runEvolutionContext, IndexRegistry indexRegistry, List<List<String>> tableByColumns, List<Integer> numericAttributes) {
        for (Integer numericAttribute : numericAttributes) {
            int actualIndex = indexRegistry.getCurrentIndex(numericAttribute);
            List<String> column = tableByColumns.get(actualIndex);
            List<List<String>> newColumns = discretizationMethod.discretize(runEvolutionContext, numericAttribute, column);
            discretizationByOriginalColumn.put(numericAttribute, newColumns);
            insertNewColumns(indexRegistry, tableByColumns, actualIndex, newColumns);
        }
    }

    private void insertNewColumns(IndexRegistry indexRegistry, List<List<String>> tableByColumns, int actualIndex, List<List<String>> newColumns) {
        int columnsNumber = newColumns.size();
        indexRegistry.moveIndexesByValue(actualIndex, tableByColumns.size(), columnsNumber - 1);
        for (int i = 0; i < columnsNumber; ++i) {
            if (i == 0){
                tableByColumns.set(actualIndex, newColumns.get(i));
            } else {
                tableByColumns.add(actualIndex, newColumns.get(i));
            }
        }
    }

    private void printFirstValues() {
        int minRows = Math.min(numRows, 5);
        for (int j = 0; j < 2; ++j) {
            for (int i = 0; i < minRows; ++i) {
                List<String> currentRow = tableByRows.get(i);
                String var = j == 0 ? String.format("Name #%d ", i + 1) : String.format("Decision #%d ", i + 1);
                String value = j == 0 ? currentRow.get(0) : currentRow.get(currentRow.size() - 1);
                DisplayInfo.displayInitialization(var, value);
            }
        }
    }

    protected void reorderNameNadDecisionAttributes(IndexRegistry indexRegistry, List<List<String>> tableByColumns, boolean nameColumnExists, int nameColumnPosition, int decisionColumnPosition) {
        int columns = tableByColumns.size();
        int rows = tableByColumns.get(0).size();

        if (nameColumnExists && decisionColumnPosition == 0 && nameColumnPosition == columns - 1) {
            swap(tableByColumns, 0, columns - 1);
            indexRegistry.registerMapping(0, columns - 1);
        } else {
            if (decisionColumnPosition != -1) {
                swap(tableByColumns, columns - 1, decisionColumnPosition);
                indexRegistry.registerMapping(columns - 1, decisionColumnPosition);
            }
            if (nameColumnExists && nameColumnPosition != 0) {
                swap(tableByColumns, 0, nameColumnPosition);
                indexRegistry.registerMapping(0, nameColumnPosition);
            }
            if (!nameColumnExists) {
                List<String> emptyColumn = new ArrayList<>();
                for (int i = 0; i < rows; ++i) {
                    emptyColumn.add("");
                }
                tableByColumns.add(0, emptyColumn);

                for (int i = columns; i > 0; --i) {
                    indexRegistry.registerMapping(i, i - 1);
                }
            }
        }
    }

    private void swap(List<List<String>> table, int i, int j) {
        List<String> tmp = table.get(i);
        table.set(i, table.get(j));
        table.set(j, tmp);
    }

    /**
     * creates points for OrderBased GA system
     */
    private void createPoints(int limit) {
        for (int i = 0; i < limit; i++) {
            //create points from 1 to n+m
            points.add(new Points(i + 1));

        }

    }

    /**
     * checks of the specified set of attributes is a reduct
     *
     * @return true if it is a reduct, false otherwise
     */
    private Boolean checkReduct(Map<Integer, Points> bireductAttributes, Map<Integer, Points> bireductObjects, Map<Integer, Points> bireductObjectsByIndex) {
        Map<Integer, String> hm = new TreeMap<>();

        ArrayList<Points> pointses = new ArrayList<>(bireductObjectsByIndex.values());

        for (int row = 0; row < bireductObjects.size(); row++) {
            List<String> columns = new ArrayList<>();
            List<String> rows = tableByRows.get(pointses.get(row).id - 1);
            for (int column = 0; column < numColumns; column++) {
                if (bireductAttributes.containsKey(column)) {
                    columns.add(rows.get(column));
                } else {
                    columns.add("");
                }
            }

            // skip rows which are empty
            if (!columns.isEmpty()) {
                // take a hash code if the current row
                int hc = columns.hashCode();

                // if we don't have a record with the same hash code (it means it's the first occurrence if the raw with that set of conditional attributes)
                String value = tableByRows.get(pointses.get(row).id - 1).get(numColumns - 1);
                if (!hm.containsKey(hc)) {

                    // we add the hash code and decision attribute to the hash map
                    hm.put(hc, value);

                    // else if we have a record with the same hash code (it means we have a row with the same conditional attributes)
                } else {
                    // take the decision attribute if the previous record
                    String rez = hm.get(hc);

                    // if the decision attributes are not the same, return false
                    if (!rez.equals(value)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * tries to find reduct for specified chromosome (permutation)
     *
     * @param chromosome : supplied by GA system. permutation if attributes to find
     *                   reduct
     * @return reduct based on provided chromosome (permutation)
     */
    private ArrayList<ArrayList<Points>> findBiReducts(ChromosomeRepresentationInterface chromosome) {

        // create an array of conditional attributes from the initial array of
        // points from 0 to numConditionalAttributes
        // Initially we have all attributes (B <- A)
        Map<Integer, Points> bireductAttributes = new TreeMap<>();
        for (Points p : points.subList(0, numConditionalAttributes)) {
            bireductAttributes.put(p.id, p);
        }

        // create an array of objects for which the attributes are valid
        // Initially we have 0 objects (X <- empty set)
        Map<Integer, Points> bireductObjectsById = new TreeMap<>();
        Map<Integer, Points> bireductObjectsByIndex = new TreeMap<>();

        // go through all points (m+n), where
        // n=|A| = numConditionalAttributes and m = |U|
        for (int i = 0; i < numRows + numConditionalAttributes; i++) {
            // if a(i) <=n, if the value of gene at i-th position less than
            // number of conditional attributes
            Integer gene = (Integer) chromosome.getGene(i);
            if (gene <= numConditionalAttributes) {

                Points tmp = bireductAttributes.get(gene);
                // if the attribute that is equal to the value of gene at i-th
                // position exists, we store the index of the attribute
                if (tmp != null) {

                    // remove the attribute
                    bireductAttributes.remove(tmp.id);

                    // check if bireductAttributes is a minimal subset of attributes
                    // for bireductObjects
                    if (!checkReduct(bireductAttributes, bireductObjectsById, bireductObjectsByIndex)) {

                        // if it's not, restore previous values of the attributes
                        // array
                        //bireductAttributes = new ArrayList<Points>(initialbiReductAttributes);
                        bireductAttributes.put(tmp.id, tmp);

                    }
                }
                // else if a(i) > n, if the value of gene at i-th position more
                // than number of conditional attributes (it means we have to
                // work not with attributes but objects)
            } else {

                // add the object in the object array
                Points tmp = new Points(gene - numConditionalAttributes);
                bireductObjectsById.put(tmp.id, tmp);
                bireductObjectsByIndex.put(bireductObjectsByIndex.size(), tmp);

                // check if bireductAttributes is a minimal subset of attributes
                // for bireductObjects
                if (!checkReduct(bireductAttributes, bireductObjectsById, bireductObjectsByIndex)) {

                    // if it's not, restore previous values of the objects
                    // array (remove recently added object)
                    bireductObjectsById.remove(tmp.id);
                    bireductObjectsByIndex.remove(bireductObjectsByIndex.size() - 1);
                }
            }

        }

        ArrayList<ArrayList<Points>> result = new ArrayList<ArrayList<Points>>();
        result.add(new ArrayList<>(bireductAttributes.values()));
        result.add(new ArrayList<>(bireductObjectsById.values()));

        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see problems.ProblemInterface#evaluateFitness()
     */
    @SuppressWarnings("unchecked")
    @Override
    public String evaluateFitness(RunEvolutionContext runEvolutionContext, ChromosomeRepresentationInterface chromosome) {

        List<ArrayList<Points>> biReduct = this.findBiReducts(chromosome);

        Set<Integer> attrs = new TreeSet<>();
        for(Points points: biReduct.get(0)){
            attrs.add(indexRegistry.getOriginalIndex(points.id));
        }

        List<Points> objects = biReduct.get(1);
        Collections.sort(objects);

        StringBuilder result = new StringBuilder();
        result.append("attributes:").append("\n");

        for (int d: attrs) {
            result.append(d).append(" ");
        }
        result.append("\n").append("objects:").append("\n");
        for (Points object : objects) {
            result.append(object.id).append(" ");
        }
        result.append("\n");

        String chromosomeResult = result.toString();

        System.out.println(chromosome.toString());
        System.out.println(chromosomeResult);
        System.out.println();
        System.out.println("-----------------------");

        ProblemResultCache problemResultCache = runEvolutionContext.getProblemResultCache();
        problemResultCache.putResults(chromosome, chromosomeResult);

        Map<Integer, List<List<String>>> discretizationResults = new HashMap<>();
        for (Integer attr : attrs) {
            if (discretizationByOriginalColumn.containsKey(attr)){
                discretizationResults.put(attr, discretizationByOriginalColumn.get(attr));
            }
        }

        problemResultCache.putResults(chromosome, discretizationResults);

        return attrs.size() + "," + (numRows - objects.size());
    }

	/*
     * (non-Javadoc)
	 * 
	 * @see problems.ProblemInterface#getPoints()
	 */

    @Override
    public ArrayList<Points> getPoints() {
        // TODO Auto-generated method stub
        return this.points;
    }

    /*
     * (non-Javadoc)
     *
     * @see problems.ProblemInterface#getSizeOfChromosome()
     */
    @Override
    public int getSizeOfChromosome() {
        // TODO Auto-generated method stub
        return points.size();
    }

}