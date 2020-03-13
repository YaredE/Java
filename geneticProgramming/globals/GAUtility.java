package globals;

import java.io.File;

import gpDataAccessLayer.FileParser;


/**
 * <p>
 * Represents Genetic Algorithm global variables and functions
 * </p>
 * 
 * @author Yared K Estifanos
 */
public class GAUtility 
{
	
	/**
	 * <p>
	 * The full path to the training data
	 * </p>
	 */
	public static final String TRANING_DATA_FILENAME =
		"/GPLastVersion/geneticProgramming/gpData/defaultTrainingData.txt";
	
	/**
	 * <p>
	 * The full path to the training data
	 * </p>
	 */
	public static String trainingDataFileName = TRANING_DATA_FILENAME;


	/**
	 * <p>
	 * Represents the training data from the file.
	 * </p>
	 */
	public static String[][] trainingData = null;

	/**
	 * <p>
	 * Selection rate to be used for crossover operation
	 * </p>
	 */
	public static double crossoverProbability = 0.90D;

	/**
	 * <p>
	 * Represents the crossover probability
	 * </p>
	 */
	public static double mutationProbability = 0.05D;

	/**
	 * <p>
	 * The inital depth of the tree at generation
	 * </p>
	 */
	public static int initalDepthofIndividualTree = 3;

	/**
	 * <p>
	 * The maximum depth of the tree allowed
	 * </p>
	 */
	public static int maximumAllowedDepthofIndividualTree = 200;

	/**
	 * <p>
	 * initial population size the population size that is used to start the
	 * Genetic Algorithm
	 * </p>
	 */
	public static int initialPopulationSize = 150;

	/**
	 * <p>
	 * The maximum number of generations allowed
	 * </p>
	 */
	public static int maximumNumberofGenerations = 1000;
	
	/**
	 * <p>The fitness level that is to be used</p>
	 */
	public static double fitnessLevelMeasure = 550D;

	/**
	 * <p>
	 * Represents the values that can be used for expression. i.e. these are the
	 * list of values that can be used as variable and constant in the equation
	 * generation process.
	 * </p>
	 */
	public static final String[] domainValues = { "+", "-", "*", "/", "0", "1",
			"2", "3", "4", "5", "6", "7", "8", "9", "X" };
	
	/**
	 * <p>The target function data used for graph</p>
	 */
	public static double targetFunctionData [][] = null;	
	
	/**
	 * <p>File parser</p>
	 */	
	public static FileParser ofileParser = null;
	

	/**
	 * Gets the domain value at specified index.
	 * 
	 * @param idx
	 * @return the domain value at idx
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public static String getDomainValues(int idx)
			throws ArrayIndexOutOfBoundsException {
		return domainValues[idx];
	}

	/**
	 * <p>First thing the application does when running.</p>
	 */
	static 
	{
		File f = new File(trainingDataFileName);

		if (f.exists())
		{
			ofileParser = 
				new FileParser(trainingDataFileName);
		}
	}
}
