/**
 * <p>Package used for creating,updating,and evaluating population
 * to determin the best fit solution</p>
 */
package gpAnalysisEngine;

/**
 * <p>
 * An interface the individual tree will implement Minimum number of methods
 * expected
 * </p>
 * 
 * @author Yared K Estifanos.
 * 
 */
public interface BinaryTree {
	/**
	 * <p>
	 * Used to get the root
	 * </p>
	 * 
	 * @return the root
	 */
	public Chromosome root();

	/**
	 * <p>
	 * Used to check if it is root
	 * </p>
	 * 
	 * @param _chromToCheck
	 * @return true if it is the root, false otherwise.
	 */
	public boolean isRoot(Chromosome _chromToCheck);

	/**
	 * <p>
	 * Used to check if it is a leaf node(Chromosome)
	 * </p>
	 * 
	 * @param _chromToCheck
	 * @return true if it is false if it is not.
	 */
	public boolean isExternal(Chromosome _chromToCheck);

	/**
	 * <p>
	 * Used for checking if node(Chromosome) is internal node
	 * </p>
	 * 
	 * @param _chromToCheck
	 * @return true if it is false if it is not.
	 */
	public boolean isInternal(Chromosome _chromToCheck);

	/**
	 * <p>
	 * Gets the number of nodes in the tree
	 * </p>
	 * 
	 * @return the size
	 */
	public int size();

	/**
	 * <p>
	 * Checks if tree is empty
	 * </p>
	 * 
	 * @return true if empty false if not
	 */
	public boolean isEmpty();
}
