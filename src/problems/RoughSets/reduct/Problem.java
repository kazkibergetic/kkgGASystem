/**
 *
 */
package problems.RoughSets.reduct;

import chromosome.ChromosomeRepresentationInterface;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import evolver.ProblemResultCache;
import evolver.RunEvolutionContext;
import problems.Points;
import problems.ProblemInterface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author or13uw
 */
public class Problem implements ProblemInterface {

    /**
     * Dataset for a problem with all values
     */
    private static Table<Integer, Integer, String> table;

    /**
     * Points for GA System
     */
    public static ArrayList<Points> points;

    /**
     * Number of columns in the dataset
     */
    private static int numColumns;

    /**
     * Number of values in the dataset
     */
    private static int numRows;

    public void initialize(File file) {
        // TODO Auto-generated method stub
        numRows = 0;
        numColumns = 0;
        points = new ArrayList<Points>();
        table = HashBasedTable.create();

        File input = file;
        try {
            readDataSet(input);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * reads dataset from the specified file, writes values into table data
     * structure
     *
     * @param file : file to read dataset from
     */
    private void readDataSet(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;
        String[] items = new String[]{};
        int row = 0;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            numRows++;

            items = line.split(",");
            for (int col = 0; col < items.length; col++) {
                table.put(row, col, items[col]);
            }
            //System.out.println(table.row(row).hashCode());
            row++;

        }
        reader.close();
        numColumns = items.length;

        createPoints();

    }

    /**
     * creates points for OrderBased GA system
     */
    private void createPoints() {
        for (int i = 1; i < numColumns - 1; i++) {
            points.add(new Points(i));

        }

    }

    /**
     * checks of the specified set of attributes is a reduct
     *
     * @param reduct : set of attributes to check
     * @return true if it is a reduct, false otherwise
     */
    private Boolean checkReduct(Map<Integer, Points> reduct) {

        List<Integer> indexes = new ArrayList<>();
        indexes.add(0);
        indexes.add(numColumns - 1);

        ArrayList<Points> remove = new ArrayList<Points>(points);
        remove.removeAll(reduct.values());

        for (Points aRemove : remove) {
            indexes.add(aRemove.id);
        }

        Map<Integer, String> hm = new HashMap<Integer, String>();
        for (int i = 0; i < numRows; i++) {
            Map<Integer, String> row = new HashMap<>(table.row(i));
            for (int j : indexes) {
                row.remove(j);
            }
            int hc = row.hashCode();

            if (!hm.containsKey(hc)) {
                hm.put(hc, table.get(i, numColumns - 1));
            } else {
                String rez = hm.get(hc);
                if (!rez.equals(table.get(i, numColumns - 1))) {
                    return false;
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
    private ArrayList<Points> findReduct(ChromosomeRepresentationInterface chromosome) {

        Map<Integer, Points> reduct = new TreeMap<>();
        for (Points point : points) {
            reduct.put(point.id, point);
        }

        for (int i = 0; i < reduct.size(); i++) {
            Integer gene = (Integer) chromosome.getGene(i);
            Points tmp = reduct.get(gene);
            if (tmp != null) {
                reduct.remove(gene);

                if (!checkReduct(reduct)) {
                    reduct.put(tmp.id, tmp);
                }
            }
        }

        return new ArrayList<>(reduct.values());
    }

    /*
     * (non-Javadoc)
     *
     * @see problems.ProblemInterface#evaluateFitness()
     */
    @Override
    public String evaluateFitness(RunEvolutionContext runEvolutionContext, ChromosomeRepresentationInterface chromosome) {

        List<Points> reduct = this.findReduct(chromosome);

		StringBuilder result = new StringBuilder();
		for(int d = 0; d <reduct.size(); d++) {
            result.append(reduct.get(d).id).append(" ");
        }
        result.append("\n");

        ProblemResultCache problemResultCache = runEvolutionContext.getProblemResultCache();
        problemResultCache.putResults(chromosome, result.toString());


        return String.valueOf(reduct.size());
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see problems.ProblemInterface#getPoints()
	 */

    @Override
    public ArrayList<Points> getPoints() {
        // TODO Auto-generated method stub
        return Problem.points;
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
