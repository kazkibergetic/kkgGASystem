package params;

import exceptions.ParametersInitializationException;

/**
 * ParametersValidator.
 *
 * @author Maxim Rybakov
 */
public class ParametersValidator {

    public static final String BIREDUCT_CLASS = "problems.RoughSets.bireduct.Problem";
    public static final String[] REQUIRED_PARAMETERS = {
            InputParametersNames.NAME_COLUMN,
            InputParametersNames.NAME_COLUMN_POSITION,
            InputParametersNames.DECISION_ATTRIBUTE_POSITION,
            InputParametersNames.BIN_NUMBER,
            InputParametersNames.NUMERIC_ATTRIBUTES
    };

    void validateParameters() throws ParametersInitializationException {
        String property = ParametersInitialization.properties.getProperty(InputParametersNames.CROSSOVER_OPERATOR);
        if (BIREDUCT_CLASS.equals(property)) {
            for (String requiredParameter : REQUIRED_PARAMETERS) {
                if (ParametersInitialization.properties.getProperty(requiredParameter) == null) {
                    throw new ParametersInitializationException(requiredParameter);
                }
            }
        }
    }
}
