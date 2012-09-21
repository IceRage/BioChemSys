package ro.ubb.biochem.reaction.components;

import java.util.Random;


public class Reaction {

	private Rule pattern;
	private Double kineticRate;
	
	public Reaction(Rule rule) {
		this.pattern = rule;
		this.kineticRate = (new Random()).nextDouble();
	}
	
	public Reaction(Rule rule, Double kineticRate) {
		this.pattern = rule;
		this.kineticRate = kineticRate;
	}
	
	public Rule getPattern() {
		return pattern;
	}
	
	public Double getKineticRate() {
		return kineticRate;
	}
	
	public void updateKineticRate(Double kineticRate) {
		this.kineticRate = kineticRate;
	}
	
	public Reaction clone() {
		return new Reaction(pattern, kineticRate);
	}

	public void changePattern(Rule newPattern) {
		this.pattern = newPattern;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Reaction) {
			return (((Reaction) obj).getPattern().equals(this.pattern));
		}
		else {
			return false;
		}
	}
	
	public int hashCode() {
		return pattern.hashCode();
	}
	
	public String toString() {
		return pattern.toString() + " (" + kineticRate + ")";
	}
}
