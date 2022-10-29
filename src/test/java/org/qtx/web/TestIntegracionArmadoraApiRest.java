package org.qtx.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.qtx.entidades.Armadora;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
class TestIntegracionArmadoraApiRest {
	
	private static String URI_BASE_ARMADORAS;

	private static Logger bitacora = LoggerFactory.getLogger(TestIntegracionArmadoraApiRest.class);

	@BeforeAll
	static void inicializarClienteWeb(
			@Value("${server.servlet.context-path}") String contexto,
			@Value("${spring.jersey.application-path}") String pathApi) {
		
		String contextPath = contexto.substring(0, contexto.length() - 1);
		String jerseyApplicationPath = pathApi;
		URI_BASE_ARMADORAS = contextPath + jerseyApplicationPath + "armadoras";	
		
		/* Requiere un script que asegure que:
		 *  existe una armadora "BMW"
		 *  no existe una armadora "ARMMEX"
		*/
	}

	public TestIntegracionArmadoraApiRest() {
		bitacora.info("TestIntegracionArmadoraApiRest()");
	}
	
	@Test
	void testGetArmadoras(@Autowired WebTestClient webClient) {
		bitacora.info("testGetArmadoras(webClient)");
		
		//Dados
		
		String RECURSO = URI_BASE_ARMADORAS;
		bitacora.info("testGetArmadoras: Recurso= " + RECURSO);
		
		//Cuando
		
		EntityExchangeResult<String> resCadArmadoras = 
		   webClient.get().uri(RECURSO)
				          .accept(MediaType.TEXT_PLAIN)
		            .exchange()
		//Entonces
		            .expectStatus().isOk()
		            .expectHeader().contentType(MediaType.TEXT_PLAIN)
		            .expectBody(String.class).returnResult();
		
		String cadArmadoras = resCadArmadoras.getResponseBody();
		assertThat(cadArmadoras).isNotBlank();
		
		bitacora.info("testGetArmadoras: cadArmadoras = " + cadArmadoras);
	}

	@Test
	void testGetArmadoraXID_texto_ExisteID( @Autowired WebTestClient webClient ) {
		bitacora.info("testGetArmadoraXID_texto_ExisteID(webClient)");
		//Dados
		String param = "cve";
		String idArmadora = "BMW";		
		String RECURSO = URI_BASE_ARMADORAS + "/id" + "?" + param + "=" + idArmadora ;
		bitacora.info("testGetArmadoraXID_texto_ExisteID: Recurso= " + RECURSO );
		
		//Cuando
		
		EntityExchangeResult<String> resCadArmadora = 
		   webClient.get().uri(RECURSO)
				          .accept(MediaType.TEXT_PLAIN)
		            .exchange()
		    		//Entonces
		            .expectStatus().isOk()
		            .expectHeader().contentType(MediaType.TEXT_PLAIN)
		            .expectBody(String.class).returnResult();
		
		String cadArmadora = resCadArmadora.getResponseBody();
		assertThat(cadArmadora).isNotBlank()
		                       .contains(idArmadora);
		
		bitacora.info("testGetArmadoraXID_texto_ExisteID: cadArmadora = " + cadArmadora);
		
	}

	@Test
	void testGetArmadoraXID_texto_NoExisteID( @Autowired WebTestClient webClient ) {
		bitacora.info("testGetArmadoraXID_texto_NoExisteID(webClient)");
		
		// Dados
		
		String param = "cve";
		String idArmadora = "XXX";	// Armadora inexistente	
		String RECURSO = URI_BASE_ARMADORAS + "/id" + "?" + param + "=" + idArmadora ;
		bitacora.info("testGetArmadoraXID_texto_NoExisteID: Recurso= " + RECURSO );
		
		// Cuando
		
		EntityExchangeResult<String> resCadArmadora = 
		   webClient.get().uri(RECURSO)
				          .accept(MediaType.TEXT_PLAIN)
		            .exchange()
	    // Entonces
		            .expectStatus().isOk()
		            .expectHeader().contentType(MediaType.TEXT_PLAIN)
		            .expectBody(String.class).returnResult();
		
		String cadArmadora = resCadArmadora.getResponseBody();
		assertThat(cadArmadora).isNotBlank()
		                       .contains("NO EXISTE");
		
		bitacora.info("testGetArmadoraXID_texto_NoExisteID: cadArmadora = " + cadArmadora);
		
	}
	
	@Test
	void testGetArmadoraXID_html_ExisteID( @Autowired WebTestClient webClient ) {
		bitacora.info("testGetArmadoraXID_html_ExisteID(webClient)");
		//Dados
		String param = "cve";
		String idArmadora = "BMW";		
		String RECURSO = URI_BASE_ARMADORAS + "/id" + "?" + param + "=" + idArmadora ;
		bitacora.info("testGetArmadoraXID_html_ExisteID: Recurso= " + RECURSO );
		
		//Cuando
		
		EntityExchangeResult<String> resCadArmadora = 
		   webClient.get().uri(RECURSO)
				          .accept(MediaType.TEXT_HTML)
		            .exchange()
		    		//Entonces
		            .expectStatus().isOk()
		            .expectHeader().contentType(MediaType.TEXT_HTML)
		            .expectBody(String.class).returnResult();
		
		String cadArmadora = resCadArmadora.getResponseBody();
		assertThat(cadArmadora).isNotBlank()
		                       .contains(idArmadora)
		                       .contains("</span>");
		
		bitacora.info("testGetArmadoraXID_html_ExisteID: cadArmadora = " + cadArmadora);
		
	}

	@Test
	void testGetArmadoraXID_JSonXML_SiExisteYenJson( @Autowired WebTestClient webClient ) {
		bitacora.info("testGetArmadoraXID_JSonXML_SiExisteYenJson(webClient)");
		
		// Dados
		String idArmadora = "BMW";		
		String RECURSO = URI_BASE_ARMADORAS + "/" + idArmadora ;
		
		bitacora.debug("testGetArmadoraXID_JSonXML_SiExisteYenJson: Recurso= " + RECURSO );		
		// Cuando
		
	    webClient.get().uri(RECURSO)
				       .accept(MediaType.APPLICATION_JSON)
		         .exchange()
		// Entonces
		         .expectStatus().isOk()
		         .expectHeader().contentType(MediaType.APPLICATION_JSON)
		         .expectBody()
		             .jsonPath("$.clave").isEqualTo(idArmadora)
		             .jsonPath("$.nombre").isNotEmpty()
		         .consumeWith(resp -> bitacora.debug("testGetArmadoraXID_JSonXML_SiExisteYenJson: response = " + resp));
				
	}

//	@Test
	void testGetModelosXarmadora() {
		fail("Not yet implemented");
	}

//	@Test
	void testGetArmadorasXml() {
		fail("Not yet implemented");
	}

	@Test
	void testInsertarArmadora_OK_FORM_URLENCODED( @Autowired WebTestClient webClient ) {
		bitacora.info("testInsertarArmadora_OK_FORM_URLENCODED(webClient)");
		
		// Dados
		String idArmadora = "ARMMEX";
		String nombre = "Armadora Mexicana SA de CV";
		String paisOrigen = "México";
		int nPlantas = 2;
		String RECURSO = URI_BASE_ARMADORAS;
		
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("clave", idArmadora);
		formData.add("nombre", nombre);
		formData.add("pais", paisOrigen);
		formData.add("nplantas", "" + nPlantas);
		
		bitacora.info("testInsertarArmadora_OK_FORM_URLENCODED: Recurso= " + RECURSO );	
		
		// Cuando
		
		EntityExchangeResult<String> resCadArmadora = 		
				webClient.post().uri(RECURSO)
				       .accept(MediaType.TEXT_PLAIN)
				       .contentType(MediaType.APPLICATION_FORM_URLENCODED)
				       .body(BodyInserters.fromFormData(formData))
		         .exchange()
		//Entonces
		         .expectStatus().isOk()
		         .expectHeader().contentType(MediaType.TEXT_PLAIN_VALUE + "; charset=ISO-8859-1")
		         .expectBody(String.class).returnResult();
		
		String cadArmadora = resCadArmadora.getResponseBody();
		assertThat(cadArmadora).isNotBlank()
		                       .contains(idArmadora)
		                       .contains(nombre)
		                       .contains(paisOrigen)
		                       .doesNotContain("Error");
		
		bitacora.info("testInsertarArmadora_OK_FORM_URLENCODED: cadArmadora = " + cadArmadora);
		
	}

	@Test
	void testInsertarArmadora_Json_OK( @Autowired WebTestClient webClient ) {
		bitacora.info("testInsertarArmadora_Json_OK(webClient)");
		
		// Dados
		String idArmadora = "ARMMEX2";
		String nombre = "Otra Armadora Mexicana SA de CV";
		String paisOrigen = "México";
		int nPlantas = 3;
		
		Armadora armadora = new Armadora(idArmadora,nombre, paisOrigen, nPlantas);
		String RECURSO = URI_BASE_ARMADORAS;
		
		bitacora.info("testInsertarArmadora_Json_OK: Recurso= " + RECURSO );	
		bitacora.info("testInsertarArmadora_Json_OK: Json Body= " + armadora.toJson().toString() );	
		
		// Cuando
		
		EntityExchangeResult<String> resCadArmadora = 		
				webClient.post().uri(RECURSO)
				       .accept(MediaType.TEXT_PLAIN)
				       .contentType(MediaType.APPLICATION_JSON)
				       .bodyValue(armadora.toJson().toString())
		         .exchange()
		         
		 		//Entonces
		         .expectStatus().isOk()
		         .expectHeader().contentType(MediaType.TEXT_PLAIN_VALUE + "; charset=ISO-8859-1")
		         .expectBody(String.class)
		         .returnResult();
		
		String cadArmadora = resCadArmadora.getResponseBody();
		assertThat(cadArmadora).isNotBlank()
		                       .contains(idArmadora)
		                       .contains(nombre)
		                       .contains(paisOrigen)
		                       .doesNotContain("Error");
		
		bitacora.info("testInsertarArmadora_Json_OK: cadArmadora = " + cadArmadora);
	}

//	@Test
	void testActualizarArmadora_Json() {
		fail("Not yet implemented");
	}

//	@Test
	void testGetArmadorasHateoas() {
		fail("Not yet implemented");
	}

}
