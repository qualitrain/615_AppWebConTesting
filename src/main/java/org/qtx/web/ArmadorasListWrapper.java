package org.qtx.web;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.qtx.entidades.Armadora;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="armadoras")
public class ArmadorasListWrapper {

	@XmlElementWrapper()
	@XmlElement(name = "armadora")
	private List<Armadora> listaArmadoras;

	public ArmadorasListWrapper(List<Armadora> armadoras) {
		this.listaArmadoras = armadoras;
	}

	public List<Armadora> getArmadoras() {
		return listaArmadoras;
	}

	public void setArmadoras(List<Armadora> armadoras) {
		this.listaArmadoras = armadoras;
	}

}
