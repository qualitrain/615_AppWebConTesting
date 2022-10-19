package org.qtx.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.qtx.entidades.Armadora;
import org.qtx.entidades.ModeloAuto;
import org.qtx.web.IArmadoraService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArmadoraServiceV01 implements IArmadoraService {
	
	@Autowired
	IGestorDatos gestorDatos;

	private static Logger bitacora = LoggerFactory.getLogger(ArmadoraServiceV01.class);

	public ArmadoraServiceV01() {
		bitacora.info("ArmadoraService()");
	}

	@Override
	public List<Armadora> getArmadoras() {
		List<Armadora> listaArmadoras = 
				new ArrayList<Armadora>(this.gestorDatos.getArmadorasTodas());
		return listaArmadoras;
	}
	@Override
	public String getArmadoras_String() {
		List<Armadora> listaArmadoras = 
				new ArrayList<Armadora>(this.gestorDatos.getArmadorasTodas());
		return listaArmadoras.toString();
	}

	@Override
	public String getArmadoraXID_texto(String cveArmadora) {
		Armadora armadora = this.gestorDatos.getArmadoraXID(cveArmadora);
		if(armadora == null) {
			return "Armadora con cve " + cveArmadora + " NO EXISTE";
		}
		return armadora.toString();
	}

	@Override
	public String getArmadoraXID_html(String cveArmadora) {
		Armadora armadora = this.gestorDatos.getArmadoraXID(cveArmadora);
		if(armadora == null) {
			return "<span style='color:red;'>Armadora con cve " + cveArmadora + " NO EXISTE</span>";
		}
		return armadora.toHtml();
	}

	@Override
	public Armadora getArmadoraXID(String cveArmadora) {
		Armadora armadora = this.gestorDatos.getArmadoraXID(cveArmadora);
		if(armadora == null) {
			Armadora armadoraVacia = new Armadora();
			armadoraVacia.setClave(cveArmadora);
			armadoraVacia.setNombre("NO EXISTE");
			armadoraVacia.setPaisOrigen("NO EXISTE");
			return armadoraVacia;
		}
		return armadora;
	}

	@Override
	public List<ModeloAuto> getModelosXarmadora(String cveArmadora) {
		Armadora armadora = this.gestorDatos.getArmadoraConModelosXID(cveArmadora);
		if(armadora == null) {			
			List<ModeloAuto> listaVacia = new ArrayList<>();
			listaVacia.add(new ModeloAuto("NO EXISTE ARMADORA " + cveArmadora ,
					                      "NO EXISTE ARMADORA" ,
					                      null, 
					                      "NO EXISTE ARMADORA", 
					                      true));
			return listaVacia;			
		}
		Set<ModeloAuto> modelos = armadora.getModelos();
		return new ArrayList<ModeloAuto>(modelos);
	}

	@Override
	public Armadora insertarArmadora(Armadora nvaArmadora) {
		String error = this.validarDatosArmadora(nvaArmadora);
		if(error != null) {
			Armadora armError = new Armadora();
			armError.setClave(error);
			return armError;
		}
		nvaArmadora = this.gestorDatos.insertarArmadora(nvaArmadora);
		return nvaArmadora;
	}
	private String validarDatosArmadora(Armadora armadora) {
		return this.validarDatosArmadora(armadora.getClave(),armadora.getNombre(), 
				                         armadora.getPaisOrigen(), armadora.getnPlantas());
	}

	private String validarDatosArmadora(String clave, String nombre, String pais, int nPlantas) {
		String camposVacios = "";
		if (campoVacio(clave))
			camposVacios += "clave, ";
		if (campoVacio(nombre))
			camposVacios += "nombre, ";
		if (campoVacio(pais))
			camposVacios += "pais, ";
		if(camposVacios.isEmpty() == false)
			return "{Error; faltan los campos: " + camposVacios.substring(0, camposVacios.lastIndexOf(",")) + "}";
		Armadora armadora = this.gestorDatos.getArmadoraXID(clave);
		if(armadora != null)
			return "{Error; ya existe una armadora con esa clave:" + armadora + "}";
		return null;
	}
	private boolean campoVacio(String campo) {
		if(campo == null)
			return true;
		if(campo.trim().isEmpty())
			return true;
		return false;
	}

	@Override
	public Armadora actualizarArmadora(Armadora updArmadora) {
		String error = this.validarDatosArmadoraUpdate(updArmadora);
		if(error != null) {
			Armadora armError = new Armadora();
			armError.setClave(error);
			return armError;
		}
		updArmadora = this.gestorDatos.actualizarArmadora(updArmadora);
		return updArmadora;
	}
	
	private String validarDatosArmadoraUpdate(Armadora armadora) {
		return this.validarDatosArmadoraUpdate(armadora.getClave(),armadora.getNombre(), 
                armadora.getPaisOrigen(), armadora.getnPlantas());
	}
	
	private String validarDatosArmadoraUpdate(String clave, String nombre, String pais, int nPlantas) {
		String camposVacios = "";
		if (campoVacio(clave))
			camposVacios += "clave, ";
		if (campoVacio(nombre))
			camposVacios += "nombre, ";
		if (campoVacio(pais))
			camposVacios += "pais, ";
		if(camposVacios.isEmpty() == false)
			return "Error, faltan los campos: " + camposVacios;
		Armadora armadora = this.gestorDatos.getArmadoraXID(clave);
		if(armadora == null)
			return "Error, No existe una armadora con esa clave:" + clave;
		return null;
	}

}
