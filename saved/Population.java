/**
 * 
 */
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
 * @author or13uw
 *
 */
public class Population {

	private ClassInitialization ci = new ClassInitialization();

	private double bestFitness;
	private double averageFitness;

	private ArrayList<ChromosomeRepresentationInterface> chromosomes = new ArrayList<ChromosomeRepresentationInterface>();
	private ChromosomeRepresentationInterface bestIndividual = ci
			.getChromosomeRepresentation();

	Population() {

	}

	Population(ArrayList<ChromosomeRepresentationInterface> all_chromosomes) {
		chromosomes = new ArrayList<ChromosomeRepresentationInterface>(
				all_chromosomes);

		this.evaluateFitness();

	}

	Population initialPopulation() {
		for (int i = 0; i < Parameters.getPopulationSize(); i++) {
			ClassInitialization chromosome = new ClassInitialization();
			ChromosomeRepresentationInterface ch = chromosome
					.getChromosomeRepresentation();
			ch.generateChromosome();
			chromosomes.add(ch);
		}

		return (new Population(this.chromosomes));

	}

	/**
	 * @param p
	 * @return
	 */
	Population createPopulation(Population p) {

		CrossoverInterface crossover = ci.getCrossoverOperator();
		MutationInterface mutation = ci.getMutationOperator();
		SelectionInterface selection = ci.getSelectionOperator();

		chromosomes.add(p.getBestIndividual());
		while (chromosomes.size() < Parameters.getPopulationSize()) {
			ChromosomeRepresentationInterface ind = ci
					.getChromosomeRepresentation();
			ind = selection.performSelection(p);

			Random rand = new Random();
			if (rand.nextFloat() <= Parameters.getCrossoverProbability()) {
				ChromosomeRepresentationInterface ind2 = ci
						.getChromosomeRepresentation();
				ind2 = selection.performSelection(p);

				// System.out.println(ind);

				// System.out.println(ind2);

				ArrayList<ChromosomeRepresentationInterface> offsprings;
				try {
					offsprings = new ArrayList<ChromosomeRepresentationInterface>(
							crossover.performCrossover(ind, ind2));

					if (chromosomes.size() + 2 < Parameters.getPopulationSize()) {

						if (rand.nextFloat() <= Parameters
								.getMutationProbability()) {
							offsprings
									.set(0, mutation.performMutation(offsprings
											.get(0)));
						}

						if (rand.nextFloat() <= Parameters
								.getMutationProbability()) {
							offsprings
									.set(1, mutation.performMutation(offsprings
											.get(1)));
						}

						chromosomes.add(offsprings.get(0));
						chromosomes.add(offsprings.get(1));

					} else {
						if (rand.nextFloat() <= Parameters
								.getMutationProbability()) {
							offsprings
									.set(0, mutation.performMutation(offsprings
											.get(0)));
						}
						chromosomes.add(offsprings.get(0));
					}

				} catch (ChromomesInequalityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				chromosomes.add(ind);
			}

		}

		return (new Population(this.chromosomes));

	}

	public ArrayList<ChromosomeRepresentationInterface> getChromosomes() {
		return this.chromosomes;
	}

	/* 
	  	private void evaluateFitness() {
	
		if (this.chromosomes.size() == Parameters.getPopulationSize()) {
			// ClassInitialization fitnessCalculator = new
			// ClassInitialization();
	
	
    	int processorCount =   parameters.getNumberofThreads();  //Runtime.getRuntime().availableProcessors();
		int portion = populationSize / processorCount;
		
		Thread[] threads = new Thread[processorCount];
	
			for (int i = 0; i < processorsCount; i++) {
			
				Eval.beginIndex = ?
				Eval.endIndex = ?
				
				
			    threads[i] = new Thread(eval);
		     	threads[i].start();
			}
			
		for (int i = 0 ; i < processorCount ; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
			
			
			this.findAverageFitness();
			this.findBestFitness();

		} else {
			// error
		}

	}
	
	
	private class Eval implements Runnable
	{
		int beginIndex;
		int endIndex; 
		public void  run()
		{
				for (int i = beginIndex; i < endIndex; i++) {
				double f = ci.getFitnessEvaluationOperator().evaluateFitness(
						this.chromosomes.get(i));
				this.chromosomes.get(i).setFitness(f);
				// System.out.println(chromosomes.get(i).getFitness());
			}

		}
	}

	 */
	
	private void evaluateFitness() {
		if (this.chromosomes.size() == Parameters.getPopulationSize()) {
			// ClassInitialization fitnessCalculator = new
			// ClassInitialization();
			for (int i = 0; i < this.chromosomes.size(); i++) {
				double f = ci.getFitnessEvaluationOperator().evaluateFitness(
						this.chromosomes.get(i));
				this.chromosomes.get(i).setFitness(f);
				// System.out.println(chromosomes.get(i).getFitness());
			}
			this.findAverageFitness();
			this.findBestFitness();

		} else {
			// error
		}

	}

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
		// System.out.println(">>>"+this.bestFitness);
		this.bestIndividual = this.chromosomes.get(minFitnessID);

	}

	private void findAverageFitness() {
		float totalFitness = 0;
		for (int i = 0; i < this.chromosomes.size(); i++) {

			totalFitness += this.chromosomes.get(i).getFitness();

		}

		this.averageFitness = totalFitness / this.chromosomes.size();

	}

	public double getAverageFitness() {

		return this.averageFitness;
	}

	public double getBestFitness() {
		return this.bestFitness;
	}

	public ChromosomeRepresentationInterface getBestIndividual() {
		return this.bestIndividual;
	}

}
