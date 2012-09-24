package ro.ubb.biochem.reaction.components;

import java.util.List;

import ro.ubb.biochem.species.components.Specie;

public interface RuleRepository {

	public void addRule(Rule rule);
	
	public List<Rule> getRules();
	
	public List<Rule> getRulesInvolvingSpecie(Specie specie);
	
	public List<Rule> getRulesForRhs(List<Specie> species);
	
	public List<Rule> getRulesForLhs(List<Specie> species);

	public Double getNumberOfRules();
	
	public List<Rule> getRandomSetOfRules(Integer requiredSetSize);

	public Rule getSimmilarRule(Rule rule);
	
	public Rule getRandomRule();

	public List<Specie> getSpecies();

}
