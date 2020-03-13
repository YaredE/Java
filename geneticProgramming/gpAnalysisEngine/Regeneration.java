package gpAnalysisEngine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import globals.GAUtility;

/**
 * <p>
 * Represents a class used for doing the reproduction(regeneration) process
 * </p>
 * <p>
 * It first Selects individuals for offspring production operation i.e.
 * crossover and mutation.
 * </p>
 * <p>
 * After crossover and mutation are completed; results from both are combined
 * and evaluated.
 * </p>
 * <p>
 * The best individuals are then introduced into the population.
 * </p>
 * <p>
 * Old population is replaced by the newly generated population. This newly
 * generated population has best results from old,crossover and mutation
 * </p>
 * 
 * @author Yared Kassa Estifanos
 * 
 */
public class Regeneration {

	/**
	 * <p>
	 * The old population
	 * </p>
	 */
	private Population oldPopulation;

	/**
	 * <p>
	 * The new population
	 * </p>
	 */
	private Population newPopulation;

	/**
	 * @param oldPopulation
	 */
	public Regeneration(Population oldPopulation) {
		this.oldPopulation = oldPopulation;
		newPopulation = oldPopulation;
		this.runRegeneration();
	}

	/**
	 * @return the newPopulation
	 */
	public Population getNewPopulation() {
		return newPopulation;
	}

	/**
	 * @param newPopulation
	 *            the newPopulation to set
	 */
	public void setNewPopulation(Population newPopulation) {
		this.newPopulation = newPopulation;
	}

	/**
	 * @return the oldPopulation
	 */
	public Population getOldPopulation() {
		return oldPopulation;
	}

	/**
	 * @param oldPopulation
	 *            the oldPopulation to set
	 */
	public void setOldPopulation(Population oldPopulation) {
		this.oldPopulation = oldPopulation;
	}

	/**
	 * <p>
	 * Runs the regeneration process
	 * </p>
	 */
	private void runRegeneration() {
		// run the selection algorithm and select...
		Selection mSelection = new Selection(this.getOldPopulation(),
				GAUtility.crossoverProbability, GAUtility.mutationProbability);

		// do the crossover ...
		Crossover mCrossover = new Crossover(mSelection
				.getSelectedforCrossoverandMutation());

		// determin the population size for mutation...
		int _sizeOfmutation = (new Double(mCrossover
				.getNewlyGeneratedIndividuals().size()
				* mSelection.getCrossoverProbability())).intValue();
		// list used for storing population to be mutated...
		List<Individual> mutationList = 
			new ArrayList<Individual>();

		int index = 0;
		while (_sizeOfmutation > 0) {
			Individual o = 
				(Individual) mCrossover.getNewlyGeneratedIndividuals().get(index);
			mutationList.add(o);
			_sizeOfmutation--;
			index++;
		}

		Mutation mMutation = new Mutation(mutationList);

		// combined list of population ....
		List<Individual> oPopulationCombined = new ArrayList<Individual>();

		Iterator it = mMutation.getNewlyMutatedIndividuals().iterator();

		while (it.hasNext()) {
			Individual oIndividual = (Individual) it.next();
			oPopulationCombined.add(oIndividual);
		}
		it = mCrossover.getNewlyGeneratedIndividuals().iterator();
		while (it.hasNext()) {
			Individual oIndividual = (Individual) it.next();
			if (!oPopulationCombined.contains(oIndividual)) {
				oPopulationCombined.add(oIndividual);
			}
		}
		// evaluate run fitness and handle malformed population....
		for (int _index = 0; _index < oPopulationCombined.size(); _index++) {
			Individual oIndividual = 
				(Individual) oPopulationCombined.get(_index);
			MalformedHandler handler = new MalformedHandler(oIndividual);
			oPopulationCombined.set(_index, handler.getRegeneratedIndividual());
		}
		// now get the original population and add it to oPopulationCombined
		// list
		it = getOldPopulation().getPopulation().iterator();
		while (it.hasNext()) {
			Individual oIndividual = (Individual) it.next();
			MalformedHandler handler = new MalformedHandler(oIndividual);
			if (!oPopulationCombined.contains(oIndividual)) {
				oIndividual = handler.getRegeneratedIndividual();
				oPopulationCombined.add(oIndividual);
			}
		}
		// now sort by their fitness...
		java.util.Collections.sort(oPopulationCombined, new GAComparator());
		int originalSize = getOldPopulation().getPopulationSize();

		ArrayList<Individual> bestpopulation = new ArrayList<Individual>();

		for (int combo = 0; combo < originalSize; combo++) {
			bestpopulation.add(oPopulationCombined.get(combo));
		}		
		
		it = bestpopulation.iterator();
		GARandom oGARandom = 
			new GARandom();
		while(it.hasNext())
		{
			Individual oIndividual =
				(Individual)it.next();
			if (!oIndividual.parentheticalExpression().contains("X"))
			{
				int maxLeaves=2;
				try {
					maxLeaves = (new Double(Math.pow(2, oIndividual.getDepthOfTree()))).intValue();
				} catch (GPExceptions e) {
					e.printStackTrace();
				}
				int iRandom = 
					oGARandom.intRandom(1,maxLeaves);
				oIndividual.replaceExternalNodesVariables(iRandom);
				MalformedHandler handler =
					new MalformedHandler(oIndividual);
				oIndividual = handler.getRegeneratedIndividual();
			}
		}		
		// now set the new population
		this.getNewPopulation().setPopulation(bestpopulation);
	}
}
