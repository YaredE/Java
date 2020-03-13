package gpAnalysisEngine;

/**
 * <p>
 * Random number generator
 * </p>
 * 
 * @author Yared Kassa Estifanos
 */
public class GARandom {

	/**
	 * <p>
	 * Return a random integer from low to high (inclusive)
	 * 
	 * @param _floor
	 * @param _ceiling
	 * @return the random integer number.
	 */
	public int intRandom(int _floor, int _ceiling) {
		int _diff = _ceiling - _floor + 1;
		return (int) (Math.random() * _diff + _floor);
	}

	/**
	 * <p>
	 * Return a random integer from 1 to n inclusive.
	 * </p>
	 * 
	 * @param _number
	 * @return the number generated.
	 */
	public int intRandom(int _number) {
		return (int) (Math.random() * _number + 1);
	}

	/**
	 * <p>
	 * Default constructor
	 * </p>
	 */
	public GARandom() {
	}
}
