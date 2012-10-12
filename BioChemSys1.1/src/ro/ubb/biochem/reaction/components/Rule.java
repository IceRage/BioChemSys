package ro.ubb.biochem.reaction.components;

import java.util.List;

import ro.ubb.biochem.species.components.Specie;

public class Rule {

	private List<Specie> lhs;
	private List<Specie> rhs;

	public Rule(List<Specie> lhs, List<Specie> rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	public List<Specie> getLhs() {
		return lhs;
	}

	public void setLhs(List<Specie> lhs) {
		this.lhs = lhs;
	}

	public List<Specie> getRhs() {
		return rhs;
	}

	public void setRhs(List<Specie> rhs) {
		this.rhs = rhs;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Rule) {
			boolean equal = true;
			Rule otherRule = (Rule) obj;
			for (Specie s : this.lhs) {
				if (!otherRule.lhs.contains(s)) {
					return false;
				}
			}
			for (Specie s : this.rhs) {
				if (!otherRule.rhs.contains(s)) {
					return false;
				}
			}
			for (Specie s : otherRule.lhs) {
				if (!this.lhs.contains(s)) {
					return false;
				}
			}
			for (Specie s : otherRule.rhs) {
				if (!this.rhs.contains(s)) {
					return false;
				}
			}
			return equal;
		} else {
			return false;
		}
	}

	public boolean isSimmilar(Rule otherRule) {
		return differenceBetweenSpecieLists(this.lhs, otherRule.lhs) == 1
				&& differenceBetweenSpecieLists(this.rhs, otherRule.rhs) == 1;
	}
	
	public int hashCode() {
		int hashCode = 0;
		for (Specie s : lhs) {
			hashCode += s.hashCode();
		}
		for (Specie s : rhs) {
			hashCode -= s.hashCode();
		}
		return hashCode;
		
	}

	private Integer differenceBetweenSpecieLists(List<Specie> list1,
			List<Specie> list2) {
		Integer difference1  = 0;
		for (Specie s : list1) {
			if (!list2.contains(s)) {
				difference1++;
			}
		}
		Integer difference2 = 0;
		for (Specie s : list2) {
			if (!list1.contains(s)) {
				difference2++;
			}
		}
		return Math.max(difference1, difference2);
	}
	
	public String toString() {
		return lhs + " -> " + rhs;
	}
}
