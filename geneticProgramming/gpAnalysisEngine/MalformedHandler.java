package gpAnalysisEngine;

import globals.GAUtility;

/**
 * <p>
 * Represents a class responsible for handling malformed individuals during
 * generation - It creates a new individual and replaces it with a better
 * individual by evaluating it.
 * </p>
 * <p>
 * Malformed individual is an individual when evaluated by expression evaluator
 * gives errors i.e Infinity, Not a Number
 * </p>.
 * 
 * @author Yared K Estifanos.
 * 
 */
public class MalformedHandler {

	/**
	 * <p>
	 * Represents a malformed individual
	 * </p>
	 */
	private Individual malformedIndividual;

	/**
	 * @param malformedIndividual
	 */
	public MalformedHandler(Individual malformedIndividual) {
		this.malformedIndividual = malformedIndividual;
		this.malformedHandler();
	}

	/**
	 * @return the malformedIndividual
	 */
	public Individual getRegeneratedIndividual() {
		return malformedIndividual;
	}

	/**
	 * @param malformedIndividual
	 *            the malformedIndividual to set
	 */
	public void setMalformedIndividual(Individual malformedIndividual) {
		this.malformedIndividual = malformedIndividual;
	}

	/**
	 * <p>
	 * Malformed individuals will be evaluated and checked for errors and a new
	 * replacement will be generated
	 * </p>
	 * <p>
	 * The replacement will also be evaluated before if it is malformed we will
	 * do this in a loop
	 * </p>
	 * 
	 */
	private void malformedHandler() {
		int minimumDepth = GAUtility.initalDepthofIndividualTree;
		new Fitness(this.getRegeneratedIndividual());
		double _evaluatedResult = this.getRegeneratedIndividual()
				.getEvaluationOutcome();

		if (isMalformed(_evaluatedResult)) {
			Individual _regeneratedIndividual = new Individual(minimumDepth);
			new Fitness(_regeneratedIndividual);
			_evaluatedResult = 
				_regeneratedIndividual.getEvaluationOutcome();
			while (isMalformed(_evaluatedResult)) 
			{
				_regeneratedIndividual = 
					new Individual(minimumDepth);
				new Fitness(_regeneratedIndividual);
				_evaluatedResult =
					_regeneratedIndividual.getEvaluationOutcome();
			}
			this.setMalformedIndividual(_regeneratedIndividual);
		}
	}

	/**
	 * <p>
	 * Checks if the value is malformed member i.e. isNAN() or isInfinite()
	 * </p>
	 * 
	 * @param _evaluatedResult
	 * @return true if malformed
	 */
	private boolean isMalformed(double _evaluatedResult) {
		Double tobeChecked = new Double(_evaluatedResult);
		if (tobeChecked.isInfinite() || tobeChecked.isNaN()) {
			return true;
		}
		return false;
	}
}
