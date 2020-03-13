package gpAnalysisEngine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * Represents a class used for selecting a random Chromosome node in the
 * inidvidual and changes its value
 * </p>
 * <p>
 * If selected node is internal node the value that will be assigned is
 * something that is different from the current value and is operator
 * </p>
 * <p>
 * If value that is selected is external node the value that will replace the
 * existing node is and something different from its current value and is
 * operand
 * </p>
 * 
 * @author Yared Kassa Estifanos.
 * 
 */
public class Mutation {

	/**
	 * <p>
	 * Represents the random generator to be used for mutation operation
	 * </p>
	 */
	private GARandom randomGenerator = new GARandom();

	/**
	 * <p>
	 * Represents individuals selected for mutation operation
	 * </p>
	 */
	private List<Individual> selectedforMutation;

	/**
	 * <p>
	 * Represents a newly generated ossover Individuals
	 * </p>
	 */
	private List<Individual> newlyMutatedIndividuals;

	/**
	 * <p>
	 * Constructs a Mutation object
	 * </p>
	 * 
	 * @param selectedforMutation
	 */
	public Mutation(List<Individual> selectedforMutation) {
		this.selectedforMutation = selectedforMutation;
		newlyMutatedIndividuals = new ArrayList<Individual>();
		this.runMutation();
	}

	/**
	 * @return the selectedforMutation
	 */
	public List<Individual> getSelectedforMutation() {
		return selectedforMutation;
	}

	/**
	 * @param selectedforMutation
	 *            the selectedforMutation to set
	 */
	public void setSelectedforMutation(List<Individual> selectedforMutation) {
		this.selectedforMutation = selectedforMutation;
	}

	/**
	 * @return the newlyMutatedIndividuals
	 */
	public List<Individual> getNewlyMutatedIndividuals() {
		return newlyMutatedIndividuals;
	}

	/**
	 * <p>
	 * Finds and replaces individual with new node values
	 * </p>
	 */
	private void runMutation() {
		Iterator it = this.getSelectedforMutation().iterator();

		while (it.hasNext()) {
			Individual _mutatable = (Individual) it.next();
			int _nodeCount = _mutatable.size();
			if (_mutatable.size() > 0) {
				int _mutateSize = this.randomGenerator.intRandom(1, _nodeCount);
				this.mutationHelper(_mutatable, _mutateSize);
				newlyMutatedIndividuals.add(_mutatable);
			}
		}
	}

	/**
	 * <p>
	 * Helper method for mutation operation
	 * </p>
	 * 
	 * @param _mutatable
	 *            individual to mutate
	 * @param _nodesTomutate
	 *            number of nodes to mutate
	 */
	private void mutationHelper(Individual _mutatable, int _nodesTomutate) {
		for (; _nodesTomutate > 0;) {
			int _ceiling = _mutatable.size() - 1;
			int _keyToReplace = randomGenerator.intRandom(0, _ceiling);
			Chromosome _chrom = _mutatable.searchByKey(_keyToReplace);

			int _indexOfData;
			if (_chrom != null)
			{
			if (_mutatable.isInternal(_chrom)) {
				_indexOfData = // index is for operator
				randomGenerator.intRandom(0, 3);
			} else {
				_indexOfData = // index is for leaves/operands
				randomGenerator.intRandom(4, 13);
			}
			_mutatable.findAndReplace(_keyToReplace, _indexOfData);
			_nodesTomutate--;
			}
		}
	}
}
