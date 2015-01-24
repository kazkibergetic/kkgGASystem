/*********************************************************************************
 * Copyright 2014 Oleg Rybkin
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *********************************************************************************/

package chromosome;

import java.util.List;

/**
* KazKiberGetic GA System (kkgGA)
* The ChromosomeRepresentationInterface is an interface defining the structure and functions of each chromosome in the GA System. 
*
* @author  Oleg Rybkin, kazkibergetic@gmail.com
* @version 1.0
* @since   2014-08-31 
*/

public interface ChromosomeRepresentationInterface<T> extends Cloneable {

	void setMetaData(String meta);

	String getMetaData();
	
	/**Checks if the chromosome is feasible
	 * @return true if the chromosome is feasible, otherwise false
	 */
	boolean isFeasible();
	
	/**Generates random chromosome
	 */
	void generateChromosome();
	
	/**Replaces the current chromosome's genes with the provided ones
	 * @param newChromosome : set of genes to replace current genes
	 */
	void setChromosome(List<T> newChromosome);
	
	/**Receives current chromosome
	 * @return the current chsromosome's genes
	 */
	List<T> getChromosome();
	
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
	
	
	/**Changes the fitness value of the current chromosome
	 * @param newFitness : new fitness value
	 */
	void setFitness(double newFitness);
	
	/**Replaces the i-th position gene of the current chromosome with the gene provided
	 * @param position : gene's position to replace
	 * @param gene : new gene
	 */
	void setGene(int position, T gene);
	
	/**Receives the gene at the provided position
	 * @param position : position of the gene
	 * @return gene at the provided position
	 */
	T getGene(int position);
	
	
	void setAllGenes(ChromosomeRepresentationInterface<T> ch);

	/**Initialize current chromosome with some default genes
	 * @param size : number of genes in chromosome
	 */
	void initializeDefault(int size);

    ChromosomeRepresentationInterface<T> clone();
}
