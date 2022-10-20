package org.qtx.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class TestArmadoraApiRest {
	
	private static WebTestClient client;
	private static String URL = "http://localhost:8085/miapp/webapi/armadoras";

	private static Logger bitacora = LoggerFactory.getLogger(TestArmadoraApiRest.class);

	@BeforeAll
	static void inicializarClienteWeb() {
		client = WebTestClient.bindToServer()
				              .baseUrl(URL)
				              .build();
	}
	@AfterAll
	static void cerrarCliente() {
	}
	
	public TestArmadoraApiRest() {
		bitacora.info("TestArmadoraApiRest()");
	}
	
	@Test
	void testGetArmadoras() {
		bitacora.info("testGetArmadoras");
		
		//Dados
		
		//Cuando
		
		EntityExchangeResult<String> resCadArmadoras = client.get().accept(MediaType.TEXT_PLAIN)
		            .exchange()
		//Entonces
		            .expectStatus().isOk()
		            .expectHeader().contentType(MediaType.TEXT_PLAIN)
		            .expectBody(String.class).returnResult();
		
		String cadArmadoras = resCadArmadoras.getResponseBody();
		assertThat(cadArmadoras).isNotBlank();
		
		bitacora.info("cadArmadoras = " + cadArmadoras);
	}

	@Test
	void testGetArmadoraXID_texto() {
		fail("Not yet implemented");
	}

	@Test
	void testGetArmadoraXID_html() {
		fail("Not yet implemented");
	}

	@Test
	void testGetArmadoraXID_JSonXML() {
		fail("Not yet implemented");
	}

	@Test
	void testGetModelosXarmadora() {
		fail("Not yet implemented");
	}

	@Test
	void testGetArmadorasXml() {
		fail("Not yet implemented");
	}

	@Test
	void testInsertarArmadora() {
		fail("Not yet implemented");
	}

	@Test
	void testInsertarArmadora_Json() {
		fail("Not yet implemented");
	}

	@Test
	void testActualizarArmadora_Json() {
		fail("Not yet implemented");
	}

	@Test
	void testGetArmadorasHateoas() {
		fail("Not yet implemented");
	}

}
