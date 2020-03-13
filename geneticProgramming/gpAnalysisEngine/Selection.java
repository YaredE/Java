package gpAnalysisEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Represents a class responsible for selecting individuals for regeneration
 * process.
 * </p>
 * 
 * @author Yared K Estifanos
 */
public class Selection {

	/**
	 * <p>
	 * The original population for selection
	 * </p>
	 */
	private Population originalPopulation;

	/**
	 * <p>
	 * Represents the crossover probability
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
	 * Represents the selected list of individuals for crossover
	 * </p>
	 */
	private List<Individual> selectedforCrossoverandMutation;

	/**
	 * @param originalPopulation
	 * @param crossoverProbability
	 * @param mutationProbability
	 */
	public Selection(Population originalPopulation,
			double crossoverProbability, double mutationProbability) {
		this.originalPopulation = originalPopulation;
		this.crossoverProbability = crossoverProbability;
		this.mutationProbability = mutationProbability;
		this.doSelection();
	}

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
	}

	/**
	 * @return the originalPopulation
	 */
	public Population getOriginalPopulation() {
		return originalPopulation;
	}

	/**
	 * @param originalPopulation
	 *            the originalPopulation to set
	 */
	public void setOriginalPopulation(Population originalPopulation) {
		this.originalPopulation = originalPopulation;
	}

	/**
	 * @return the selectedforCrossoverandMutation
	 */
	public List<Individual> getSelectedforCrossoverandMutation() {
		return selectedforCrossoverandMutation;
	}

	/**
	 * @param selectedforCrossoverandMutation
	 *            the selectedforCrossoverandMutation to set
	 */
	public void setSelectedforCrossoverandMutation(
			List<Individual> selectedforCrossoverandMutation) {
		this.selectedforCrossoverandMutation = selectedforCrossoverandMutation;
	}

	/**
	 * <p>
	 * Runs the selection algorithm
	 * </p>
	 */
	private void doSelection() {
		int _size = new Double(this.getOriginalPopulation().getPopulationSize()
				* this.getCrossoverProbability()).intValue();
		if (_size % 2 != 0)
			_size = _size + 1;
		ArrayList<Individual> selectedforCrossoverandMutation = new ArrayList<Individual>(
				_size);
		this.setSelectedforCrossoverandMutation(selectedforCrossoverandMutation);
		this.runFitness();
		java.util.Collections.sort((List<Individual>) this
				.getOriginalPopulation().getPopulation(), new GAComparator());
		for (int i = 0; i < _size; i++) {
			Individual _individual = 
				this.getOriginalPopulation().getIndividual(i);
			_individual = 
				(new MalformedHandler(_individual)).getRegeneratedIndividual();
			selectedforCrossoverandMutation.add(_individual);
		}
		this.setSelectedforCrossoverandMutation(selectedforCrossoverandMutation);
	}

	/**
	 * <p>
	 * runs the fitness measure evaluates each member with evaluation
	 * </p>
	 */
	private void runFitness()
	{
		for (int _idx = 0; _idx < this.getOriginalPopulation()
				.getPopulationSize(); _idx++) {
			Individual _individual = this.getOriginalPopulation()
					.getIndividual(_idx);
			_individual = (new Fitness(_individual)).getIndividual();
			this.getOriginalPopulation().setIndividual(_idx, _individual);
		}
	}
}