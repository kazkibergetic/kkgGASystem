package discretization;

import java.util.List;

/**
 * DiscretizationMethod.
 *
 * @author Maxim Rybakov
 */
public interface DiscretizationMethod<T> {
    List<List<T>> discretize(List<T> originalData);
}
