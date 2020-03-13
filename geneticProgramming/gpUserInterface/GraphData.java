package gpUserInterface;

/*
 * GraphData.java
 * By Yared Estifanos
 *
 */

import gpAnalysisEngine.ExpressionEvaluator;
import gpAnalysisEngine.GPExceptions;
import gpAnalysisEngine.Individual;

/*
 * class GraphData is used to hold the data for graphs including
 * the color of the Graph and the data used to draw the graph
 */
public class GraphData
{
	/**
	 * <p>The target function data ( training data)</p>
	 */
	private double [][] targetFunctionData;
	
	/**
	 * <p>The best fit inidividual function found</p>
	 */
	private Individual bestFitIndividual;

	/**
	 * @return the bestFitIndividual
	 */
	public Individual getBestFitIndividual() {
		return bestFitIndividual;
	}

	/**
	 * @param bestFitIndividual the bestFitIndividual to set
	 */
	public void setBestFitIndividual(Individual bestFitIndividual) {
		this.bestFitIndividual = bestFitIndividual;
	}

	/**
	 * @return the targetFunctionData
	 */
	public double[][] getTargetFunctionData() {
		return targetFunctionData;
	}

	/**
	 * @param targetFunctionData
	 * @param bestFitIndividual
	 */
	public GraphData(double[][] targetFunctionData, Individual bestFitIndividual) {
		this.targetFunctionData = targetFunctionData;
		this.bestFitIndividual = bestFitIndividual;
	}

	/**
	 * <p>Populates the best function obtained</p>
	 * @return data for found function.
	 *
	 */
	public double [][] getBestFunctionObtained()
	{
		double [][] _bestFunctionObtained = null;
		if (getBestFitIndividual() != null)
		{
			String _expressionTobeEvaluated =
				this.getBestFitIndividual().expressionPostorder();
			ExpressionEvaluator mEvaluator = 
				new ExpressionEvaluator(_expressionTobeEvaluated);
			 _bestFunctionObtained = getTargetFunctionData();
			
			//for each rows first column get the value evaluate and populate the matrix.	
			
			for (int i = 0; i < _bestFunctionObtained.length; i++)
			{
				double _valueofVariable =  
					_bestFunctionObtained[i][0];
				double _evaluatedResult = 0D;			
				try {
					 _evaluatedResult =
						mEvaluator.evaluateExpression(_valueofVariable);
				} catch (GPExceptions e) {
					e.printStackTrace();
				}
				_bestFunctionObtained[i][1] = _evaluatedResult;
			}
		}
		return _bestFunctionObtained;
	}
}
