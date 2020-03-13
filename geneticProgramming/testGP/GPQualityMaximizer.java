/**
 * 
 */
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
import junit.framework.TestCase;
import junit.textui.TestRunner;

/**
 * @author Yared K Estifanos
 *
 */
public class GPQualityMaximizer extends TestCase  {
	
	/**
	 * <p>To be used by pass test</p>
	 */
	public final String passMessage = "Expected to pass ";
	
	/**
	 * <p>To be used by failed tests</p>
	 */
	public final String failMessage = "Expected to fail";
	

	/**
	 * @param name
	 */
	public GPQualityMaximizer(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * <p>
	 * used for testing chromosome (Node)
	 * </p>
	 * @throws Exception
	 */
	public void testChromosome() throws Exception{
		
		Chromosome testChrom =
			new Chromosome("+", null, null, 0);

		String expected = "+";
		String actual = testChrom.getData().toString();
 
		assertEquals(passMessage, expected, actual);
		expected = "0";
		boolean condition = expected.equalsIgnoreCase(actual);
		assertFalse(failMessage, condition);
		
		testChrom.setLeftChromosome(new Chromosome("*", null, null, 0));
		actual = testChrom.getLeftChromosome().getData().toString();
		expected = "+";
		condition = expected.equalsIgnoreCase(actual);
		assertFalse(failMessage, condition);
		
		condition = (testChrom.getRightChromosome() == null);
		assertTrue(passMessage, condition);
	}
	
	/**
	 * <p>
	 * Additional Method for testing Chromosome
	 * </p>
	 * @throws Exception
	 */
	public void testChromosomeAdditional() throws Exception {
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
	 * @throws Exception
	 */
	public void testRandomGenerator() throws Exception
	{
		GARandom random = new GARandom();
		boolean condition =  random.intRandom(0, 16) <= 16;
		assertTrue(passMessage, condition);
		
		condition =  random.intRandom(0, 16) > 16;
		assertFalse(passMessage, condition);

	}

	/**
	 * <p>
	 * Testing Individual tree generation
	 * </p>
	 * @throws Exception
	 */
	public void testIndividual() throws Exception {

		Individual testIndividual =
				   new Individual(3);
		String actual = 
			   testIndividual.parentheticalExpression();
		
		boolean condition =  actual != null;
		assertTrue(passMessage, condition);
		
		condition =  actual == null;
		assertFalse(failMessage, condition);
		
		condition =  testIndividual.getDepthOfTree() == 3;
		assertTrue(passMessage, condition);
		
		condition =  testIndividual.getDepthOfTree() == 33;
		assertFalse(failMessage, condition);
		
		condition = testIndividual.getRoot() != null;
		assertTrue(passMessage, condition);
		
		testIndividual = new Individual(-3);
		condition = testIndividual.getRoot() == null;
		assertTrue(passMessage, condition);
		
		condition = testIndividual.getRoot() != null;
		assertFalse(failMessage, condition);
			
		testIndividual = new Individual(0);
		condition = testIndividual.getRoot() != null;
		assertFalse(failMessage, condition);
		
		testIndividual = new Individual(2);
		Iterator it = testIndividual.iteratorLevelOrder();
		while (it.hasNext()) {
			int keyAssigned = (((Chromosome) (it.next())).getKeyToThisPosition());
			condition = keyAssigned <= 6;
			assertTrue(passMessage, condition);			
		}
	}
	
	/**
	 * <p>
	 * used for testing the population generation class
	 * </p>
	 * @throws Exception.
	 */
	public void testPopulationGeneration() throws Exception {
		Population population = new Population(50);
		
		boolean condition = population.getPopulationSize() == 50;		
		assertTrue(passMessage,condition);

		for (int i = 0; i < population.getPopulationSize(); i++) {
			System.out.println("Generated\ti= " + i + "\t"
					+ population.getIndividual(i));
			condition =  population.getIndividual(i).height() == GAUtility.initalDepthofIndividualTree;
			assertTrue(passMessage,condition);
		}
	}
	
	/**
	 * <p>
	 * used for testing the expression evaluation
	 * </p>
	 */
	public void testExpressionEvaluation() {
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
	public void testFitness() {
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
	public void testSelection() {
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
	public void printPopulation(Population population) {
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
	public void testLevelOrder() {
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
	public void testingCrossover() {
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
	public void testMutation() throws Exception {
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
	public void testRegeneration() throws Exception {
		Population population = new Population(100);
		System.out.println("\t\tOriginal ...\t");
		printPopulation(population);
		Regeneration mRegeneration = 
			new Regeneration(population);
		System.out.println("\t\tRegenerated ...\t");
		printPopulation(mRegeneration.getNewPopulation());
	}
	

	/**
	 * <p>
	 * JUnit test runner
	 * </p>
	 * @param args
	 */
    public static void main(String args[]) {
    	TestRunner.run(GPQualityMaximizer.class);    	
    }

}
