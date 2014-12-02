/**
 * 
 */
package chromosome;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import params.Parameters;
import params.ClassInitialization;
import problems.ProblemInterface;



/**
 * @author or13uw
 *
 */
public class OrderBasedInteger implements ChromosomeRepresentationInterface{

	
	ArrayList<Integer> chromosome;
	double fitness=Double.MAX_VALUE;
	private static  ClassInitialization ci = new ClassInitialization();
	private static ProblemInterface problem = ci.getProblem();
	


	
	@Override
	public boolean isFeasible() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see chromosome.ChromosomeRepresentationInterface#generateChromosome()
	 */
	@Override
	public void generateChromosome() {
		
		/*Random rand = new Random();
		while(chromosome.size() < Parameters.getChromosomeSize())
		{
			int toAdd = rand.nextInt(Parameters.getChromosomeSize()+1);
			
			if(!chromosome.contains(toAdd))
			{
				chromosome.add(toAdd);
			}
		}*/
		
		ArrayList<Integer> range = new ArrayList<Integer> ();
		
	//	System.out.println("SIZE"+problem.getSizeOfChromosome());
		
		for(int i=0; i<problem.getSizeOfChromosome(); i++)
		{
			range.add(problem.getPoints().get(i).id);
		}
				
		Collections.shuffle(range);
		
		//this.setChromosome(range);
		this.chromosome = new ArrayList<Integer>(range);
		//System.out.println(this.chromosome);
		
	}

	/* (non-Javadoc)
	 * @see chromosome.ChromosomeRepresentationInterface#setChromosome(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setChromosome(ArrayList<Integer> newChromosome) {
		this.chromosome = new ArrayList<Integer>(newChromosome);
		
		
	}

	/* (non-Javadoc)
	 * @see chromosome.ChromosomeRepresentationInterface#getChromosome()
	 */
	@Override
	public ArrayList<?> getChromosome() {
		
		return this.chromosome;
	}

	/* (non-Javadoc)
	 * @see chromosome.ChromosomeRepresentationInterface#getSize()
	 */
	@Override
	public int getSize() {
	
		return chromosome.size();
	}

	/* (non-Javadoc)
	 * @see chromosome.ChromosomeRepresentationInterface#erase()
	 */
	@Override
	public void erase() {
		
		chromosome.clear();
		
	}

	/* (non-Javadoc)
	 * @see chromosome.ChromosomeRepresentationInterface#getFitness()
	 */
	@Override
	public double getFitness() {
		
		return this.fitness;
	}

	/* (non-Javadoc)
	 * @see chromosome.ChromosomeRepresentationInterface#setFitness(float)
	 */
	@Override
	public void setFitness(double newFitness) {
		this.fitness = newFitness;
		
	}

	/* (non-Javadoc)
	 * @see chromosome.ChromosomeRepresentationInterface#addGene(int, java.lang.Object)
	 */
	@Override
	public void setGene(int position, Object gene) {
		
		chromosome.set(position, (Integer) gene);
	}

	/* (non-Javadoc)
	 * @see chromosome.ChromosomeRepresentationInterface#addAllGenes()
	 */
	@Override
	public void setAllGenes(ChromosomeRepresentationInterface ch) {
	
		
		this.setChromosome((ArrayList<Integer>) ch.getChromosome());
		
		
	}

	/* (non-Javadoc)
	 * @see chromosome.ChromosomeRepresentationInterface#getGene(int)
	 */
	@Override
	public Integer getGene(int position) {
		
		return chromosome.get(position);
	}

	/* (non-Javadoc)
	 * @see chromosome.ChromosomeRepresentationInterface#initializeDefailt()
	 */
	@Override
	public void initializeDefault(int size) {
		
		
		chromosome = new ArrayList<Integer>(size);
		for(int i=0; i< size; i++)
		{
			chromosome.add(0);
			
		}
	}
	
	


	

}
