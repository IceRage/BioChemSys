package ro.ubb.biochem.species.components;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class SpeciePoolEvolution {

	private static final Double GEN_SPECIE_CONCENTRATION = 0.0d;
	
	private List<SpeciePool> phases;			// The collection of all (specie, concentration, time) tuples
	private Set<Specie> speciesSet; 			// The set of species for which the concentrations are known
	private Set<Specie> generatedSpeciesSet;	// The set of species which were obtained after generating all possible reactions using the given templates
	private List<Integer> times; 				// Time measured in seconds
	
	public SpeciePoolEvolution(){
		phases = new ArrayList<SpeciePool>();
		speciesSet = new HashSet<Specie>();
		generatedSpeciesSet = new HashSet<Specie>();
		times = new ArrayList<Integer>();
	}
	
	public Set<Specie> getSpecieSet() {
		return speciesSet;
	}

	public void addGeneratedSpecie(Specie specie) {
		if (specie != null) {
			generatedSpeciesSet.add(specie);
		}
	}
	
	public SpeciePoolEvolution(SpeciePool initialPhase) {
		this();
		addPhase(0, initialPhase);
	}
	
	public void addPhase(Integer time, SpeciePool nextPool) {
		for (Specie sp : nextPool.getSpecies()) {
			speciesSet.add(sp);
		}
		phases.add(nextPool);
		times.add(time);
	}
	
	public Integer getNumberOfPhases() {
		return phases.size();
	}
	
	public Integer getTime(Integer index) {
		return times.get(index);
	}
	
	public SpeciePool getPhase(Integer index) {
		return phases.get(index);
	}
	
	public SpeciePool getInitialPhase() {
		SpeciePool initialPhase = phases.get(0);
		
		addGeneratedSpeciesToInitialPhase(initialPhase);
		
		return initialPhase;
	}

	public Integer getNumberOfSpecies() {
		return speciesSet.size();
	}
	
	/**
	 * Add (specie, concentration) pairs to the given SpeciePool, where
	 * 		specie - One of the species from generatedSpeciesSet
	 * 		concentration - The concentration of the specie is zero
	 * 
	 * @param initialPhase The initial SpeciePool i.e. the collection of (specie, concentration)
	 *                     pairs, where concentration = initial concentration of the specie
	 */
	private void addGeneratedSpeciesToInitialPhase(SpeciePool initialPhase) {
		for (Specie generatedSpecie : generatedSpeciesSet) {
			initialPhase.addConcentration(generatedSpecie, GEN_SPECIE_CONCENTRATION);
		}
	}
	
}
