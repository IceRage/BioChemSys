package ro.ubb.biochem.program.elements;

import ro.ubb.biochem.exceptions.InvalidInputException;
import ro.ubb.biochem.species.components.Specie;
import ro.ubb.biochem.species.components.SpeciePool;
import ro.ubb.biochem.species.components.SpeciePoolEvolution;

/**
 * Sum of differences between actual and target results averaged over timesteps
 * + penalty function
 * 
 * 
 */
public class FitnessFunctionImpl implements FitnessFunction {

	private static final Double PENALTY_FOR_MISSING_SPECIE = 0.1;
	private static final Double PENALTY_FOR_EXTRA_SPECIE = 0.1;
	private static final Double MAX_KINETIC_RATE_ADJUSTMENT = 50.0;

	private SpeciePoolEvolution speciePoolEvolution;

	public FitnessFunctionImpl(SpeciePoolEvolution speciePoolEvolution) {
		this.setSpeciePoolEvolution(speciePoolEvolution);
	}

	// TODO: Remove source code for displaying elapsed time
	@Override
	public synchronized double computeFitness(Program program) {
		Double fitness = 0.0;
		Double penalty = 0.0;
		Double penaltyExtra = 0.0;
		Double penaltyMissing = 0.0;
		SpeciePool inputPhase = speciePoolEvolution.getInitialPhase();

		long startTime = System.nanoTime();
		for (int i = 1; i < speciePoolEvolution.getNumberOfPhases(); i++) {
			try {
				SpeciePool outputPhase = program.run(inputPhase, speciePoolEvolution.getTime(i)
						- speciePoolEvolution.getTime(i - 1));
				fitness += computeDifferences(outputPhase, speciePoolEvolution.getPhase(i));
				penaltyExtra += computePenaltyExtra(outputPhase, speciePoolEvolution.getPhase(i));
				penaltyMissing += computePentaltyMissing(outputPhase, speciePoolEvolution.getPhase(i));
				inputPhase = outputPhase;
			} catch (InvalidInputException ex) {
				ex.printStackTrace();
			}
		}
		
		penalty = penaltyExtra + penaltyMissing;
		fitness += penalty;
		fitness = fitness/speciePoolEvolution.getNumberOfPhases();

		program.setMaxKineticRateStep(fitness
				/ (speciePoolEvolution.getNumberOfPhases() * speciePoolEvolution.getNumberOfSpecies())
				* MAX_KINETIC_RATE_ADJUSTMENT);
		program.setFitness(fitness);
		program.setPenaltyExtra(penaltyExtra);
		program.setPenaltyMissing(penaltyMissing);
		
		double elapsedTime = (System.nanoTime() - startTime) * 1e-9;
		System.out.println("Compute fitness time: " + elapsedTime);

		return fitness;
	}

	protected Double computePentaltyMissing(SpeciePool actualResult, SpeciePool targetResult){
		Double penalty = 0.0;
		for (Specie s : targetResult.getSpecies()) {
			if (targetResult.getSpecieConcentration(s) > 0) {
				if (actualResult.getSpecieConcentration(s) == 0) {
					penalty += PENALTY_FOR_MISSING_SPECIE;
				}
			}
		}

		return penalty;
	}

	protected Double computePenaltyExtra(SpeciePool actualResult, SpeciePool targetResult) {
		Double penalty = 0.0;

		for (Specie s : actualResult.getSpecies()) {
			if (actualResult.getSpecieConcentration(s) > 0) {
				
				// Consider only the species for which the concentration is known
				// when computing the penalty for the current solution
				if (isKnownTargetResultForSpecie(s, targetResult)) {
					if (targetResult.getSpecieConcentration(s) == 0) {
						penalty += PENALTY_FOR_EXTRA_SPECIE;
					}
				}
				
			}
		}
		return penalty;
	}

	/**
	 * Check if the target results contain information for the given specie
	 * 
	 * @param specie The input specie
	 * @param targetResult The wanted behaviour/target result
	 * @return True, if the target behaviour of the specie is known
	 */
	private boolean isKnownTargetResultForSpecie(Specie specie, SpeciePool targetResult) {
		return targetResult.containsSpecie(specie);
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

}
