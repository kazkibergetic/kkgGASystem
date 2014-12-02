/**
 * 
 */
package chromosome;

import java.util.ArrayList;

import params.Parameters;

/**
 * @author or13uw
 *
 */
public interface ChromosomeRepresentationInterface {

	ArrayList<?> chromosome = new ArrayList();
	
	

	
	/**Checks if the chromosome is feasible
	 * @return true if the chromosome is feasible, otherwise false
	 */
	boolean isFeasible();
	
	/**Generates random chromosome
	 * 
	 */
	void generateChromosome();
	
	/**Replaces the current chromosome's genes with the provided ones
	 * @param newChromosome - set of genes to replace current genes
	 */
	void setChromosome(ArrayList<Integer> newChromosome);
	
	/**Receives current chromosome
	 * @return the current chsromosome's genes
	 */
	ArrayList<?> getChromosome();
	
	/**Receives the size of the chromosome
	 * @return number of genes in chromosome
	 */
	int getSize();
	
	/**Erases all genes in the current chromosome
	 * 
	 */
	void erase();
	
	/**Receives fitness value of the current chromosome
	 * @return fitness value
	 */
	double getFitness();
	
	void setFitness(double newFitness);
	
	/**Replaces the i-th position gene of the current chromosome with the gene provided
	 * @param position - gene's position to replace
	 * @param gene - new gene
	 */
	void setGene(int position, Object gene);
	
	/**Receives the gene at the provided position
	 * @param position of the gene
	 * @return gene at the provided position
	 */
	Object getGene(int position);
	
	
	void setAllGenes(ChromosomeRepresentationInterface ch);
	
	/**Initialise current chromosome with some default genes
	 * 
	 */
	void initializeDefault(int size);
	
	
	
	
}
