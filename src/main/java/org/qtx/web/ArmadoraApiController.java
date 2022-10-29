package org.qtx.web;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.qtx.entidades.Armadora;
import org.qtx.entidades.ModeloAuto;
import org.qtx.web.hateoas.LinkHateoas;
import org.qtx.web.hateoas.ObjetoHateoas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ArmadoraApiController {
	@Autowired
	private IArmadoraService servicioArmadoras;
	
	private static Logger bitacora = LoggerFactory.getLogger(ArmadoraApiController.class);

	public ArmadoraApiController() {
		bitacora.info("ArmadoraApiController()");		
	}
		
	@GetMapping(path = "/armadoras", produces =MediaType.TEXT_PLAIN_VALUE)
	public String getArmadoras(){
		bitacora.info("getArmadoras()");		
		return servicioArmadoras.getArmadoras_String();		
	}

	@GetMapping(path = "/armadoras/id", produces =MediaType.TEXT_PLAIN_VALUE)
	public String getArmadoraXID_texto(
			@RequestParam(value = "cve",defaultValue = "000000")
			String cveArmadora) {
		Armadora armadora = this.servicioArmadoras.getArmadoraXID(cveArmadora);
		return armadora.toString();
	}

	@GetMapping(path = "/armadoras/id", produces =MediaType.TEXT_HTML_VALUE)
	public String getArmadoraXID_html(
			@RequestParam(value = "cve",defaultValue = "000000")
			String cveArmadora) {
		return this.servicioArmadoras.getArmadoraXID_html(cveArmadora);
	}
	
	@GetMapping(path = "/armadoras/{cve}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public Armadora getArmadoraXID_JSonXML(
			@PathVariable(name = "cve")
			String cveArmadora) {
		return this.servicioArmadoras.getArmadoraXID(cveArmadora);
	}

	@GetMapping(path = "/armadoras/{cve}/modelos", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public List<ModeloAuto> getModelosXarmadora(
			@PathVariable(name = "cve")
			String cveArmadora){
		bitacora.debug("getModelosXarmadora(" + cveArmadora 	+ ")");	
		List<ModeloAuto> listModelos = this.servicioArmadoras.getModelosXarmadora(cveArmadora);
		bitacora.debug("modelos = " + listModelos);	
		return listModelos;
	}

	@GetMapping(path = "/armadorasArr", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public Armadora[] getArmadorasXml_Arr(){
		List<Armadora> listArmadoras = this.servicioArmadoras.getArmadoras();
		Armadora[] arr = new Armadora[listArmadoras.size()];
		return listArmadoras.toArray(arr);
	}
	
	@GetMapping(path = "/armadorasWrap", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ArmadorasListWrapper getArmadorasXml(){
		List<Armadora> listArmadoras = this.servicioArmadoras.getArmadoras();
		return new ArmadorasListWrapper(listArmadoras);
	}
	
	@GetMapping(path = "/armadoras", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public List<Armadora> getArmadorasXmlWrap(){
		List<Armadora> listArmadoras = this.servicioArmadoras.getArmadoras();
		return listArmadoras;
	}
	
	@PostMapping(path = "/armadoras", produces=MediaType.TEXT_PLAIN_VALUE + "; charset=ISO-8859-1", 
			                          consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String insertarArmadora(
			@RequestParam("clave")			
			String clave, 
			@RequestParam("nombre")
			String nombre,
			@RequestParam("pais")
			String pais, 
			@RequestParam(name="nplantas",defaultValue = "0")
			int nPlantas) 
        {
		bitacora.debug("insertarArmadora form-url-encoded");
		Armadora nvaArmadora = new Armadora(clave,nombre,pais,nPlantas);
		bitacora.debug(nvaArmadora.toString());
		return this.servicioArmadoras.insertarArmadora(nvaArmadora)
				                     .toString();
	}

	@PostMapping(path = "/armadoras", produces=MediaType.TEXT_PLAIN_VALUE + "; charset=ISO-8859-1", 
            consumes = MediaType.APPLICATION_JSON_VALUE)
	public String insertarArmadora_Json(@RequestBody Armadora nvaArmadora){
		bitacora.debug("insertarArmadora Json");
		bitacora.debug(nvaArmadora.toString());
		return this.servicioArmadoras.insertarArmadora(nvaArmadora)
                    .toString();		
	}
	
	@PutMapping(path = "/armadoras", produces=MediaType.TEXT_PLAIN_VALUE + "; charset=ISO-8859-1", 
            consumes = MediaType.APPLICATION_JSON_VALUE)
	public String actualizarArmadora_Json(Armadora armadora){
		return this.servicioArmadoras.actualizarArmadora(armadora).toString();
	}
	
	@GetMapping(path = "/armadoras", produces ="application/hal+json")
	public String getArmadorasHateoas() {
		ObjetoHateoas objHateos = new ObjetoHateoas();
		objHateos.agregarLink("self", new LinkHateoas("webapi/armadoras",false));
		objHateos.agregarLink("filtroXpais", new LinkHateoas("webapi/armadoras{?pais}",true));
		
		List<Armadora> armadoras = this.servicioArmadoras.getArmadoras();
		for(Armadora armadoraI: armadoras) {
			Map<String,Object> mapArmadoraI = getMapaArmadora(armadoraI);
			objHateos.agregarDataItem(mapArmadoraI);
		}
		return objHateos.toJson().toString();
	}
	
	private Map<String, Object> getMapaArmadora(Armadora armadoraI) {
		Map<String,Object> mapaArmadora = new LinkedHashMap<>();
		Map<String,LinkHateoas> mapaLinks = new LinkedHashMap<>();

		mapaLinks.put("self", new LinkHateoas("webapi/armadoras/" + armadoraI.getClave(), false));
		mapaLinks.put("modelos", new LinkHateoas("webapi/armadoras/" + armadoraI.getClave() + "/modelos", false));
		mapaArmadora.put("_links",mapaLinks);
		
		mapaArmadora.put("clave", armadoraI.getClave());
		mapaArmadora.put("nombre", armadoraI.getNombre());
		mapaArmadora.put("paisOrigen", armadoraI.getPaisOrigen());
		mapaArmadora.put("nPlantas", armadoraI.getnPlantas());
		return mapaArmadora;
	}
	
}
