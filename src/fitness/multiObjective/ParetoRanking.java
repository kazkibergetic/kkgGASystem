/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitness.multiObjective;


import chromosome.ChromosomeRepresentationInterface;
import params.ClassInitialization;
import params.Parameters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author anthony
 */
public class ParetoRanking implements MultiObjective{

	private static ArrayList<Double> rankResults;
	private static ArrayList<String> ranks = new ArrayList<String>();
	ClassInitialization ci = new ClassInitialization();

	private static int counter = 0;
	/* (non-Javadoc)
	 * @see fitness.FitnessEvaluationInterface#evaluateFitness(chromosome.ChromosomeRepresentationInterface)
	 */
	
    
    
    
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
	
    @SuppressWarnings("unchecked")
	public ArrayList<Double> paretoCalculations()
    {
       ArrayList<String> rankedLevels = new ArrayList<String>();
       ArrayList<String> rankCopy = (ArrayList<String>) ranks.clone();
       ArrayList<Double> selectionRank = new ArrayList<Double>();
       //int count=0;
       
       int rank = 1;
       
       Collections.sort(ranks); //sort to arrange first objective in ascending manner : NB: small values are best
       //System.out.println("#SORTED");
       //replace with a more efficient later
       for(int i=0; i<Parameters.getPopulationSize();i++)
       {
    	   selectionRank.add(Double.POSITIVE_INFINITY); //fill with dump values to enable set in second loop
       }
       
       for(int i=0; i<Parameters.getPopulationSize();i++)
       {
         ArrayList<Double> ranked = new ArrayList<>();  //keep records of currently ranked level. eg. rank 0, 1, etc
         //ranked.add(Double.parseDouble(ranks2.get(0).split(",")[1]));
         for(int j=0; j<ranks.size();j++)
         { 
             String[] sp = ranks.get(j).split(","); // ranks.add("2,1000")
             //check if second objective exists in currently ranked values
             if(isExistIndexPareto(ranked,Double.parseDouble(sp[1])))
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
                 int ind = returnIndexS(selectionRank,rankCopy,sp[0]+","+sp[1]);
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
    
    private boolean isExistIndexPareto(ArrayList<Double> c1, Double value)
    { 
    	boolean b = true;
       
       for (int i = 0; i < c1.size(); i++)
       {  
         b = b && (value<=c1.get(i));
       } 
      return b;
    }
    
    private int returnIndexS(ArrayList<Double> parent,ArrayList<String> c1, String value)
    {
       for (int i = 0; i < c1.size(); i++)
       {
           if (value.equals(c1.get(i)) && (parent.get(i)== Double.POSITIVE_INFINITY) ) //ensure index doesnt already exist
           {  
               return i;
           }
       }  
      return -1;
    }
    
    public  void preEvaluateFitness() {
    	counter = 0;
    	
		
	}


    
	@Override
	public double evaluateFitness(ChromosomeRepresentationInterface chromosome) throws Exception {
		// TODO Auto-generated method stub
		String result;
		result = ci.getProblem().evaluateFitness(chromosome);
		
		if(result.split(",").length>1)
		{
							
			ranks.add(result);
			chromosome.setMetaData(result);
			//System.out.println(chromosome.getMetaData());
			//ranks.add(result);
		}
		else
		{
			throw new Exception("Fitness value is in a wrong format!");
		}
		
		return Double.POSITIVE_INFINITY;
	}
	
	

	/* (non-Javadoc)
	 * @see fitness.FitnessEvaluationInterface#postEvaluateFitness(java.util.ArrayList)
	 */
	@Override
	public void postEvaluateFitness(
			List<ChromosomeRepresentationInterface> chromosomes) throws Exception {
		// TODO Auto-generated method stub
		
		rankResults = new ArrayList<Double>();
		ArrayList<String> ranksCopy = new ArrayList<String>(ranks);
    	//chromosome.setChromosome(TSPproblem.getOptimalTour());
    	if(ranks.size() == Parameters.getPopulationSize())
		{
    		rankResults = paretoCalculations();
		}
		else
		{
			throw new Exception("Number of ranks is not equal to number of populations.");
		}
		
		for(int i=0; i< chromosomes.size(); i++)
		{
			
			int position = ranksCopy.indexOf(chromosomes.get(i).getMetaData());
			
			//System.out.println(position + " >>> "+ i);				
			chromosomes.get(i).setFitness(rankResults.get(position));
		
			
		}
		
		
	}
}