/**
 * 
 */
package operators.crossover;

import chromosome.ChromosomeRepresentationInterface;
import params.ClassInitialization;
import params.Parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @author or13uw
 *
 */
public class UniformOrderCrossover implements CrossoverInterface {

	
	
		/* (non-Javadoc)
	 * @see operators.crossover.CrossoverInterface#performCrossover(chromosome.ChromosomeRepresentationInterface, chromosome.ChromosomeRepresentationInterface)
	 */
	@Override
	public List<ChromosomeRepresentationInterface> performCrossover(
			ChromosomeRepresentationInterface parent1,
			ChromosomeRepresentationInterface parent2) {
		
	//	System.out.printf("%-30.30s  %-30.290s%n","Parent1: ", parent1.getChromosome());
		
	//	System.out.printf("%-30.30s  %-30.290s%n","Parent2: ", parent2.getChromosome());
		
		
		ArrayList<ChromosomeRepresentationInterface> offsprings = new ArrayList<ChromosomeRepresentationInterface>();
		
		ArrayList<Boolean> mask = new ArrayList<Boolean>();
		
		Random rand = new Random(Parameters.getSeed());
				
		if(parent1.getSize() == parent2.getSize())
		{
			int size = parent1.getSize();
			for(int i=0; i<size; i++)
			{
				Boolean maskValue = rand.nextBoolean();
				mask.add(maskValue);
			}
		}
		
		ClassInitialization init = new ClassInitialization();
		
		ChromosomeRepresentationInterface offspring1 = init.getChromosomeRepresentation();
		ChromosomeRepresentationInterface offspring2 = init.getChromosomeRepresentation();
		
		offspring1.initializeDefault(mask.size());
		offspring2.initializeDefault(mask.size());
		
		for(int i=0; i<mask.size(); i++)
		{
			if(mask.get(i))
			{
				offspring1.setGene(i, parent1.getGene(i));
				offspring2.setGene(i, parent2.getGene(i));
			}						
			
		}
		for(int i=0; i<mask.size(); i++)
		{
			if(!mask.get(i))
			{
				for(int j=0; j < parent1.getSize(); j++)
				{
					  if (offspring1.getChromosome().indexOf(parent2.getGene(j)) == -1) {
						  offspring1.setGene(j, parent2.getGene(j));
	                  }
				}
				
				for(int j=0; j < parent2.getSize(); j++)
				{
					if (offspring2.getChromosome().indexOf(parent1.getGene(j)) == -1) {
						  offspring2.setGene(j, parent1.getGene(j));
	                  }
				}
				
				
			}	
		
		}
		
		
		offsprings.add(offspring1);
		offsprings.add(offspring2);
		//System.out.printf("%-30.30s  %-30.290s%n","Parent1: ", parent1.getChromosome());
		//System.out.printf("%-30.30s  %-30.290s%n","Mask: ", mask);
		//System.out.printf("%-30.30s  %-30.290s%n","Offspring1: ", offspring1.getChromosome());
		
		//System.out.printf("%-30.30s  %-30.290s%n","Parent2: ", parent2.getChromosome());
		//System.out.printf("%-30.30s  %-30.290s%n","Mask: ", mask);
		//System.out.printf("%-30.30s  %-30.290s%n","Offspring2: ", offspring2.getChromosome());
		
		return offsprings;
		/*
	
	    
	        for (int i = 0; i < maskoff1.length; i++) {
	            if (maskoff1[i] == 0) {
	                for (int z = 0; z < ind2.path.size(); z++) {

	                    if (off1path.indexOf(ind2.path.get(z)) == -1) {
	                        off1path.set(i, ind2.path.get(z));

	                        break;
	                    }
	                }
	            }
	        }

	        for (int i = 0; i < ind2.path.size(); i++) {

	            if (maskoff1[i] == 1) {
	                off2path.set(i, ind2.path.get(i));
	            }

	        }
	        for (int i = 0; i < maskoff1.length; i++) {
	            if (maskoff1[i] == 0) {
	                for (int z = 0; z < ind1.path.size(); z++) {

	                    if (off2path.indexOf(ind1.path.get(z)) == -1) {
	                        off2path.set(i, ind1.path.get(z));

	                        break;
	                    }
	                }
	            }
	        }

	        offsprings[0] = new Individual(off1path);
	        offsprings[1] = new Individual(off2path);
	       // System.out.println("child:" + off1path);
	        return offsprings;
	        
	        
		
		*/
		// TODO Auto-generated method stub
		
	}
	 
}
