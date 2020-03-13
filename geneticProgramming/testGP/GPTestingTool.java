package testGP;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import globals.GAUtility;
import gpAnalysisEngine.Chromosome;
import gpAnalysisEngine.Crossover;
import gpAnalysisEngine.ExpressionEvaluator;
import gpAnalysisEngine.Fitness;
import gpAnalysisEngine.GARandom;
import gpAnalysisEngine.GPExceptions;
import gpAnalysisEngine.Individual;
import gpAnalysisEngine.MalformedHandler;
import gpAnalysisEngine.Mutation;
import gpAnalysisEngine.Population;
import gpAnalysisEngine.Regeneration;
import gpAnalysisEngine.Selection;
import gpRunningEngine.GARunningEngine;

/**
 * <p>
 * This is a tester class
 * </p>
 * 
 * @author Yared K Estifanos.
 * 
 */
public class GPTestingTool {

	/**
	 * <p>
	 * Main method to execute the test
	 * </p>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		testAllInOne();
		testGARunningEngine();
	}

	/**
	 * <p>
	 * Test methods for all classes
	 * </p>
	 * 
	 */
	private static void testAllInOne() {
		// Test case number 2: - testingChromosome()
		testingChromosome();

		// Test case number 2: - testingRandomGenerator()
		testingRandomGenerator();

		// testingIndividual
		testingIndividual();

		// testExpressionEvaluation()
		testExpressionEvaluation();

		// testFitness()
		testFitness();

		// testPopulationGeneration()
		testPopulationGeneration();

		// testSelection()
		testSelection();

		// testingLevelOrder
		testingLevelOrder();

		// testingCrossover()
		testingCrossover();

		// testingMutation()
		testingMutation();

		// testingRegeneration()
		testingRegeneration();
	}

	/**
	 * <p>
	 * used for testing chromosome class This is our node class
	 * </p>
	 * 
	 */
	private static void testingChromosome() {
		System.out.println("Attempting to create a Chromosome node ");
		Chromosome testChrom = new Chromosome("+", null, null, 0);
		System.out.println(testChrom);
		System.out.println("Attempting to SET the left a Chromosome node ");
		testChrom.setLeftChromosome(new Chromosome("*", null, null, 0));
		System.out.println("Attempting to print a left Chromosome node ");
		System.out.println(testChrom.getLeftChromosome());
		System.out.println("Attempting to print a right Chromosome node ");
		System.out.println(testChrom.getRightChromosome());
		System.out
				.println("Attempting to SET the right Chromosome node and print ");
		testChrom.setRightChromosome(new Chromosome("/", null, null, 0));
		System.out.println(testChrom.getRightChromosome());
	}

	/**
	 * <p>
	 * Testing the random generator
	 * </p>
	 * 
	 */
	private static void testingRandomGenerator() {
		GARandom random = new GARandom();
		System.out.println("Attempting to generate a number b/n 0 -16");
		System.out.println("Generated\t" + random.intRandom(0, 16));
	}

	/**
	 * <p>
	 * Testing Individual tree generation
	 * </p>
	 * 
	 */
	private static void testingIndividual() {

		System.out.println("Attempting to generate a tree depth=3");
		Individual testIndividual = new Individual(3);
		System.out.println("Generated\t"
				+ testIndividual.parentheticalExpression());
		System.out.println("Generated\t"
				+ testIndividual.postorderHelper(testIndividual.getRoot()));
		System.out.println("Attempting to generate a tree depth= -3");
		testIndividual = new Individual(-3);
		System.out.println("Generated\t" + testIndividual.getRoot());
		System.out.println("Attempting to generate a tree depth= 0");
		testIndividual = new Individual(0);
		System.out.println("Generated\t" + testIndividual.getRoot());

		System.out.println("Attempting to generate a tree depth=4");
		testIndividual = new Individual(2);
		System.out.println("Generated\t"
				+ testIndividual.parentheticalExpression());

		Iterator it = testIndividual.iteratorLevelOrder();
		while (it.hasNext()) {
			System.out.print(((Chromosome) (it.next())).getKeyToThisPosition()
					+ "\t");
		}
		System.out.println();
		System.out.println("Attempting to generate a tree depth=20 times");
		for (int i = 0; i < 20; i++) {
			testIndividual = new Individual(3);
			System.out.println("Generated\ti= " + i + "\t"
					+ testIndividual.parentheticalExpression());
		}
	}

	/**
	 * <p>
	 * used for testing the population generation class
	 * </p>
	 */
	private static void testPopulationGeneration() {
		Population population = new Population(50);

		for (int i = 0; i < population.getPopulationSize(); i++) {
			System.out.println("Generated\ti= " + i + "\t"
					+ population.getIndividual(i));
		}
	}

	/**
	 * <p>
	 * used for testing the expression evaluation
	 * </p>
	 */
	private static void testExpressionEvaluation() {
		Population population = new Population(50);
		ExpressionEvaluator exp = null;

		for (int i = 0; i < population.getPopulationSize(); i++) {
			String _expression = ((Individual) population.getIndividual(i))
					.expressionPostorder();
			exp = new ExpressionEvaluator(_expression);
			double _valueofVariable = 9;
			System.out.println(_expression);
			try {
				System.out.print("\t" + exp.evaluateExpression(_valueofVariable));
			} catch (GPExceptions e) {
				e.printStackTrace();
			}
			System.out.println();
		}
	}

	/**
	 * <p>
	 * used for testing the expression evaluation
	 * </p>
	 */
	private static void testFitness() {
		Population population = new Population(50);

		for (int i = 0; i < population.getPopulationSize(); i++) {
			new Fitness(population.getIndividual(i));
			Individual _individual = (new MalformedHandler(population
					.getIndividual(i))).getRegeneratedIndividual();
			population.setIndividual(i, _individual);
			System.out.println(population.getIndividual(i)
					.parentheticalExpression()
					+ "\t"
					+ +population.getIndividual(i).getEvaluationOutcome());
		}
	}

	/**
	 * <p>
	 * used for testing the expression evaluation
	 * </p>
	 */
	private static void testSelection() {
		Population population = new Population(50);

		System.out.print("Printing original population");
		printPopulation(population);

		List<Individual> selected = (new Selection(population,
				GAUtility.crossoverProbability, GAUtility.mutationProbability))
				.getSelectedforCrossoverandMutation();

		System.out.println("SELECTED SIZE\t" + selected.size());
		for (int i = 0; i < selected.size(); i++) {
			System.out.println("INDIVIDUAL ID:\t " + i + "\t"
					+ selected.get(i).parentheticalExpression() + "\t"
					+ +selected.get(i).getEvaluationOutcome());
		}
	}

	/**
	 * <p>
	 * Prints the population in the array list ranked by their fitness
	 * </p>
	 */
	private static void printPopulation(Population population) {
		System.out.println();
		System.out
				.println(" ------------------------------------------------------------------------------");
		for (int i = 0; i < population.getPopulationSize(); i++) {
			System.out.print("Original id:\t" + i + "\t");
			System.out
					.println(population.getIndividual(i)
							.parentheticalExpression()
							+ "\t"
							+ population.getIndividual(i)
									.getEvaluationOutcome());
		}
		System.out
				.println(" ------------------------------------------------------------------------------");
	}

	/**
	 * <p>
	 * Method for testing reassignKeysToIndividualTree()
	 * </p>
	 */
	private static void testingLevelOrder() {
		Individual levelTest = new Individual(2);
		levelTest.reassignKeysToIndividualTree();

		Iterator it = levelTest.iteratorLevelOrder();
		while (it.hasNext()) {
			Chromosome p = (Chromosome) it.next();
			System.out.print(p.getKeyToThisPosition() + "\t");
		}
	}

	/**
	 * <p>
	 * Method for testing Crossover
	 * </p>
	 * 
	 */
	private static void testingCrossover() {
		Population population = new Population(150);

		printPopulation(population);

		Selection mSelection = new Selection(population, 0.89, 0.05);

		Crossover crossOver = new Crossover(mSelection
				.getSelectedforCrossoverandMutation());

		List<Individual> newGenerated = crossOver
				.getNewlyGeneratedIndividuals();

		System.out.println(newGenerated.size() + "\t");

		Iterator it = newGenerated.iterator();

		int i = 0;
		while (it.hasNext()) {
			i++;
			Individual p = (Individual) it.next();
			new Fitness(p);
			System.out.println(p.parentheticalExpression() + "\t" + i + "\t"
					+ p.getEvaluationOutcome());
		}
	}

	/**
	 * <p>
	 * Represents a method for testing mutation
	 * </p>
	 */
	private static void testingMutation() {
		Population population = new Population(150);

		Selection mSelection = new Selection(population, 0.89, 0.05);

		Crossover crossOver = new Crossover(mSelection
				.getSelectedforCrossoverandMutation());

		List<Individual> oIndividual = new ArrayList<Individual>();

		int _sizeOfmutation = (new Double(crossOver
				.getNewlyGeneratedIndividuals().size()
				* mSelection.getCrossoverProbability())).intValue();
		int index = 0;
		while (_sizeOfmutation > 0) {
			Individual o = (Individual) crossOver
					.getNewlyGeneratedIndividuals().get(index);
			oIndividual.add(o);
			_sizeOfmutation--;
			index++;
		}

		Mutation testMutation = new Mutation(oIndividual);

		Iterator it = testMutation.getSelectedforMutation().iterator();

		while (it.hasNext()) {
			Individual printIndividual = (Individual) it.next();
			new Fitness(printIndividual);
			System.out.println("Mutated:\t"
					+ printIndividual.parentheticalExpression() + "\t"
					+ printIndividual.getEvaluationOutcome());
		}
		System.out.println("INITIAL SIZE:\t"
				+ mSelection.getSelectedforCrossoverandMutation().size());
		System.out.println("Mutated SIZE:\t"
				+ testMutation.getSelectedforMutation().size());
	}

	/**
	 * <p>
	 * Regeneration testing method
	 * </p>
	 */
	private static void testingRegeneration() {
		Population population = new Population(100);
		System.out.println("\t\tOriginal ...\t");
		printPopulation(population);
		Regeneration mRegeneration = 
			new Regeneration(population);
		System.out.println("\t\tRegenerated ...\t");
		printPopulation(mRegeneration.getNewPopulation());
	}

	/*
	 * GARunningEngine(int initialPopulationSize, int
	 * maximumNumberofGenerations, double crossoverProbability, double
	 * mutationProbability, int initalDepthofIndividualTree, int
	 * maximumAllowedDepthofIndividualTree, Individual
	 * closestIndividualGenerated, String trainingDataFileName, double
	 * closenessToResult)
	 */
	private static void testGARunningEngine() {
		GARunningEngine mRunningEngine =
		new GARunningEngine(GAUtility.initialPopulationSize,
				GAUtility.maximumNumberofGenerations,
				GAUtility.crossoverProbability,
				GAUtility.mutationProbability,
				GAUtility.initalDepthofIndividualTree,
				GAUtility.maximumAllowedDepthofIndividualTree,
				GAUtility.trainingDataFileName, 100D);
		mRunningEngine.runGA();
	}
}
