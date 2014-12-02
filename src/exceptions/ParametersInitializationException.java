/**
 * 
 */
package exceptions;

/**
 * @author or13uw
 *
 */
public class ParametersInitializationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ParametersInitializationException()
	{
		super("Some of the parameters are not defined or have invalid values.");
	}
	public ParametersInitializationException(String parameterName)
	{
		super("Parameter '" + parameterName + "' is not defined or has an inavlid value.");
	}
	public ParametersInitializationException(String parameterName, int min, int max)
	{
		super("Parameter '" + parameterName + "' has invalid value. The value must be in range between '"+min+"' and '"+max+"'");
	}
}
