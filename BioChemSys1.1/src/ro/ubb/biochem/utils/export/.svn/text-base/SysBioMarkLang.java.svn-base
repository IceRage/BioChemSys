package ro.ubb.biochem.utils.export;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sbml")
@XmlAccessorType(XmlAccessType.FIELD)
public class SysBioMarkLang {
	
	
	@XmlAttribute
	private String version = "3";
	
	
	@XmlAttribute
	private String level = "2";
	
	@XmlAttribute
	private String xmlns ="http://www.sbml.org/sbml/level2/version3";
	
	@XmlElement
	private Model model;


	public String getXmlns() {
		return xmlns;
	}


	public void setXmlns(String xmlns) {
		this.xmlns = xmlns;
	}


	public String getLevel() {
		return level;
	}


	public void setLevel(String level) {
		this.level = level;
	}


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public Model getModel() {
		return model;
	}


	public void setModel(Model model) {
		this.model = model;
	}


	@Override
	public String toString() {
		return "SysBioMarkLang [xmlns=" + xmlns + ", level=" + level
				+ ", version=" + version + ", model=" + model + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		SysBioMarkLang other = (SysBioMarkLang) obj;
		if (level == null) {
			if (other.level != null)
				return false;
		} else if (!level.equals(other.level))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		if (xmlns == null) {
			if (other.xmlns != null)
				return false;
		} else if (!xmlns.equals(other.xmlns))
			return false;
		return true;
	}



}
