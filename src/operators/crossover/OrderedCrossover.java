/**
 * 
 */
package operators.crossover;

import chromosome.ChromosomeRepresentationInterface;
import exceptions.ChromomesInequalityException;
import params.ClassInitialization;
import params.Parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author or13uw
 *
 */
public class OrderedCrossover implements CrossoverInterface {

	ClassInitialization ci = new ClassInitialization();
	/* (non-Javadoc)
	 * @see operators.crossover.CrossoverInterface#performCrossover(chromosome.ChromosomeRepresentationInterface, chromosome.ChromosomeRepresentationInterface)
	 */
	@Override
	public List<ChromosomeRepresentationInterface> performCrossover(
			ChromosomeRepresentationInterface parent1,
			ChromosomeRepresentationInterface parent2) throws ChromomesInequalityException {
		
		
		
			if(parent1.getSize() == parent2.getSize())
			{
				
				int len = parent1.getSize();
				
				List<ChromosomeRepresentationInterface> offsprings = new ArrayList<ChromosomeRepresentationInterface>();
				
				ChromosomeRepresentationInterface offspring1 = ci.getChromosomeRepresentation();
				ChromosomeRepresentationInterface offspring2 = ci.getChromosomeRepresentation();
				
				offspring1.initializeDefault(len);
				offspring2.initializeDefault(len);
				
				Random rand = new Random(Parameters.getSeed());
				int p1 = rand.nextInt(len);
				int p2 = rand.nextInt(len);
				
				for (int i = p1; i <= p2; ++i){					
					offspring1.setGene(i, parent1.getGene(i));
					offspring2.setGene(i, parent2.getGene(i));
				}
				
				offsprings.add(fillChild(offspring1, parent1, parent2, p1, p2));
				offsprings.add(fillChild(offspring2, parent2, parent1, p1, p2));
				
				

				return offsprings;
			}
			else
			{
				throw new ChromomesInequalityException();
			}
		
		
		
		
		
	}
	
	private ChromosomeRepresentationInterface fillChild(ChromosomeRepresentationInterface offspring, ChromosomeRepresentationInterface parent1, ChromosomeRepresentationInterface parent2, int startIdx, int endIdx){
		
		ArrayList<Object> list = new ArrayList<Object>(offspring.getSize());
		
		int i = endIdx;
		int count = 0;
		while (count < offspring.getSize()){
			i++;
			if (i == offspring.getSize()){
				i = 0;
			}
			list.add(parent2.getGene(i));
			count++;
		}
			
		for (i = startIdx; i <= endIdx; ++i){
			list.remove(list.indexOf(parent1.getGene(i)));
		}
		
		i = endIdx; 
		for (Object value : list){
			i++;
			if (i == offspring.getSize()){
				i = 0;
			}
			offspring.setGene(i, value);
			
		}
		
		return offspring;
	}

	

	
	

}
