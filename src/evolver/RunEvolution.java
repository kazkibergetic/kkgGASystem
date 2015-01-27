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
import output.DisplayInfo;
import params.Parameters;

import java.util.ArrayList;
import java.util.List;

/**
 * KazKiberGetic GA System (kkgGA) The RunEvolution responsible for running the
 * entire evolution (from generation 0 to generation n) for each experiment
 * (run)
 *
 * @author Oleg Rybkin, kazkibergetic@gmail.com
 * @version 1.0
 * @since 2014-08-31
 */

public class RunEvolution {

    // array of last populations
    ArrayList<Population> populations = new ArrayList<Population>();

    // id of the current generation
    int currentGeneration;

    // best fitness of the run
    private double bestFitness = Double.POSITIVE_INFINITY;

    // best chromosome of the run
    private List<ChromosomeRepresentationInterface> bestIndividuals = new ArrayList<>();

    private long duration;

    private RunEvolutionContext runEvolutionContext;

    /**
     * Constructor. Runs evolution for each experiment(run)
     *
     * @param run           : id if the current run
     */
    RunEvolution(int run, RunEvolutionContext runEvolutionContext) {
        currentGeneration = 1;

        long a = System.currentTimeMillis();
        this.evolve(runEvolutionContext);
        duration = System.currentTimeMillis() - a;
    }

    RunEvolution() throws Exception {
        throw new Exception(
                "Please, provide the constructor with necessary parameters");
    }

    /**
     * Evolver. Responsible for running the entire evolution and keeping the
     * best fitness and chromosome of the previous experiments(runs)
     *
     */
    void evolve(RunEvolutionContext runEvolutionContext) {

        for (int i = 0; i < Parameters.getNumberOfGenerations(); i++) {

            // create initial population
            // Population populationGenerator = new Population();
            DisplayInfo.displayGeneration(i);

            Population current = new Population();
            if (this.currentGeneration == 1) {
                // generate initial population
                current.initialPopulation(runEvolutionContext);

            } else {
                // create population based on the previous population
                current.createPopulation(runEvolutionContext, populations.get(populations.size() - 1));

            }

            DisplayInfo.displayGenerationStatistics(current, runEvolutionContext);
            runEvolutionContext.getFitnessOutput().writeStatistics(currentGeneration, current, runEvolutionContext);

            // add current population to the array of populations
            populations.add(current);

            // increase current generation
            currentGeneration++;

            // if this experiment(run) has chromosome with the better fitness
            // value than the best chromosome of the previous runs
            if (current.getBestFitness() < this.bestFitness) {
                this.bestFitness = current.getBestFitness();

                this.bestIndividuals = current.getBestIndividuals();

            }
            // keep only last 2 populations
            if (populations.size() > 3) {
                populations.remove(populations.size() - 2);
            }

        }

    }

    /**
     * Return the best fitness of the all experiments(runs)
     *
     * @return best fitness among all runs
     */
    public double getBestFitness() {
        return this.bestFitness;
    }

    /**
     * Return the best chromosome of the all experiments(runs)
     *
     * @return best chromosome among all runs
     */
    public List<ChromosomeRepresentationInterface> getBestIndividuals() {
        return this.bestIndividuals;
    }

    public RunEvolutionContext getRunEvolutionContext() {
        return runEvolutionContext;
    }

    public long getDuration() {
        return duration;
    }
}
