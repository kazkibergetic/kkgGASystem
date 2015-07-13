/**
 * 
 */
package operators.crossover;

import chromosome.ChromosomeRepresentationInterface;
import evolver.RunEvolutionContext;
import exceptions.ChromomesInequalityException;
import params.ClassInitialization;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
	public List<ChromosomeRepresentationInterface> performCrossover(
			RunEvolutionContext runEvolutionContext,
			ChromosomeRepresentationInterface ind1,
			ChromosomeRepresentationInterface ind2)
			throws ChromomesInequalityException {

		// System.out.println("PA1: "+ ind1.getChromosome());
		// System.out.println("PA2: " + ind2.getChromosome());

		
//		ind1.setGene(0, 8);
//		ind1.setGene(1, 4);
//		ind1.setGene(2, 7);
//		ind1.setGene(3, 3);
//		ind1.setGene(4, 6);
//		ind1.setGene(5, 2);
//		ind1.setGene(6, 5);
//		ind1.setGene(7, 1);
//		ind1.setGene(8, 9);
//		ind1.setGene(9, 10);
//
//		ind2.setGene(0, 10);
//		ind2.setGene(1, 1);
//		ind2.setGene(2, 2);
//		ind2.setGene(3, 3);
//		ind2.setGene(4, 4);
//		ind2.setGene(5, 5);
//		ind2.setGene(6, 6);
//		ind2.setGene(7, 7);
//		ind2.setGene(8, 8);
//		ind2.setGene(9, 9);
//		
//		ind1.setGene(0, 4);
//		ind1.setGene(1, 9);
//		ind1.setGene(2, 3);
//		ind1.setGene(3, 7);
//		ind1.setGene(4, 2);
//		ind1.setGene(5, 6);
//		ind1.setGene(6, 8);
//		ind1.setGene(7, 1);
//		ind1.setGene(8, 5);
//
//		ind2.setGene(0, 5);
//		ind2.setGene(1, 6);
//		ind2.setGene(2, 8);
//		ind2.setGene(3, 2);
//		ind2.setGene(4, 3);
//		ind2.setGene(5, 4);
//		ind2.setGene(6, 7);
//		ind2.setGene(7, 1);
//		ind2.setGene(8, 9);

		ArrayList<ChromosomeRepresentationInterface> offsprings = new ArrayList<ChromosomeRepresentationInterface>();

		
		ChromosomeRepresentationInterface offspring1 = ci.getChromosomeRepresentation();
		ChromosomeRepresentationInterface offspring2 = ci.getChromosomeRepresentation();

		
		
		if (ind1.getSize() == ind2.getSize()) {

			offspring1 = ind1.clone();
			offspring2 = ind2.clone();

			// the set of all visited indices so far
			final Set<Integer> visitedIndices = new HashSet<Integer>(
					ind1.getSize());
			// the indices of the current cycle
			final List<Integer> indices = new ArrayList<Integer>(ind1.getSize());

			Random rand = runEvolutionContext.getRandom();
			// determine the starting index
			int idx = rand.nextInt(ind1.getSize());
			
			System.out.println("ind "+idx);
			int cycle = 1;

			while (visitedIndices.size() < ind1.getSize()) {
				indices.add(idx);

				Object item = ind2.getGene(idx);
				idx = ind1.getChromosome().indexOf(item);

				while (idx != indices.get(0)) {
					// add that index to the cycle indices
					indices.add(idx);
					// get the item in the second parent at that index
					item = ind2.getGene(idx);
					// get the index of that item in the first parent
					idx = ind1.getChromosome().indexOf(item);
				}

				// for even cycles: swap the child elements on the indices found
				// in this cycle
				if (cycle++ % 2 != 0) {
					for (int i : indices) {
						Object tmp = offspring1.getGene(i);
						offspring1.setGene(i, offspring2.getGene(i));
						offspring2.setGene(i, tmp);
					}
				}

				visitedIndices.addAll(indices);
				// find next starting index: last one + 1 until we find an
				// unvisited index
				idx = (indices.get(0) + 1) % ind1.getSize();
				while (visitedIndices.contains(idx)
						&& visitedIndices.size() < ind1.getSize()) {
					idx++;
					if (idx >= ind1.getSize()) {
						idx = 0;
					}
				}
				indices.clear();
			}

			offsprings.add(offspring1);
			offsprings.add(offspring2);
			// offsprings.add(this.fillOffspring(ind1, ind2));
			// offsprings.add(this.fillOffspring(ind2, ind1));

			// System.out.println("CH1: "+offsprings.get(0).getChromosome());
			// System.out.println("CH2: " + offsprings.get(1).getChromosome());

		} else {
			throw new ChromomesInequalityException();
		}
//
//		System.out.println("Parent 1: " + ind1.getChromosome());
//		System.out.println("Parent 2: " + ind2.getChromosome());
//
//		System.out.println("off1: " + offsprings.get(0));
//		System.out.println("off2: " + offsprings.get(1));

		return offsprings;
	}

	// private ChromosomeRepresentationInterface fillOffspring(
	// ChromosomeRepresentationInterface ind1,
	// ChromosomeRepresentationInterface ind2) {
	//
	// Object value = ind1.getGene(0);
	// int i = 0;
	//
	// ChromosomeRepresentationInterface offspring = ci
	// .getChromosomeRepresentation();
	// offspring.initializeDefault(ind1.getSize());
	//
	// while (((Integer) offspring.getGene(i)) == 0) {
	//
	// offspring.setGene(i, value);
	// value = ind1.getGene(i);
	// i = ind1.getChromosome().indexOf(value);
	// }
	//
	// for (i = 0; i < offspring.getSize(); ++i) {
	// if (((Integer) offspring.getGene(i)) == 0) {
	// offspring.setGene(i, ind2.getGene(i));
	//
	// }
	// }
	// return offspring;
	// }
}
