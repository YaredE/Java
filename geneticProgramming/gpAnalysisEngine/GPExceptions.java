package gpAnalysisEngine;

/**
 * <p>
 * Represents a custom class for GPExceptions
 * </p>
 * 
 * @author Yared Kassa Estifanos
 * 
 */
public class GPExceptions extends Exception {

	/**
	 * <p>
	 * Serial version UID
	 * </p>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>
	 * Constructs the GPExceptions
	 * </p>
	 * 
	 * @param _name
	 */
	public GPExceptions(String _name) {
		super(_name);
	}
}
