/**
 * 
 */
package problems.biReduct;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
public class BiReductProblem implements ProblemInterface {

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
		table = TreeBasedTable.create();
		
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
			//items = line.split(" ");
			
			for (int col = 0; col < items.length; col++) {
				table.put(row, col, items[col]);
			}
			row++;

		}
		reader.close();
		numColumns = items.length;

		
		createPoints(numColumns+numRows);
		
	}

	/**
	 * creates points for OrderBased GA system
	 */
	private void createPoints(int limit) {
		for (int i = 1; i < limit - 1; i++) {
			points.add(new Points(i));

		}

	}

	/**
	 * checks of the specified set of attributes is a reduct
	 * 
	 * @param reduct
	 *            : set of attributes to check
	 * @return true if it is a reduct, false otherwise
	 */
	private Boolean checkReduct(ArrayList<Points> bireductAttributes, ArrayList<Points> bireductObjects ) {

		
		
		Table<Integer, Integer, String> workData = TreeBasedTable.create();
		//
		
		
		/*int m = bireductObjects.size();
		System.out.println("rows to add "+m);
		for (int i = 0; i < m; i++) {			
			workData.row(i).putAll(table.row(bireductObjects.get(i).id));			
			System.out.println(workData.row(i));
		}
		System.out.println();
		
		
		for(int i=0; i< numRows; i++)
		{
			for(int j=1; j < numColumns-1; j++)
			{
				System.out.print(workData.row(i).get(j)+" ");
			}
			System.out.println();
		}
		*/
		workData.putAll(table);
		workData.column(0).clear();
		workData.column(numColumns - 1).clear();
		boolean result = true;

		//for (int i = 0; i < bireductObjects.size(); i++) {	
		//	System.out.print(bireductObjects.get(i).id+" ");
		//}
		
		ArrayList<Points> removeObjects = new ArrayList<Points>(points);
		removeObjects.removeAll(bireductObjects);
		
		int m = removeObjects.size();
		for (int i = 0; i < m; i++) {	
			if(removeObjects.get(i).id > numColumns-2){
		//	System.out.println("IN "+removeObjects.get(i).id);
				workData.row(removeObjects.get(i).id).clear();
			}
			
		}
		
		ArrayList<Points> removeAttributes = new ArrayList<Points>(points);
		removeAttributes.removeAll(bireductAttributes);
		
		int n = removeAttributes.size();
		for (int i = 0; i < n; i++) {
			if(removeAttributes.get(i).id <= numColumns-2)
			{
				workData.column(removeAttributes.get(i).id).clear();
			}
			
		}
		
		
		/*for(int i=0; i< numRows; i++)
		{
			for(int j=1; j < numColumns-1; j++)
			{
				//System.out.print(workData.row(i).get(j)+" ");
			}
			//System.out.println();
		}
		*/
		
		for (int i = 0; i < numRows - 1; i++) {
			for (int z = i + 1; z < numRows; z++) {
				if (workData.row(i).equals(workData.row(z)) && !workData.row(i).isEmpty() && !workData.row(z).isEmpty()) {

					if (!table.get(i, numColumns - 1).equals(
							table.get(z, numColumns - 1))) {
						result = false;
						
					}
					

				}

			}
		}
		return result;
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

		ArrayList<Points> bireductAttributes = new ArrayList<Points>(points.subList(0, numColumns-2));
		ArrayList<Points> bireductObjects = new ArrayList<Points>();
		
		
		ArrayList<Points> initialbiReductAttributes;
		
		for (int i = 0; i < points.size(); i++) {		
			
			
			if(((Integer) chromosome.getGene(i)) <= numColumns-2)
			{
				initialbiReductAttributes = new ArrayList<Points>(bireductAttributes);
				int position = 0;
				
				
				if((position = bireductAttributes.indexOf(new Points((Integer) chromosome.getGene(i))))>0){
					bireductAttributes.remove(position);
				}	
				
				if (!checkReduct(bireductAttributes, bireductObjects)) {
					bireductAttributes = new ArrayList<Points>(initialbiReductAttributes);			
				} 				
			}
			else
			{
				
				bireductObjects.add(new Points(((Integer) chromosome.getGene(i))-(numColumns-2)));
			
				if (!checkReduct(bireductAttributes, bireductObjects)) {
					bireductObjects.remove(bireductObjects.size()-1);
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
	@Override
	public double evaluateFitness(ChromosomeRepresentationInterface chromosome) {

	//System.out.println(points.size());
		
/*
		ArrayList<Integer> ch = new ArrayList<Integer>();
		ch.add(2);
		ch.add(4);
		ch.add(8);
		ch.add(5);
		ch.add(3);
		ch.add(1);
		ch.add(9);
		ch.add(10);
		ch.add(6);
		ch.add(7);
		chromosome.setChromosome(ch);
		//"2, 4, 8, 5, 3, 1, 9, 10, 6, 7"
		*/
		ArrayList<ArrayList<Points>> biReduct = this.findBiReducts(chromosome);
		
		//System.out.println("===");
		System.out.println("attributes:");
		  for(int d = 0; d <biReduct.get(0).size(); d++) {
		  System.out.print(biReduct.get(0).get(d).id +" ");
		  
		  } System.out.println();
		  System.out.println("objects:");
		  for(int d = 0; d <biReduct.get(1).size(); d++) {
			  System.out.print(biReduct.get(1).get(d).id +" ");
			  
			  }  System.out.println();
			
	    double rez = biReduct.get(0).size() + (numRows - biReduct.get(1).size());
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
