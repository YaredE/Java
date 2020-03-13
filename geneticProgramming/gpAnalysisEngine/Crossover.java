package gpAnalysisEngine;

import globals.GAUtility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * <p>
 * Responsible for handling the crossover function.</p.
 * <p>
 * Given the list of individuals to crossover it randomly selects two
 * individuals at a time and then does the crossover by selecting random
 * crossover points. The two newly generated individuals are stored in the list
 * for all the crossover list completed.
 * </p>
 * 
 * @author Yared Kassa Estifanos
 * @version 0.5
 * 
 */
public class Crossover {
	/**
	 * <p>
	 * Represents the random generator to be used for crossover operation
	 * </p>
	 */
	private GARandom randomGenerator = new GARandom();

	/**
	 * <p>
	 * Represents individuals selected for crossover operation
	 * </p>
	 */
	private List<Individual> selectedforCrossover;

	/**
	 * <p>
	 * Represents a newly generated ossover Individuals
	 * </p>
	 */
	private List<Individual> newlyGeneratedIndividuals;

	/**
	 * @param selectedforCrossover
	 */
	public Crossover(List<Individual> selectedforCrossover) {
		this.selectedforCrossover = selectedforCrossover;
		newlyGeneratedIndividuals = new ArrayList<Individual>();
		this.doCrossover();
	}

	/**
	 * @return the newlyGeneratedIndividuals
	 */
	public List<Individual> getNewlyGeneratedIndividuals() {
		return newlyGeneratedIndividuals;
	}

	/**
	 * @param newlyGeneratedIndividuals
	 *            the newlyGeneratedIndividuals to set
	 */
	public void setNewlyGeneratedIndividuals(
			List<Individual> newlyGeneratedIndividuals) {
		this.newlyGeneratedIndividuals = newlyGeneratedIndividuals;
	}

	/**
	 * @return the randomGenerator
	 */
	public GARandom getRandomGenerator() {
		return randomGenerator;
	}

	/**
	 * @param randomGenerator
	 *            the randomGenerator to set
	 */
	public void setRandomGenerator(GARandom randomGenerator) {
		this.randomGenerator = randomGenerator;
	}

	/**
	 * @return the selectedforCrossover
	 */
	public List<Individual> getSelectedforCrossover() {
		return selectedforCrossover;
	}

	/**
	 * @param selectedforCrossover
	 *            the selectedforCrossover to set
	 */
	public void setSelectedforCrossover(List<Individual> selectedforCrossover) {
		this.selectedforCrossover = selectedforCrossover;
	}

	/**
	 * <p>
	 * Selects two random individuals and runs crossover.
	 * </p>
	 * <p>
	 * Crossover is one point cross over.The two newly generated (crossed over)
	 * individuals are added in the newlyGeneratedIndividuals list.
	 * </p>
	 */
	private void doCrossover() 
	{
			List<Individual> selectedforCrossover = this.getSelectedforCrossover();
			Individual _firstIndividual = null, _secondIndividual = null;
			java.util.Collections.shuffle(selectedforCrossover);

			Iterator it = selectedforCrossover.iterator();

			while (it.hasNext())
			{
				_firstIndividual = (Individual) it.next();
				_secondIndividual = (Individual) it.next();

				int _rndCrossoverPoint_First = 
					randomGenerator.intRandom(0,
					(_firstIndividual.size() - 1));

				
				int _rndCrossoverPoint_Second = 
					randomGenerator.intRandom(0, 
					(_secondIndividual.size() - 1));
				

				//Now Get the nodes - chromosomes at thoes points ... 
				Chromosome _rndFirstChrom = 
					_firstIndividual.searchByKey(_rndCrossoverPoint_First);
					
				//Now get the nodes - chromosomes at the second individual
				Chromosome _rndSecondChrom = 
					_secondIndividual.searchByKey(_rndCrossoverPoint_Second);				

				
				replaceChromosomeInThisTree(_firstIndividual, 
											_rndCrossoverPoint_First,
											_rndSecondChrom); // takes care of the first individual
				
				replaceChromosomeInThisTree(_secondIndividual, 
											_rndCrossoverPoint_Second,
											_rndFirstChrom); // takes care of the second individual

				this.newlyGeneratedIndividuals.add(_firstIndividual);
				this.newlyGeneratedIndividuals.add(_secondIndividual);
			}
	}
	
	/**
	 * 
	 * @param _Individual
	 * @param _selectionCrossoverPoint
	 * @param _newChrom
	 */
	private void replaceChromosomeInThisTree(Individual _Individual,
											 int _selectionCrossoverPoint, 
											 Chromosome _newChrom) 
	{
		
		if (_newChrom != null)
		{
			if (_Individual.height() < 
					GAUtility.maximumAllowedDepthofIndividualTree)
			{//checking the height - depth of the tree.
				_Individual.findAndReplace(_newChrom,
				_selectionCrossoverPoint);
			}
			_Individual.reassignKeysToIndividualTree();
		}
	}
}
