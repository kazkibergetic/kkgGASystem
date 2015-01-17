/**
 * 
 */
package problems.RoughSets.bireduct;

import chromosome.ChromosomeRepresentationInterface;
import problems.Points;
import problems.ProblemInterface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author or13uw
 *
 */
public class Problem implements ProblemInterface {

    /**
     * Dataset for a problem with all values
     */
	private static List<List<String>> table = new ArrayList<>();

    /**
	 * Points for GA System
	 */
	public static ArrayList<Points> points;

	/**
	 * Number of values in the dataset
	 */
	private static int numRows, numColumns, numConditionalAttributes;

	public void initialize(File file) {
		// TODO Auto-generated method stub
		numRows = 0;
		numColumns = 0;
		numConditionalAttributes = 0;
		points = new ArrayList<Points>();

        try {
			readDataSet(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * reads dataset from the specified file, writes values into table data
	 * structure
	 *
	 * @param file
	 *            : file to read dataset from
	 */
	private void readDataSet(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));

		String line;
		String[] items = new String[] {};
		int row = 0;

        while ((line = reader.readLine()) != null) {
			line = line.trim();
			numRows++;

			items = line.split(",");

            List<String> columns = new ArrayList<String>();
            for (int col = 0; col < items.length; col++) {
                columns.add(items[col]);
            }
            table.add(columns);
            // System.out.println(table.row(row).hashCode());
			row++;

		}
		reader.close();
		numConditionalAttributes = items.length-2;
		numColumns = items.length;

		createPoints(numConditionalAttributes + numRows);

	}

	/**
	 * creates points for OrderBased GA system
	 */
	private void createPoints(int limit) {
		for (int i = 0; i < limit; i++) {
			//create points from 1 to n+m
			points.add(new Points(i+1));

		}

	}

	/**
	 * checks of the specified set of attributes is a reduct
	 *
	 * @return true if it is a reduct, false otherwise
	 */
	private Boolean checkReduct(Map<Integer,Points> bireductAttributes, Map<Integer,Points> bireductObjects, Map<Integer,Points> bireductObjectsByIndex) {
        Map<Integer, String> hm = new TreeMap<>();

        ArrayList<Points> pointses = new ArrayList<>(bireductObjectsByIndex.values());

        for (int row = 0; row < bireductObjects.size(); row++) {
            // skip rows which are not in the bireductObjects array
            if (bireductObjects.containsKey(row + 1)){
                List<String> columns = new ArrayList<>();
                List<String> rows = table.get(pointses.get(row).id - 1);
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
                    String value = columns.get(numColumns - 1);
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
        }

        return true;

    }

	/**
	 * tries to find reduct for specified chromosome (permutation)
	 *
	 * @param chromosome
	 *            : supplied by GA system. permutation if attributes to find
	 *            reduct
	 * @return reduct based on provided chromosome (permutation)
	 */
	private ArrayList<ArrayList<Points>> findBiReducts(ChromosomeRepresentationInterface chromosome) {

		// create an array of conditional attributes from the initial array of
		// points from 0 to numConditionalAttributes
		// Initially we have all attributes (B <- A)
        Map<Integer,Points> bireductAttributes = new TreeMap<>();
        for (Points p : points.subList(0, numConditionalAttributes)) {
            bireductAttributes.put(p.id, p);
        }

		// create an array of objects for which the attributes are valid
		// Initially we have 0 objects (X <- empty set)
        Map<Integer,Points> bireductObjectsById =  new TreeMap<>();
        Map<Integer,Points> bireductObjectsByIndex =  new TreeMap<>();

        long a = System.currentTimeMillis();


        // go through all points (m+n), where
		// n=|A| = numConditionalAttributes and m = |U|
		for (int i = 0; i < numRows + numConditionalAttributes; i++) {
            if (i % 500 == 0) {
                System.out.println("" + i + "\t"+(System.currentTimeMillis()-a));
            }
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
	public String evaluateFitness(ChromosomeRepresentationInterface chromosome) {

		// System.out.println(points.size());

		/*
		 * ArrayList<Integer> ch = new ArrayList<Integer>(); ch.add(2);
		 * ch.add(4); ch.add(8); ch.add(5); ch.add(3); ch.add(1); ch.add(9);
		 * ch.add(10); ch.add(6); ch.add(7); chromosome.setChromosome(ch);
		 * //"2, 4, 8, 5, 3, 1, 9, 10, 6, 7"
		 */
		ArrayList<ArrayList<Points>> biReduct = this.findBiReducts(chromosome);

		// System.out.println("===");

		Collections.sort(biReduct.get(0));
		Collections.sort(biReduct.get(1));
		System.out.println("attributes:");
		for (int d = 0; d < biReduct.get(0).size(); d++) {
			System.out.print(biReduct.get(0).get(d).id + " ");

		}
		System.out.println();
		System.out.println("objects:");
		for (int d = 0; d < biReduct.get(1).size(); d++) {
			System.out.print(biReduct.get(1).get(d).id + " ");

		}
		System.out.println();
		System.out.println("-----------------------");

		//String rez = String.valueOf(biReduct.get(0).size()
		//		+ (numRows - biReduct.get(1).size()));

		String rez = biReduct.get(0).size()	+ ","+ (numRows - biReduct.get(1).size());
		//System.out.println(rez);
		return rez;
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