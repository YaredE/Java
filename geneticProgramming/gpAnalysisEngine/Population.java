/**
 * 
 */
package gpAnalysisEngine;

import globals.GAUtility;

import java.util.ArrayList;

/**
 * <p>
 * Represents the population i.e. the array of individuals which will be
 * generated by the genetic algorithm
 * </p>
 * 
 * @author Yared Kassa Estifanos
 * 
 */
public class Population {

	/**
	 * <p>
	 * Represents the array list used for storing individual population
	 * </p>
	 */
	private ArrayList<Individual> population;

	/**
	 * <p>
	 * Gets the population size
	 * </p>
	 * 
	 * @return the population size
	 */
	public int getPopulationSize() {
		return this.population.size();
	}

	/**
	 * <p>
	 * Gets the individual value at specified index.
	 * </p>
	 * 
	 * @param _idx
	 * @return the individual
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public Individual getIndividual(int _idx)
			throws ArrayIndexOutOfBoundsException {
		return this.population.get(_idx);
	}

	/**
	 * <p>
	 * Sets the individual value at specified index.
	 * </p>
	 * 
	 * @param _idx
	 * @param _individual
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public void setIndividual(int _idx, Individual _individual)
			throws ArrayIndexOutOfBoundsException {
		this.population.set(_idx, _individual);
	}

	/**
	 * <p>
	 * Creates a population
	 * </p>
	 * 
	 * @param populationSize
	 */
	public Population(int populationSize) {
		this.generate(populationSize);
	}

	/**
	 * <p>
	 * Helper method to generate the population
	 * </p>
	 * 
	 * @param populationSize
	 */
	private void generate(int _populationSize) {
		this.population = new ArrayList<Individual>(_populationSize);
		for (int i = 0; i < _populationSize; i++) {
			Individual _individual = new Individual(
					GAUtility.initalDepthofIndividualTree);
			_individual = new MalformedHandler(_individual)
					.getRegeneratedIndividual();
			this.population.add(_individual);
		}
	}

	/**
	 * @return the population
	 */
	public ArrayList<Individual> getPopulation() {
		return population;
	}

	/**
	 * @param population
	 *            the population to set
	 */
	public void setPopulation(ArrayList<Individual> population) {
		this.population = population;
	}

}