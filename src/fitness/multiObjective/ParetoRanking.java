/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitness.multiObjective;




import java.util.ArrayList;
import java.util.Collections;

import chromosome.ChromosomeRepresentationInterface;

/**
 *
 * @author anthony
 */
public class ParetoRanking implements MultiObjective{

	/* (non-Javadoc)
	 * @see fitness.FitnessEvaluationInterface#evaluateFitness(chromosome.ChromosomeRepresentationInterface)
	 */
	@Override
	public double evaluateFitness(ChromosomeRepresentationInterface chromosome) {
		// TODO Auto-generated method stub
		return 0;
	}
    
    
    
    /**
     * complete ranking of dual objectives
     * @param ranks  : dual ranks are parsed in string formats e.g. ranks.add("2,1000");
     * @param populationSize  : specify pop size because @ranks will be modified in operation which means ranks.size() wont be reliable
     * 
     * #sample ranked data
     * 2,1000 #Rank:1
     * 4,600  #Rank:1
     * 8,400  #Rank:1
     * 7,800  #Rank:2
     * 9,500  #Rank:2
     */
	/*
    @SuppressWarnings("unchecked")
	public ArrayList<Double> paretoCalculations(ArrayList<String> ranks, int popSize)
    {
       ArrayList<String> rankedLevels = new ArrayList<String>();
       ArrayList<String> rankCopy = (ArrayList<String>) ranks.clone();
       ArrayList<Double> selectionRank = new ArrayList<Double>();
       //int count=0;
       
       int rank = 1;
       
       Collections.sort(ranks); //sort to arrange first objective in ascending manner : NB: small values are best
       //System.out.println("#SORTED");
       //replace with a more efficient later
       for(int i=0; i<popSize;i++)
       {
    	   selectionRank.add((double)Constants.DEFAULT_WORSE_FITNESS); //fill with dump values to enable set in second loop
       }
       
       for(int i=0; i<popSize;i++)
       {
         ArrayList<Double> ranked = new ArrayList<>();  //keep records of currently ranked level. eg. rank 0, 1, etc
         //ranked.add(Double.parseDouble(ranks2.get(0).split(",")[1]));
         for(int j=0; j<ranks.size();j++)
         { 
             String[] sp = ranks.get(j).split(","); // ranks.add("2,1000")
             //check if second objective exists in currently ranked values
             if(GenerateMask.isExistIndexPareto(ranked,Double.parseDouble(sp[1])))
             {
                 ranked.add((Double.parseDouble(sp[1])));
                 //ranked.get(j);
                 if(!rankedLevels.contains(ranks.get(j))) // records of ranked values from ranks
                 {
                    rankedLevels.add(ranks.get(j));
                 } 
                 //ranks2.remove(rank);
                 //System.out.println(sp[0]+","+sp[1]+" #Rank:"+rank);
                 //set appropriate indexes with ranked values
                 //selectionRank.add((double) rank);
                 int ind = GenerateMask.returnIndexS(selectionRank,rankCopy,sp[0]+","+sp[1]);
                 selectionRank.set(ind,(double) rank);
                 
                 //selectionRank.set(GenerateMask.returnIndexS(selectionRank,rankCopy,sp[0]+","+sp[1]),(double) rank);
                 //count++;
                 //System.out.println("#outmask"+GenerateMask.returnIndexS(selectionRank,rankCopy,sp[0]+","+sp[1]));
             } 
             //System.out.println(ranks.get(j));
         } 
         //remove occurences of similar ranks from main array
         for(String v:rankedLevels)
         {
             while(ranks.contains(v))
             {
                 ranks.remove(v);
             }
         }
         rank++; //increment ranking number e.g. rank 0, rank 1, e.t.c
         ranked.clear(); //clear contents of current rank for a new rank
       }
      
       //System.out.println("#counting "+count); 
       
       return selectionRank;
       
    }
    */
    
    
    
}
