package ro.ubb.biochem.program.elements.model;

import java.util.List;

public class ExpressionValue {

	private double kineticRate;
	private List<Integer> speciesIndexes;
	private double value;
	
	public ExpressionValue(double kineticRate, List<Integer> speciesIndexes) {
		this.kineticRate = kineticRate;
		
		if (speciesIndexes != null) {
			this.speciesIndexes = speciesIndexes;
		}
	}

	public double getKineticRate() {
		return kineticRate;
	}

	public List<Integer> getSpeciesIndexes() {
		return speciesIndexes;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
}
