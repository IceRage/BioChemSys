package ro.ubb.biochem.utils.export;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "kineticLaw")
@XmlAccessorType(XmlAccessType.FIELD)
public class KineticLaw {

	@XmlElement
	private ro.ubb.biochem.utils.export.Math math;

	@Override
	public String toString() {
		return "KineticLaw [math=" + math + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((math == null) ? 0 : math.hashCode());
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
		KineticLaw other = (KineticLaw) obj;
		if (math == null) {
			if (other.math != null)
				return false;
		} else if (!math.equals(other.math))
			return false;
		return true;
	}

	public ro.ubb.biochem.utils.export.Math getMath() {
		return math;
	}

	public void setMath(ro.ubb.biochem.utils.export.Math math) {
		this.math = math;
	}
	
	
}
