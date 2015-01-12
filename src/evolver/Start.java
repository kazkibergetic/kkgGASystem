/*********************************************************************************
 * Copyright 2014 Oleg Rybkin
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *********************************************************************************/

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

/**
 * KazKiberGetic GA System (kkgGA) The Start is a 'start button' of the GA
 * System.
 * 
 * @author Oleg Rybkin, kazkibergetic@gmail.com
 * @version 1.0
 * @since 2014-08-31
 */

public class Start {

	public static void main(String[] args) throws Exception {

		// display copyright information
		DisplayInfo.displayCopyrights();

		// read parameters from params/init.param file
		new ParametersInitialization(args);

		// initialize interfaces
		ClassInitialization ci = new ClassInitialization();
		ProblemInterface problem = ci.getProblem();

		// the program will read all files in the provided input folder with
		// specified in param file extension
		File folder = new File(Parameters.getInputFolder());
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			File file = listOfFiles[i];
			if (file.isFile()
					&& file.getName().endsWith(Parameters.getFilesExtension())) {

				// display information about the current file
				DisplayInfo.displayStartReadingFile(file.getName());

				// initialize the problem, read dataset from the provided file
				problem.initialize(file);

				Graph fitnessVsGenerations = new Graph("My Title");
				RunEvolution r = null;

				// perform number of experiments, specified in param file
				for (int z = 0; z < Parameters.getNumberOfRuns(); z++) {

					FitnessResults fitnessOutput = new FitnessResults(
							file.getName(), z, fitnessVsGenerations);

					// display information about the current run
					DisplayInfo.displayRun(z);

					r = new RunEvolution(z, fitnessOutput);
					ResultOutput resultOutput = new ResultOutput(
							file.getName(), z, r);
					resultOutput.finish();
					fitnessOutput.finish();
				}

				try {
					// display graph
					fitnessVsGenerations.start(Parameters.getOutputFolder()
							+ "/" + file.getName() + "/");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				DisplayInfo.displayRunStatistics(r);
				DisplayInfo.displayEndReadingFile(file.getName());

			}

		}
		;

	}
}
