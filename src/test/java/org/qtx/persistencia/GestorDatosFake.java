package org.qtx.persistencia;

import java.util.Map;
import java.util.Set;

import org.qtx.entidades.Armadora;
import org.qtx.entidades.ModeloAuto;
import org.qtx.servicios.IGestorDatos;

public class GestorDatosFake implements IGestorDatos {

	@Override
	public Armadora getArmadoraXID(String cveArmadora) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Armadora> getArmadorasTodas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Armadora> getMapaArmadoras() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Armadora insertarArmadora(Armadora armadora) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModeloAuto insertarModeloAuto(ModeloAuto modelo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Armadora getArmadoraConModelosXID(String cveArmadora) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Armadora actualizarArmadora(Armadora armadora) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Armadora eliminarArmadora(String cveArmadora) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModeloAuto eliminarModeloAuto(String cveModelo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModeloAuto getModeloAutoXID(String cveModelo) {
		// TODO Auto-generated method stub
		return null;
	}

}
