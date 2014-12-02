/**
 * 
 */
package evolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import chromosome.ChromosomeRepresentationInterface;
import params.ClassInitialization;

/**
 * @author or13uw
 *
 */
public class InitialPopulation implements Runnable {

	int beginIndex=0;
	int endIndex=0; 
	private ArrayList<ChromosomeRepresentationInterface> chromosomes;
	
	private ClassInitialization ci = new ClassInitialization();
	List l;
	InitialPopulation(ArrayList<ChromosomeRepresentationInterface> ch)
	{
		chromosomes=ch;
		l = Collections.synchronizedList(ch);
	}
	
	public void run() {
		ClassInitialization chromosome = new ClassInitialization();
		System.out.println("FROM "+beginIndex+" TO "+endIndex);
		for (int i = beginIndex; i < endIndex; i++) {
			ChromosomeRepresentationInterface ch = chromosome
					.getChromosomeRepresentation();
			ch.generateChromosome();
			//System.out.println(ch.getChromosome());
			l.add(ch);
		}
		System.out.println("TOTAL: "+chromosomes.size());
		chromosomes.addAll(new ArrayList<ChromosomeRepresentationInterface>(l));
	}

}
