/**
 * 
 */
package operators.crossover;

import chromosome.ChromosomeRepresentationInterface;
import evolver.RunEvolutionContext;
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
            RunEvolutionContext runEvolutionContext,
			ChromosomeRepresentationInterface parent1,
			ChromosomeRepresentationInterface parent2) throws ChromomesInequalityException {
		
		
//		System.out.println("!!!");
			if(parent1.getSize() == parent2.getSize())
			{
//				
//				Parent 1: [4, 9, 3, 7, 2, 6, 8, 1, 5]
//						Parent 2: [5, 6, 8, 2, 3, 4, 7, 1, 9]
//								
//				parent1.setGene(0, 4);
//				parent1.setGene(1, 9);
//				parent1.setGene(2, 3);
//				parent1.setGene(3, 7);
//				parent1.setGene(4, 2);
//				parent1.setGene(5, 6);
//				parent1.setGene(6, 8);
//				parent1.setGene(7, 1);
//				parent1.setGene(8, 5);
//				
//				
//				parent2.setGene(0, 5);
//				parent2.setGene(1, 6);
//				parent2.setGene(2, 8);
//				parent2.setGene(3, 2);
//				parent2.setGene(4, 3);
//				parent2.setGene(5, 4);
//				parent2.setGene(6, 7);
//				parent2.setGene(7, 1);
//				parent2.setGene(8, 9);
								
				int len = parent1.getSize();
				
				List<ChromosomeRepresentationInterface> offsprings = new ArrayList<ChromosomeRepresentationInterface>();
				
				ChromosomeRepresentationInterface offspring1 = ci.getChromosomeRepresentation();
				ChromosomeRepresentationInterface offspring2 = ci.getChromosomeRepresentation();
				
				offspring1.initializeDefault(len);
				offspring2.initializeDefault(len);
				
				Random rand = runEvolutionContext.getRandom();
				int p1 = rand.nextInt(len);
				int p2 = rand.nextInt(len);
				
//				System.out.println("r1: " +p1);
//				
//				System.out.println("r2: " +p2);
				
				for (int i = p1; i <= p2; ++i){					
					offspring1.setGene(i, parent1.getGene(i));
					offspring2.setGene(i, parent2.getGene(i));
				}
				
				offsprings.add(fillChild(offspring1, parent1, parent2, p1, p2));
				offsprings.add(fillChild(offspring2, parent2, parent1, p1, p2));
				
				
//				System.out.println("Parent 1: "+parent1.getChromosome());
//				System.out.println("Parent 2: "+parent2.getChromosome());
//				
//				
//				
//				
//				System.out.println("off1: "+offsprings.get(0));
//				System.out.println("off2: "+offsprings.get(1));
				

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
