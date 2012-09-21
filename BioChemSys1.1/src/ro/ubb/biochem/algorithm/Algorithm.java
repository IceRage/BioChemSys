package ro.ubb.biochem.algorithm;

import ro.ubb.biochem.gui.AlgorithmListener;
import ro.ubb.biochem.population.Population;
import ro.ubb.biochem.program.elements.Program;

public interface Algorithm extends Runnable{

	/**
	 * creates separate thread for running the algorithm
	 */
	public void run();
	
	/**
	 * creates separate thread for running the algorithm
	 */
	public void run(Population initialPopulation);
	
	public Population getPopulation();
	
	public Program getBestProgram();
	
	public void reset();
	
	public void addAlgorithmListener(AlgorithmListener algorithmListener);
}
