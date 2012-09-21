package ro.ubb.biochem.algorithm;

import ro.ubb.biochem.gui.AlgorithmListener;
import ro.ubb.biochem.population.Population;
import ro.ubb.biochem.program.elements.Program;

/**
 * @interface Interface for a generic "Genetic Programming" (GP) algorithm
 */
public interface Algorithm extends Runnable {

	/**
	 * Creates separate thread for running the algorithm
	 */
	public void run();
	
	/**
	 * Creates separate thread for running the algorithm
	 * 
	 * @param initialPopulation The population with which the GP algorithm starts
	 */
	public void run(Population initialPopulation);
	
	public Population getPopulation();
	
	public Program getBestProgram();
	
	public void reset();
	
	public void addAlgorithmListener(AlgorithmListener algorithmListener);
	
}
