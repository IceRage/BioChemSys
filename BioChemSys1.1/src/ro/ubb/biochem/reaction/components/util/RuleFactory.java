package ro.ubb.biochem.reaction.components.util;

import java.util.ArrayList;
import java.util.List;

import ro.ubb.biochem.reaction.components.Rule;
import ro.ubb.biochem.species.components.Specie;

/**
 * Class for creating instances of the Rule class
 * (Implementing the Factory design pattern)
 */
public class RuleFactory {

	private static final String NULL_ELEMENTS = "The provided array of elements is null.";
	private static final String NULL_REACTION = "The provided reaction is null.";
	private static final String RHS_SEPARATOR = ",";
	private static final String LHS_SEPARATOR = "\\+";
	private static final String REACTION_SEPARATOR = "->";
	private static RuleFactory instance = null;
	
	public static RuleFactory getInstance() {
		if (instance == null) {
			instance = new RuleFactory();
		}
		
		return instance;
	}

	/**
	 * Create the rule corresponding to the reaction given as a string having one of the following formats:
	 * 	1. A->B,C
	 *  2. A->B
	 *  3. A+B->C
	 *  4. A+B->C,D
	 *  
	 * @param reaction The reaction given as a string
	 * @return The rule corresponding to the given reaction
	 */
	public Rule createRuleFromString(String reaction) {
		if (reaction == null) {
			throw new NullPointerException(NULL_REACTION);
		}
		
		String[] sides = reaction.split(REACTION_SEPARATOR);
		String[] leftHandSideElements = sides[0].split(LHS_SEPARATOR);
		String[] rightHandSideElements = sides[1].split(RHS_SEPARATOR);
		
		List<Specie> leftHandSideSpecies = getSpeciesFromStrings(leftHandSideElements);
		List<Specie> rightHandSideSpecies = getSpeciesFromStrings(rightHandSideElements);
		
		return new Rule(leftHandSideSpecies, rightHandSideSpecies);
	}
	
	private RuleFactory() {
		// Do nothing
	}
	
	/**
	 * Get the species corresponding to the given array of strings
	 * 
	 * @param elements Array of elements represented as strings
	 * @return List of species
	 */
	private List<Specie> getSpeciesFromStrings(String[] elements) {
		if (elements == null) {
			throw new NullPointerException(NULL_ELEMENTS);
		}
		
		List<Specie> species = new ArrayList<Specie>();
		
		for (String tmpElement : elements) {
			species.add(new Specie(tmpElement));
		}
		
		return species;
	}
	
}
