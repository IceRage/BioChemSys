package ro.ubb.biochem.optim;

import java.util.Random;

import ro.ubb.biochem.program.elements.FitnessFunction;
import ro.ubb.biochem.program.elements.Program;

public class SimulatedAnnealingThread implements Runnable{
	
	private static final int INT_MAX_TEMPERATURE = 9;
	private static final int MAX_CYCLES_PER_TEMPERATURE = 5;
	
	private Program program;
	private AnnealedProgramsGatherer gatherer;
	private FitnessFunction fitnessFunction;
	private Random randomizer;

	public SimulatedAnnealingThread(Program program, AnnealedProgramsGatherer gatherer, FitnessFunction fitnessFunction) {
		super();
		this.program = program;
		this.gatherer = gatherer;
		this.fitnessFunction = fitnessFunction;
		randomizer = new Random();
	}


	@Override
	public void run() {
		ProgramSimulatedAnnealing sim = new ProgramSimulatedAnnealing
				(program, randomizer.nextDouble(), 
				 randomizer.nextInt(MAX_CYCLES_PER_TEMPERATURE)+1, 
				 randomizer.nextDouble() + randomizer.nextInt(INT_MAX_TEMPERATURE), 
				 fitnessFunction, gatherer);
		sim.anneal();
	}

}
