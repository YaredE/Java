package gpAnalysisEngine;

/**
 * <p>
 * Represents a chromosome object; this is the node for the tree that will be
 * generated.
 * </p>
 * 
 * @author Yared Estifanos
 * @version 0.5
 */
public class Chromosome {
	/**
	 * <p>
	 * Represents the chromosome data that is stored at the node level.
	 * </p>
	 */
	private Object data;

	/**
	 * <p>
	 * Represents the left chromosome node that is stored in this node.
	 * </p>
	 */
	private Chromosome leftChromosome;

	/**
	 * <p>
	 * Represents the right chromosome node that is stored in this node.
	 * </p>
	 */
	private Chromosome rightChromosome;

	/**
	 * <p>
	 * Represents the Key used this will be unique value for each node and
	 * represnets the position of the node in the tree.
	 * </p>
	 */
	private int keyToThisPosition;

	/**
	 * <p>
	 * Creates a Chromosome Object
	 * </p>
	 * 
	 * @param data
	 * @param leftChromosome
	 * @param rightChromosome
	 * @param keyPosition
	 */
	public Chromosome(Object data, Chromosome leftChromosome,
			Chromosome rightChromosome, int keyPosition) {
		this.data = data;
		this.leftChromosome = leftChromosome;
		this.rightChromosome = rightChromosome;
		this.keyToThisPosition = keyPosition;
	}

	/**
	 * <p>
	 * Gets the data stored in this node
	 * </p>
	 * 
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * <p>
	 * Sets the data
	 * </p>
	 * 
	 * @param _data
	 */
	public void setData(Object _data) {
		this.data = _data;
	}

	/**
	 * @return the keyToThisPosition
	 */
	public int getKeyToThisPosition() {
		return keyToThisPosition;
	}

	/**
	 * <p>
	 * Sets the keyToThisPosition
	 * </p>
	 * 
	 * @param _keyToThisPosition
	 */
	public void setKeyToThisPosition(int _keyToThisPosition) {
		this.keyToThisPosition = _keyToThisPosition;
	}

	/**
	 * <p>
	 * Gets the left chromosome
	 * </p>
	 * 
	 * @return the leftChromosome
	 */
	public Chromosome getLeftChromosome() {
		return leftChromosome;
	}

	/**
	 * <p>
	 * Sets the left chromosome
	 * </p>
	 * 
	 * @param leftChromosome
	 *            the leftChromosome to set
	 */
	public void setLeftChromosome(Chromosome leftChromosome) {
		this.leftChromosome = leftChromosome;
	}

	/**
	 * <p>
	 * Gets the right chromosome
	 * </p>
	 * 
	 * @return the rightChromosome
	 */
	public Chromosome getRightChromosome() {
		return rightChromosome;
	}

	/**
	 * <p>
	 * sets the rightChromosome
	 * </p>
	 * 
	 * @param _rightChromosome
	 */
	public void setRightChromosome(Chromosome _rightChromosome) {
		this.rightChromosome = _rightChromosome;
	}

	/**
	 * <p>
	 * Overriding the default toString method.
	 * </p>
	 * 
	 * @return the string representation of this object.
	 */
	public String toString() {
		return this.getData().toString();
	}
}
