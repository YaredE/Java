package gpAnalysisEngine;

import globals.GAUtility;

/**
 * <p>
 * Used for measuring the fitness of the individual in the population rearranges
 * them in the order of their fitness level.If there are some individuals in the
 * population with some evaluation problems they will be thrown out and replaced
 * with a new generation.
 * </p>
 * 
 * @author Yared Kassa Estifanos.
 * 
 */
public class Fitness {

	/**
	 * <p>
	 * The Individual to be measured by the fitness function
	 * </p>
	 */
	private Individual individual;

	/**
	 * @param _individual
	 */
	public Fitness(Individual _individual) {
		this.individual = _individual;
		this.calculateFitnessMeasure();
	}

	/**
	 * @return the individual
	 */
	public Individual getIndividual() {
		return individual;
	}

	/**
	 * @param individual
	 *            the individual to set
	 */
	public void setIndividual(Individual individual) {
		this.individual = individual;
	}

	/**
	 * <p>
	 * Calculates the sum of all evaluations differnces from expected value
	 * </p>
	 * 
	 * @param _tobeEvaluated
	 */
	private void calculateFitnessMeasure() 
	{
		Individual _tobeEvaluated = this.getIndividual();
		double _fitnessSum = 0.0;
		String _expression = _tobeEvaluated.expressionPostorder();
		ExpressionEvaluator _evaluator = new ExpressionEvaluator(_expression);
		String[][] valuesOfVariable = GAUtility.trainingData;
		for (int i = 0; i < valuesOfVariable.length; i++) {
			double _valueofVariable = Double
					.parseDouble(valuesOfVariable[i][0]);
			double _expectedvalue = Double.parseDouble(valuesOfVariable[i][1]);
			try
			{
			double _evaluatedResult = 
				_evaluator.evaluateExpression(_valueofVariable);
			_fitnessSum += Math.abs(_expectedvalue - _evaluatedResult);
			}
			catch(GPExceptions gp)
			{
				gp.printStackTrace();				
			}
		}
		_tobeEvaluated.setEvaluationOutcome(_fitnessSum);
		this.setIndividual(_tobeEvaluated);
	}
}
