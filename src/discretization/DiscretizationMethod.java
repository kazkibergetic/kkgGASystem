package discretization;

import evolver.RunEvolutionContext;

import java.util.List;

/**
 * DiscretizationMethod.
 *
 * @author Maxim Rybakov
 */
public interface DiscretizationMethod<T> {
    List<List<T>> discretize(RunEvolutionContext runEvolutionContext, int attributeIndex, List<T> originalData);
}
