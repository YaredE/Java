package gpAnalysisEngine;

import java.util.Comparator;

/**
 * <p>
 * Comarator used for ordering(sorting) population
 * </p>
 * 
 * @author Yared K Estifanos.
 */
public class GAComparator implements Comparator<Object> {
	/***************************************************************************
	 * <p>
	 * compare method used for comparison implemented for gentic programming
	 * individual members
	 * </p>
	 * 
	 * @param _memberOne
	 * @param _memberTwo
	 * @return comparison result(0,1,-1) equal,greater,lesser
	 */
	public int compare(Object _memberOne, Object _memberTwo) {
		Individual firstIndividual = (Individual) _memberOne;
		Individual secondIndividual = (Individual) _memberTwo;

		if (firstIndividual.getEvaluationOutcome() > secondIndividual
				.getEvaluationOutcome()) {
			return 1;
		} else if (firstIndividual.getEvaluationOutcome() < secondIndividual
				.getEvaluationOutcome()) {
			return -1;
		}
		return 0;
	}
}