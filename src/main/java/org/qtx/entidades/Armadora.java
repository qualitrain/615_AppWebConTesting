package org.qtx.entidades;


import java.util.Set;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Armadora {
	@Id
	private String clave;
	private String nombre;
	private String paisOrigen;
	private int nPlantas;
	@XmlTransient
	@OneToMany(mappedBy="armadora")
	private Set<ModeloAuto> modelos;
	
	public Armadora(String clave, String nombre, String paisOrigen, int nPlantas) {
		super();
		this.clave = clave;
		this.nombre = nombre;
		this.paisOrigen = paisOrigen;
		this.nPlantas = nPlantas;
	}
	public Armadora() {
		super();
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPaisOrigen() {
		return paisOrigen;
	}
	public void setPaisOrigen(String paisOrigen) {
		this.paisOrigen = paisOrigen;
	}
	public int getnPlantas() {
		return nPlantas;
	}
	public void setnPlantas(int nPlantas) {
		this.nPlantas = nPlantas;
	}
	public Set<ModeloAuto> getModelos() {
		return modelos;
	}
	public void setModelos(Set<ModeloAuto> modelos) {
		this.modelos = modelos;
	}
	@Override
	public String toString() {
		return "Armadora [clave=" + clave + ", nombre=" + nombre + ", paisOrigen=" + paisOrigen + ", nPlantas="
				+ nPlantas + "]";
	}
	public String toHtml() {
		String html = "<span style='font-weight: bold;'>Clave:</span>" + this.clave + "<br>"
		            + "<span style='font-weight: bold;'>Nombre:</span>" + this.nombre + "<br>"
		            + "<span style='font-weight: bold;'>PaisOrigen:</span>" + this.paisOrigen + "<br>"
		            + "<span style='font-weight: bold;'>N&iacute;umero de plantas:</span>" + this.nPlantas + "<br>";
		return html;
	}
	public JsonObject toJson() {
		JsonArrayBuilder builderArrModelos = Json.createArrayBuilder();
		if(this.modelos != null) {
			for(ModeloAuto modeloI : this.modelos) {
				builderArrModelos.add(modeloI.toJson());
			}
		}
		
		return Json.createObjectBuilder()
				       .add("clave", this.clave)
		               .add("nombre", this.nombre)
		               .add("paisOrigen", this.paisOrigen)
		               .add("nPlantas", this.nPlantas)
				       .add("modelos", builderArrModelos.build())
			           .build();
		
	}
	public boolean equivaleA(Armadora otraArmadora) {

		// Compara campo por campo
		
		if(this.clave.equals(otraArmadora.clave) == false)
			return false;
		if(this.nombre.equals(otraArmadora.nombre) == false)
			return false;
		if(this.paisOrigen.equals(otraArmadora.paisOrigen) == false)
			return false;
		if(this.nPlantas != otraArmadora.nPlantas)
			return false;
		
		// Compara los modelos anidados en la armadora
		
		//Caso que la primera armadora no tiene modelos
		if(this.modelos == null) {
			if(otraArmadora.modelos == null)
				return true;
			else
				return false;
		}
		// Caso la segunda armadora no tiene modelos, pero la primera si
		if(otraArmadora.modelos == null)
			return false;
		
		if(this.modelos.size() != otraArmadora.modelos.size())
			return false;
		
		if(otraArmadora.modelos.containsAll(this.modelos))
			return true;
		return false;
	
	}
}
