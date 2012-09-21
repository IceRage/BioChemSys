package ro.ubb.biochem.utils.export;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "species")
@XmlAccessorType(XmlAccessType.FIELD)
public class Species {
	@XmlAttribute
	private String hasOnlySubstanceUnits="true";
	
	@XmlAttribute
	private String initialAmount;
	
	@XmlAttribute
	private String compartment="compartment";
	
	@XmlAttribute
	private String id;
	


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompartment() {
		return compartment;
	}

	public void setCompartment(String compartment) {
		this.compartment = compartment;
	}

	public String getInitialAmount() {
		return initialAmount;
	}

	public void setInitialAmount(String initialAmount) {
		this.initialAmount = initialAmount;
	}

	public String getHasOnlySubstanceUnits() {
		return hasOnlySubstanceUnits;
	}

	public void setHasOnlySubstanceUnits(String hasOnlySubstanceUnits) {
		this.hasOnlySubstanceUnits = hasOnlySubstanceUnits;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result
				+ ((compartment == null) ? 0 : compartment.hashCode());
		result = prime
				* result
				+ ((hasOnlySubstanceUnits == null) ? 0 : hasOnlySubstanceUnits
						.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((initialAmount == null) ? 0 : initialAmount.hashCode());
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
		Species other = (Species) obj;
		if (compartment == null) {
			if (other.compartment != null)
				return false;
		} else if (!compartment.equals(other.compartment))
			return false;
		if (hasOnlySubstanceUnits == null) {
			if (other.hasOnlySubstanceUnits != null)
				return false;
		} else if (!hasOnlySubstanceUnits.equals(other.hasOnlySubstanceUnits))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (initialAmount == null) {
			if (other.initialAmount != null)
				return false;
		} else if (!initialAmount.equals(other.initialAmount))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Species [id=" + id + ", compartment=" + compartment
				+ ", initialAmount=" + initialAmount
				+ ", hasOnlySubstanceUnits=" + hasOnlySubstanceUnits + "]";
	}
	
	
	
	
}
