package ro.ubb.biochem.species.components;

public class Specie implements Comparable<Specie> {

	private String name;
	
	public Specie(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Specie other = (Specie) obj;
		
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		
		return true;
	}

	@Override
	public int compareTo(Specie specie) {
		return this.name.compareTo(specie.name);
	}
	
}
