package ro.ubb.biochem.program.elements;

import ro.ubb.biochem.exceptions.InvalidInputException;
import ro.ubb.biochem.species.components.Specie;
import ro.ubb.biochem.species.components.SpeciePool;
import ro.ubb.biochem.species.components.SpeciePoolEvolution;

/**
 * Minimizing differences between actual and target results averaged over time steps
 * + minimizing the number of reactions in each individual 
 */
public class FitnessFunctionImpl implements FitnessFunction {

	private static final double NR_OF_REACTIONS_RATIO = 0.5;
	private static final double BEHAVIOUR_RELEVANCE = 0.5;
	private static final Double MAX_KINETIC_RATE_ADJUSTMENT = 50.0;

	private SpeciePoolEvolution speciePoolEvolution;
	private int maxNumberOfSpecies;

	
	public FitnessFunctionImpl(SpeciePoolEvolution speciePoolEvolution) {
		this.setSpeciePoolEvolution(speciePoolEvolution);
		
		int numberOfSpecies = speciePoolEvolution.getNumberOfSpecies();
		maxNumberOfSpecies = (numberOfSpecies * numberOfSpecies) * (numberOfSpecies - 1);
	}

	@Override
	public synchronized double computeFitness(Program program) {
		Double fitness = 0.0;
		SpeciePool inputPhase = speciePoolEvolution.getInitialPhase();

		for (int i = 1; i < speciePoolEvolution.getNumberOfPhases(); i++) {
			try {
				SpeciePool outputPhase = program.run(inputPhase, speciePoolEvolution.getTime(i)
						- speciePoolEvolution.getTime(i - 1));
				fitness += computeDifferences(outputPhase, speciePoolEvolution.getPhase(i));
				inputPhase = outputPhase;
			} catch (InvalidInputException ex) {
				ex.printStackTrace();
			}
		}
		
		fitness = BEHAVIOUR_RELEVANCE * fitness + NR_OF_REACTIONS_RATIO * changeToSubunitary(program.getReactionNo());
		fitness = fitness/speciePoolEvolution.getNumberOfPhases();

		program.setMaxKineticRateStep(fitness
				/ (speciePoolEvolution.getNumberOfPhases() * speciePoolEvolution.getNumberOfSpecies())
				* MAX_KINETIC_RATE_ADJUSTMENT);
		program.setFitness(fitness);

		return fitness;
	}

	protected Double computeDifferences(SpeciePool actualResult, SpeciePool targetResult) {
		Double difference = 0.0;
		for (Specie s : targetResult.getSpecies()) {
			difference += Math.abs(targetResult.getSpecieConcentration(s)
					- actualResult.getSpecieConcentration(s));
		}
		return difference;
	}

	public void setSpeciePoolEvolution(SpeciePoolEvolution speciePoolEvolution) {
		this.speciePoolEvolution = speciePoolEvolution;
	}

	public SpeciePoolEvolution getSpeciePoolEvolution() {
		return speciePoolEvolution;
	}

	/**
	 * Convert the given integer to the interval [0,1]
	 * 
	 * @param supraUnitaryNumber Input number
	 * @return Non-negative subunitary corresponding number
	 */
	private double changeToSubunitary(int supraUnitaryNumber) {
		return (supraUnitaryNumber/maxNumberOfSpecies);
	}
	
}
