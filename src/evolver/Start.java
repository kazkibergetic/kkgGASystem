package evolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import output.DisplayInfo;
import output.Graph;
import output.ResultOutput;
import output.statistics.FitnessResults;
import params.ClassInitialization;
import params.Parameters;
import params.ParametersInitialization;
import problems.ProblemInterface;



public class Start {

	


	public static void main(String[] args) throws Exception
	{
		
		DisplayInfo.displayCopyrights();
		new ParametersInitialization(args);
		
		
		
		
		ClassInitialization ci = new ClassInitialization();	
		ProblemInterface problem = ci.getProblem();
		
		File folder = new File(Parameters.getInputFolder());
		File[] listOfFiles = folder.listFiles();
	
		for (int i = 0; i < listOfFiles.length; i++) {
		  File file = listOfFiles[i];
		  if (file.isFile() && file.getName().endsWith(Parameters.getFilesExtension())) {
			  			 
			DisplayInfo.displayStartReadingFile(file.getName());
			
			problem.initialize(file);
			Graph fitnessVsGenerations = new Graph("My Title");
			RunEvolution r=null;
			for(int z=0; z<Parameters.getNumberOfRuns(); z++)
			{
				FitnessResults fitnessOutput = new FitnessResults(file.getName(), z, fitnessVsGenerations);
				
				
				DisplayInfo.displayRun(z);
				
				r = new RunEvolution(z, fitnessOutput);
				ResultOutput resultOutput = new ResultOutput(file.getName(), z, r);
				resultOutput.finish();
				fitnessOutput.finish();
			}			
			
			try {
				fitnessVsGenerations.start(Parameters.getOutputFolder()+"/"+file.getName()+"/");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			DisplayInfo.displayRunStatistics(r);
			DisplayInfo.displayEndReadingFile(file.getName());
			
		  } 
		  
		  
		};
		
				
		
	}
}
