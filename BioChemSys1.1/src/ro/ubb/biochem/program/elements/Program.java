package ro.ubb.biochem.program.elements;

import java.util.List;

import ro.ubb.biochem.exceptions.InvalidInputException;
import ro.ubb.biochem.reaction.components.Reaction;
import ro.ubb.biochem.species.components.SpeciePool;

/**
 * An instance of a class which implements this interface corresponds to
 * a chromosome/individual in the GP algorithm population
 */
public interface Program  {
	
	public SpeciePool run(SpeciePool input, Integer time) throws InvalidInputException;
	
	public Program clone();
	
	public int getReactionNo();
	
	public List<Reaction> getReactions();

	public Double getMaxKineticRateStep();
	
	public void setMaxKineticRateStep(Double maxKineticRateStep);
	
	public Double getFitness();

	public void setFitness(Double fitness);
	
}
