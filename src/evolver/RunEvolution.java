package evolver;

import java.util.ArrayList;

import chromosome.ChromosomeRepresentationInterface;
import output.DisplayInfo;
import output.statistics.FitnessResults;
import params.Parameters;

public class RunEvolution {

	
	ArrayList<Population> populations = new ArrayList<Population>();
	int currentGeneration;
	
	private double bestFitness = Double.MAX_VALUE;
	private ChromosomeRepresentationInterface bestIndividual= null;
	
	
	RunEvolution(int run, FitnessResults f)
	{
		currentGeneration=1;
					
		this.evolve(f);
	}
	
	RunEvolution()
	{
		
	}
	void evolve(FitnessResults fitnessOutput)
	{
		
		
		for(int i = 0; i < Parameters.getNumberOfGenerations(); i++)
		{
			Population populationGenerator = new Population();
			DisplayInfo.displayGeneration(i);
			
			
			Population current = new Population();
			if(this.currentGeneration == 1)
			{				
				current = populationGenerator.initialPopulation();
				//System.out.println(current.getAverageFitness());
			
			}
			else
			{
				current = populationGenerator.createPopulation(populations.get(populations.size()-1));
							
			}
			DisplayInfo.displayGenerationStatistics(current);
			fitnessOutput.writeStatistics(currentGeneration, current);
			populations.add(current);			
			currentGeneration++;
			
			
			if(current.getBestFitness() < this.bestFitness)
			{
				this.bestFitness = current.getBestFitness();
				this.bestIndividual = current.getBestIndividual();
				
				
			}
			if(populations.size()>3)
			{
				populations.remove(populations.size()-2);
			}
			
			
			
		}
	
		
	
	}
	
	public double getBestFitness()
	{
		return this.bestFitness;
	}
	
	public ChromosomeRepresentationInterface getBestIndividual()
	{
		return this.bestIndividual;
	}
	
	
	
	
}
