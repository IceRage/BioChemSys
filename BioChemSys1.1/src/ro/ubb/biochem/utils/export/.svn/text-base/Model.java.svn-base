package ro.ubb.biochem.utils.export;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "model")
@XmlAccessorType(XmlAccessType.FIELD)
public class Model {
	
	@XmlAttribute
	private String id;
	

	
	@XmlElementWrapper(name="listOfSpecies")
	@XmlElement(name="species")
	private Species[] listOfSpecies;
	
	@XmlElementWrapper(name="listOfParameters")
	@XmlElement(name="parameter")
	private Parameter[] listOfParameters;
	
	@XmlElementWrapper(name="listOfReactions")
	@XmlElement(name="reaction")
	private Reaction[] listOfReactions;
	
	
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	
	
	public Species[] getListOfSpecies() {
		return listOfSpecies;
	}

	public void setListOfSpecies(Species[] listOfSpecies) {
		this.listOfSpecies = listOfSpecies;
	}

	
	public Parameter[] getListOfParameters() {
		return listOfParameters;
	}

	public void setListOfParameters(Parameter[] listOfParameters) {
		this.listOfParameters = listOfParameters;
	}


	public Reaction[] getListOfReactions() {
		return listOfReactions;
	}

	public void setListOfReactions(Reaction[] listOfReactions) {
		this.listOfReactions = listOfReactions;
	}

	@Override
	public String toString() {
		return "Model [id=" + id +  
				 ", listOfSpecies="
				+ Arrays.toString(listOfSpecies) + ", listOfParameters="
				+ Arrays.toString(listOfParameters) + ", listOfReactions="
				+ Arrays.toString(listOfReactions) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + Arrays.hashCode(listOfParameters);
		result = prime * result + Arrays.hashCode(listOfReactions);
		result = prime * result + Arrays.hashCode(listOfSpecies);
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
		Model other = (Model) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (!Arrays.equals(listOfParameters, other.listOfParameters))
			return false;
		if (!Arrays.equals(listOfReactions, other.listOfReactions))
			return false;
		if (!Arrays.equals(listOfSpecies, other.listOfSpecies))
			return false;
		return true;
	}

	
	
	
	
	
	
	
}
