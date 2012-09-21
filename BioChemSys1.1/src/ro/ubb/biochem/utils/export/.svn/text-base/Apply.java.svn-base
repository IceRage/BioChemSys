package ro.ubb.biochem.utils.export;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "apply")
@XmlAccessorType(XmlAccessType.FIELD)
public class Apply {

	@XmlElement
	private String times="";
	
	@XmlElement(name="ci")
	private String[] ci;

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String[] getCi() {
		return ci;
	}

	public void setCi(String[] ci) {
		this.ci = ci;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(ci);
		result = prime * result + ((times == null) ? 0 : times.hashCode());
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
		Apply other = (Apply) obj;
		if (!Arrays.equals(ci, other.ci))
			return false;
		if (times == null) {
			if (other.times != null)
				return false;
		} else if (!times.equals(other.times))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Apply [times=" + times + ", ci=" + Arrays.toString(ci) + "]";
	}
	
	
	
	
	
}
