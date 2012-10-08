package ro.ubb.biochem.program.elements;

import java.util.ArrayList;
import java.util.List;

import ro.ubb.biochem.exceptions.InvalidInputException;
import ro.ubb.biochem.reaction.components.Reaction;
import ro.ubb.biochem.reaction.components.Rule;
import ro.ubb.biochem.species.components.Specie;
import ro.ubb.biochem.species.components.SpeciePhase;
import ro.ubb.biochem.species.components.SpeciePool;
import flanagan.integration.DerivnFunction;
import flanagan.integration.RungeKutta;

public class ProgramImpl implements Program {

	private List<Reaction> reactions;
	private Double maxKineticRateStep;
	private Double fitness;
	private Double penaltyExtra;
	private Double penaltyMissing;

	public Double getFitness() {
		return fitness;
	}

	public void setFitness(Double fitness) {
		this.fitness = fitness;
	}


	public ProgramImpl() {
		reactions = new ArrayList<Reaction>();
		maxKineticRateStep = 1.0;
	}

	public ProgramImpl(List<Reaction> reactions) {
		this.reactions = reactions;
		maxKineticRateStep = 1.0;
	}

	public void addReaction(Reaction reaction) {
		if (!reactions.contains(reaction)) {
			reactions.add(reaction);
		}
	}

	public void removeReaction(Reaction reaction) {
		reactions.remove(reaction);
	}

	public List<Reaction> getReactions() {
		return reactions;
	}

	@Override
	public SpeciePool run(SpeciePool input, Integer time) throws InvalidInputException {
		SystemOfDerivatives systemOfDerivatives = new SystemOfDerivatives(input);

		int nrOfEquations = input.getSpecies().size();
		double h = 0.1;
		double t0 = 0;
		double tn = time;
		double[] m0 = new double[nrOfEquations];
		double[] mn = new double[nrOfEquations];
		m0 = extractPreviousConcentrations(m0, input);

		RungeKutta rk = new RungeKutta();
		rk.setInitialValueOfX(t0);
		rk.setFinalValueOfX(tn);
		rk.setInitialValuesOfY(m0);
		rk.setStepSize(h);
		mn = rk.fourthOrder(systemOfDerivatives);
		
		return createCorrespondingSpeciePool(mn, input);
	}

	/**
	 * Create a new specie phase with the concentrations at the next step
	 */
	private SpeciePool createCorrespondingSpeciePool(double[] mn, SpeciePool input) {
		SpeciePool speciePhase = new SpeciePhase();
		List<Specie> species = input.getSpecies();
		for (int i = 0; i < species.size(); i++) {
			speciePhase.addConcentration(species.get(i), mn[i]);
		}
		return speciePhase;
	}

	/**
	 * Extract concentrations at the previos step
	 */
	private double[] extractPreviousConcentrations(double[] m0, SpeciePool input) {
		List<Specie> species = input.getSpecies();
		
		for (int i = 0; i < species.size(); i++) {
			m0[i] = input.getSpecieConcentration(species.get(i));
		}
		
		return m0;
	}

	public ProgramImpl clone() {
		List<Reaction> reactionsCopy = new ArrayList<Reaction>();
		for (Reaction r : reactions) {
			reactionsCopy.add(r.clone());
		}
		ProgramImpl newProgram = new ProgramImpl(reactionsCopy);
		newProgram.setMaxKineticRateStep(maxKineticRateStep);
		return newProgram;
	}

	public boolean equals(Object obj) {
		if (obj instanceof ProgramImpl) {
			List<Reaction> otherProgramReactions = ((ProgramImpl) obj).getReactions();
			for (Reaction r : otherProgramReactions) {
				if (!this.reactions.contains(r)) {
					return false;
				}
			}
			for (Reaction r : this.reactions) {
				if (!otherProgramReactions.contains(r)) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Class to hold the system of differential equations for the biological network
	 * 
	 * Class implements interface which provides the abstract method
	 * through which a set of ODEs may be coded and supplied to the class RungeKutta
	 */
	class SystemOfDerivatives implements DerivnFunction {

		private SpeciePool input;

		public SystemOfDerivatives(SpeciePool input) {
			this.input = input;
		}

		/**
		 * Returns a vector which contains the values of the derivatives of each place with 
		 * respect to time (i.e. d(place_i) / d(time), for all places "i")
		 * 
		 * In the bio-chemical context this vector actually represents
		 * the derivative of each of the species with respect to time
		 * 
		 * In order to compute these value, the law of "Mass Action Kinetics" is considered.
		 * 
		 * @param time The value of the time variable
		 * @param currentMarking The current marking of the Petri net
		 */
		@Override
		public double[] derivn(double time, double[] currentMarking) {

			double[] derivativePlaceOverDerivativeTime = new double[input.getSpecies().size()];
			List<Specie> species = input.getSpecies();

			for (int i = 0; i < species.size(); i++) {
				Specie specie = species.get(i);
				
				for (Reaction reaction : reactions) {
					Rule currentRule = reaction.getPattern();
					
					if (currentRule.getLhs().contains(specie)) {
						Double negativeTerm = -1.0;
						
						for (Specie currentRuleSpecie : currentRule.getLhs()) {
							negativeTerm *= currentMarking[species.indexOf(currentRuleSpecie)];
						}
						
						derivativePlaceOverDerivativeTime[i] += negativeTerm * reaction.getKineticRate();
					} else if (currentRule.getRhs().contains(specie)) {
						Double positiveTerm = 1.0;
						
						for (Specie currentRuleSpecie : currentRule.getLhs()) {
							positiveTerm *= currentMarking[species.indexOf(currentRuleSpecie)];
						}
						
						derivativePlaceOverDerivativeTime[i] += positiveTerm * reaction.getKineticRate();
					}
				}
			}
			
			return derivativePlaceOverDerivativeTime;
			
		}
	}

	@Override
	public int getReactionNo() {
		return reactions.size();
	}
	
	public String toString() {
		return reactions.toString();
	}
	
	public void setMaxKineticRateStep(Double maxKineticRateStep) {
		this.maxKineticRateStep = Math.min(maxKineticRateStep, 1);
	}

	@Override
	public Double getMaxKineticRateStep() {
		return maxKineticRateStep;
	}

	public Double getPenaltyExtra() {
		return penaltyExtra;
	}

	public void setPenaltyExtra(Double penaltyExtra) {
		this.penaltyExtra = penaltyExtra;
	}

	public Double getPenaltyMissing() {
		return penaltyMissing;
	}

	public void setPenaltyMissing(Double penaltyMissing) {
		this.penaltyMissing = penaltyMissing;
	}
}
