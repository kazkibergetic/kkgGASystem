/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitness.multiObjective;


import chromosome.ChromosomeRepresentationInterface;
import evolver.RunEvolutionContext;
import params.ClassInitialization;
import params.Parameters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author anthony
 */
public class SumOfRanks implements RankEvaluator {
    protected static List<String> ranks = new CopyOnWriteArrayList<>();
    private ClassInitialization ci = new ClassInitialization();

    @Override
    public double evaluateFitness(RunEvolutionContext runEvolutionContext, ChromosomeRepresentationInterface chromosome) throws Exception{
        String result = ci.getProblem().evaluateFitness(runEvolutionContext, chromosome);
        if (result.split(",").length > 1) {
            ranks.add(result);
            chromosome.setMetaData(result);
        } else {
            throw new Exception("Fitness value is in a wrong format!");
        }

        return Double.POSITIVE_INFINITY;
    }


/**
 * @param ranks
 * @param popSize #SORTED
 *                ranks  obj1 obj2 normalized sum-of-ranks
 *                2,1000 1 5 1.2
 *                4,600  2 3 1.0
 *                7,800  3 4 1.4
 *                8,400  4 1 1.0
 *                9,500  5 2 1.4
 */
    @SuppressWarnings("unchecked")
	public ArrayList<Double> sorCalculations(ArrayList<String> ranks)
    {
        ArrayList<Double> r1 = new ArrayList<>();
        ArrayList<Double> selectionRank = new ArrayList<>();
        ArrayList<String> rankCopy = (ArrayList<String>) ranks.clone();

        Collections.sort(ranks);
        //System.out.println("#SORTED");

         for(int j=0; j<ranks.size();j++)
         {
             String[] sp = ranks.get(j).split(","); // ranks.add("2,1000")
             r1.add(Double.parseDouble(sp[1]));

             //pad with dummy values to enable set of content in later code
             //remove if problems are reported
             selectionRank.add(1.00);
         }
         //sort both values
         Collections.sort(r1);

         for(int j=1; j<=ranks.size();j++)
         {
             String[] sp = ranks.get(j-1).split(","); // ranks.add("2,1000")

             //selectionRank.add(j-1,((j+( GenerateMask.returnIndexD(r1,Double.parseDouble(sp[1])+"") +1))/(double)ranks.size()));

             //set appropriate index with sum of rank value
             selectionRank.set(GenerateMask.returnIndexS(rankCopy,ranks.get(j-1)),((j+( GenerateMask.returnIndexD(r1,Double.parseDouble(sp[1])+"") +1))/(double)ranks.size()));
             //System.out.println(ranks.get(j-1) +":"+ GenerateMask.returnIndexS(rankCopy,ranks.get(j-1))+" " + j + " "+ ( GenerateMask.returnIndexD(r1,Double.parseDouble(sp[1])+"") +1) + " " + selectionRank.get(j-1));
             //System.out.println(selectionRank.get(GenerateMask.returnIndexS(rankCopy,ranks.get(j-1))) +":"+ ranks.get(j-1) +" "+ rankCopy.get(j-1));

         }

         return selectionRank;

    }

    @Override
    public void preEvaluateFitness() {
    }

    @Override
    public void postEvaluateFitness(List<ChromosomeRepresentationInterface> chromosomes) throws Exception{
        List<Double> rankResults;
        ArrayList<String> ranksCopy = new ArrayList<String>(ranks);
        if (ranks.size() == Parameters.getPopulationSize()) {
            rankResults = sorCalculations(ranksCopy);
            ranks.clear();
        } else {
            throw new Exception("Number of ranks is not equal to number of populations.");
        }

        for (ChromosomeRepresentationInterface chromosome : chromosomes) {
            int position = ranksCopy.indexOf(chromosome.getMetaData());
            chromosome.setFitness(rankResults.get(position));
        }
    }

}
