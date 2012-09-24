package ro.ubb.biochem.reaction.components;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import ro.ubb.biochem.species.components.Specie;

public class RuleRepositoryImpl implements RuleRepository {

	private static final String MSG_LIST_OF_RULES = "The list of rules is:";
	private static final String MSG_EMPTY_LIST_OF_RULES = "There are no rules present in the repository.";
	
	private List<Rule> rules;

	public RuleRepositoryImpl() {
		this.rules = new ArrayList<Rule>();
	}

	@Override
	public void addRule(Rule rule) {
		if(!rules.contains(rule))
			rules.add(rule);
	}

	@Override
	public List<Rule> getRules() {
		return rules;
	}

	@Override
	public List<Rule> getRulesInvolvingSpecie(Specie specie) {
		List<Rule> affectingRules = new ArrayList<Rule>();
		for (Rule rule : rules) {
			if (rule.getLhs().contains(specie))
				affectingRules.add(rule);
			if (rule.getRhs().contains(specie)) {
				affectingRules.add(rule);
			}
		}
		return affectingRules;
	}

	@Override
	public List<Rule> getRulesForRhs(List<Specie> species) {
		List<Rule> affectingRHSRules = new ArrayList<Rule>();
		for (Rule rule : rules) {
			for (Specie specie : species) {
				if (!rule.getRhs().contains(specie)) {
					break;
				}
				affectingRHSRules.add(rule);
			}
		}
		return affectingRHSRules;
	}

	@Override
	public List<Rule> getRulesForLhs(List<Specie> species) {
		List<Rule> affectingLHSRules = new ArrayList<Rule>();
		for (Rule rule : rules) {
			for (Specie specie : species) {
				if (!rule.getLhs().contains(specie)) {
					break;
				}
				affectingLHSRules.add(rule);
			}
		}
		return affectingLHSRules;
	}

	@Override
	public Double getNumberOfRules() {
		return (double) rules.size();
	}

	@Override
	public List<Rule> getRandomSetOfRules(Integer requiredSetSize) {
		if (requiredSetSize > rules.size()) {
			requiredSetSize = rules.size();
		}
		List<Rule> resultSet = new ArrayList<Rule>();
		Random randomGenerator = new Random();
		while (resultSet.size() < requiredSetSize) {
			Rule newRule = rules.get(randomGenerator.nextInt(rules.size()));
			if (!resultSet.contains(newRule)) {
				resultSet.add(newRule);
			}
		}
		return resultSet;
	}

	@Override
	public Rule getSimmilarRule(Rule rule) {
		List<Rule> possibleResults = new ArrayList<Rule>();
		for (Rule r : rules) {
			if (r.isSimmilar(rule)) {
				possibleResults.add(r);
			}
		}
		if (possibleResults.isEmpty()) {
			return rule;
		} else {
			return possibleResults.get((new Random()).nextInt(possibleResults.size()));
		}
	}

	@Override
	public Rule getRandomRule() {
		return rules.get((new Random()).nextInt(rules.size()));
	}

	@Override
	public List<Specie> getSpecies() {
		Set<Specie> specieSet = new HashSet<Specie>();
		for (Rule r : rules) {
			specieSet.addAll(r.getLhs());
			specieSet.addAll(r.getRhs());
		}
		List<Specie> specieList = new ArrayList<Specie>();
		specieList.addAll(specieSet);
		return specieList;
	}

	@Override
	public String toString() {
		String r = "";
		for(Rule ru: rules)
			r += "\n" + ru.toString();
		
		return r;
	}
	
	/**
	 * Print the list of rules on the console
	 * Precondition: List of rules is not null/empty
	 * Postcondition: -
	 */
	public void printRules() {
		if ((rules != null) && (rules.size() > 0)) {
			System.out.println(MSG_LIST_OF_RULES);
			
			for (Rule rule : rules) {
				System.out.println(rule);
			}
		} else {
			System.out.println(MSG_EMPTY_LIST_OF_RULES);
		}
	}

}
