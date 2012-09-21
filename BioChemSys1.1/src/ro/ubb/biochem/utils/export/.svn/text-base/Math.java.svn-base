package ro.ubb.biochem.utils.export;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "math")
@XmlAccessorType(XmlAccessType.FIELD)
public class Math {
	
	@XmlAttribute
	private String xmlns="http://www.w3.org/1998/Math/MathML";
	
	@XmlElement
	private Apply apply;

	public String getXmlns() {
		return xmlns;
	}

	public void setXmlns(String xmlns) {
		this.xmlns = xmlns;
	}

	public Apply getApply() {
		return apply;
	}

	public void setApply(Apply apply) {
		this.apply = apply;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apply == null) ? 0 : apply.hashCode());
		result = prime * result + ((xmlns == null) ? 0 : xmlns.hashCode());
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
		Math other = (Math) obj;
		if (apply == null) {
			if (other.apply != null)
				return false;
		} else if (!apply.equals(other.apply))
			return false;
		if (xmlns == null) {
			if (other.xmlns != null)
				return false;
		} else if (!xmlns.equals(other.xmlns))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Math [xmlns=" + xmlns + ", apply=" + apply + "]";
	}
	
	

}
