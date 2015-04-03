/**
 * 
 */
package params;

import chromosome.ChromosomeRepresentationInterface;
import discretization.DiscretizationMethod;
import fitness.FitnessEvaluator;
import operators.crossover.CrossoverInterface;
import operators.mutation.MutationInterface;
import operators.selection.SelectionInterface;
import problems.ProblemInterface;

/**
 * @author or13uw
 *
 */
public class ClassInitialization extends ParametersInitialization{

	public CrossoverInterface getCrossoverOperator() {
		CrossoverInterface crossover = null;
		String className = ParametersInitialization.properties
				.getProperty(InputParametersNames.CROSSOVER_OPERATOR).trim();
		try {

			Class<?> obj = Class.forName(className);
			crossover = (CrossoverInterface) obj.newInstance();

		} catch (Exception e) {
			System.err.println(this.getClass().getName() + ". exception: " + e);
			e.printStackTrace();
		}
		return crossover;
	}

	public MutationInterface getMutationOperator() {
		MutationInterface mutation = null;
		String className = ParametersInitialization.properties
				.getProperty(InputParametersNames.MUTATION_OPERATOR).trim();
		try {

			Class<?> obj = Class.forName(className);
			mutation = (MutationInterface) obj.newInstance();

		} catch (Exception e) {
			System.err.println(this.getClass().getName() + ". exception: " + e);
			e.printStackTrace();
		}
		return mutation;
	}

	public SelectionInterface getSelectionOperator() {
		SelectionInterface selection = null;
		String className = ParametersInitialization.properties
				.getProperty(InputParametersNames.SELECTION_OPERATOR).trim();
		try {

			Class<?> obj = Class.forName(className);
			selection = (SelectionInterface) obj.newInstance();

		} catch (Exception e) {
			System.err.println(this.getClass().getName() + ". exception: " + e);
			e.printStackTrace();
		}
		return selection;
	}

	public FitnessEvaluator getFitnessEvaluationOperator() {
		FitnessEvaluator fitness = null;
		String className = ParametersInitialization.properties
				.getProperty(InputParametersNames.FITNESS_FUNCTION).trim();
		try {

			Class<?> obj = Class.forName(className);
			fitness = (FitnessEvaluator) obj.newInstance();

		} catch (Exception e) {
			System.err.println(this.getClass().getName() + ". exception: " + e);
			e.printStackTrace();
		}
		return fitness;
	}
	
	public ChromosomeRepresentationInterface getChromosomeRepresentation() {
		ChromosomeRepresentationInterface chromosome = null;
	
		String className = ParametersInitialization.properties
				.getProperty(InputParametersNames.CHROMOSOME_REPRESENTATION).trim();
		
		try {

			Class<?> obj = Class.forName(className);
			chromosome = (ChromosomeRepresentationInterface) obj.newInstance();

		} catch (Exception e) {
			System.err.println(this.getClass().getName() + ". exception: " + e);
			e.printStackTrace();
		}
		return chromosome;
	}
	
	public ProblemInterface getProblem() {
		ProblemInterface problem = null;
	
		String className = ParametersInitialization.properties
				.getProperty(InputParametersNames.PROBLEM).trim();
		
		try {

			Class<?> obj = Class.forName(className);
			problem = (ProblemInterface) obj.newInstance();

		} catch (Exception e) {
			System.err.println(this.getClass().getName() + ". exception: " + e);
			e.printStackTrace();
		}
		return problem;
	}

    public <T> DiscretizationMethod<T> getDiscretizationMethod() {
        DiscretizationMethod<T> discretizationMethod = null;

        String className = ParametersInitialization.properties.getProperty(InputParametersNames.DISCRETIZATION_METHOD).trim();

        try {

            Class<?> obj = Class.forName(className);
            discretizationMethod = (DiscretizationMethod<T>) obj.getConstructor(int.class).newInstance(Parameters.getBinNumber());

        } catch (Exception e) {
            System.err.println(this.getClass().getName() + ". exception: " + e);
            e.printStackTrace();
        }
        return discretizationMethod;
    }

}
