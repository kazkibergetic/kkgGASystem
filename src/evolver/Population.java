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

import chromosome.ChromosomeRepresentationInterface;
import operators.crossover.CrossoverInterface;
import operators.mutation.MutationInterface;
import operators.selection.SelectionInterface;
import params.ClassInitialization;
import params.Parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
    protected List<ChromosomeRepresentationInterface> chromosomes;

    // the fittest in the population
    private ChromosomeRepresentationInterface bestIndividual;
    private ExecutorService executorService;

    Population() {
        chromosomes = new CopyOnWriteArrayList<ChromosomeRepresentationInterface>();
        bestIndividual = ci.getChromosomeRepresentation();
        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(Parameters.getPopulationSize() * 2);
        }
    }


    /**
     * Create initial population for the first generation
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
     * @param prevPopulation : previous population (from the previous generation)
     * @return new population based on the previous population
     */
    void createPopulation(Population prevPopulation) {

        Random rand = new Random();

        // Elitism. We want to keep the best individual from the previous generation
        chromosomes.add(prevPopulation.getBestIndividual());
        int numberOfChromosomes = chromosomes.size();
        List<Future> futures = new ArrayList<>(Parameters.getPopulationSize());
        while (numberOfChromosomes < Parameters.getPopulationSize()) {
            // selects one chromosome based on the selection method
            ChromosomeRepresentationInterface ind = selection.performSelection(prevPopulation);

            // performs crossover with specified in the parameter file
            // probability
            if (rand.nextFloat() <= Parameters.getCrossoverProbability()) {
                int numberToAdd = Parameters.getPopulationSize() - numberOfChromosomes >= 2? 2: 1;

                // selects another chromosome for crossover
                ChromosomeRepresentationInterface ind2 = selection.performSelection(prevPopulation);
                CrossoverWithMutations crossoverWithMutations = new CrossoverWithMutations(ind, ind2, numberToAdd);
                futures.add(executorService.submit(crossoverWithMutations));
                numberOfChromosomes += 2;
            } else { // if no crossover was done
                MutationWithoutCrossover mutationWithoutCrossover = new MutationWithoutCrossover(ind);
                futures.add(executorService.submit(mutationWithoutCrossover));
                ++numberOfChromosomes;
            }
        }
        try {
            for (Future future : futures) {
                future.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            this.evaluateFitness();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class CrossoverWithMutations implements Runnable {
        private Random rand = new Random();
        private int numberToAdd;
        private ChromosomeRepresentationInterface ind;
        private ChromosomeRepresentationInterface ind2;

        private CrossoverWithMutations(ChromosomeRepresentationInterface ind, ChromosomeRepresentationInterface ind2, int numberToAdd) {
            this.numberToAdd = numberToAdd;
            this.ind = ind;
            this.ind2 = ind2;
        }

        @Override
        public void run() {
            try {
                // performs crossover
                List<ChromosomeRepresentationInterface> offsprings = new ArrayList<>(crossover.performCrossover(ind, ind2));

                Future<ChromosomeRepresentationInterface> mutation1 = null;
                Future<ChromosomeRepresentationInterface> mutation2 = null;

                // performs mutation on first offspring with specified in the parameter file probability
                if (rand.nextFloat() <= Parameters.getMutationProbability()) {
                    Mutation mutation = new Mutation(offsprings.get(0));
                    mutation1 = executorService.submit(mutation);
                }

                // if we can add both offsprings to the current population
                if (numberToAdd == 2) {

                    // performs mutation on second offspring with specified in the parameter file probability
                    if (rand.nextFloat() <= Parameters.getMutationProbability()) {
                        Mutation mutation = new Mutation(offsprings.get(1));
                        mutation2 = executorService.submit(mutation);
                    }
                    if (mutation1 != null) {
                        offsprings.set(0, mutation1.get());
                    }
                    if (mutation2 != null) {
                        offsprings.set(1, mutation2.get());
                    }
                    chromosomes.add(offsprings.get(1));
                } else {
                    if (mutation1 != null) {
                        offsprings.set(0, mutation1.get());
                    }
                }

                // adds offsprings to the current population
                chromosomes.add(offsprings.get(0));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class MutationWithoutCrossover implements Runnable {
        private ChromosomeRepresentationInterface ind;
        private Random rand = new Random();

        private MutationWithoutCrossover(ChromosomeRepresentationInterface ind) {
            this.ind = ind;
        }

        @Override
        public void run() {
            // performs mutation on the chromosome with specified in the
            // parameter file probability
            if (rand.nextFloat() <= Parameters.getMutationProbability()) {
                Mutation mutation = new Mutation(ind);
                try {
                    ind = executorService.submit(mutation).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // adds chromosome to the current population
            chromosomes.add(ind);
        }
    }

    /**
     * Receive chromosomes of the provided population
     *
     * @return array of genes of the population
     */
    public List<ChromosomeRepresentationInterface> getChromosomes() {
        return this.chromosomes;
    }

    /**
     * Send current population to concurrent fitness evaluation
     */
    private void evaluateFitness() throws Exception {
        ci.getFitnessEvaluationOperator().preEvaluateFitness();
        if (this.chromosomes.size() == Parameters.getPopulationSize()) {

            // number of chromosomes to evaluate in one thread
            int numberOfProcessors = chromosomes.size();

            List<Future> futures = new ArrayList<>(numberOfProcessors);
            for (int i = 0; i < numberOfProcessors; i++) {
                Evaluator evaluator = new Evaluator(chromosomes);
                evaluator.beginIndex = i;
                evaluator.endIndex = i + 1;

                Future<?> future = executorService.submit(evaluator);
                futures.add(future);
            }
            for (Future future : futures) {
                future.get();
            }
        } else {
            throw new Exception("Current population size in not equal to desired population size.");
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

    private class Crossover implements Callable<List<ChromosomeRepresentationInterface>> {
        private ChromosomeRepresentationInterface ind1;
        private ChromosomeRepresentationInterface ind2;

        private Crossover(ChromosomeRepresentationInterface ind1,
                          ChromosomeRepresentationInterface ind2) {
            this.ind1 = ind1;
            this.ind2 = ind2;
        }

        @Override
        public List<ChromosomeRepresentationInterface> call() throws Exception {
            return new ArrayList<ChromosomeRepresentationInterface>(
                    crossover.performCrossover(ind1, ind2));
        }
    }

    private class Mutation implements Callable<ChromosomeRepresentationInterface> {

        private ChromosomeRepresentationInterface ind;

        private Mutation(ChromosomeRepresentationInterface ind) {
            this.ind = ind;
        }

        @Override
        public ChromosomeRepresentationInterface call() throws Exception {
            return mutation.performMutation(ind);
        }
    }

}
