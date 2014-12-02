/**
 * 
 */
package output;

import evolver.Population;
import evolver.RunEvolution;

/**
 * @author or13uw
 *
 */
public class DisplayInfo {

	public static void displayCopyrights()
	{
		
		System.out.println("kkgGASystem 2014");
		System.out.println("Oleg Rybkin");
		System.out.println("Computer Science");
		System.out.println("Brock University");
		System.out.println("or13uw@brocku.ca");
		
		
	}
	public static void displayInitialization(String var, String value)
	{
		System.out.printf("%-30.30s  %-30.90s%n",var, value);
		
	}
	
	public static void displayGeneration(int generation)
	{
		System.out.println("==================================================");
		System.out.println("GENERATION "+generation);
		System.out.println();
		
	}
	public static void displayRun(int run)
	{
		System.out.println("==================================================");
		System.out.println("RUN "+ (run+1));
		
		
	}
	public static void displayGenerationStatistics(Population p)
	{
		System.out.println("Best fitness: "+p.getBestFitness());
		System.out.println("Average fitness: "+p.getAverageFitness());
	}
	
	public static void displayStartReadingFile(String filename)
	{
		System.out.println(); 
		System.out.print(">>>>>>>>>>>>>> ");
		System.out.print("Start reading file: "+filename);
		System.out.print(" >>>>>>>>>>>>>>");
		System.out.println(); 
	}
	public static void displayEndReadingFile(String filename)
	{
		System.out.println(); 
		System.out.print("xxxxxxxxxxxxxx ");			
		System.out.print("End reading file: "+filename);
		System.out.print(" xxxxxxxxxxxxxx");	
		System.out.println(); 
	}
	
	public static void displayRunStatistics(RunEvolution r)
	{
		System.out.println("========== Run statistics ==========");
		System.out.println("Best fitness: "+r.getBestFitness());
		
	}
	
}
