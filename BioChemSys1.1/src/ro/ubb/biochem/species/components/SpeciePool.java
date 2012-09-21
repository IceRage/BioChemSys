package ro.ubb.biochem.species.components;

import java.util.List;


public interface SpeciePool {

	public List<Specie> getSpecies();
	
	public Double getSpecieConcentration(Specie specie);
	
	public void addConcentration(Specie specie, Double concentration);
}
