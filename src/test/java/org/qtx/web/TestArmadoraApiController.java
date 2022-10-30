package org.qtx.web;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.qtx.entidades.Armadora;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

//import org.mockito.ArgumentMatchers;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = ArmadoraApiController.class)
public class TestArmadoraApiController {
	private static final String URI_BASE_ARMADORAS="/armadoras";
		
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IArmadoraService servicioArmadoras;
	
	private static Logger bitacora = LoggerFactory.getLogger(TestArmadoraApiController.class);

	
	public TestArmadoraApiController() {
		super();
		bitacora.info("TestArmadoraApiController()");
	}


	@Test
	@DisplayName("TEST de @GetMapping(path = \"/armadoras\", produces =MediaType.TEXT_PLAIN_VALUE)")
	public void testGetArmadoras_texto() {
		
		//Dados
		List<Armadora> armadorasMock = new ArrayList<>();
		armadorasMock.add(new Armadora("BMW","BMW de México SA de CV","Alemania",2));
		armadorasMock.add(new Armadora("KIA","Autos de Corea SA de CV","Corea del Sur",1));
		armadorasMock.add(new Armadora("Mazda","Autos de Oriente SA de CV","Japón",1));
		
		when(servicioArmadoras.getArmadoras_String()).thenReturn(armadorasMock.toString());		
		//Cuando
		
		try {
			mockMvc.perform(get(URI_BASE_ARMADORAS).accept(MediaType.TEXT_PLAIN_VALUE))
        //Entonces
			       .andExpect(status().isOk())
			       .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8"))
			       .andExpect(content().string(armadorasMock.toString()))
			       .andReturn();
		} catch (Exception e) {
			fail("Ex:" + e.getClass().getName() + ", Msg:" + e.getMessage());
		}
	}
	
	@Test
	@DisplayName("TEST de @GetMapping(path = \"/armadoras/id\", produces =MediaType.TEXT_PLAIN_VALUE) id_Existe")
	public void testGetArmadoraXID_texto_ExisteID() {
		//Dados
		String param = "cve";
		String idArmadora = "BMW";		
		String RECURSO = URI_BASE_ARMADORAS + "/id" + "?" + param + "=" + idArmadora ;
		Armadora armadoraMock = new Armadora(idArmadora, "BMW European cars Corporation", "Alemania", 0);
		when(servicioArmadoras.getArmadoraXID(idArmadora)).thenReturn(armadoraMock);		
		//Cuando
		try {
			MvcResult resultado = mockMvc.perform(get(RECURSO).accept(MediaType.TEXT_PLAIN_VALUE))			
			//Entonces
		           .andExpect(status().isOk())
		           .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8"))
		           .andExpect(content().string(armadoraMock.toString()))
		           .andReturn();
			String contenidoResponse = resultado.getResponse().getContentAsString();
			assertThat(contenidoResponse).contains(armadoraMock.getPaisOrigen())
			                             .contains(armadoraMock.getNombre())
			                             .contains(armadoraMock.getClave())
			                             .contains(armadoraMock.getnPlantas()+"");
//			assertThat( resultado.getResponse().getContentAsString()).contains("Bolivia");
		}catch (Exception e) {
			fail("Ex:" + e.getClass().getName() + ", Msg:" + e.getMessage());
		}
	}

	@Test
	@DisplayName("TEST de @GetMapping(path = \"/armadoras/id\", produces =MediaType.TEXT_PLAIN_VALUE) id_No_Existe")
	public void testGetArmadoraXID_texto_NoExisteID() {
		// Dados		
		String param = "cve";
		String idArmadora = "XXX";	// Armadora inexistente	
		String RECURSO = URI_BASE_ARMADORAS + "/id" + "?" + param + "=" + idArmadora ;
		
		Armadora armadoraVacia = new Armadora();
		armadoraVacia.setClave(idArmadora);
		armadoraVacia.setNombre("NO EXISTE");
		armadoraVacia.setPaisOrigen("NO EXISTE");
		
		when(servicioArmadoras.getArmadoraXID(idArmadora)).thenReturn(armadoraVacia);	
		
		//Cuando
		try {
			MvcResult resultado = mockMvc.perform(get(RECURSO).accept(MediaType.TEXT_PLAIN_VALUE))			
			//Entonces
		           .andExpect(status().isOk())
		           .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8"))
		           .andReturn();
			String contenidoResponse = resultado.getResponse().getContentAsString();
			assertThat(contenidoResponse).contains("NO EXISTE");
		}catch (Exception e) {
			fail("Ex:" + e.getClass().getName() + ", Msg:" + e.getMessage());
		}
	}
	@Test
	@DisplayName("TEST de @GetMapping(path = \"/armadoras/id\", produces =MediaType.TEXT_HTML_VALUE)")
	public void testGetArmadoraXID_html_ExisteID() {
		//Dados
		String param = "cve";
		String idArmadora = "BMW";		
		String RECURSO = URI_BASE_ARMADORAS + "/id" + "?" + param + "=" + idArmadora ;
		Armadora armadoraMock = new Armadora(idArmadora, "BMW European cars Corporation", "Alemania", 0);
		when(servicioArmadoras.getArmadoraXID_html(idArmadora)).thenReturn(armadoraMock.toHtml());		
		
		//Cuando
		try {
			MvcResult resultado = mockMvc.perform(get(RECURSO).accept(MediaType.TEXT_HTML_VALUE))			
			//Entonces
		           .andExpect(status().isOk())
		           .andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
		           .andReturn();
			String contenidoResponse = resultado.getResponse().getContentAsString();
			assertThat(contenidoResponse).isNotBlank()
                                         .contains(idArmadora)
                                         .contains("</span>");
		}catch (Exception e) {
			fail("Ex:" + e.getClass().getName() + ", Msg:" + e.getMessage());
		}		
	}
	@Test
	@DisplayName("TEST de @GetMapping(path = \"/armadoras/{cve}\", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}) Json")
	public void testGetArmadoraXID_JSonXML_SiExisteYenJson() {
	    bitacora.debug("testGetArmadoraXID_JSonXML_SiExisteYenJson()");
		//Dados
		String idArmadora = "BMW";		
		String RECURSO = URI_BASE_ARMADORAS + "/" + idArmadora ;
		Armadora armadoraMock = new Armadora(idArmadora, "BMW European cars Corporation", "Alemania", 0);
		when(servicioArmadoras.getArmadoraXID(idArmadora)).thenReturn(armadoraMock);		
		//Cuando
		try {
		    bitacora.debug(RECURSO);
			mockMvc.perform(get(RECURSO).accept(MediaType.APPLICATION_JSON_VALUE))			
			//Entonces
		           .andExpect(status().isOk())
		           .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		           .andExpect(jsonPath("$.paisOrigen").value(armadoraMock.getPaisOrigen()))
		           .andExpect(jsonPath("$.nombre").value(armadoraMock.getNombre()))
		           .andExpect(jsonPath("$.clave").value(armadoraMock.getClave()))
		           .andExpect(jsonPath("$.nPlantas").value(armadoraMock.getnPlantas()))
		           .andReturn();
		}catch (Exception e) {
			fail("Ex:" + e.getClass().getName() + ", Msg:" + e.getMessage());
		}
	}

	@Test
	@DisplayName("TEST de @GetMapping(path = \"/armadoras\", produces = "
			+ "{MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})")
	public void testGetArmadorasXml() {
	    bitacora.info("testGetArmadorasXml()");
		
		//Dados
	    
		List<Armadora> armadorasMock = new ArrayList<>();
		armadorasMock.add(new Armadora("BMW","BMW de México SA de CV","Alemania",2));
		armadorasMock.add(new Armadora("KIA","Autos de Corea SA de CV","Corea del Sur",1));
		armadorasMock.add(new Armadora("Mazda","Autos de Oriente SA de CV","Japón",1));
		
		when(servicioArmadoras.getArmadoras()).thenReturn(armadorasMock);	
	
		//Cuando
		
		try {
			MvcResult resultado = 
				mockMvc.perform(get(URI_BASE_ARMADORAS).accept(MediaType.APPLICATION_JSON_VALUE+ ";charset=UTF-8"))
            //Entonces
			           .andExpect(status().isOk())
			           .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE+ ";charset=UTF-8"))
			           .andExpect(jsonPath("$[0].paisOrigen").value(armadorasMock.get(0).getPaisOrigen()))
			           .andExpect(jsonPath("$[1].paisOrigen").value(armadorasMock.get(1).getPaisOrigen()))
			           .andExpect(jsonPath("$[2].paisOrigen").value(armadorasMock.get(2).getPaisOrigen()))
			           .andExpect(jsonPath("$[0].clave").value(armadorasMock.get(0).getClave()))
			           .andExpect(jsonPath("$[1].clave").value(armadorasMock.get(1).getClave()))
			           .andExpect(jsonPath("$[2].clave").value(armadorasMock.get(2).getClave()))
			           .andReturn();
			
			String body = resultado.getResponse()
					               .getContentAsString();
			bitacora.info("body:" + body);
			
		} catch (Exception e) {
			fail("Ex:" + e.getClass().getName() + ", Msg:" + e.getMessage());
		}
	}

	
	@Test
	@DisplayName("TEST de @PostMapping(path = \"/armadoras\", produces=MediaType.TEXT_PLAIN_VALUE + \"; charset=ISO-8859-1\", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)")
	public void testInsertarArmadora_OK_FORM_URLENCODED() {
		
		// Dados
		Armadora armadoraMock = new Armadora("ARMMEX", "Armadora Mexicana SA de CV", "México", 2);
		String RECURSO = URI_BASE_ARMADORAS;
		
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("clave", armadoraMock.getClave());
		formData.add("nombre", armadoraMock.getNombre());
		formData.add("pais", armadoraMock.getPaisOrigen());
		formData.add("nplantas",armadoraMock.getnPlantas() + "");
		
		
//		when(servicioArmadoras.insertarArmadora(ArgumentMatchers.any(Armadora.class))).thenReturn(armadoraMock);
		when(servicioArmadoras.insertarArmadora(any(Armadora.class))).thenReturn(armadoraMock);
		// Cuando
		try {
			MvcResult resultado = 
					mockMvc.perform(post(RECURSO).accept(MediaType.TEXT_PLAIN_VALUE+ "; charset=ISO-8859-1")
					                             .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
					                             .params(formData))
					// Entonces
			           .andExpect(status().isOk())
			           .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE + "; charset=ISO-8859-1"))
			           .andReturn();
			
			String contenidoResponse = resultado.getResponse()
					                            .getContentAsString();
			assertThat(contenidoResponse).isNotBlank()
                                         .contains(armadoraMock.getClave())
                                         .contains(armadoraMock.getNombre())
                                         .contains(armadoraMock.getPaisOrigen())
                                         .contains(armadoraMock.getnPlantas()+"");
                                         
		} catch (Exception e) {
			fail("Ex:" + e.getClass().getName() + ", Msg:" + e.getMessage());
		}		
	}

	@Test
	@DisplayName("TEST de 	@PostMapping(path = \"/armadoras\", produces=MediaType.TEXT_PLAIN_VALUE + \"; charset=ISO-8859-1\", consumes = MediaType.APPLICATION_JSON_VALUE)")
	public void testInsertarArmadora_Json_OK() {
		bitacora.info("testInsertarArmadora_Json_OK()");
		// Dados
		String idArmadora = "ARMMEX2";
		String nombre = "Otra Armadora Mexicana SA de CV";
		String paisOrigen = "México";
		int nPlantas = 3;
		
		Armadora armadora = new Armadora(idArmadora,nombre, paisOrigen, nPlantas);
		String RECURSO = URI_BASE_ARMADORAS;
	
//		when(servicioArmadoras.insertarArmadora(armadora)).thenReturn(armadora); //No funciona porque los hash de la armadora enviada y la que crea el controlador son distintos
		when(servicioArmadoras.insertarArmadora(any(Armadora.class))).thenReturn(armadora);
		bitacora.info("Hash armadora=" + armadora.hashCode());
		
		try {
			MvcResult resultado = 
					mockMvc.perform(post(RECURSO).accept(MediaType.TEXT_PLAIN_VALUE+ "; charset=ISO-8859-1")
					                             .contentType(MediaType.APPLICATION_JSON_VALUE)
					                             .content(armadora.toJson().toString()))
					// Entonces
			           .andExpect(status().isOk())
			           .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE + "; charset=ISO-8859-1"))
			           .andReturn();
			
			String contenidoResponse = resultado.getResponse()
					                            .getContentAsString();
			assertThat(contenidoResponse).isNotBlank()
                                         .contains(idArmadora)
                                         .contains(nombre)
                                         .contains(paisOrigen)
                                         .contains(nPlantas+"");
                                         
		} catch (Exception e) {
			fail("Ex:" + e.getClass().getName() + ", Msg:" + e.getMessage());
		}		

	}

}
