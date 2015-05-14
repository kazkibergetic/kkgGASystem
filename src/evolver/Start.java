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

import com.google.common.io.Files;
import fitness.FitnessEvaluator;
import fitness.multiObjective.ParetoRankEvaluator;
import fitness.multiObjective.RankEvaluator;
import output.DisplayInfo;
import output.Graph;
import output.ResultOutput;
import output.statistics.FitnessResults;
import params.ClassInitialization;
import params.Parameters;
import params.ParametersInitialization;
import problems.ProblemInterface;

import java.io.File;
import java.util.Random;
import java.util.concurrent.Executors;

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
        ParametersInitialization.loadDefaultParameters();

        // the program will read all files in the provided input folder with
        // specified in param file extension
        File folder = new File(Parameters.getInputFolder());
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().endsWith(Parameters.getFilesExtension())) {
                    RunEvolutionContext runEvolutionContext = null;
                    try {
                        // display information about the current file
                        DisplayInfo.displayStartReadingFile(file.getName());

                        ParametersInitialization.loadDefaultParameters();
                        String dataFileName = Files.getNameWithoutExtension(file.getName());
                        File customParameters = new File(file.getParent() + File.separator + dataFileName + ParametersInitialization.PARAMS_EXTENSION);
                        if (customParameters.exists()) {
                            ParametersInitialization.loadParameters(customParameters.getAbsolutePath());
                        }

                        ClassInitialization ci = new ClassInitialization();
                        ProblemInterface problem = ci.getProblem();
                        FitnessEvaluator fitnessEvaluationOperator = ci.getFitnessEvaluationOperator();
                        runEvolutionContext = new RunEvolutionContext();
                        runEvolutionContext.setRankOption(fitnessEvaluationOperator instanceof RankEvaluator);
                        runEvolutionContext.setExecutorService(Executors.newFixedThreadPool(Parameters.getNumberOfProcessors()));
                        runEvolutionContext.setRandom(new Random(Parameters.getSeed()));
                        runEvolutionContext.setProblemResultCache(new ProblemResultCache());

                        // initialize the problem, read dataset from the provided file
                        problem.initialize(runEvolutionContext, file);

                        Graph fitnessVsGenerations = null;
                        RunEvolution r = null;

                        // perform number of experiments, specified in param file
                        for (int z = 0; z < Parameters.getNumberOfRuns(); z++) {
                            FitnessResults fitnessOutput = new FitnessResults(file.getName(), z, fitnessVsGenerations);
                            runEvolutionContext.setFitnessOutput(fitnessOutput);

                            // display information about the current run
                            DisplayInfo.displayRun(z);

                            r = new RunEvolution(z, runEvolutionContext);
                            ResultOutput resultOutput = new ResultOutput(file.getName(), z, r, runEvolutionContext);
                            resultOutput.finish();
                            fitnessOutput.finish();
                        }

                        runEvolutionContext.getProblemResultCache().removeDiscretizationIntervals();


                        // display graph
                        if (!runEvolutionContext.isRankOption()) {
                            if (fitnessVsGenerations != null) {
                                fitnessVsGenerations.start(Parameters.getOutputFolder() + "/" + file.getName() + "/");
                            }
                        }

                        DisplayInfo.displayRunStatistics(r);
                        DisplayInfo.displayEndReadingFile(file.getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (runEvolutionContext != null) {
                            runEvolutionContext.getExecutorService().shutdown();
                        }
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("No input files found");
        }
    }

}
