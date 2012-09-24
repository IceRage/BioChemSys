package ro.ubb.biochem.program.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		Derivative der = new Derivative(input);

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
		mn = rk.fourthOrder(der);
		
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
	 * Class to hold the system of differential equation for the bio-network
	 * 
	 * @author Silvia Rausanu
	 */
	class Derivative implements DerivnFunction {

		private SpeciePool input;

		public Derivative(SpeciePool input) {
			this.input = input;
		}

		@Override
		public double[] derivn(double t, double[] m) {

			double[] dmdt = new double[input.getSpecies().size()];
			List<Specie> species = input.getSpecies();
			Map<Specie, Integer> specieIndexMap = new HashMap<Specie, Integer>();
			for (int i = 0; i < species.size(); i++) {
				specieIndexMap.put(species.get(i), i);
			}
			for (int i = 0; i < species.size(); i++) {
				Specie s = species.get(i);
				for (Reaction r : reactions) {
					Rule currentRule = r.getPattern();
					if (currentRule.getLhs().contains(s)) {
						Double negativeTerm = -1.0;
						for (Specie sp : currentRule.getLhs()) {
							negativeTerm *= m[specieIndexMap.get(sp)];
						}
						dmdt[i] += negativeTerm * r.getKineticRate();
					} else if (currentRule.getRhs().contains(s)) {
						Double positiveTerm = 1.0;
						for (Specie sp : currentRule.getLhs()) {
							positiveTerm *= m[specieIndexMap.get(sp)];
						}
						dmdt[i] += positiveTerm * r.getKineticRate();
					}
				}
			}
			return dmdt;
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
