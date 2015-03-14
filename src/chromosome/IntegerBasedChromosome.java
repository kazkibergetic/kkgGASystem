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

import params.ClassInitialization;
import params.Parameters;
import problems.Points;
import problems.ProblemInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * KazKiberGetic GA System (kkgGA)
 * The IntegerBasedCgromosome implements the chromosome representation, where each gene is an integer.
 * For example, the chromosome could be [1,2,3,4,5,6,7,8,9]
 *
 * @author Oleg Rybkin, kazkibergetic@gmail.com
 * @version 1.0
 * @since 2014-08-31
 */
public class IntegerBasedChromosome implements ChromosomeRepresentationInterface<Integer> {


    //Each chromosome is an array of integers
    List<Integer> chromosome;

    //Fitness value of the chromosome. The aim is to minimize fitness, so the fitness initialized to  infinity
    double fitness = Double.POSITIVE_INFINITY;


    private static ClassInitialization ci = new ClassInitialization();
    private static ProblemInterface problem = ci.getProblem();
    private String metadata;


    public void setMetaData(String meta) {
        metadata = meta;
    }

    public String getMetaData() {
        return metadata;
    }


    @Override
    public boolean isFeasible() {
        throw new UnsupportedOperationException("The method is not implemented yet");
        //return false;
    }

    /* (non-Javadoc)
     * Generates random Integer based chromosome using values, provided by problem interface.
     * @see chromosome.ChromosomeRepresentationInterface#generateChromosome()
     */
    @Override
    public void generateChromosome(Random random) {
        List<Integer> range = new ArrayList<>(problem.getSizeOfChromosome());

        List<Points> points = problem.getPoints();
        for (int i = 0; i < problem.getSizeOfChromosome(); ++i) {
            range.add(points.get(i).id);
        }

        Collections.shuffle(range, random);
        this.chromosome = range;
    }

    /* (non-Javadoc)
     * @see chromosome.ChromosomeRepresentationInterface#setChromosome(java.util.ArrayList)
     */
    @Override
    public void setChromosome(List<Integer> newChromosome) {
        this.chromosome = new ArrayList<Integer>(newChromosome);
    }

    /* (non-Javadoc)
     * @see chromosome.ChromosomeRepresentationInterface#getChromosome()
     */
    @Override
    public List<Integer> getChromosome() {

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
    public void setGene(int position, Integer gene) {
        chromosome.set(position, gene);
    }

    /* (non-Javadoc)
     * @see chromosome.ChromosomeRepresentationInterface#addAllGenes()
     */
    @SuppressWarnings("unchecked")
    @Override
    public void setAllGenes(ChromosomeRepresentationInterface<Integer> ch) {
        this.setChromosome(ch.getChromosome());
    }

    /* (non-Javadoc)
     * @see chromosome.ChromosomeRepresentationInterface#getGene(int)
     */
    @Override
    public Integer getGene(int position) {
        return chromosome.get(position);
    }

    /* (non-Javadoc)
     * Initialize the Integer based chromosome with 0's
     * @see chromosome.ChromosomeRepresentationInterface#initializeDefailt()
     */
    @Override
    public void initializeDefault(int size) {
        chromosome = new ArrayList<Integer>(size);
        for(int i = 0; i < size; ++i){
            chromosome.add(0);
        }
    }

    @Override
    public ChromosomeRepresentationInterface<Integer> clone() {
        IntegerBasedChromosome clone = new IntegerBasedChromosome();
        clone.setAllGenes(this);
        return clone;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Integer gene : chromosome) {
            builder.append(gene).append(" ");
        }
        return builder.toString();
    }
}
