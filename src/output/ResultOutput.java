/**
 * 
 */
package output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import evolver.RunEvolution;
import params.Parameters;

/**
 * @author or13uw
 *
 */

public class ResultOutput {
	
	PrintWriter writer =null; 

	
	public ResultOutput(String currentProblemFileName, int run, RunEvolution r)
	{		
	
		String mresultFile;
		
        mresultFile = Parameters.getOutputFolder()+"/"+currentProblemFileName+"/"+"run" + (run + 1)+".output";
        
               
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
        
        writeIndividual(r);
        
	}
	
	private void writeIndividual(RunEvolution r)
	{
		writer.println(r.getBestIndividual().getChromosome().toString());
	}
	
	public void finish()
	{
		
		  if (writer != null) {
		    	writer.close();
		    	writer = null;
		    	
		    }
	}
	
}
