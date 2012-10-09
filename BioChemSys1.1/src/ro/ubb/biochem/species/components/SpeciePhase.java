package ro.ubb.biochem.species.components;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SpeciePhase implements SpeciePool {
	
	private Map<Specie, Double> specieConcentrationsPhase;
	
	public SpeciePhase() {
		specieConcentrationsPhase = new LinkedHashMap<Specie, Double>();
	}
	
	@Override
	public List<Specie> getSpecies() {
		return new ArrayList<Specie>(specieConcentrationsPhase.keySet());
	}

	@Override
	public boolean containsSpecie(Specie specie) {
		return specieConcentrationsPhase.keySet().contains(specie);
	}
	
	@Override
	public Double getSpecieConcentration(Specie specie) {
		return specieConcentrationsPhase.get(specie);
	}

	@Override
	public void addConcentration(Specie specie, Double concentration) {
		specieConcentrationsPhase.put(specie, concentration);
	}
	
	public String toString(){
		String toS = "";
		System.out.println(getSpecies());
		for(Specie s: getSpecies()){
			if(!toS.equals("")) toS+="|";
			toS += getSpecieConcentration(s).toString();
		}
		return toS;
	}

}
