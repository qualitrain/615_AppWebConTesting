package org.qtx.web;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.qtx.entidades.Armadora;
import org.qtx.entidades.ModeloAuto;
import org.qtx.web.hateoas.LinkHateoas;
import org.qtx.web.hateoas.ObjetoHateoas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Path("armadoras")
public class ArmadoraApiRest {
	@Autowired
	private IArmadoraService servicioArmadoras;
	
	private static Logger bitacora = LoggerFactory.getLogger(ArmadoraApiRest.class);

	public ArmadoraApiRest() {
		bitacora.info("ArmadoraApiRest()");
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getArmadoras(){
		return servicioArmadoras.getArmadoras_String();		
	}
		
	@Path("id")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getArmadoraXID_texto(
			@QueryParam("cve")
			@DefaultValue("000000")
			String cveArmadora) {
		Armadora armadora = this.servicioArmadoras.getArmadoraXID(cveArmadora);
		return armadora.toString();
	}
	
	@Path("id")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getArmadoraXID_html(
			@QueryParam("cve")
			@DefaultValue("000000")
			String cveArmadora) {
		return this.servicioArmadoras.getArmadoraXID_html(cveArmadora);
	}
	
	@Path("{cve}")
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Armadora getArmadoraXID_JSonXML(
			@PathParam("cve")
			@DefaultValue("000000")
			String cveArmadora) {
		return this.servicioArmadoras.getArmadoraXID(cveArmadora);
	}
	
	@GET
	@Path("{cve}/modelos")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<ModeloAuto> getModelosXarmadora(
			@PathParam("cve")
			String cveArmadora){
		return this.servicioArmadoras.getModelosXarmadora(cveArmadora);
	}

	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Armadora> getArmadorasXml(){
		return this.servicioArmadoras.getArmadoras();
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN + "; charset=ISO-8859-1")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String insertarArmadora(
			@FormParam("clave")
			String clave, 
			@FormParam("nombre")
			String nombre,
			@FormParam("pais")
			String pais, 
			@FormParam("nplantas")
			@DefaultValue("0")
			int nPlantas) {
		Armadora nvaArmadora = new Armadora(clave,nombre,pais,nPlantas);
		return this.servicioArmadoras.insertarArmadora(nvaArmadora)
				                     .toString();
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN + "; charset=ISO-8859-1")
	@Consumes(MediaType.APPLICATION_JSON)
	public String insertarArmadora_Json(Armadora nvaArmadora){
		return this.servicioArmadoras.insertarArmadora(nvaArmadora)
                .toString();		
	}
	
	@PUT
	@Produces(MediaType.TEXT_PLAIN + "; charset=ISO-8859-1")
	@Consumes(MediaType.APPLICATION_JSON)
	public String actualizarArmadora_Json(Armadora armadora){
		return this.servicioArmadoras.actualizarArmadora(armadora).toString();
	}
	
	
	@GET
	@Produces("application/hal+json")	
	public String getArmadorasHateoas() {
		ObjetoHateoas objHateos = new ObjetoHateoas();
		objHateos.agregarLink("self", new LinkHateoas("webapi/armadoras",false));
		objHateos.agregarLink("filtroXpais", new LinkHateoas("webapi/armadoras{?pais}",true));
		
		List<Armadora> armadoras = this.getArmadorasXml();
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
