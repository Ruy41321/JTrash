package application.model;

/**
 * This exception occurs when some one tries to get an instance of a class not
 * instanced yet
 * 
 * @author Luigi Pennisi
 *
 */
public class ClassNotInstancedException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * contructor of the exception using the error message
	 * 
	 * @param message is the error message
	 */
	public ClassNotInstancedException(String message) {
		super(message);
	}

}
