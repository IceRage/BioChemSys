package ro.ubb.biochem.optim;

import java.util.Random;

import ro.ubb.biochem.gui.plotting.FitnessPlotWindow;
import ro.ubb.biochem.program.elements.FitnessFunction;
import ro.ubb.biochem.program.elements.FitnessFunction1;
import ro.ubb.biochem.program.elements.Program;
import ro.ubb.biochem.species.components.SpeciePoolEvolution;

public class ProgramSimulatedAnnealing {

	private  Double maxTemerature = 25.0;
	private  Double minTemperature = 1.0E-5;
	private  Integer maxCyclesPerTemperature  = 5;
	
	private Program program;
	private AnnealedProgramsGatherer gatherer;
	
	private FitnessFunction fct;
	private Double fitness;
	private Double temperature;
	
	private FitnessPlotWindow plotter = null;
	
	public ProgramSimulatedAnnealing(Program program, Double minTemperature, Integer maxCyclesPerTemperature, 
			Double maxTemperature, FitnessFunction fct, AnnealedProgramsGatherer gatherer){
		this.maxTemerature = maxTemperature;
		this.minTemperature = minTemperature;
		this.maxCyclesPerTemperature = maxCyclesPerTemperature;
		this.fct = fct;
		this.program = program;
		this.fitness = (program.getFitness() == null)? fct.computeFitness(program) : program.getFitness();
		this.temperature = maxTemperature;
		this.gatherer = gatherer;
	}

	public ProgramSimulatedAnnealing(Program program, SpeciePoolEvolution expectedEvolution, FitnessPlotWindow plotter) {
		super();
		this.setProgram(program);
		fitness = (program.getFitness() == null)? Double.MAX_VALUE : program.getFitness();
		temperature = maxTemerature;
		fct = new FitnessFunction1(expectedEvolution);
		this.plotter = plotter;
	}
	
	public Program anneal(){
		int cycles = 1; Random randomizer = new Random();
		while(temperature > minTemperature){
			Program clone = program.clone();
			int selectedReaction = randomizer.nextInt(program.getReactionNo());
			int sign = 0;
			Double newKineticRate =randomizer.nextDouble() + sign;
			
			clone.getReactions().get(selectedReaction).updateKineticRate(newKineticRate);
			double newFitness = fct.computeFitness(clone);
			if(newFitness < fitness || Math.random() < Math.exp(-(newFitness - fitness)/temperature)){ 
				fitness = newFitness;
				program = clone;
				if(plotter != null){
					plotter.newGenerationCreatedNotification(fitness, program);
				}
			} else {
				//System.out.println("Dismissed: " + newFitness);
			}
			if(cycles % maxCyclesPerTemperature == 0) {
				temperature = ratio(cycles) * temperature;
			}
			
			cycles++;
		}
		program.setFitness(fitness);
	    gatherer.addProgram(program);
		//System.out.println(log);
		return program;
	}

	private Double ratio(int cycles) {
		return Math.exp(Math.log(minTemperature/maxTemerature)/(cycles-1));
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

}
