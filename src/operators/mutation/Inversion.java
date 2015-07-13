/**
 * 
 */
package operators.mutation;

import chromosome.ChromosomeRepresentationInterface;
import evolver.RunEvolutionContext;

import java.util.Collections;
import java.util.Random;

import params.ClassInitialization;

/**
 * @author or13uw
 *
 */
public class Inversion implements MutationInterface {

	ClassInitialization ci = new ClassInitialization();

	/*
	 * (non-Javadoc)
	 * 
	 * @see operators.mutation.MutationInterface#performMutation(chromosome.
	 * ChromosomeRepresentationInterface)
	 */
	@Override
	public ChromosomeRepresentationInterface performMutation(
			RunEvolutionContext runEvolutionContext,
			ChromosomeRepresentationInterface ind) {

//		System.out.println(ind.getSize());
//		ind.setGene(0, 4);
//		ind.setGene(1, 9);
//		ind.setGene(2, 3);
//		ind.setGene(3, 7);
//		ind.setGene(4, 2);
//		ind.setGene(5, 6);
//		ind.setGene(6, 8);
//		ind.setGene(7, 1);

		ChromosomeRepresentationInterface clone = ind.clone();
		// Collections.reverse(clone.getChromosome());

		ChromosomeRepresentationInterface result = ci
				.getChromosomeRepresentation();

		result.initializeDefault(clone.getSize());

		Random rand = runEvolutionContext.getRandom();

		
		
		
//		System.out.println("Initial "+clone.getChromosome());
		
		
		
		
		
		// Subtour start and end positions.
//		
		 int position2 = rand.nextInt(clone.getSize()-1);
		 int position1 = rand.nextInt(++position2);
		 
		 if(position2-position1 >position2-2)
		 {
			 position2-=2;
		 }
//
//		int position2 = 3;
//		int position1 = 1;

//		System.out.println("P1: " + position1);
//		System.out.println("P2: " + position2);

		// Tour subtour = t.subTour(position1,position2);

		ChromosomeRepresentationInterface subtour = ci
				.getChromosomeRepresentation();
		int repsize = position2 - position1 + 1;
		subtour.initializeDefault(repsize);
		int d = 0;
		for (int i = position1; i <= position2; i++) {
			subtour.setGene(d, clone.getGene(i));
			d++;

		}

		// Lists of cities to be added to tour.
		// List<Integer> subTourRoute = subtour.getTour();
		// List<Integer> route = new ArrayList<Integer>();
		//
		// route.addAll(t.getTour());
		// route.removeAll(subTourRoute);
		// Generate somewhere to place the subtour.

		 int insertionPoint = rand.nextInt(result.getSize() - repsize);
		 
//		 int n = result.getSize() - repsize;
//		 System.out.println("max "+n);
//		 int i = rand.nextInt() % n;
//		 int insertionPoint =  1 + i;

//     	int insertionPoint = 1;
		// Add cities up to insertion point

		 
		 
//		System.out.println("Insert: " + insertionPoint);
		
		

//		for (int i = 0; i <= insertionPoint; i++) {
//			result.setGene(i, clone.getGene(i));
//
//		}
		
		
		int inserted = 0;
		int currentPosition = 0;
		while (insertionPoint != 0 && inserted-1 != insertionPoint)
		{
			
			if (currentPosition >= position1 && currentPosition <= position2) {
				currentPosition++;
				continue;
			}
			result.setGene(inserted, clone.getGene(currentPosition));
			inserted++;
			currentPosition++;
		}

		
		
//		System.out.println("after move"+result.getChromosome());
		
		
		
		
		if (insertionPoint > 0) {
			insertionPoint++;
		}

		int afterInsert = insertionPoint;
		
		
//		System.out.println(afterInsert);
		
		
		
		// Insert from end (reversed subtour)
		for (int j = (subtour.getSize() - 1); j >= 0; j--) {
			result.setGene(afterInsert, subtour.getGene(j));
			// System.out.println("a "+afterInsert);
			afterInsert++;
		}

//		System.out.println("after insert"+result.getChromosome());
//		
//		
//		// Add remainder of cities.
//		
//		System.out.println("IP " + afterInsert);
//		System.out.println("CP " + currentPosition);
		
		
		
		for (int k = currentPosition; k < clone.getSize(); k++) {
//			System.out.println("k = " + k);
			if (k >= position1 && k <= position2) {
				continue;
			}
			if(afterInsert == clone.getSize())
				break;
			
			result.setGene(afterInsert, clone.getGene(k));
//			System.out.println("a " + afterInsert);
			afterInsert++;

		}

//		System.out.println("R: " + result.getChromosome());
		
		
		return result;

		// return clone;
	}

}
