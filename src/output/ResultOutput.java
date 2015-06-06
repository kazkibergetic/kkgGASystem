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

    private final String runName;

    public ResultOutput(int run, RunEvolution r, RunEvolutionContext runEvolutionContext) {
        runName = "run" + (run + 1);
        String parametersPath = runEvolutionContext.getMainOutputDir() + runName + "/" + runName + ".output";
        OutputUtils.mkdirsForPath(parametersPath);
        try (PrintWriter chromosomeWriter = new PrintWriter(new FileOutputStream(parametersPath))) {
            writeParametersFile(r, chromosomeWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        writeIndividual(r, runEvolutionContext);
    }

    private void writeParametersFile(RunEvolution r, PrintWriter chromosomeWriter) throws IOException {
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
            String name = runEvolutionContext.getMainOutputDir() + runName + "/" + "chromosome" + (i + 1) + ".output";
            OutputUtils.mkdirsForPath(name);
            try (PrintWriter chromosomeWriter = new PrintWriter(new FileOutputStream(name))) {
                chromosomeWriter.println(chromosome.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String resultName = runEvolutionContext.getMainOutputDir() + runName + "/" + "chromosome" + (i + 1) + ".result";
            OutputUtils.mkdirsForPath(resultName);
            try (PrintWriter chromosomeResultWriter = new PrintWriter(new FileOutputStream(resultName))) {
                String results = problemResultCache.getResults(chromosome);
                chromosomeResultWriter.println(results);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Map<Integer, List<List<String>>> discretizationResults = problemResultCache.getDiscretizationResults(chromosome);
            for (Integer attr : discretizationResults.keySet()) {
                String discretizationIntervals = problemResultCache.getDiscretizationIntervals(attr);
                List<List<String>> columns = discretizationResults.get(attr);
                String discretizeName = runEvolutionContext.getExtraOutputDir() + runName + "/" + "chromosome" + (i + 1) + "[attr="+attr + "].discretize";
                OutputUtils.mkdirsForPath(discretizeName);
                try (PrintWriter chromosomeResultWriter = new PrintWriter(new FileOutputStream(discretizeName))) {
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
