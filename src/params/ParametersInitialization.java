package params;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import output.DisplayInfo;
import chromosome.ChromosomeRepresentationInterface;
import exceptions.ParametersInitializationException;
import fitness.FitnessEvaluationInterface;
import operators.crossover.CrossoverInterface;
import operators.mutation.MutationInterface;
import operators.selection.SelectionInterface;

public class ParametersInitialization extends Parameters {

	protected static Properties properties = new Properties();

	public ParametersInitialization() {

	}

	public ParametersInitialization(String[] args) throws Exception {

		try {

			File f = new File("src/params/init.params");

			if (!f.exists()) {
				// load default
				System.out.println("A parameter files was not detected.");
				
			} else {
				FileInputStream is = new FileInputStream(f);
				properties.load(is);

				// System.out.println(this.properties.getProperty("runs"));

			}
			System.out
					.println("==================================================");
			System.out.println("Initializing parameters...");
			System.out
					.println("==================================================");

			if (this.properties.getProperty(InputParametersNames.RUNS) == null) {
				throw new ParametersInitializationException(
						InputParametersNames.RUNS);
			}
			runs = Integer.parseInt(this.properties.getProperty(
					InputParametersNames.RUNS).trim());

			DisplayInfo.displayInitialization(InputParametersNames.RUNS,
					this.properties.getProperty(InputParametersNames.RUNS));

			if (this.properties.getProperty(InputParametersNames.GENERATIONS) == null) {
				throw new ParametersInitializationException(
						InputParametersNames.GENERATIONS);
			} else {
				generations = Integer.parseInt(this.properties.getProperty(
						InputParametersNames.GENERATIONS).trim());

				DisplayInfo.displayInitialization(
						InputParametersNames.GENERATIONS, this.properties
								.getProperty(InputParametersNames.GENERATIONS));
			}

			if (this.properties
					.getProperty(InputParametersNames.POPULATION_SIZE) == null) {
				throw new ParametersInitializationException(
						InputParametersNames.POPULATION_SIZE);
			} else {
				population_size = Integer.parseInt(this.properties.getProperty(
						InputParametersNames.POPULATION_SIZE).trim());

				DisplayInfo
						.displayInitialization(
								InputParametersNames.POPULATION_SIZE,
								this.properties
										.getProperty(InputParametersNames.POPULATION_SIZE));
			}

			if (this.properties
					.getProperty(InputParametersNames.TOURNAMENT_SIZE) == null) {
				throw new ParametersInitializationException(
						InputParametersNames.TOURNAMENT_SIZE);
			} else {
				tournament_size = Integer.parseInt(this.properties.getProperty(
						InputParametersNames.TOURNAMENT_SIZE).trim());

				DisplayInfo
						.displayInitialization(
								InputParametersNames.TOURNAMENT_SIZE,
								this.properties
										.getProperty(InputParametersNames.TOURNAMENT_SIZE));

			}
			if (this.properties
					.getProperty(InputParametersNames.CROSSOVER_PROBABILITY) == null) {
				throw new ParametersInitializationException(
						InputParametersNames.CROSSOVER_PROBABILITY);
			} else {

				crossover_probability = Double
						.parseDouble(this.properties.getProperty(
								InputParametersNames.CROSSOVER_PROBABILITY)
								.trim());

				if (crossover_probability < 0 || crossover_probability > 1) {
					throw new ParametersInitializationException(
							InputParametersNames.CROSSOVER_PROBABILITY, 0, 1);
				}
				DisplayInfo
						.displayInitialization(
								InputParametersNames.CROSSOVER_PROBABILITY,
								this.properties
										.getProperty(InputParametersNames.CROSSOVER_PROBABILITY));
			}
			if (this.properties
					.getProperty(InputParametersNames.MUTATION_PROBABILITY) == null) {
				throw new ParametersInitializationException(
						InputParametersNames.MUTATION_PROBABILITY);
			} else {
				mutation_probability = Double.parseDouble(this.properties
						.getProperty(InputParametersNames.MUTATION_PROBABILITY)
						.trim());

				if (mutation_probability < 0 || mutation_probability > 1) {
					throw new ParametersInitializationException(
							InputParametersNames.MUTATION_PROBABILITY, 0, 1);
				}
				DisplayInfo
						.displayInitialization(
								InputParametersNames.MUTATION_PROBABILITY,
								this.properties
										.getProperty(InputParametersNames.MUTATION_PROBABILITY));

			}
			
			if (this.properties.getProperty(InputParametersNames.PROCESSORS) == null) {
				processors = Runtime.getRuntime().availableProcessors()-1;
			}
			else
			{
				processors = Integer.parseInt(this.properties.getProperty(
					InputParametersNames.PROCESSORS).trim());
			}
			DisplayInfo
			.displayInitialization(
					InputParametersNames.PROCESSORS,
					String.valueOf(processors));

			if (this.properties.getProperty(InputParametersNames.INPUT_FOLDER) == null) {
				throw new ParametersInitializationException(
						InputParametersNames.INPUT_FOLDER);
			} else {
				input_folder = this.properties.getProperty(
						InputParametersNames.INPUT_FOLDER).trim();
			}

			if (this.properties
					.getProperty(InputParametersNames.FILE_EXTENSION) == null) {
				throw new ParametersInitializationException(
						InputParametersNames.FILE_EXTENSION);
			} else {
				file_extension = this.properties.getProperty(
						InputParametersNames.FILE_EXTENSION).trim();
			}
			
			
			if (this.properties
					.getProperty(InputParametersNames.STATISTICS_OUTPUT) == null) {
				throw new ParametersInitializationException(
						InputParametersNames.STATISTICS_OUTPUT);
			} else {
				stat_out = this.properties.getProperty(
						InputParametersNames.STATISTICS_OUTPUT).trim();
			}
			
			
			/*
			 * ==================================================================
			 * ================================
			 */

			DisplayInfo
					.displayInitialization(
							InputParametersNames.CROSSOVER_OPERATOR,
							this.properties
									.getProperty(InputParametersNames.CROSSOVER_OPERATOR));

			DisplayInfo
					.displayInitialization(
							InputParametersNames.MUTATION_OPERATOR,
							this.properties
									.getProperty(InputParametersNames.MUTATION_OPERATOR));

			DisplayInfo
					.displayInitialization(
							InputParametersNames.SELECTION_OPERATOR,
							this.properties
									.getProperty(InputParametersNames.SELECTION_OPERATOR));

			DisplayInfo
					.displayInitialization(
							InputParametersNames.FITNESS_FUNCTION,
							this.properties
									.getProperty(InputParametersNames.FITNESS_FUNCTION));

			DisplayInfo
					.displayInitialization(
							InputParametersNames.CHROMOSOME_REPRESENTATION,
							this.properties
									.getProperty(InputParametersNames.CHROMOSOME_REPRESENTATION));

			/*
			 * ==================================================================
			 * ================================
			 */

			System.out
					.println("==================================================");

		} catch (IOException e) {
			System.out.println(e.toString());
		}

	}

}
