/**
 * 
 */
package problems.reduct;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
public class ReductProblem implements ProblemInterface {

	/**
	 * Dataset for a problem with all values
	 */
	private static Table<Integer, Integer, String> table;

	private Map <Integer, String> hm = new HashMap<Integer, String>();
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
			readReductDataSet(input);
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
	private void readReductDataSet(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));

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
			//System.out.println(table.row(row).hashCode());
			row++;

		}
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
	Table<Integer, Integer, String> workData;
	/**
	 * checks of the specified set of attributes is a reduct
	 * 
	 * @param reduct
	 *            : set of attributes to check
	 * @return true if it is a reduct, false otherwise
	 */
	private Boolean checkReduct(ArrayList<Points> reduct) {

		workData = HashBasedTable.create(table);
		workData.column(0).clear();
		workData.column(numColumns - 1).clear();
		//boolean result = true;

		ArrayList<Points> remove = new ArrayList<Points>(points);
		remove.removeAll(reduct);
		
		int n = remove.size();
		for (int i = 0; i < n; i++) {			
			workData.column(remove.get(i).id).clear();
		}
		
		
		for(int i=0; i< numRows; i++)
		{
			int hc = workData.row(i).hashCode();
			//String rez1 = hm.get(hc);
			//System.out.println(rez1);
			if(!hm.containsKey(hc))
			{
				hm.put(hc, table.get(i, numColumns - 1));
			}
			else
			{
				String rez = hm.get(hc);
				if(!rez.equals(table.get(i, numColumns - 1)))
				{
					return false;
				}
			}
			
		}
		/*
		for (int i = 0; i < numRows - 1; i++) {
			for (int z = i + 1; z < numRows; z++) {
				if (workData.row(i).equals(workData.row(z))) {

					if (!table.get(i, numColumns - 1).equals(
							table.get(z, numColumns - 1))) {
						return false;
					}

				}

			}
		}
		
		*/
		
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
	private ArrayList<Points> findReduct(
			ChromosomeRepresentationInterface chromosome) {

		ArrayList<Points> reduct = new ArrayList<Points>(points);
		
		ArrayList<Points> initialReduct;
		for (int i = 0; i < reduct.size(); i++) {
			initialReduct = new ArrayList<Points>(reduct);
			int position = 0;
					
			if((position = reduct.indexOf(new Points((Integer) chromosome.getGene(i))))>0){
				reduct.remove(position);
			}

			if (!checkReduct(reduct)) {
				reduct = new ArrayList<Points>(initialReduct);
			} 
		}

		return reduct;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see problems.ProblemInterface#evaluateFitness()
	 */
	@Override
	public double evaluateFitness(ChromosomeRepresentationInterface chromosome) {

		ArrayList<Points> reduct = this.findReduct(chromosome);

		
		 /*for(int d = 0; d <reduct.size(); d++) {
		  System.out.print(reduct.get(d).id +" ");
		  
		  } System.out.println();
		 
	*/
		return reduct.size();
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
