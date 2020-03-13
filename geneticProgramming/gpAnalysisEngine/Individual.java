package gpAnalysisEngine;

import java.util.Iterator;
import java.util.LinkedList;

import globals.GAUtility;

/**
 * <p>
 * Represents the individual tree that will be generated using the Genetic
 * Algorithm.
 * </p>
 * 
 * @author Yared Kassa Estifanos
 * 
 */
public class Individual implements BinaryTree {
	/**
	 * <p>
	 * The root of the tree
	 * </p>
	 */
	private Chromosome root;

	/**
	 * <p>
	 * Represents the depth of the tree (used for first generation)
	 * </p>
	 */
	private int initalDepthofIndividualTree;

	/**
	 * 
	 * <p>
	 * Rrepresents the member to be used for storing the evaluation results this
	 * is set by expression evaluator. Fitness is measured by the magnitude of
	 * this value.
	 * </p>
	 */
	private double evaluationOutcome;

	/**
	 * <p>
	 * Used to store the chromosome matrix. Maintained for Regeneration of the
	 * inidividual.
	 * </p>
	 */
	private Chromosome[][] chromMatrix;

	/**
	 * <p>
	 * Constructs the individual tree
	 * </p>
	 * 
	 * @param _root
	 */
	public Individual(Chromosome _root) {
		this.setRoot(_root);
	}

	/**
	 * <p>
	 * Given the inital depth creates an individual tree
	 * </p>
	 * 
	 * @param _initalDepth
	 */
	public Individual(int _initalDepth) {
		try {
			this.setDepthOfTree(_initalDepth);
		} catch (GPExceptions gp) {
			this.setRoot(null);
		} finally {
			this.setRoot(this.initializeIndividual());
		}
	}

	/**
	 * @return the depthOfTree
	 * @throws GPExceptions
	 */
	public int getDepthOfTree() throws GPExceptions {
		if (this.initalDepthofIndividualTree <= 0) {
			throw new GPExceptions("Inital depth cannot be negative");
		} else {
			return initalDepthofIndividualTree;
		}
	}

	/**
	 * @param _depthOfTree
	 *            the depthOfTree to set
	 * @throws GPExceptions
	 */
	public void setDepthOfTree(int _depthOfTree) throws GPExceptions {
		if (_depthOfTree <= 0) {
			throw new GPExceptions("Depth cannot be negative number");
		} else {
			this.initalDepthofIndividualTree = _depthOfTree;
		}
	}

	/**
	 * @return the evaluationOutcome
	 */
	public double getEvaluationOutcome() {
		return evaluationOutcome;
	}

	/**
	 * @param evaluationOutcome
	 *            the evaluationOutcome to set
	 */
	public void setEvaluationOutcome(double evaluationOutcome) {
		this.evaluationOutcome = evaluationOutcome;
	}

	/**
	 * @return the chromMatrix
	 */
	public Chromosome[][] getChromMatrix() {
		return chromMatrix;
	}

	/**
	 * @param chromMatrix
	 *            the chromMatrix to set
	 */
	public void setChromMatrix(Chromosome[][] chromMatrix) {
		this.chromMatrix = chromMatrix;
	}

	/**
	 * <p>
	 * Used for showing the produced out put as nested parenthetical way for
	 * ease of readability
	 * </p>
	 * 
	 * @param _node
	 * @return nested parenthetical representation of the equation.
	 */
	public String parentheticalExpression(Chromosome _node) {
		String result = "";
		if (this.isInternal(_node))
			result += ("(");
		if (_node.getLeftChromosome() != null)
			result += parentheticalExpression(_node.getLeftChromosome());
		if (this.isInternal(_node)) {
			result += (_node.getData());
		} else {
			result += (_node.getData());
		}
		if (_node.getRightChromosome() != null)
			result += parentheticalExpression(_node.getRightChromosome());
		if (this.isInternal(_node))
			result += ")";
		return result;
	}

	/**
	 * <p>
	 * Gets the parenthetical representation of an Individual tree
	 * </p>
	 * 
	 * @return parenthetical representation of a tree
	 */
	public String parentheticalExpression() {
		return this.parentheticalExpression(this.getRoot());
	}

	/**
	 * <p>
	 * postorder traversal used for expression evaluating
	 * </p>
	 * 
	 * @return expression post order
	 */
	public String expressionPostorder() {
		return postorderHelper(this.getRoot());
	}

	/**
	 * <p>
	 * postorder traversal of a an individual tree
	 * </p>
	 * 
	 * @param _chrom
	 * @return the post order
	 */
	public String postorderHelper(Chromosome _chrom) {
		String _result = "";
		if (_chrom == null)
			return "";

		_result = _result + postorderHelper(_chrom.getLeftChromosome());
		_result = _result + postorderHelper(_chrom.getRightChromosome());

		return _result + _chrom.getData().toString() + " ";
	}

	/**
	 * <p>
	 * Helper method for the generation process. It generates the index values
	 * of the expression operators (0 -3) 1. {+,-,*,/} And generates the index
	 * values of the constant, Coffiecent for the expression data. ( 4 - 14)
	 * inclusive. 2. {0,1,2,3,4,5,6,7,8,9,X} Note all the expression data is
	 * stored as one string array. {+,-,*,/,0,1,2,3,4,5,6,7,8,9,X}
	 * </p>
	 * 
	 * @return random values generated
	 */
	public int[][] GenerateRandomValues() {
		try {
			int actualSize = this.getDepthOfTree() + 1;
			boolean atleastOneVariable = false;
			int[][] expressionBuildingData = new int[actualSize][];
			GARandom randomize = new GARandom();

			for (int i = 0; i < expressionBuildingData.length; i++) 
			{// for each location produce 2^i random values
				int operatorCount = (int) Math.pow(2, i);
				int[] operatorData = new int[operatorCount];
				for (int l = 0; l < operatorData.length; l++) {
					int generatedIndex = randomize.intRandom(0,3);
					if (expressionBuildingData.length == i + 1) 
					{// these are the external nodes of the tree...
						generatedIndex = randomize.intRandom(4, 14);
						if (generatedIndex == 14) {
							atleastOneVariable = true;
						}
					}
					operatorData[l] = generatedIndex;
				}
				expressionBuildingData[i] = operatorData;
			}
			if (!atleastOneVariable) {
				int[] _leaves = 
					expressionBuildingData[expressionBuildingData.length - 1];
				int iRndIndex =
					randomize.intRandom(0, _leaves.length - 1);
				_leaves[iRndIndex] = 14;
				expressionBuildingData[expressionBuildingData.length - 1] = _leaves;
			}
			return expressionBuildingData;
		} catch (GPExceptions gp) {
			System.out.println("Exception has occurred:\t" + gp.toString());
		}
		return null;
	}

	/**
	 * <p>
	 * Using the data matrix generated it creates the tree randomly.
	 * </p>
	 * 
	 * @return the root of the initalized tree
	 */
	public Chromosome initializeIndividual() {

		int[][] iRandomMatrix =
			GenerateRandomValues();
		if (iRandomMatrix == null) {
			return null;
		}
		String[] expressionSet = 
			GAUtility.domainValues;
		Chromosome[][] oChromosomeMatrix =
			new Chromosome[iRandomMatrix.length][];

		Chromosome parent = null;
		int key = 0;

		for (int index = 0; index < iRandomMatrix.length; index++) {
			int[] data = iRandomMatrix[index];
			Chromosome[] list = new Chromosome[data.length];

			for (int l = 0; l < data.length; l++) {
				int locator = data[l];
				list[l] = 
					new Chromosome(expressionSet[locator], null, null,key);
				key++;
			}
			oChromosomeMatrix[index] = list;
		}

		convertToTree(oChromosomeMatrix);
		parent = oChromosomeMatrix[0][0];
		oChromosomeMatrix = null;
		return parent;
	}

	/**
	 * <p>
	 * Helper method to convert the randomly generated matrix to a tree
	 * </p>
	 * 
	 * @param oChromosomeMatrix
	 */
	public void convertToTree(Chromosome[][] oChromosomeMatrix) {
		// / now create a link b/n children and parents.
		for (int b = 0; b < oChromosomeMatrix.length; b++) {
			for (int l = 0; l < oChromosomeMatrix[b].length; l++) {
				int rightChild = 2 * l + 1;
				int leftChild = 2 * l;

				if (b + 1 < oChromosomeMatrix.length) {
					Chromosome _rightChromosome = oChromosomeMatrix[b + 1][rightChild];
					Chromosome _leftChromosome = oChromosomeMatrix[b + 1][leftChild];
					oChromosomeMatrix[b][l].setLeftChromosome(_leftChromosome);
					oChromosomeMatrix[b][l].setRightChromosome(_rightChromosome);
				}
			}
		}
		this.chromMatrix = oChromosomeMatrix;
	}

	/**
	 * <p>
	 * Gets the number of nodes in the tree
	 * </p>
	 * 
	 * @return the number of nodes(Chromosome) in the tree
	 */
	public int size() {
		return sizeHelper(this.getRoot());
	}

	/**
	 * <p>
	 * Helper method to calculate the size of a tree
	 * </p>
	 * 
	 * @param _root
	 * @return the size of chromsome node passed.(count of nodes)
	 */
	private int sizeHelper(Chromosome _root) {
		if (_root == null)
			return 0;
		else if (isExternal(_root))
			return 1;
		else
			return 1 + sizeHelper(_root.getLeftChromosome())
					+ sizeHelper(_root.getRightChromosome());
	}

	/**
	 * @return the root
	 */
	public Chromosome getRoot() {
		return root;
	}

	/**
	 * @param root
	 *            the root to set
	 */
	public void setRoot(Chromosome root) {
		this.root = root;
	}

	/**
	 * <p>
	 * checks if the chromosome has left node
	 * </p>
	 * 
	 * @param _chromToCheck
	 * @return true if there is false if null.
	 */
	public boolean hasLeft(Chromosome _chromToCheck) {
		return (_chromToCheck.getLeftChromosome() != null);
	}

	/**
	 * <p>
	 * checks if Chromosome has right node
	 * </p>
	 * 
	 * @param _chromToCheck
	 * @return true if there is false if null.
	 */
	public boolean hasRight(Chromosome _chromToCheck) {
		return (_chromToCheck.getRightChromosome() != null);
	}

	/**
	 * <p>
	 * Checks if chromosome is external (leaf) node
	 * </p>
	 * 
	 * @param _chromToCheck
	 * @return true if leaf
	 */
	public boolean isExternal(Chromosome _chromToCheck) {
		return (!isInternal(_chromToCheck));
	}

	/**
	 * <p>
	 * Checks if chromosome is internal (operator) node
	 * </p>
	 * 
	 * @param _chromToCheck
	 * @return true if it is non-leaf-node
	 */
	public boolean isInternal(Chromosome _chromToCheck) {
		return (hasLeft(_chromToCheck) || hasRight(_chromToCheck));
	}

	/**
	 * Checks if the tree is empty.
	 * 
	 * @return if tree is empty
	 */
	public boolean isEmpty() {
		return (this.getRoot() != null);
	}

	/**
	 * <p>
	 * Override the default tostring method
	 * </p>
	 * 
	 * @return the toString representation of this object
	 */
	public String toString() {
		return this.parentheticalExpression() + "\t"
				+ this.getEvaluationOutcome();
	}

	/**
	 * <p>
	 * Used to check if it is root
	 * </p>
	 * 
	 * @param _chromToCheck
	 * @return true if it is the root, false otherwise.
	 */
	public boolean isRoot(Chromosome _chromToCheck) {
		return _chromToCheck.equals(this.getRoot());
	}

	/**
	 * <p>
	 * Used to get the root
	 * </p>
	 * 
	 * @return the root
	 */
	public Chromosome root() {
		return this.getRoot();
	}

	/**
	 * <p>
	 * Lists the nodes(Chromosome) in each level of the tree
	 * </p>
	 * 
	 * @return Iterator
	 */
	public Iterator iteratorLevelOrder() {
		LinkedList<Chromosome> _list = 
			new LinkedList<Chromosome>();
		levelOrderHelper(this.getRoot(), _list);
		return _list.iterator();
	}

	/**
	 * <p>
	 * Lists the chromosomes in each level
	 * </p>
	 * 
	 * @param _chromNode
	 * @param _list
	 */
	private void levelOrderHelper(Chromosome _chromNode,
			LinkedList<Chromosome> _list) {
		LinkedList<Chromosome> _queue = new LinkedList<Chromosome>();
		_queue.add(_chromNode);
		while (_queue.size() != 0) {
			Chromosome _chromToCheck = (Chromosome) _queue.removeFirst();
			_list.add(_chromToCheck);
			if (hasLeft(_chromToCheck))
				_queue.add(_chromToCheck.getLeftChromosome());
			if (hasRight(_chromToCheck))
				_queue.add(_chromToCheck.getRightChromosome());
		}
	}
	/**
	 * <p>Replaces external nodes with some variable</p>
	 * @param _count
	 */
	public void replaceExternalNodesVariables(int _count)
	{
		Iterator it = this.iteratorLevelOrder();
		Chromosome replace;
		while(it.hasNext())
		{
			replace = (Chromosome)it.next();
			if (isExternal(replace) && _count > 0)
			{
				replace.setData(GAUtility.getDomainValues(14));
				_count --;
			}
		}
	} 

	/**
	 * <p>
	 * Chromosome to search
	 * </p>
	 * 
	 * @param _keyToSearch
	 * @return Chromosome with the _keyToSearch
	 */
	public Chromosome searchByKey(int _keyToSearch) {
		Iterator it = this.iteratorLevelOrder();
		while (it.hasNext()) {
			Chromosome found = (Chromosome) it.next();
			if (found.getKeyToThisPosition() == _keyToSearch) {
				return found;
			}
		}
		return null;
	}

	/**
	 * <p>
	 * Gets the longest height of the tree
	 * </p>
	 * 
	 * @return the height of the tree
	 */
	public int height() {
		return height(this.getRoot());
	}

	/**
	 * <p>
	 * Gets the height of the node(Chromosome)
	 * </p>
	 * 
	 * @param _chrom
	 * @return height of the chromosome
	 */
	private int height(Chromosome _chrom) {
		if (_chrom == null)
			return -1;
		else
			return 1 + Math.max(height(_chrom.getLeftChromosome()),
					height(_chrom.getRightChromosome()));
	}

	/**
	 * <p>
	 * Reassigns keys to the nodes (chromosome) of the tree.
	 * </p>
	 * <p>
	 * This is necessary method since the crossover will mixup the existing
	 * keys
	 * </p>
	 * <p>
	 * Crossover will call this method on the resulting tree to make sure all
	 * the keys in that tree are unique.
	 * </p>
	 * 
	 */
	public void reassignKeysToIndividualTree() {
		Iterator it = this.iteratorLevelOrder();
		Chromosome chrom = (Chromosome) it.next();
		int _size = this.size() - 1;
		chrom.setKeyToThisPosition(_size);

		while (it.hasNext()) {
			_size--;
			chrom = (Chromosome) it.next();
			chrom.setKeyToThisPosition(_size);
		}
	}

	/**
	 * <p>
	 * Finds and replaces chromosome with _keytoChrom key with a new chromosome
	 * </p>
	 * 
	 * @param _newChrom
	 * @param _keyToChrom
	 */
	public void findAndReplace(Chromosome _newChrom, int _keyToChrom) {
		
		Iterator it = this.iteratorLevelOrder();
		Chromosome chrom;

		while (it.hasNext()) {
			chrom = (Chromosome) it.next();

			if (chrom.getLeftChromosome() != null)
			{
				if (chrom.getLeftChromosome().getKeyToThisPosition() == _keyToChrom) 
				{
					chrom.setLeftChromosome(_newChrom);
					return;
				}
			}
			if (chrom.getRightChromosome() != null)
			{
				if (chrom.getRightChromosome().getKeyToThisPosition() == _keyToChrom) {
					chrom.setRightChromosome(_newChrom);
					return;
				}
			}
			if (chrom.getKeyToThisPosition() == _keyToChrom)
			{
				chrom = _newChrom;
				return;
			}
		}
	}

	/**
	 * <p>
	 * finds and replaces the value of the tree node
	 * </p>
	 * 
	 * @param _keyToReplace
	 * @param _indexOfData
	 */
	public void findAndReplace(int _keyToReplace, int _indexOfData) {
		Iterator it = this.iteratorLevelOrder();
		Chromosome chrom = null;
		String _newData = GAUtility.getDomainValues(_indexOfData);

		while (it.hasNext()) {
			chrom = (Chromosome) it.next();
			if (chrom.getKeyToThisPosition() == _keyToReplace) {
				chrom.setData(_newData);
				return;
			}
		}
	}
}