/**
 * 
 */
package problems.RoughSets.bireduct;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import chromosome.ChromosomeRepresentationInterface;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

import problems.Points;
import problems.ProblemInterface;

/**
 * @author or13uw
 * 
 */
public class Problem implements ProblemInterface {

	/**
	 * Dataset for a problem with all values
	 */
	private static Table<Integer, Integer, String> table;

	private Map<Integer, String> hm = new HashMap<Integer, String>();

	private Table<Integer, Integer, String> workData;

	private BufferedReader reader;

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
	 * @param file
	 *            : file to read dataset from
	 */
	private void readDataSet(File file) throws IOException {
		reader = new BufferedReader(new FileReader(file));

		String line;
		String[] items = new String[] {};
		int row = 0;
		while ((line = reader.readLine()) != null) {
			line = line.trim();
			numRows++;

			items = line.split(",");
			for (int col = 0; col < items.length; col++) {
				table.put(row, col, items[col]);
			}
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
	 * @param reduct
	 *            : set of attributes to check
	 * @return true if it is a reduct, false otherwise
	 */
	private Boolean checkReduct(ArrayList<Points> bireductAttributes,
			ArrayList<Points> bireductObjects) {

		// the data table we will be working with. Initially we add all values from our initial dataset
//		workData = HashBasedTable.create(table);
//		
//		// clear the first column (because the first column is a name of the object and in many cases is a unique value)
//		workData.column(0).clear();
//		
//		// clear the last column (because the last column is a decision attribute but we need to work with conditional attributes)
//		workData.column(numColumns - 1).clear();
//		
//		//remove all attributes, which are not in the bireductAttributes array
//		ArrayList<Points> removeAttributes = new ArrayList<Points>(points);
//		removeAttributes.removeAll(bireductAttributes);
//		int n = removeAttributes.size();
//		for (int i = 0; i < n; i++) {
//			//if (removeAttributes.get(i).id <= numConditionalAttributes) {
//				workData.column(removeAttributes.get(i).id).clear();
//			//}
//
//		}
		
		workData = HashBasedTable.create();

		// System.out.println(bireductObjects.size());
		for (int row = 0; row < bireductObjects.size(); row++) {
			//workData.put(i, i, "");
			for(int column=0; column<numColumns; column++)
			{
				if(bireductAttributes.contains(new Points(column))){
						workData.put(row, column, table.get(bireductObjects.get(row).id-1, column));
				}
				else {
					    workData.put(row, column, "");
				}
			}
			
		}
		
		
		//System.out.println(bireductObjects);
		
		// go through all rows in the data table
		for (int i = 0; i < bireductObjects.size(); i++) {
			
			// skip rows which are empty or not in the bireductObjects array
			if (workData.row(i).isEmpty() || !bireductObjects.contains(new Points(i+1))) {
				continue;
				
			}
			
			// take a hash code if the current row
			int hc = workData.row(i).hashCode();
			
			// if we don't have a record with the same hash code (it means it's the first occurrence if the raw with that set of conditional attributes) 
			if (!hm.containsKey(hc)) {
				
				// we add the hash code and decision attribute to the hash map
				hm.put(hc, table.get(i, numColumns - 1));
				
				// else if we have a record with the same hash code (it means we have a row with the same conditional attributes)
			} else {
				// take the decision attribute if the previous record
				String rez = hm.get(hc);
				
				// if the decision attributes are not the same, return false
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
	 * @param chromosome
	 *            : supplied by GA system. permutation if attributes to find
	 *            reduct
	 * @return reduct based on provided chromosome (permutation)
	 */
	private ArrayList<ArrayList<Points>> findBiReducts(
			ChromosomeRepresentationInterface chromosome) {

		// create an array of conditional attributes from the initial array of
		// points from 0 to numConditionalAttributes
		// Initially we have all attributes (B <- A)
		ArrayList<Points> bireductAttributes = new ArrayList<Points>(
				points.subList(0, numConditionalAttributes));

		// create an array of objects for which the attributes are valid
		// Initially we have 0 objects (X <- empty set)
		ArrayList<Points> bireductObjects = new ArrayList<Points>();

		// create an array to store intermediate attributes
		ArrayList<Points> initialbiReductAttributes;

		// go through all points (m+n), where
		// n=|A| = numConditionalAttributes and m = |U|
		for (int i = 0; i < numRows + numConditionalAttributes; i++) {

			// if a(i) <=n, if the value of gene at i-th position less than
			// number of conditional attributes
			if (((Integer) chromosome.getGene(i)) <= numConditionalAttributes) {

				// create copy current array with attributes
				//initialbiReductAttributes = new ArrayList<Points>(bireductAttributes);

				int position = -1;

				// if the attribute that is equal to the value of gene at i-th
				// position exists, we store the index of the attribute
				if ((position = bireductAttributes.indexOf(new Points(
						(Integer) chromosome.getGene(i)))) > 0) {

					Points tmp = bireductAttributes.get(position);
					// remove the attribute
					bireductAttributes.remove(position);
					

					// check if bireductAttributes is a minimal subset of attributes
					// for bireductObjects
					if (!checkReduct(bireductAttributes, bireductObjects)) {
	
						// if it's not, restore previous values of the attributes
						// array
						//bireductAttributes = new ArrayList<Points>(initialbiReductAttributes);
						bireductAttributes.add(position, tmp);
						
					}
				}
				// else if a(i) > n, if the value of gene at i-th position more
				// than number of conditional attributes (it means we have to
				// work not with attributes but objects)
			} else {
				
				// add the object in the object array 
				bireductObjects.add(new Points(
						((Integer) chromosome.getGene(i)) - numConditionalAttributes));

				// check if bireductAttributes is a minimal subset of attributes
				// for bireductObjects
				if (!checkReduct(bireductAttributes, bireductObjects)) {
					
					// if it's not, restore previous values of the objects
					// array (remove recently added object)
					bireductObjects.remove(bireductObjects.size() - 1);
				}
			}

		}

		ArrayList<ArrayList<Points>> result = new ArrayList<ArrayList<Points>>();
		result.add(bireductAttributes);
		result.add(bireductObjects);
		
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