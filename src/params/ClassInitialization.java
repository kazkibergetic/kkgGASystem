/**
 * 
 */
package params;

import operators.crossover.CrossoverInterface;
import operators.mutation.MutationInterface;
import operators.selection.SelectionInterface;
import problems.ProblemInterface;
import chromosome.ChromosomeRepresentationInterface;
import fitness.FitnessEvaluationInterface;

/**
 * @author or13uw
 *
 */
public class ClassInitialization extends ParametersInitialization{


	/**
	 * @param args
	 * @throws Exception
	 */
	public ClassInitialization()
	{
		
	}
	public ClassInitialization(String[] args) throws Exception {
		super(args);
		// TODO Auto-generated constructor stub
	}

	/*crossover_operator = this.getCrossoverOperator();

	mutation_operator = this.getMutationOperator(this.properties
			.getProperty(InputParametersNames.MUTATION_OPERATOR));

	selection_operator = this.getSelectionOperator(this.properties
			.getProperty(InputParametersNames.SELECTION_OPERATOR));

	fitness_evaluation_operator = this
			.getFitnessEvaluationOperator(this.properties
					.getProperty(InputParametersNames.FITNESS_FUNCTION));
	
	
	chromosome = this
			.getChromosomeRepresentation(this.properties
					.getProperty(InputParametersNames.CHROMOSOME_REPRESENTATION));
	*/
	public CrossoverInterface getCrossoverOperator() {
		CrossoverInterface crossover = null;
		String className = ParametersInitialization.properties
				.getProperty(InputParametersNames.CROSSOVER_OPERATOR).trim();
		try {

			Class<?> obj = Class.forName(className);
			crossover = (CrossoverInterface) obj.newInstance();

		} catch (IllegalAccessException e) {
			System.err.println(this.getClass().getName() + ". exception: " + e);
		} catch (ClassNotFoundException e) {
			System.err.println(this.getClass().getName() + ". exception: " + e);
			e.printStackTrace();
		} catch (InstantiationException e) {
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

		} catch (IllegalAccessException e) {
			System.err.println(this.getClass().getName() + ". exception: " + e);
		} catch (ClassNotFoundException e) {
			System.err.println(this.getClass().getName() + ". exception: " + e);
			e.printStackTrace();
		} catch (InstantiationException e) {
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

		} catch (IllegalAccessException e) {
			System.err.println(this.getClass().getName() + ". exception: " + e);
		} catch (ClassNotFoundException e) {
			System.err.println(this.getClass().getName() + ". exception: " + e);
			e.printStackTrace();
		} catch (InstantiationException e) {
			System.err.println(this.getClass().getName() + ". exception: " + e);
			e.printStackTrace();
		}
		return selection;
	}

	public FitnessEvaluationInterface getFitnessEvaluationOperator() {
		FitnessEvaluationInterface fitness = null;
		String className = ParametersInitialization.properties
				.getProperty(InputParametersNames.FITNESS_FUNCTION).trim();
		try {

			Class<?> obj = Class.forName(className);
			fitness = (FitnessEvaluationInterface) obj.newInstance();

		} catch (IllegalAccessException e) {
			System.err.println(this.getClass().getName() + ". exception: " + e);
		} catch (ClassNotFoundException e) {
			System.err.println(this.getClass().getName() + ". exception: " + e);
			e.printStackTrace();
		} catch (InstantiationException e) {
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

		} catch (IllegalAccessException e) {
			System.err.println(this.getClass().getName() + ". exception: " + e);
		} catch (ClassNotFoundException e) {
			System.err.println(this.getClass().getName() + ". exception: " + e);
			e.printStackTrace();
		} catch (InstantiationException e) {
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

		} catch (IllegalAccessException e) {
			System.err.println(this.getClass().getName() + ". exception: " + e);
		} catch (ClassNotFoundException e) {
			System.err.println(this.getClass().getName() + ". exception: " + e);
			e.printStackTrace();
		} catch (InstantiationException e) {
			System.err.println(this.getClass().getName() + ". exception: " + e);
			e.printStackTrace();
		}
		return problem;
	}
	
}
