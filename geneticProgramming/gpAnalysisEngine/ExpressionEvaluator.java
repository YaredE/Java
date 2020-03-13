package gpAnalysisEngine;

import java.util.Stack;
import java.util.StringTokenizer;

/**
 * <p>
 * Represents the class responsible for doing expression evaluation. Given an
 * individual tree it evaluates the tree and assigns the value obtained from
 * evaluating it. Incase there is some arthimitic error the tree would be
 * assigned a value Double.NaN to indicate there is something wrong with it.
 * Later by the caller of this class, this individual tree would be regenerated
 * or thrown out depending on the remaining size of the population.
 * </p>
 * 
 * @author Yared K Estifanos
 * 
 */
public class ExpressionEvaluator {
	/**
	 * <p>
	 * Represents the individual to be evaluated
	 * </p>
	 */
	private String expressionTobeEvaluated;

	/**
	 * <p>
	 * Gets the expression to be evaluated
	 * </p>
	 * 
	 * @return the expression tobe evaluated
	 */
	public String getIndividualTobeEvaluated() {
		return expressionTobeEvaluated;
	}

	/**
	 * <p>
	 * Sets the individual to be evaluated
	 * </p>
	 * 
	 * @param _expressionTobeEvaluated
	 */
	public void setIndividualTobeEvaluated(String _expressionTobeEvaluated) {
		this.expressionTobeEvaluated = _expressionTobeEvaluated;
	}

	/**
	 * <p>
	 * Evaluates the expression for the given value of the variable.
	 * </p>
	 * 
	 * @param _valueofVariable
	 * @return value after evaluated
	 * @throws GPExceptions 
	 */
	public double evaluateExpression(double _valueofVariable) 
					throws GPExceptions 
	{
		Stack<String> operand = 
			new Stack<String>();
		String expression = 
			this.getIndividualTobeEvaluated();
		expression =
			setExpressionVariable(expression, 
					(new Double(_valueofVariable)).toString());

		StringTokenizer oTokenizer =
			new StringTokenizer(expression);

		while (oTokenizer.countTokens() > 0) {
			String token = 
				oTokenizer.nextToken().toString();
			if (isOperator(token)) {
				String _operator = token.toString().trim();
				if (operand.size() < 2)
				{
					throw new GPExceptions("Evaluation Exception");
				}
				double _secondValue = 
					Double.parseDouble(operand.pop().toString());
				double _firstValue =
					Double.parseDouble(operand.pop().toString());
				double result = this.evaluate(_firstValue, _secondValue,
						_operator);
				operand.push((new Double(result)).toString());
			} else {
				operand.push(token);
			}
		}
		double result = new Double(operand.pop());
		return (double) result;
	}

	/**
	 * <p>
	 * Sets the variable value of the expression
	 * </p>
	 * 
	 * @param expression
	 * @param _value
	 * @return the new expression after the variable is set.
	 */
	private String setExpressionVariable(String expression, String _value) {
		return expression.replaceAll("X", _value);
	}

	/**
	 * 
	 * <p>
	 * Used for checking if string is operator or not
	 * <p>
	 * 
	 * @param _operator
	 * @return checks if _operator is (+,-,/,*)
	 */
	private boolean isOperator(String _operator) {
		if (_operator.trim().length() > 1) {
			return false;
		} else if (_operator.trim().equals("*") || _operator.trim().equals("-")
				|| _operator.trim().equals("+") || _operator.trim().equals("/")) {
			return true;
		}
		return false;
	}

	/**
	 * <p>
	 * Evaluation function- evaluates the value
	 * </p>
	 * 
	 * @param _firstValue
	 * @param _secondValue
	 * @param _operator
	 * @return evaluation result
	 */
	private double evaluate(double _firstValue, double _secondValue,
			String _operator) {
		_firstValue = _firstValue * 1D;
		_secondValue = _secondValue * 1D;

		if (_operator.trim().equals("*")) {
			return (double) _firstValue * _secondValue;
		}
		if (_operator.trim().equals("+")) {
			return (double) _firstValue + _secondValue;
		}
		if (_operator.trim().equals("-")) {
			return (double) _firstValue - _secondValue;
		}
		if (_operator.trim().equals("/")) {
			return (double) (_firstValue) / (_secondValue);
		}
		return Double.NaN;// this means some error has occurred.(Some unknown
							// operator is passed)
	}

	/**
	 * <p>
	 * Constructs ExpressionEvaluator with defined expression
	 * </P>
	 * 
	 * @param _expressionTobeEvaluated
	 */
	public ExpressionEvaluator(String _expressionTobeEvaluated) {
		this.setIndividualTobeEvaluated(_expressionTobeEvaluated);
	}
}
