package params;

import exceptions.ParametersInitializationException;
import output.DisplayInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class ParametersInitialization extends Parameters {

    public static final String INIT_PARAMS_PATH = "src/params/init.params";
    public static final String PARAMS_EXTENSION = ".params";

    protected static Properties properties = new Properties();

    public ParametersInitialization() {

    }

    public static void loadDefaultParameters() throws Exception {
        loadParamFile(INIT_PARAMS_PATH);
    }

    public static void loadParameters(String fileName) throws Exception {
        loadParamFile(fileName);
    }

    private static void loadParamFile(String fileName) throws IOException, ParametersInitializationException {
        File f = new File(fileName);

        if (!f.exists()) {
            System.out.println("A parameter file was not detected.");
        } else {
            FileInputStream is = new FileInputStream(f);
            properties.load(is);
        }
        System.out.println("==================================================");
        System.out.println("Initializing parameters...");
        System.out.println("==================================================");

        new ParametersValidator().validateParameters();

        if (ParametersInitialization.properties.getProperty(InputParametersNames.RUNS) == null) {
            throw new ParametersInitializationException(InputParametersNames.RUNS);
        }
        runs = Integer.parseInt(ParametersInitialization.properties.getProperty(InputParametersNames.RUNS).trim());

        DisplayInfo.displayInitialization(InputParametersNames.RUNS, ParametersInitialization.properties.getProperty(InputParametersNames.RUNS));

        if (ParametersInitialization.properties.getProperty(InputParametersNames.GENERATIONS) == null) {
            throw new ParametersInitializationException(InputParametersNames.GENERATIONS);
        } else {
            generations = Integer.parseInt(ParametersInitialization.properties.getProperty(InputParametersNames.GENERATIONS).trim());
            DisplayInfo.displayInitialization(InputParametersNames.GENERATIONS, ParametersInitialization.properties.getProperty(InputParametersNames.GENERATIONS));
        }

        if (ParametersInitialization.properties.getProperty(InputParametersNames.POPULATION_SIZE) == null) {
            throw new ParametersInitializationException(InputParametersNames.POPULATION_SIZE);
        } else {
            population_size = Integer.parseInt(ParametersInitialization.properties.getProperty(InputParametersNames.POPULATION_SIZE).trim());
            DisplayInfo.displayInitialization(InputParametersNames.POPULATION_SIZE, ParametersInitialization.properties.getProperty(InputParametersNames.POPULATION_SIZE));
        }

        if (ParametersInitialization.properties.getProperty(InputParametersNames.TOURNAMENT_SIZE) == null) {
            throw new ParametersInitializationException(InputParametersNames.TOURNAMENT_SIZE);
        } else {
            tournament_size = Integer.parseInt(ParametersInitialization.properties.getProperty(InputParametersNames.TOURNAMENT_SIZE).trim());

            DisplayInfo.displayInitialization(InputParametersNames.TOURNAMENT_SIZE, ParametersInitialization.properties.getProperty(InputParametersNames.TOURNAMENT_SIZE));

        }
        if (ParametersInitialization.properties.getProperty(InputParametersNames.CROSSOVER_PROBABILITY) == null) {
            throw new ParametersInitializationException(InputParametersNames.CROSSOVER_PROBABILITY);
        } else {
            crossover_probability = Double.parseDouble(ParametersInitialization.properties.getProperty(InputParametersNames.CROSSOVER_PROBABILITY).trim());

            if (crossover_probability < 0 || crossover_probability > 1) {
                throw new ParametersInitializationException(InputParametersNames.CROSSOVER_PROBABILITY, 0, 1);
            }
            DisplayInfo.displayInitialization(InputParametersNames.CROSSOVER_PROBABILITY, ParametersInitialization.properties.getProperty(InputParametersNames.CROSSOVER_PROBABILITY));
        }
        if (ParametersInitialization.properties.getProperty(InputParametersNames.MUTATION_PROBABILITY) == null) {
            throw new ParametersInitializationException(InputParametersNames.MUTATION_PROBABILITY);
        } else {
            mutation_probability = Double.parseDouble(ParametersInitialization.properties.getProperty(InputParametersNames.MUTATION_PROBABILITY).trim());

            if (mutation_probability < 0 || mutation_probability > 1) {
                throw new ParametersInitializationException(InputParametersNames.MUTATION_PROBABILITY, 0, 1);
            }
            DisplayInfo.displayInitialization(InputParametersNames.MUTATION_PROBABILITY, ParametersInitialization.properties.getProperty(InputParametersNames.MUTATION_PROBABILITY));
        }

        if (ParametersInitialization.properties.getProperty(InputParametersNames.PROCESSORS) == null) {
            processors = Runtime.getRuntime().availableProcessors();
        } else {
            processors = Integer.parseInt(ParametersInitialization.properties.getProperty(InputParametersNames.PROCESSORS).trim());
        }
        DisplayInfo.displayInitialization(InputParametersNames.PROCESSORS, String.valueOf(processors));

        if (ParametersInitialization.properties.getProperty(InputParametersNames.SEED) == null) {
            seed = System.currentTimeMillis();
        } else {
            seed = Long.parseLong(ParametersInitialization.properties.getProperty(InputParametersNames.SEED).trim());
        }
        DisplayInfo.displayInitialization(InputParametersNames.SEED, String.valueOf(seed));

        if (ParametersInitialization.properties.getProperty(InputParametersNames.INPUT_FOLDER) == null) {
            throw new ParametersInitializationException(InputParametersNames.INPUT_FOLDER);
        } else {
            input_folder = ParametersInitialization.properties.getProperty(InputParametersNames.INPUT_FOLDER).trim();
        }

        if (ParametersInitialization.properties.getProperty(InputParametersNames.FILE_EXTENSION) == null) {
            throw new ParametersInitializationException(InputParametersNames.FILE_EXTENSION);
        } else {
            file_extension = ParametersInitialization.properties.getProperty(InputParametersNames.FILE_EXTENSION).trim();
        }

        if (ParametersInitialization.properties.getProperty(InputParametersNames.DELIMITER) == null) {
            throw new ParametersInitializationException(InputParametersNames.DELIMITER);
        } else {
            delimiter = ParametersInitialization.properties.getProperty(InputParametersNames.DELIMITER).trim();
            if (delimiter.length() >= 3) {
                delimiter = delimiter.substring(1, delimiter.length() - 1);
            }
        }

        DisplayInfo.displayInitialization(InputParametersNames.DELIMITER, String.valueOf(delimiter));

        if (ParametersInitialization.properties.getProperty(InputParametersNames.NAME_COLUMN) != null) {
            nameColumnExists = Boolean.parseBoolean(ParametersInitialization.properties.getProperty(InputParametersNames.NAME_COLUMN).trim());
        }

        DisplayInfo.displayInitialization(InputParametersNames.NAME_COLUMN, String.valueOf(nameColumnExists));

        if (ParametersInitialization.properties.getProperty(InputParametersNames.NAME_COLUMN_POSITION) != null) {
            nameColumnPosition = Integer.parseInt(ParametersInitialization.properties.getProperty(InputParametersNames.NAME_COLUMN_POSITION).trim());
        }

        DisplayInfo.displayInitialization(InputParametersNames.NAME_COLUMN_POSITION, String.valueOf(nameColumnPosition));

        if (ParametersInitialization.properties.getProperty(InputParametersNames.DECISION_ATTRIBUTE_POSITION) != null) {
            decisionColumnPosition = Integer.parseInt(ParametersInitialization.properties.getProperty(InputParametersNames.DECISION_ATTRIBUTE_POSITION).trim());
        }

        DisplayInfo.displayInitialization(InputParametersNames.DECISION_ATTRIBUTE_POSITION, String.valueOf(decisionColumnPosition));

        if (ParametersInitialization.properties.getProperty(InputParametersNames.BIN_NUMBER) != null) {
            binNumber = Integer.parseInt(ParametersInitialization.properties.getProperty(InputParametersNames.BIN_NUMBER).trim());
        }

        DisplayInfo.displayInitialization(InputParametersNames.BIN_NUMBER, String.valueOf(binNumber));

        if (ParametersInitialization.properties.getProperty(InputParametersNames.NUMERIC_ATTRIBUTES) != null) {
            numericAttributes = new NumericAttributesParameterParser().getNumericAttributes(ParametersInitialization.properties.getProperty(InputParametersNames.NUMERIC_ATTRIBUTES));
        }

        DisplayInfo.displayInitialization(InputParametersNames.NUMERIC_ATTRIBUTES, String.valueOf(numericAttributes));

        if (ParametersInitialization.properties.getProperty(InputParametersNames.STATISTICS_OUTPUT) == null) {
            throw new ParametersInitializationException(InputParametersNames.STATISTICS_OUTPUT);
        } else {
            stat_out = ParametersInitialization.properties.getProperty(InputParametersNames.STATISTICS_OUTPUT).trim();
        }

        elitism_size = Integer.parseInt(ParametersInitialization.properties.getProperty(InputParametersNames.ELITISM_SIZE, "1").trim());

        DisplayInfo.displayInitialization(InputParametersNames.ELITISM_SIZE, String.valueOf(elitism_size));

        best_individuals_out = Integer.parseInt(ParametersInitialization.properties.getProperty(InputParametersNames.BEST_INDIVIDUAL_OUT, "1").trim());

        DisplayInfo.displayInitialization(InputParametersNames.BEST_INDIVIDUAL_OUT, String.valueOf(best_individuals_out));

        if (ParametersInitialization.properties.getProperty(InputParametersNames.MISSING_ATTRIBUTE_SYMBOL) != null) {
            missingAttributeSymbol = ParametersInitialization.properties.getProperty(InputParametersNames.MISSING_ATTRIBUTE_SYMBOL);
        }
        if (ParametersInitialization.properties.getProperty(InputParametersNames.MISSING_ATTRIBUTE_ENABLE) != null) {
            missingAttributeEnable = Boolean.parseBoolean(ParametersInitialization.properties.getProperty(InputParametersNames.MISSING_ATTRIBUTE_ENABLE));
        }
            /*
             * ==================================================================
			 * ================================
			 */

        DisplayInfo.displayInitialization(InputParametersNames.CROSSOVER_OPERATOR, ParametersInitialization.properties.getProperty(InputParametersNames.CROSSOVER_OPERATOR));
        DisplayInfo.displayInitialization(InputParametersNames.MUTATION_OPERATOR, ParametersInitialization.properties.getProperty(InputParametersNames.MUTATION_OPERATOR));
        DisplayInfo.displayInitialization(InputParametersNames.SELECTION_OPERATOR, ParametersInitialization.properties.getProperty(InputParametersNames.SELECTION_OPERATOR));
        DisplayInfo.displayInitialization(InputParametersNames.FITNESS_FUNCTION, ParametersInitialization.properties.getProperty(InputParametersNames.FITNESS_FUNCTION));
        DisplayInfo.displayInitialization(InputParametersNames.CHROMOSOME_REPRESENTATION, ParametersInitialization.properties.getProperty(InputParametersNames.CHROMOSOME_REPRESENTATION));
        DisplayInfo.displayInitialization(InputParametersNames.DISCRETIZATION_METHOD, ParametersInitialization.properties.getProperty(InputParametersNames.DISCRETIZATION_METHOD));

        System.out.println("==================================================");
    }

    public static Properties cloneProperties() {
        return (Properties) properties.clone();
    }
}
