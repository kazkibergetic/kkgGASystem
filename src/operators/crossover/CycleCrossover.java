/**
 * 
 */
package operators.crossover;

import java.util.ArrayList;

import params.ClassInitialization;
import chromosome.ChromosomeRepresentationInterface;
import exceptions.ChromomesInequalityException;

/**
 * @author or13uw
 *
 */
public class CycleCrossover implements CrossoverInterface {

	ClassInitialization ci = new ClassInitialization();

	/*
	 * (non-Javadoc)
	 * 
	 * @see operators.crossover.CrossoverInterface#performCrossover(chromosome.
	 * ChromosomeRepresentationInterface,
	 * chromosome.ChromosomeRepresentationInterface)
	 */
	@Override
	public ArrayList<ChromosomeRepresentationInterface> performCrossover(
			ChromosomeRepresentationInterface ind1,
			ChromosomeRepresentationInterface ind2) throws ChromomesInequalityException {
		
		//System.out.println("PA1: "+ ind1.getChromosome());
		//System.out.println("PA2: " + ind2.getChromosome());
		
		
		ArrayList<ChromosomeRepresentationInterface> offsprings = new ArrayList<ChromosomeRepresentationInterface>();
		
		if (ind1.getSize() == ind2.getSize()) {
			
			offsprings.add(this.fillOffspring(ind1, ind2));
			offsprings.add(this.fillOffspring(ind2, ind1));
			
			//System.out.println("CH1: "+offsprings.get(0).getChromosome());
			//System.out.println("CH2: " + offsprings.get(1).getChromosome());
			
		}
		else
		{
			throw new ChromomesInequalityException();
		}
		
		return offsprings;
	}

	private ChromosomeRepresentationInterface fillOffspring(
			ChromosomeRepresentationInterface ind1,
			ChromosomeRepresentationInterface ind2) {

		Object value = ind1.getGene(0);
		int i = 0;

		ChromosomeRepresentationInterface offspring = ci
				.getChromosomeRepresentation();
		offspring.initializeDefault(ind1.getSize());

		while (((Integer) offspring.getGene(i)) == 0) {
			offspring.setGene(i, value);
			value = ind1.getGene(i);
			i = ind1.getChromosome().indexOf(value);
		}

		for (i = 0; i < offspring.getSize(); ++i) {
			if (((Integer) offspring.getGene(i)) == 0) {
				offspring.setGene(i, ind2.getGene(i));

			}
		}
		return offspring;
	}
}
