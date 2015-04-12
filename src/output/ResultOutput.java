/**
 *
 */
package output;

import chromosome.ChromosomeRepresentationInterface;
import evolver.ProblemResultCache;
import evolver.RunEvolution;
import evolver.RunEvolutionContext;
import params.Parameters;
import params.ParametersInitialization;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author or13uw
 */

public class ResultOutput {

    private String dir;

    public ResultOutput(String currentProblemFileName, int run, RunEvolution r, RunEvolutionContext runEvolutionContext) {
        String runName = "run" + (run + 1);
        dir = Parameters.getOutputFolder() + "/" + currentProblemFileName + "/" + runName + "/";
        String mresultFile = dir + runName + ".output";


        File targetFile = new File(mresultFile);
        File parent = targetFile.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }
        try (PrintWriter chromosomeWriter = new PrintWriter(new FileOutputStream(mresultFile))) {
            writeOutputFile(r, chromosomeWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        writeIndividual(r, runEvolutionContext);
    }

    private void writeOutputFile(RunEvolution r, PrintWriter chromosomeWriter) throws IOException {
        Properties properties = ParametersInitialization.cloneProperties();
        properties.put("Duration", r.getDuration() + " ms");
        properties.store(chromosomeWriter, "");
    }

    private void writeIndividual(RunEvolution r, RunEvolutionContext runEvolutionContext) {
        ProblemResultCache problemResultCache = runEvolutionContext.getProblemResultCache();
        List<ChromosomeRepresentationInterface> individuals = r.getBestIndividuals();
        int max = Math.min(individuals.size(), Parameters.getBestIndividualsOut());
        for (int i = 0; i < max; ++i) {
            ChromosomeRepresentationInterface chromosome = individuals.get(i);
            String name = "chromosome" + (i + 1) + ".output";
            try (PrintWriter chromosomeWriter = new PrintWriter(new FileOutputStream(dir + name))) {
                chromosomeWriter.println(chromosome.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String resultName = "chromosome" + (i + 1) + ".result";
            try (PrintWriter chromosomeResultWriter = new PrintWriter(new FileOutputStream(dir + resultName))) {
                String results = problemResultCache.getResults(chromosome);
                chromosomeResultWriter.println(results);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Map<Integer, List<List<String>>> discretizationResults = problemResultCache.getDiscretizationResults(chromosome);
            for (Integer attr : discretizationResults.keySet()) {
                String discretizationIntervals = problemResultCache.getDiscretizationIntervals(attr);
                List<List<String>> columns = discretizationResults.get(attr);
                String discretizeName = "chromosome" + (i + 1) + "[attr="+attr + "].discretize";
                try (PrintWriter chromosomeResultWriter = new PrintWriter(new FileOutputStream(dir + discretizeName))) {
                    StringBuilder output = new StringBuilder();
                    output.append(discretizationIntervals).append("\n");
                    int rows = columns.get(0).size();
                    for(int j = 0 ; j < rows; ++j) {
                        for (List<String> column : columns) {
                            output.append(column.get(j)).append("\t");
                        }
                        output.append("\n");
                    }
                    chromosomeResultWriter.println(output.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            problemResultCache.removeResults(chromosome);
        }
    }

    public void finish() {
    }

}
