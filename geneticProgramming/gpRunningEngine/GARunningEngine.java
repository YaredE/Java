package gpRunningEngine;

import javax.swing.JTextArea;
import globals.GAUtility;
import gpAnalysisEngine.Fitness;
import gpAnalysisEngine.GAComparator;
import gpAnalysisEngine.Individual;
import gpAnalysisEngine.Population;
import gpAnalysisEngine.Regeneration;
import gpUserInterface.UpdateMessage;

/**
 * <p>
 * Represents a class for running genetic programming algorithm.
 * </p>
 * <p>
 * It checks constantly for the termination conditions and all start parameters
 * are taken (read) from either the user input (GUI) or uses the default value.
 * </p>
 * 
 * @author Yared K Estifanos.
 * 
 */
public class GARunningEngine
{
	/**
	 * <p>
	 * Represents a local variable to hold special termination condition
	 * </p>
	 * <p>
	 * Target function i.e. equivalent to the function we are looking for is
	 * found
	 * </p>
	 */
	private boolean targetFound = false;
	
	
	/**
	 * <p>Text area for displaying output</p>
	 */	
	private JTextArea outputArea;
	
	private boolean isRunning= true;

	/**
	 * <p>
	 * initial population size the population size that is used to start the
	 * Genetic Algorithm
	 * </p>
	 */
	private int initialPopulationSize;

	/**
	 * <p>
	 * The maximum number of generations allowed
	 * </p>
	 */
	private int maximumNumberofGenerations;

	/**
	 * <p>
	 * Selection rate to be used for crossover operation
	 * </p>
	 */
	private double crossoverProbability;

	/**
	 * <p>
	 * Represents the mutation probability
	 * </p>
	 */
	private double mutationProbability;

	/**
	 * <p>
	 * The inital depth of the tree at generation
	 * </p>
	 */
	private int initalDepthofIndividualTree;

	/**
	 * <p>
	 * The maximum depth of the tree allowed
	 * </p>
	 */
	private int maximumAllowedDepthofIndividualTree;

	/**
	 * <p>
	 * Represents the closest individual solution found
	 * </p>
	 */
	private Individual closestIndividualGenerated;

	/**
	 * <p>
	 * Represents the file path to the training data.
	 * </p>
	 */
	private String trainingDataFileName;

	/**
	 * <p>
	 * Closeness to the result
	 * </p>
	 */
	private double closenessToResult = 500D;

	/**
	 * <p>
	 * Represents the current population that is generated
	 * </p>
	 */
	private Population currentPopulation;


 	/**
	 * @return the crossoverProbability
	 */
	public double getCrossoverProbability() {
		return crossoverProbability;
	}

	/**
	 * @param crossoverProbability
	 *            the crossoverProbability to set
	 */
	public void setCrossoverProbability(double crossoverProbability) {
		this.crossoverProbability = crossoverProbability;
		GAUtility.crossoverProbability = crossoverProbability;
	}

	/**
	 * @return the initalDepthofIndividualTree
	 */
	public int getInitalDepthofIndividualTree() {
		return initalDepthofIndividualTree;
	}

	/**
	 * @param initalDepthofIndividualTree
	 *            the initalDepthofIndividualTree to set
	 */
	public void setInitalDepthofIndividualTree(int initalDepthofIndividualTree) {
		this.initalDepthofIndividualTree = initalDepthofIndividualTree;
		GAUtility.initalDepthofIndividualTree = initalDepthofIndividualTree;
	}

	/**
	 * @return the initialPopulationSize
	 */
	public int getInitialPopulationSize() {
		return initialPopulationSize;
	}

	/**
	 * @param initialPopulationSize
	 *            the initialPopulationSize to set
	 */
	public void setInitialPopulationSize(int initialPopulationSize) {
		this.initialPopulationSize = initialPopulationSize;
		GAUtility.initialPopulationSize = initialPopulationSize;
	}

	/**
	 * @return the maximumAllowedDepthofIndividualTree
	 */
	public int getMaximumAllowedDepthofIndividualTree() {
		return maximumAllowedDepthofIndividualTree;
	}

	/**
	 * @param maximumAllowedDepthofIndividualTree
	 *            the maximumAllowedDepthofIndividualTree to set
	 */
	public void setMaximumAllowedDepthofIndividualTree(
			int maximumAllowedDepthofIndividualTree) {
		this.maximumAllowedDepthofIndividualTree = maximumAllowedDepthofIndividualTree;
		GAUtility.maximumAllowedDepthofIndividualTree = maximumAllowedDepthofIndividualTree;
	}

	/**
	 * @return the maximumNumberofGenerations
	 */
	public int getMaximumNumberofGenerations() {
		return maximumNumberofGenerations;
	}

	/**
	 * @param maximumNumberofGenerations
	 *            the maximumNumberofGenerations to set
	 */
	public void setMaximumNumberofGenerations(int maximumNumberofGenerations) {
		this.maximumNumberofGenerations = maximumNumberofGenerations;
		GAUtility.maximumNumberofGenerations = maximumNumberofGenerations;
	}

	/**
	 * @return the mutationProbability
	 */
	public double getMutationProbability() {
		return mutationProbability;
	}

	/**
	 * @param mutationProbability
	 *            the mutationProbability to set
	 */
	public void setMutationProbability(double mutationProbability) {
		this.mutationProbability = mutationProbability;
		GAUtility.mutationProbability = mutationProbability;
	}

	/**
	 * @return the targetFound
	 */
	public boolean isTargetFound() {
		return targetFound;
	}

	/**
	 * @param targetFound
	 *            the targetFound to set
	 */
	public void setTargetFound(boolean targetFound) {
		this.targetFound = targetFound;
	}

	/**
	 * @return the closestIndividualGenerated
	 */
	public Individual getClosestIndividualGenerated() {
		return closestIndividualGenerated;
	}

	/**
	 * @param closestIndividualGenerated
	 *            the closestIndividualGenerated to set
	 */
	public void setClosestIndividualGenerated(
			Individual closestIndividualGenerated) {
		this.closestIndividualGenerated = closestIndividualGenerated;
	}

	/**
	 * @return the trainingDataFileName
	 */
	public String getTrainingDataFileName() {
		return trainingDataFileName;
	}

	/**
	 * @param trainingDataFileName
	 *            the trainingDataFileName to set
	 */
	public void setTrainingDataFileName(String trainingDataFileName) {
		this.trainingDataFileName = trainingDataFileName;
		GAUtility.trainingDataFileName = trainingDataFileName;
	}

	/**
	 * @return the currentPopulation
	 */
	public Population getCurrentPopulation() {
		return currentPopulation;
	}

	/**
	 * <p>
	 * Represents a method(function) used for calculating the termination
	 * condition of the genetic algorithm.
	 * </p>
	 * <p>
	 * If we have a program that fits best i.e. we generated the best individual
	 * to solve our equation then we will return true.
	 * </p>
	 * <p>
	 * If we have a program that fits best and is in the range of the minimum
	 * and maximum fitness measures then we will terminate i.e. return true.
	 * </p>
	 * <p>
	 * In all other cases we return false.
	 * </p>
	 * 
	 * @param generationCount
	 * @return the termination condition.
	 */
	private boolean terminationCondition(int generationCount) {
		
		boolean hasMoreChanceToGenerate = 
			(this.getMaximumNumberofGenerations() > generationCount);
		boolean terminating = false;

		if (!(hasMoreChanceToGenerate)) 
		{// if there are no more generations that can be done.
			terminating = true;
		}
		if (this.getClosestIndividualGenerated().getEvaluationOutcome() < 
				this.closenessToResult) {// close enough											// function...
			terminating = true;
		}
		return terminating;
	}

	/**
	 * <p>
	 * Generate initial population run regeneration until termination condition
	 * is fullfilled
	 * </p>
	 */
	public synchronized void runGA() {
		/**
		 * Choose initial population Evaluate the fitnesses of individuals in
		 * the population Repeat Select best-ranking individuals to reproduce
		 * Breed new generation through crossover and mutation (genetic
		 * operations) and give birth to offspring Evaluate the individual
		 * fitnesses of the offspring Replace worst ranked part of population
		 * with offspring Until terminating condition
		 */
		// nothing yet...
		int generationCount = 0;

		// 1. initalize population...
		currentPopulation = new Population(getInitialPopulationSize());

		// 2. a) evaluate the population....
		// expression evaluator
		// (evaluate and store the sum of all distances
		// from the expected value in the data matrix)
		evaluateTheFitnessesPopulation();

		// 2. b) get the best individual guy and...
		checkforBestGenerated();
		
		if (outputArea != null)
		{
			displayMessage(currentPopulation, generationCount);
		}else{
			printPopulation(currentPopulation,generationCount);
		}

		while (!terminationCondition(generationCount)) {
			generationCount++;
			Regeneration generation = new Regeneration(currentPopulation);
			currentPopulation = generation.getNewPopulation();
			evaluateTheFitnessesPopulation();
			checkforBestGenerated();
			if (outputArea != null)
			{
				displayMessage(currentPopulation, generationCount);
			}else{
				printPopulation(currentPopulation,generationCount);
			}
		}
		isRunning = false;		
	}

	/**
	 * <p>
	 * Checks if the best individual is generated
	 * </p>
	 * 
	 */
	private void checkforBestGenerated() {

		java.util.Collections.sort(getCurrentPopulation().getPopulation(),
				new GAComparator());
		this.closestIndividualGenerated = getCurrentPopulation()
				.getPopulation().get(0);
		
		if (Double.isNaN(closestIndividualGenerated.getEvaluationOutcome()) || 
			Double.isInfinite(closestIndividualGenerated.getEvaluationOutcome()))
		{
			this.closestIndividualGenerated = 
				getCurrentPopulation().getPopulation().get(1);
		}
		if (closestIndividualGenerated.getEvaluationOutcome() <= this.closenessToResult) {
			this.targetFound = true;
		}
	}

	/**
	 * <p>
	 * Evaluates the population by calling the evaluate function i.e. the
	 * expression evaluator
	 * </p>
	 * 
	 * @param population
	 */
	private void evaluateTheFitnessesPopulation() {
		Population population = this.getCurrentPopulation();
		if (population != null || population.getPopulationSize() > 0) {
			for (int i = 0; i < population.getPopulationSize(); i++) {
				Fitness mFitness = new Fitness(population.getIndividual(i));
				this.getCurrentPopulation().setIndividual(i,
						mFitness.getIndividual());
			}
		}
	}

	/**
	 * @param initialPopulationSize
	 * @param maximumNumberofGenerations
	 * @param crossoverProbability
	 * @param mutationProbability
	 * @param initalDepthofIndividualTree
	 * @param maximumAllowedDepthofIndividualTree
	 * @param trainingDataFileName
	 * @param closenessToResult
	 */
	public GARunningEngine(int initialPopulationSize,
			int maximumNumberofGenerations, double crossoverProbability,
			double mutationProbability, int initalDepthofIndividualTree,
			int maximumAllowedDepthofIndividualTree,
			String trainingDataFileName, double closenessToResult) {
		this.initialPopulationSize = initialPopulationSize;
		this.maximumNumberofGenerations = maximumNumberofGenerations;
		this.crossoverProbability = crossoverProbability;
		this.mutationProbability = mutationProbability;
		this.initalDepthofIndividualTree = initalDepthofIndividualTree;
		this.maximumAllowedDepthofIndividualTree = maximumAllowedDepthofIndividualTree;
		this.trainingDataFileName = trainingDataFileName;
		this.closenessToResult = closenessToResult;
	}
	
	/**
	 * <p>Prints the population in the array list ranked by their fitness</p>
	 */
	private void printPopulation(Population  population, int generation)
	{
		System.out.println( " ---------------------------------------");
		System.out.println( " GENERATION ID: " + generation + " -------");
		for(int i=0; i < population.getPopulationSize(); i++)
		{
			System.out.println(i +"\t" + 
							  population.getIndividual(i).parentheticalExpression()
							  + "\t" +
							  population.getIndividual(i).getEvaluationOutcome());
		}
		System.out.println( " ---------------------------------------");
	}
	
	/**
	 * <p>Prints the population in the array list ranked by their fitness</p>
	 * @param population 
	 * @param generation 
	 */
	private synchronized void displayMessage(Population  population, int generation)
	{
		String sData =( " ---------------------------------------");
		sData += "\n" + ( " GENERATION ID: " + generation + " -------");
		for(int i=0; i < population.getPopulationSize(); i++)
		{
			sData += "\n" +(i +"\t" + 
							  population.getIndividual(i).parentheticalExpression()
							  + "\t" +
							  population.getIndividual(i).getEvaluationOutcome());
		}
		sData += "\n" +( " ---------------------------------------");
		
		new UpdateMessage(outputArea, sData).start();
		this.outputArea.repaint();
	}
	
	/**
	 * <p>
	 * Checks if termination condition is false</p>
	 * </p>
	 * 
	 * @return true if terminated false if still running
	 */
	public synchronized boolean isRunning()
	{
		return isRunning;
	}

	/**
	 * @return the outputArea
	 */
	public JTextArea getOutputArea() {
		return outputArea;
	}

	/**
	 * @param outputArea the outputArea to set
	 */
	public void setOutputArea(JTextArea outputArea) {
		this.outputArea = outputArea;
	}
}
