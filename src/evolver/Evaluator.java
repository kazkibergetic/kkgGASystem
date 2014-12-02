/**
 * 
 */
package evolver;

import java.util.ArrayList;

import params.ClassInitialization;
import chromosome.ChromosomeRepresentationInterface;

/**
 * @author or13uw
 *
 */
public class Evaluator  implements Runnable  {
	
		int beginIndex=0;
		int endIndex=0; 
		private ArrayList<ChromosomeRepresentationInterface> chromosomes = new ArrayList<ChromosomeRepresentationInterface>();
		private ClassInitialization ci = new ClassInitialization();
		
		public Evaluator(ArrayList<ChromosomeRepresentationInterface> ch){
			chromosomes.addAll(ch);
		
		}
		public void  run()
		{
			//	System.out.println(beginIndex + " AND "+ endIndex+">>> "+this.chromosomes.get(2));
				//System.out.println("CH: "+);
				for (int i = beginIndex; i < endIndex; i++) {
					double f = this.ci.getFitnessEvaluationOperator().evaluateFitness(
							this.chromosomes.get(i));
					this.chromosomes.get(i).setFitness(f);
				// System.out.println(chromosomes.get(i).getFitness());
			}

		}
	
}
