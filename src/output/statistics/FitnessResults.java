/**
 *
 */
package output.statistics;

import chromosome.ChromosomeRepresentationInterface;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import evolver.Population;
import evolver.RunEvolutionContext;
import output.Graph;
import params.Parameters;

import java.io.File;
import java.io.PrintWriter;

/**
 * @author or13uw
 */


public class FitnessResults implements StatisticsInterface {

    PrintWriter writer = null;
    Graph statGraph = null;

    public FitnessResults(String currentProblemFileName, int run, Graph g) {
        statGraph = g;
        String mresultFile;

        mresultFile = Parameters.getOutputFolder() + "/" + currentProblemFileName + "/" + "run" + (run + 1) + "/" + "run" + (run + 1) + ".stat";


        File targetFile = new File(mresultFile);
        File parent = targetFile.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }

        try {
            writer = new PrintWriter(mresultFile, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void writeStatistics(int generation, Population current, RunEvolutionContext runEvolutionContext) {
        if (!runEvolutionContext.isRankOption()) {
            writer.println(generation + " " + current.getAverageFitness() + " " + current.getBestFitness());
            statGraph.addValues(generation, current.getAverageFitness(), current.getBestFitness());
        } else {

            Multimap<Double, Integer> ranks = ArrayListMultimap.create();
            for (ChromosomeRepresentationInterface chromosome : current.getChromosomes()) {
                Double rank = chromosome.getFitness();
                ranks.put(rank, 1);
            }
            StringBuilder builder = new StringBuilder();
            builder.append("generation: "+generation).append("\n");
            for (Double rank : ranks.keySet()) {
                builder.append(rank).append(":").append(ranks.get(rank).size()).append("\n");
            }
            String s = builder.toString();
            writer.print(s);
            System.out.println(s);
        }
    }

    public void writeResultFile(Population current) {


    }

    public void finish() {

        if (writer != null) {
            writer.close();
            writer = null;

        }
    }

}
