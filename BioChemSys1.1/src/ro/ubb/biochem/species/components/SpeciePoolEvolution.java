package ro.ubb.biochem.species.components;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class SpeciePoolEvolution {

	private List<SpeciePool> phases;
	private Set<Specie> specieSet;
	private List<Integer> times; // seconds
	
	public SpeciePoolEvolution(){
		phases = new ArrayList<SpeciePool>();
		specieSet = new HashSet<Specie>();
		times = new ArrayList<Integer>();
	}
	
	public Set<Specie> getSpecieSet() {
		return specieSet;
	}

	public SpeciePoolEvolution(SpeciePool initialPhase) {
		this();
		addPhase(0, initialPhase);
	}
	
	public void addPhase(Integer time, SpeciePool nextPool) {
		for (Specie sp : nextPool.getSpecies()) {
			specieSet.add(sp);
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

	public Integer getNumberOfSpecies() {
		return specieSet.size();
	}
	
	
}
