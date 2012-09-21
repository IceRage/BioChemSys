package ro.ubb.biochem.species.components;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class SpeciesInput implements SpeciePool{
	
	private List<Specie> species;
	private LinkedHashMap<Specie, List<Double>> concentrations;
	
	public SpeciesInput(){
		this.species = new ArrayList<Specie>();
		this.concentrations = new LinkedHashMap<Specie, List<Double>>();
	}
	
	public void addSpecies(Specie s){
		species.add(s);
	}
	
	public void addConcentrations(Specie specie, List<Double> concentrations){
		this.concentrations.put(specie, concentrations);
	}

	public List<Specie> getSpecies() {
		return species;
	}

	public void setSpecies(List<Specie> species) {
		this.species = species;
	}

	public LinkedHashMap<Specie, List<Double>> getConcentrations() {
		return concentrations;
	}

	public void setConcentrations(LinkedHashMap<Specie, List<Double>> concentrations) {
		this.concentrations = concentrations;
	}

	@Override
	public Double getSpecieConcentration(Specie specie) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addConcentration(Specie specie, Double concentration) {
		throw new UnsupportedOperationException();
	}
	

}
