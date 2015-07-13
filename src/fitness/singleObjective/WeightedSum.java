package fitness.singleObjective;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import params.ClassInitialization;
import chromosome.ChromosomeRepresentationInterface;
import evolver.RunEvolutionContext;
import fitness.FitnessEvaluator;

public class WeightedSum implements FitnessEvaluator{

	@Override
	public void preEvaluateFitness() {
		// TODO Auto-generated method stub
		
	}

	 private ClassInitialization ci = new ClassInitialization();
	 
	@Override
	public double evaluateFitness(RunEvolutionContext runEvolutionContext,
			ChromosomeRepresentationInterface chromosome) throws Exception {
		
		 List<Double> ranks = new ArrayList<Double>();
		 
		String result = ci.getProblem().evaluateFitness(runEvolutionContext, chromosome);
        if (result.split("[,]").length > 1) {
        	       
//        	System.out.println(result);
        	
        	ranks.add(Double.parseDouble(result.split("[,]")[0]));
            ranks.add(Double.parseDouble(result.split("[,]")[1]));
            
//            System.out.println(ranks.get(0));
//            System.out.println(ranks.get(1));
            
            chromosome.setMetaData(result);
        } else {
            throw new Exception("Fitness value is in a wrong format!");
        }


	        return 0.7 * ranks.get(0) + 1.5*ranks.get(1);
	        
	}

	@Override
	public void postEvaluateFitness(
			List<ChromosomeRepresentationInterface> chromosomes)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
