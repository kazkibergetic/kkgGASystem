/**
 * 
 */
package output.statistics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import output.Graph;
import evolver.Population;
import params.Parameters;

/**
 * @author or13uw
 *
 */


public class FitnessResults implements StatisticsInterface  {
	
	PrintWriter writer =null; 
	Graph statGraph=null;
	public FitnessResults(String currentProblemFileName, int run, Graph g)
	{		
		statGraph = g;
		String mresultFile;
		
        mresultFile = Parameters.getOutputFolder()+"/"+currentProblemFileName+"/"+"run" + (run + 1)+".stat";
        
               
        File targetFile = new File(mresultFile);
        File parent = targetFile.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }

        try {
			writer = new PrintWriter(mresultFile, "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	
	public void writeStatistics(int generation, Population current)
	{
		writer.println(generation+" "+ current.getAverageFitness()+" "+current.getBestFitness());
		statGraph.addValues(generation, current.getAverageFitness(), current.getBestFitness());
		
		
	}
	
	public void writeResultFile(Population current){
		
		
	}
	
	public void finish()
	{
		
		  if (writer != null) {
		    	writer.close();
		    	writer = null;
		    	
		    }
	}

}
