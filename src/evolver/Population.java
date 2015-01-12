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
package evolver;

import java.util.ArrayList;
import java.util.Random;

import operators.crossover.CrossoverInterface;
import operators.mutation.MutationInterface;
import operators.selection.SelectionInterface;
import params.ClassInitialization;
import params.Parameters;
import chromosome.ChromosomeRepresentationInterface;
import exceptions.ChromomesInequalityException;

/**
 * KazKiberGetic GA System (kkgGA) The Population is responsible for creating
 * initial population for first generation, and creating all future generations
 * based on the previous population.
 * 
 * @author Oleg Rybkin, kazkibergetic@gmail.com
 * @version 1.0
 * @since 2014-08-31
 */

public class Population extends Thread {

	private static ClassInitialization ci = new ClassInitialization();

	// best fitness of the population
	private double bestFitness;

	// average fitness of the population
	private double averageFitness;

	// array of chromosomes in the population
	protected ArrayList<ChromosomeRepresentationInterface> chromosomes;

	// the fittest in the population
	private ChromosomeRepresentationInterface bestIndividual;

	Population() {
		chromosomes = new ArrayList<ChromosomeRepresentationInterface>();
		bestIndividual = ci.getChromosomeRepresentation();
	}



	/**
	 * Create initial population for the first generation
	 * 
	 */
	void initialPopulation() {

		
		ClassInitialization chromosome = new ClassInitialization();
		for (int i = 0; i < Parameters.getPopulationSize(); i++) {
			
			ChromosomeRepresentationInterface ch = chromosome
					.getChromosomeRepresentation();
			ch.generateChromosome();
			chromosomes.add(ch);
		}
		try {
			this.evaluateFitness();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	CrossoverInterface crossover = ci.getCrossoverOperator();
	MutationInterface mutation = ci.getMutationOperator();
	SelectionInterface selection = ci.getSelectionOperator();

	/**
	 * Create a new population based on the previous population
	 * 
	 * @param prevPopulation
	 *            : previous population (from the previous generation)
	 * @return new population based on the previous population
	 */
	void createPopulation(Population prevPopulation) {

		
		// Elitism. We want to keep the best individual from the previous
		// generation
		chromosomes.add(prevPopulation.getBestIndividual());

		while (chromosomes.size() < Parameters.getPopulationSize()) {
			ChromosomeRepresentationInterface ind = ci
					.getChromosomeRepresentation();

			// selects one chromosome based on the selection method
			ind = selection.performSelection(prevPopulation);

			Random rand = new Random();

			// performs crossover with specified in the parameter file
			// probability
			if (rand.nextFloat() <= Parameters.getCrossoverProbability()) {
				ChromosomeRepresentationInterface ind2 = ci
						.getChromosomeRepresentation();

				// selects another chromosome for crossover
				ind2 = selection.performSelection(prevPopulation);

				ArrayList<ChromosomeRepresentationInterface> offsprings;
				try {
					// performs crossover
					offsprings = new ArrayList<ChromosomeRepresentationInterface>(
							crossover.performCrossover(ind, ind2));

					// checks, if we can add both offsprings to the current
					// population
					if (chromosomes.size() + 2 < Parameters.getPopulationSize()) {

						// performs mutation on first offspring with specified
						// in the parameter file probability
						if (rand.nextFloat() <= Parameters
								.getMutationProbability()) {
							offsprings
									.set(0, mutation.performMutation(offsprings
											.get(0)));
						}

						// performs mutation on second offspring with specified
						// in the parameter file probability
						if (rand.nextFloat() <= Parameters
								.getMutationProbability()) {
							offsprings
									.set(1, mutation.performMutation(offsprings
											.get(1)));
						}

						// adds offsprings to the current population
						chromosomes.add(offsprings.get(0));
						chromosomes.add(offsprings.get(1));

					} else { // if we can add inly one offspring

						// performs mutation on first offspring with specified
						// in the parameter file probability
						if (rand.nextFloat() <= Parameters
								.getMutationProbability()) {
							offsprings
									.set(0, mutation.performMutation(offsprings
											.get(0)));
						}
						// adds one offspring to the current population
						chromosomes.add(offsprings.get(0));
					}

				} catch (ChromomesInequalityException e) {
					// if chromosomes are not equal
					e.printStackTrace();
				}

			} else { // if no crossover was done

				// performs mutation on the chromosome with specified in the
				// parameter file probability
				if (rand.nextFloat() <= Parameters.getMutationProbability()) {
					ind = mutation.performMutation(ind);
				}

				// adds chromosome to the current population
				chromosomes.add(ind);
			}

		}

		try {
			this.evaluateFitness();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	/**
	 * Receive chromosomes of the provided population
	 * 
	 * @return array of genes of the population
	 */
	public ArrayList<ChromosomeRepresentationInterface> getChromosomes() {
		return this.chromosomes;
	}

	/**
	 * Send current population to concurrent fitness evaluation
	 * 
	 */
	private void evaluateFitness() throws Exception {
		ci.getFitnessEvaluationOperator().preEvaluateFitness();
		if (this.chromosomes.size() == Parameters.getPopulationSize()) {

			// number of chromosomes to evaluate in one thread
			int portion = Parameters.getPopulationSize()
					/ Parameters.getNumberOfProcessors();

			Thread[] threads = new Thread[Parameters.getNumberOfProcessors()];

			int offset = 0;
			for (int i = 0; i < Parameters.getNumberOfProcessors(); i++) {

				if (i == Parameters.getNumberOfProcessors() - 1) {
					if (offset + portion < Parameters.getPopulationSize()) {
						portion = (Parameters.getPopulationSize() - ((Parameters
								.getNumberOfProcessors() - 1) * portion));
					}
				}
				Evaluator evaluator = new Evaluator(chromosomes);
				evaluator.beginIndex = offset;
				evaluator.endIndex = offset + portion;

				offset += portion;

				threads[i] = new Thread(evaluator);
				threads[i].start();
			}

			for (int i = 0; i < Parameters.getNumberOfProcessors(); i++) {
				try {
					threads[i].join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			
			
			

		} else {
			throw new Exception(
					"Current population size in not equal to desired population size.");
		}
		ci.getFitnessEvaluationOperator().postEvaluateFitness(chromosomes);
		
		// finds average fitness of the population
		this.findAverageFitness();
		// finds best fitness of the population
		this.findBestFitness();
	}

	/**
	 * Find the best individual in the current population besed on the fitness
	 * values
	 * 
	 */
	private void findBestFitness() {

		double minFitness = chromosomes.get(0).getFitness();
		int minFitnessID = 0;
		for (int i = 1; i < this.chromosomes.size(); i++) {
			if (this.chromosomes.get(i).getFitness() < minFitness) {
				minFitness = this.chromosomes.get(i).getFitness();
				minFitnessID = i;
			}
		}
		this.bestFitness = minFitness;
		this.bestIndividual = this.chromosomes.get(minFitnessID);

	}

	/**
	 * Find the average fitness of the entire population
	 * 
	 */
	private void findAverageFitness() {
		float totalFitness = 0;
		for (int i = 0; i < this.chromosomes.size(); i++) {

			totalFitness += this.chromosomes.get(i).getFitness();

		}

		this.averageFitness = totalFitness / this.chromosomes.size();

	}

	/**
	 * Receive the average fitness of the entire population
	 * 
	 * @return average fitness of the entire population
	 */
	public double getAverageFitness() {

		return this.averageFitness;
	}

	/**
	 * Receive the best fitness value in the population
	 * 
	 * @return the best fitness value in the population
	 */
	public double getBestFitness() {
		return this.bestFitness;
	}

	/**
	 * Receive the best individual in the population based on the fitness value
	 * 
	 * @return the best individual in the population
	 */
	public ChromosomeRepresentationInterface getBestIndividual() {
		return this.bestIndividual;
	}

}
