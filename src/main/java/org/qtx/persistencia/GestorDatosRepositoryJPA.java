package org.qtx.persistencia;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.qtx.entidades.Armadora;
import org.qtx.entidades.ModeloAuto;
import org.qtx.servicios.IGestorDatos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
//@Primary
public class GestorDatosRepositoryJPA implements IGestorDatos {
	@Autowired
	private IArmadoraRepository repoArmadora;

	private static Logger bitacora = LoggerFactory.getLogger(GestorDatosRepositoryJPA.class);
	
	public GestorDatosRepositoryJPA() {
		super();
		bitacora.info("GestorDatosRepositoryJPA()");
	}

	@Override
	public Armadora getArmadoraXID(String cveArmadora) {
		bitacora.info("getArmadoraXID(" + cveArmadora + ")");
		Optional<Armadora> armadora = repoArmadora.findById(cveArmadora);
		if(armadora.isPresent())
			return armadora.get();
		return null;
	}

	@Override
	public Set<Armadora> getArmadorasTodas() {
		bitacora.info("getArmadorasTodas()");
		return new HashSet<>(repoArmadora.findAll());
	}

	@Override
	public Map<String, Armadora> getMapaArmadoras() {
		Map<String,Armadora> mapArmadoras = new HashMap<>();
		List<Armadora> lstArmadoras = this.repoArmadora.findAll();
		lstArmadoras.stream()
		            .forEach( armI -> mapArmadoras.put(armI.getClave(), armI) );
		return mapArmadoras;
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

}
