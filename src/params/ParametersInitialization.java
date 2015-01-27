package params;

import exceptions.ParametersInitializationException;
import output.DisplayInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


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

			if (ParametersInitialization.properties.getProperty(InputParametersNames.RUNS) == null) {
				throw new ParametersInitializationException(
						InputParametersNames.RUNS);
			}
			runs = Integer.parseInt(ParametersInitialization.properties.getProperty(
					InputParametersNames.RUNS).trim());

			DisplayInfo.displayInitialization(InputParametersNames.RUNS,
					ParametersInitialization.properties.getProperty(InputParametersNames.RUNS));

			if (ParametersInitialization.properties.getProperty(InputParametersNames.GENERATIONS) == null) {
				throw new ParametersInitializationException(
						InputParametersNames.GENERATIONS);
			} else {
				generations = Integer.parseInt(ParametersInitialization.properties.getProperty(
						InputParametersNames.GENERATIONS).trim());

				DisplayInfo.displayInitialization(
						InputParametersNames.GENERATIONS, ParametersInitialization.properties
								.getProperty(InputParametersNames.GENERATIONS));
			}

			if (ParametersInitialization.properties
					.getProperty(InputParametersNames.POPULATION_SIZE) == null) {
				throw new ParametersInitializationException(
						InputParametersNames.POPULATION_SIZE);
			} else {
				population_size = Integer.parseInt(ParametersInitialization.properties.getProperty(
						InputParametersNames.POPULATION_SIZE).trim());

				DisplayInfo
						.displayInitialization(
								InputParametersNames.POPULATION_SIZE,
								ParametersInitialization.properties
										.getProperty(InputParametersNames.POPULATION_SIZE));
			}

			if (ParametersInitialization.properties
					.getProperty(InputParametersNames.TOURNAMENT_SIZE) == null) {
				throw new ParametersInitializationException(
						InputParametersNames.TOURNAMENT_SIZE);
			} else {
				tournament_size = Integer.parseInt(ParametersInitialization.properties.getProperty(
						InputParametersNames.TOURNAMENT_SIZE).trim());

				DisplayInfo
						.displayInitialization(
								InputParametersNames.TOURNAMENT_SIZE,
								ParametersInitialization.properties
										.getProperty(InputParametersNames.TOURNAMENT_SIZE));

			}
			if (ParametersInitialization.properties
					.getProperty(InputParametersNames.CROSSOVER_PROBABILITY) == null) {
				throw new ParametersInitializationException(
						InputParametersNames.CROSSOVER_PROBABILITY);
			} else {

				crossover_probability = Double
						.parseDouble(ParametersInitialization.properties.getProperty(
								InputParametersNames.CROSSOVER_PROBABILITY)
								.trim());

				if (crossover_probability < 0 || crossover_probability > 1) {
					throw new ParametersInitializationException(
							InputParametersNames.CROSSOVER_PROBABILITY, 0, 1);
				}
				DisplayInfo
						.displayInitialization(
								InputParametersNames.CROSSOVER_PROBABILITY,
								ParametersInitialization.properties
										.getProperty(InputParametersNames.CROSSOVER_PROBABILITY));
			}
			if (ParametersInitialization.properties
					.getProperty(InputParametersNames.MUTATION_PROBABILITY) == null) {
				throw new ParametersInitializationException(
						InputParametersNames.MUTATION_PROBABILITY);
			} else {
				mutation_probability = Double.parseDouble(ParametersInitialization.properties
						.getProperty(InputParametersNames.MUTATION_PROBABILITY)
						.trim());

				if (mutation_probability < 0 || mutation_probability > 1) {
					throw new ParametersInitializationException(
							InputParametersNames.MUTATION_PROBABILITY, 0, 1);
				}
				DisplayInfo
						.displayInitialization(
								InputParametersNames.MUTATION_PROBABILITY,
								ParametersInitialization.properties
										.getProperty(InputParametersNames.MUTATION_PROBABILITY));

			}

			if (ParametersInitialization.properties.getProperty(InputParametersNames.PROCESSORS) == null) {
				processors = Runtime.getRuntime().availableProcessors()-1;
			}
			else
			{
				processors = Integer.parseInt(ParametersInitialization.properties.getProperty(
					InputParametersNames.PROCESSORS).trim());
			}
			DisplayInfo
			.displayInitialization(
					InputParametersNames.PROCESSORS,
					String.valueOf(processors));

			if (ParametersInitialization.properties.getProperty(InputParametersNames.INPUT_FOLDER) == null) {
				throw new ParametersInitializationException(
						InputParametersNames.INPUT_FOLDER);
			} else {
				input_folder = ParametersInitialization.properties.getProperty(
						InputParametersNames.INPUT_FOLDER).trim();
			}

			if (ParametersInitialization.properties
					.getProperty(InputParametersNames.FILE_EXTENSION) == null) {
				throw new ParametersInitializationException(
						InputParametersNames.FILE_EXTENSION);
			} else {
				file_extension = ParametersInitialization.properties.getProperty(
						InputParametersNames.FILE_EXTENSION).trim();
			}


			if (ParametersInitialization.properties
					.getProperty(InputParametersNames.STATISTICS_OUTPUT) == null) {
				throw new ParametersInitializationException(
						InputParametersNames.STATISTICS_OUTPUT);
			} else {
				stat_out = ParametersInitialization.properties.getProperty(
						InputParametersNames.STATISTICS_OUTPUT).trim();
			}

            elitism_size = Integer.parseInt(ParametersInitialization.properties.getProperty(InputParametersNames.ELITISM_SIZE, "1").trim());

            DisplayInfo.displayInitialization(InputParametersNames.ELITISM_SIZE, String.valueOf(elitism_size));

            best_individuals_out = Integer.parseInt(ParametersInitialization.properties.getProperty(InputParametersNames.BEST_INDIVIDUAL_OUT, "1").trim());

            DisplayInfo.displayInitialization(InputParametersNames.BEST_INDIVIDUAL_OUT, String.valueOf(best_individuals_out));
			/*
			 * ==================================================================
			 * ================================
			 */

			DisplayInfo
					.displayInitialization(
							InputParametersNames.CROSSOVER_OPERATOR,
							ParametersInitialization.properties
									.getProperty(InputParametersNames.CROSSOVER_OPERATOR));

			DisplayInfo
					.displayInitialization(
							InputParametersNames.MUTATION_OPERATOR,
							ParametersInitialization.properties
									.getProperty(InputParametersNames.MUTATION_OPERATOR));

			DisplayInfo
					.displayInitialization(
							InputParametersNames.SELECTION_OPERATOR,
							ParametersInitialization.properties
									.getProperty(InputParametersNames.SELECTION_OPERATOR));

			DisplayInfo
					.displayInitialization(
							InputParametersNames.FITNESS_FUNCTION,
							ParametersInitialization.properties
									.getProperty(InputParametersNames.FITNESS_FUNCTION));

			DisplayInfo
					.displayInitialization(
							InputParametersNames.CHROMOSOME_REPRESENTATION,
							ParametersInitialization.properties
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

    public static Properties cloneProperties(){
        return (Properties) properties.clone();
    }
}
