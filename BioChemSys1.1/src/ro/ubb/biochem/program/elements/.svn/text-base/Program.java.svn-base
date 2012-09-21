package ro.ubb.biochem.program.elements;

import java.util.List;

import ro.ubb.biochem.exceptions.InvalidInputException;
import ro.ubb.biochem.reaction.components.Reaction;
import ro.ubb.biochem.species.components.SpeciePool;


public interface Program  {
	
	public SpeciePool run(SpeciePool input, Integer time) throws InvalidInputException;
	
	public Program clone();
	
	public int getReactionNo();
	
	public List<Reaction> getReactions();

	public Double getMaxKineticRateStep();
	
	public void setMaxKineticRateStep(Double maxKineticRateStep);
	
	public Double getFitness();

	public void setFitness(Double fitness);
	
	public Double getPenaltyExtra();

	public void setPenaltyExtra(Double penaltyExtra);

	public Double getPenaltyMissing();

	public void setPenaltyMissing(Double penaltyMissing);
	
}
