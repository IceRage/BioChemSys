package ro.ubb.biochem.utils.export;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "reaction")
@XmlAccessorType(XmlAccessType.FIELD)
public class Reaction {

	
	@XmlAttribute
	private String reversible = "false";
	

	@XmlAttribute
	private String id;
	
	@XmlElementWrapper(name="listOfReactants")
	@XmlElement(name="speciesReference")
	private SpeciesReference[] listOfReactants;
	
	@XmlElementWrapper(name="listOfProducts")
	@XmlElement(name="speciesReference")
	private SpeciesReference[] listOfProducts;
	
	@XmlElement
	private KineticLaw kineticLaw;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReversible() {
		return reversible;
	}

	public void setReversible(String reversible) {
		this.reversible = reversible;
	}

	
	public SpeciesReference[] getListOfReactants() {
		return listOfReactants;
	}

	public void setListOfReactants(SpeciesReference[] listOfReactants) {
		this.listOfReactants = listOfReactants;
	}

	
	public SpeciesReference[] getListOfProducts() {
		return listOfProducts;
	}

	public void setListOfProducts(SpeciesReference[] listOfProducts) {
		this.listOfProducts = listOfProducts;
	}

	public KineticLaw getKineticLaw() {
		return kineticLaw;
	}

	public void setKineticLaw(KineticLaw kineticLaw) {
		this.kineticLaw = kineticLaw;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((kineticLaw == null) ? 0 : kineticLaw.hashCode());
		result = prime * result + Arrays.hashCode(listOfProducts);
		result = prime * result + Arrays.hashCode(listOfReactants);
		result = prime * result
				+ ((reversible == null) ? 0 : reversible.hashCode());
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
		Reaction other = (Reaction) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (kineticLaw == null) {
			if (other.kineticLaw != null)
				return false;
		} else if (!kineticLaw.equals(other.kineticLaw))
			return false;
		if (!Arrays.equals(listOfProducts, other.listOfProducts))
			return false;
		if (!Arrays.equals(listOfReactants, other.listOfReactants))
			return false;
		if (reversible == null) {
			if (other.reversible != null)
				return false;
		} else if (!reversible.equals(other.reversible))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reaction [id=" + id + ", reversible=" + reversible
				+ ", listOfReactants=" + Arrays.toString(listOfReactants)
				+ ", listOfProducts=" + Arrays.toString(listOfProducts)
				+ ", kineticLaw=" + kineticLaw + "]";
	}
	
	
	
}
