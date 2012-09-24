package ro.ubb.biochem.algorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ro.ubb.biochem.exceptions.InvalidInputException;
import ro.ubb.biochem.exceptions.InvalidProgramException;
import ro.ubb.biochem.gui.AlgorithmListener;
import ro.ubb.biochem.gui.plotting.FitnessPlotWindow;
import ro.ubb.biochem.operators.Crossover;
import ro.ubb.biochem.operators.DeletionForProgram1;
import ro.ubb.biochem.operators.InsertionForProgram1;
import ro.ubb.biochem.operators.KineticRateAlterationForProgram1;
import ro.ubb.biochem.operators.Mutation;
import ro.ubb.biochem.operators.Selection;
import ro.ubb.biochem.operators.SpecieReplaceMutationForProgram1;
import ro.ubb.biochem.optim.AnnealedProgramsGatherer;
import ro.ubb.biochem.optim.ProgramSimulatedAnnealing;
import ro.ubb.biochem.optim.SimulatedAnnealingThread;
import ro.ubb.biochem.persistance.InputReader;
import ro.ubb.biochem.population.Population;
import ro.ubb.biochem.population.PopulationImpl;
import ro.ubb.biochem.program.elements.FitnessFunctionImpl;
import ro.ubb.biochem.program.elements.Program;
import ro.ubb.biochem.program.elements.ProgramGeneratorForProgramImpl;
import ro.ubb.biochem.reaction.components.RuleRepository;
import ro.ubb.biochem.species.components.SpeciePoolEvolution;
import ro.ubb.biochem.utils.SBMLExporter;

@SuppressWarnings("all")
public class GPAlgorithm implements Algorithm {

	private static final int SA_TRIGGERED_AFTER_NO_ITERATIONS = 75;
	private static final double FITNESS_THRESHOLD = 0.3;
	
	private SpeciePoolEvolution speciesInput;
	private RuleRepository ruleRepository;
	private Integer populationSize;
	private Integer maxIterations;
	private Population population;
	private Crossover crossoverOp;
	private List<Mutation> mutationsOps;
	private Selection selectionCrossoverOp;
	private Selection selectionSurvivalOp;
	private Double mutationProbability;
	private Random randomizer = new Random();
	private List<AlgorithmListener> algorithmListenerList;
	private String outputFile = "";
	private boolean isStoppingCriterion = false;
	

	public GPAlgorithm(AlgorithmSettings settings) throws InvalidInputException {
		readTargetBehaviourInputFile(settings);
		
		this.ruleRepository = InputReader.readTemplatesAndCreateRules(settings.getTemplatesFile(), speciesInput);
		
		this.populationSize = settings.getPopulationSize();
		this.maxIterations = settings.getMaxInterations();
		this.mutationsOps = settings.getMutationOps();
		setSpecificMutationsFields();
		this.crossoverOp = settings.getCrossoverOp();
		this.selectionCrossoverOp = settings.getSelectionCrossoverOp();
		this.selectionSurvivalOp = settings.getSelectionsSurvivalOp();
		this.mutationProbability = settings.getMutationProbability();
		this.outputFile = settings.getOutputFileName();
		
		this.algorithmListenerList = new ArrayList<AlgorithmListener>();
		
		this.isStoppingCriterion = settings.isStoppingCriteria();

		initializePopulation();
	}

	private void readTargetBehaviourInputFile(AlgorithmSettings settings)
			throws InvalidInputException {
		try {
			this.speciesInput = InputReader.readTargetBehavior(settings.getTargetBehaviorFile());
		} catch (InvalidInputException exception) {
			throw exception;
		}
	}

	private void initializePopulation() {
		this.population = new PopulationImpl(populationSize, new FitnessFunctionImpl(speciesInput),
				new ProgramGeneratorForProgramImpl(ruleRepository));
	}

	public void addAlgorithmListener(AlgorithmListener algorithmListener) {
		this.algorithmListenerList.add(algorithmListener);
	}

	/**
	 * Run the main Genetic Programming algorithm
	 * 
	 * The population has already been initialised
	 */
	@Override
	public void run() {
		int currentIteration = 0;
		
		while (isStoppingCriterionNotMet(currentIteration)) {
			doIteration();
			
			for (AlgorithmListener algorithmListener : algorithmListenerList) {
				algorithmListener.newGenerationCreatedNotification();
			}
			
			currentIteration++;
			
			Integer extra = 0;
			Integer missing = 0;
			
			countPenalizedPrograms(extra, missing);
			
			if(currentIteration % SA_TRIGGERED_AFTER_NO_ITERATIONS == 0) {
				kickStartSimulatedAnnealing();
			}
		}
		
		SBMLExporter.exportProgramToSMBL(speciesInput.getPhase(0), population.getBestProgram(), outputFile);
	}
	
	/**
	 * Check if the stopping criterion was met
	 */
	private boolean isStoppingCriterionNotMet(int currentIteration) {
		if (!UserStopCondition.shouldStop()) {
			if (isStoppingCriterion) {
				return (population.getBestFitness() > FITNESS_THRESHOLD);
			} else {
				return ((maxIterations == 0) || (currentIteration < maxIterations));
			}
		} else {
			return false;
		}
    }

	public void kickStartSimulatedAnnealing(){
		System.out.println("Start pool for SA");
		AnnealedProgramsGatherer gatherer = new AnnealedProgramsGatherer();
		List<Thread> annealedSimulators = new ArrayList<Thread>();
		List<Program> toAnneal = selectionCrossoverOp.select(population, populationSize/2 + 1);
		List<Thread> threads = new ArrayList<Thread>();
		for(final Program program: toAnneal) {
			Thread t = new Thread(new SimulatedAnnealingThread(program.clone(), gatherer, population.getFitnessFunction()));
			threads.add(t);
			t.start();
		}
		try {
			for(Thread t: threads)
				t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		population.clear();
		population.addAllPrograms(gatherer.getAnnealed());
		doIteration();
	}


	
	/**
	 * generates n offspring and performs survival selection between the n
	 * parents and n offspring
	 */
	private void doIteration() {
		Population newPopulation = population.clone();
		int attempts = 0;
		while (newPopulation.getSize() < population.getSize() * 2) {
			Program offspring = generateOffspring();
			if(offspring != null)
				newPopulation.addProgram(offspring);
		}

		List<Program> survivedPrograms = selectionSurvivalOp.select(newPopulation, populationSize);
		
		newPopulation.clear();
		newPopulation.addAllPrograms(survivedPrograms);
		population = newPopulation;
	}

	/**
	 * generates one offspring
	 * 
	 * @return
	 */
	private Program generateOffspring() {
		List<Program> crossoverParents = selectionCrossoverOp.select(population, 2);
		Program offspring = null;
		try {
			if(crossoverParents != null && crossoverParents.size() > 1) {
				offspring = crossoverOp.generateOffsrping(crossoverParents.get(0), crossoverParents.get(1));
				offspring = applyMutations(offspring); 
			}
		} catch (InvalidProgramException e1) {
			e1.printStackTrace();
		}
		return offspring;
	}

	/**
	 * applies all mutations, each of them with the probability
	 * mutationProbability
	 * 
	 * @param offspring
	 * @return
	 * @throws InvalidProgramException
	 */
	private Program applyMutations(Program offspring) throws InvalidProgramException {
		int numberOfMutationOps = mutationsOps.size();
		for (Mutation m : mutationsOps) {
			if (m instanceof KineticRateAlterationForProgram1 || m instanceof InsertionForProgram1 || 
					m instanceof DeletionForProgram1 ||
					randomizer.nextDouble() < mutationProbability) {
				offspring = m.mutate(offspring);
			}
		}
		return offspring;
	}

	@Override
	public void run(Population initialPopulation) {
		population = initialPopulation;
		new Thread(this).start();
	}

	@Override
	public Population getPopulation() {
		return population;
	}

	@Override
	public Program getBestProgram() {
		return population.getBestProgram();
	}

	private void setSpecificMutationsFields() {
		for (Mutation mutation : mutationsOps) {
			if (mutation instanceof SpecieReplaceMutationForProgram1) {
				((SpecieReplaceMutationForProgram1) mutation).setRuleRepository(ruleRepository);
			} else if (mutation instanceof InsertionForProgram1) {
				((InsertionForProgram1) mutation).setRuleRepository(ruleRepository);
			}
		}
	}

	@Override
	public void reset() {
		initializePopulation();
	}
	
	public void countPenalizedPrograms(Integer extra, Integer missing){
		for(Program program: selectionCrossoverOp.select(population, populationSize/2)){
			if(program.getPenaltyExtra() > 0.0)
				extra++;
			if(program.getPenaltyMissing() > 0.0)
				missing++;
		}
	}
}
