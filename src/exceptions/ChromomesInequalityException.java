/**
 * 
 */
package exceptions;

/**
 * @author or13uw
 *
 */
public class ChromomesInequalityException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ChromomesInequalityException(String message) {
        super(message);
    }
	public ChromomesInequalityException() {
        super("Chromosomes, provided to perform genetic operations, have different length");
    }
}
