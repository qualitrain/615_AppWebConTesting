package org.qtx.web;

import java.util.List;

import org.qtx.entidades.Armadora;
import org.qtx.entidades.ModeloAuto;

public interface IArmadoraService {
	List<Armadora> getArmadoras();
	String getArmadoras_String();
	String getArmadoraXID_texto(String cveArmadora);
	String getArmadoraXID_html(String cveArmadora);
	Armadora getArmadoraXID(String cveArmadora);
	List<ModeloAuto> getModelosXarmadora(String cveArmadora);
	Armadora insertarArmadora(Armadora nvaArmadora);
	Armadora actualizarArmadora(Armadora updArmadora);
}
