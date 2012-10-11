package ro.ubb.biochem.program.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import ro.ubb.biochem.exceptions.InvalidInputException;
import ro.ubb.biochem.program.elements.model.ExpressionValue;
import ro.ubb.biochem.reaction.components.Reaction;
import ro.ubb.biochem.species.components.Specie;
import ro.ubb.biochem.species.components.SpeciePhase;
import ro.ubb.biochem.species.components.SpeciePool;
import flanagan.integration.DerivnFunction;
import flanagan.integration.RungeKutta;

public class ProgramImpl implements Program {

	private List<Reaction> reactions;
	private List<ExpressionValue> expressionValuesTable;
	private Map<Specie, List<Integer>> specieExpressionsTable;

	private Double maxKineticRateStep;
	private Double fitness;


	public ProgramImpl() {
		reactions = new ArrayList<Reaction>();
		expressionValuesTable = new ArrayList<ExpressionValue>();
		specieExpressionsTable = new HashMap<Specie, List<Integer>>();
		maxKineticRateStep = 1.0;
	}

	public ProgramImpl(List<Reaction> reactions) {
		this.reactions = reactions;
		expressionValuesTable = new ArrayList<ExpressionValue>();
		specieExpressionsTable = new HashMap<Specie, List<Integer>>();
		maxKineticRateStep = 1.0;
	}

	public Double getFitness() {
		return fitness;
	}

	public void setFitness(Double fitness) {
		this.fitness = fitness;
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
		constructTablesFromPool(input);

		SystemOfDerivatives systemOfDerivatives = new SystemOfDerivatives(input, null, null);

		int nrOfEquations = input.getNumberOfSpecies();
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
	 * Construct the tables from the given input SpeciePool
	 * 
	 * The expressionValueTable will contain tuples of the form (expression, value),
	 * 		where value represents the computed value of the expression
	 * The specieExpressionTable will contain tuples of the form (specie, expression_index),
	 * 		such that the value of the specie can be computed based on the
	 *      value of the expressions which were computed beforehand
	 */
	private void constructTablesFromPool(SpeciePool input) {
		List<Specie> species = input.getSpecies();

		initializeSpecieExpressionsTable(species);
		constructTables(species);
	}

	private void constructTables(List<Specie> species) {
		for (Reaction reaction : reactions) {
			int indexOfExpression = insertExpressionValueInTable(species, reaction);
			
			// Add the expression value for the left hand side species
			List<Specie> lhsSpecies = reaction.getPattern().getLhs();

			for (Specie specie : lhsSpecies) {
				specieExpressionsTable.get(specie).add(-indexOfExpression);
			}
			
			// Add the expression value for the right hand side species
			List<Specie> rhsSpecies = reaction.getPattern().getRhs();
			
			for (Specie specie : rhsSpecies) {
				specieExpressionsTable.get(specie).add(indexOfExpression);
			}
		}
	}

	/**
	 * Insert the expression value in the table and return its index
	 * 
	 * @param species List of all species
	 * @param reaction Reaction on which the expression is being built
	 */
	private int insertExpressionValueInTable(List<Specie> species, Reaction reaction) {
		ExpressionValue expressionValue = getExpressionValueFromReaction(reaction, species);

		expressionValuesTable.add(expressionValue);
		
		return (expressionValuesTable.size() - 1);
	}

	/**
	 * Construct the expression value by constructing a (kinetic_rate, specie_indexes) tuple, where
	 * 		- kinetic_rate represents the kinetic rate of the reaction
	 * 		- specie_indexes represents a list of indexes such that for each specie from
	 *                       the reaction we obtain its index in the input list of species
	 *                       and add the index to the list
	 *
	 * @param reaction The input reaction
	 * @param species The input list of species
	 * @return
	 */
	private ExpressionValue getExpressionValueFromReaction(Reaction reaction, List<Specie> species) {
		double kineticRate = reaction.getKineticRate();
		List<Integer> speciesIndexes = new ArrayList<Integer>();

		List<Specie> speciesFromLHS = reaction.getPattern().getLhs();

		for (Specie specieFromLHS : speciesFromLHS) {
			speciesIndexes.add(species.indexOf(specieFromLHS));
		}

		return new ExpressionValue(kineticRate, speciesIndexes);
	}

	/**
	 * Add an entry in the map for each specie and associate to it
	 * an instantiated, empty list
	 * 
	 * @param species List of species
	 */
	private void initializeSpecieExpressionsTable(List<Specie> species) {
		for (Specie specie : species)
			specieExpressionsTable.put(specie, new ArrayList<Integer>());
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

		
		public SystemOfDerivatives(SpeciePool input, List<ExpressionValue> expressionValueTable, SortedMap<Specie, List<Integer>> specieExpressionTable) {
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
			
			updateExpressionValuesTable(currentMarking);
			
			double[] derivativePlaceOverDerivativeTime = new double[input.getSpecies().size()];
			List<Specie> species = input.getSpecies();

			for (int i = 0; i < species.size(); i++) {
				Specie specie = species.get(i);

				List<Integer> expressionsForSpecie = specieExpressionsTable.get(specie);
				
				/**
				 * Convention:
				 * 
				 * expressionIndex > 0, if the value must be added
				 * expressionIndex < 0, if the value must be subtracted
				 */
				for (Integer expressionIndex : expressionsForSpecie) {
					ExpressionValue expressionValue = expressionValuesTable.get(Math.abs(expressionIndex));
					
					if (expressionIndex > 0) {
						derivativePlaceOverDerivativeTime[i] += expressionValue.getValue();
					} else {
						derivativePlaceOverDerivativeTime[i] -= expressionValue.getValue();
					}
				}
			}

			return derivativePlaceOverDerivativeTime;

		}

		/**
		 * Update the values in the expressionValuesTable using the current marking
		 * 
		 * @param currentMarking Current marking of the network
		 */
		private void updateExpressionValuesTable(double[] currentMarking) {
			
			for (int i=0; i<expressionValuesTable.size(); i++) {
				ExpressionValue expressionValue = expressionValuesTable.get(i);
				List<Integer> speciesIndexes = expressionValue.getSpeciesIndexes();
				
				double value = expressionValue.getKineticRate();
				
				for (Integer specieIndex : speciesIndexes)
					value *= currentMarking[specieIndex];
				
				expressionValue.setValue(value);
			}
			
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

}
