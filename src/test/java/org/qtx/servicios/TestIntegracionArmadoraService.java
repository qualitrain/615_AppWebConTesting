package org.qtx.servicios;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.qtx.entidades.Armadora;
import org.qtx.entidades.ModeloAuto;
import org.qtx.web.IArmadoraService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestIntegracionArmadoraService {
	
	@Autowired
	IArmadoraService armadoraService;

	private static Logger bitacora = LoggerFactory.getLogger(TestIntegracionArmadoraService.class);
	
	public TestIntegracionArmadoraService() {
		bitacora.info("TestArmadoraService()");
	}

	@Test
	void testGetArmadoras() {
		bitacora.info("testGetArmadoras()");
		// Dados
		
		// Cuando
		List<Armadora> listArmadoras = armadoraService.getArmadoras();
		
		// Entonces
		assertNotNull(listArmadoras);
		bitacora.info(listArmadoras.toString());
	}

	@Test
	void testGetArmadoras_String() {
		bitacora.info("testGetArmadoras_String()");
		// Dados
		
		// Cuando
		String cadArmadoras = armadoraService.getArmadoras_String();
		
		// Entonces
		assertNotNull(cadArmadoras);
		bitacora.info(cadArmadoras);
	}
	
	@Test
	void testGetArmadoraXID_texto_ArmadoraExiste() {
		bitacora.info("testGetArmadoraXID_texto_ArmadoraExiste()");
		// Dados
		String cveArmadoraExistente = "GM";
		
		// Cuando
		String cadArmadora = armadoraService.getArmadoraXID_texto(cveArmadoraExistente);
		
		// Entonces
		assertThat(cadArmadora).isNotBlank()
		                       .doesNotContain("NO EXISTE");
		bitacora.info(cadArmadora);
	}

	@Test
	void testGetArmadoraXID_texto_ArmadoraNoExiste() {
		bitacora.info("testGetArmadoraXID_texto_ArmadoraNoExiste()");
		// Dados
		String cveArmadoraInexistente = "XX";
		
		// Cuando
		String cadArmadora = armadoraService.getArmadoraXID_texto(cveArmadoraInexistente);
		
		// Entonces
		assertThat(cadArmadora).isNotNull()
		                       .isNotBlank()
		                       .contains("NO EXISTE");
		bitacora.info(cadArmadora);
	}
	
	@Test
	void testGetArmadoraXID_html_ArmadoraExiste() {
		bitacora.info("testGetArmadoraXID_html_ArmadoraExiste()");
		// Dados
		String cveArmadoraExistente = "GM";
		
		// Cuando
		String cadArmadora = armadoraService.getArmadoraXID_html(cveArmadoraExistente);
		
		// Entonces
		assertThat(cadArmadora).isNotBlank()
		                       .doesNotContain("NO EXISTE")
		                       .contains("<span")
		                       .contains("</span")
		                       .contains("Clave")
		                       .contains("Nombre");
		bitacora.info(cadArmadora);		
	}

	@Test
	void testGetArmadoraXID_html_ArmadoraNoExiste() {
		bitacora.info("testGetArmadoraXID_html_ArmadoraNoExiste()");
		// Dados
		String cveArmadoraExistente = "XX";
		
		// Cuando
		String cadArmadora = armadoraService.getArmadoraXID_html(cveArmadoraExistente);
		
		// Entonces
		assertThat(cadArmadora).isNotBlank()
							   .isNotNull()
		                       .contains("NO EXISTE")
		                       .contains("<span")
		                       .contains("</span");
		bitacora.info(cadArmadora);		
	}
	
	@Test
	void testGetArmadoraXID_ArmadoraExiste() {
		bitacora.info("testGetArmadoraXID_ArmadoraExiste()");
		// Dados
		String cveArmadoraExistente = "GM";
		String nombre = "General Motors Company";
		
		// Cuando
		Armadora armadora = armadoraService.getArmadoraXID(cveArmadoraExistente);
		
		// Entonces
		assertThat(armadora).isNotNull()
		                    .hasFieldOrPropertyWithValue("clave", cveArmadoraExistente)
		                    .hasFieldOrPropertyWithValue("nombre", nombre);
		
		bitacora.info(armadora.toString());		
		
	}

	@Test
	void testGetArmadoraXID_ArmadoraNoExiste() {
		bitacora.info("testGetArmadoraXID_ArmadoraExiste()");
		// Dados
		String cveArmadoraExistente = "XX";
		String labelNoExiste = "NO EXISTE";
		
		// Cuando
		Armadora armadora = armadoraService.getArmadoraXID(cveArmadoraExistente);
		
		// Entonces
		assertThat(armadora).isNotNull()
		                    .hasFieldOrPropertyWithValue("clave", cveArmadoraExistente)
		                    .hasFieldOrPropertyWithValue("nombre", labelNoExiste)
		                    .hasFieldOrPropertyWithValue("paisOrigen", labelNoExiste);
		
		bitacora.info(armadora.toString());		
	}
	
	@Test
	void testGetModelosXarmadora() {
		bitacora.info("testGetModelosXarmadora()");
		// Dados
		String cveArmadoraExistente = "Mazda"; //tiene 2 o más modelos
		
		// Cuando
		List<ModeloAuto> listaModelos = armadoraService.getModelosXarmadora(cveArmadoraExistente);
		
		// Entonces
		assertThat(listaModelos).isNotNull();
		assertTrue(listaModelos.size()>1);
		bitacora.info(listaModelos.toString());
	}

	@Test
	void testGetModelosXarmadora_sinModelos() {
		bitacora.info("testGetModelosXarmadora_sinModelos()");
		// Dados
		String cveArmadoraExistente = "Renault"; //tiene 0 modelos
		
		// Cuando
		List<ModeloAuto> listaModelos = armadoraService.getModelosXarmadora(cveArmadoraExistente);
		
		// Entonces
		assertThat(listaModelos).isNotNull();
		assertTrue(listaModelos.size() == 0);
		bitacora.info(listaModelos.toString());
	}
	@Test
	void testGetModelosXarmadora_ArmadoraInexistente() {
		bitacora.info("testGetModelosXarmadora_ArmadoraInexistente()");
		// Dados
		String cveArmadoraInexistente = "XX";
		String labelNoExiste = "NO EXISTE";
		
		// Cuando
		List<ModeloAuto> listaModelos = armadoraService.getModelosXarmadora(cveArmadoraInexistente);
		
		// Entonces
		assertThat(listaModelos).isNotNull();
		assertTrue(listaModelos.size() == 1);
		assertThat(listaModelos.get(0)).isNotNull();
		assertThat(listaModelos.get(0).getClaveModelo()).contains(labelNoExiste)
		                                                .contains(cveArmadoraInexistente);
		bitacora.info(listaModelos.toString());
	}
	@Test
	void testInsertarArmadora_Plana_OK() {
		bitacora.info("testInsertarArmadora_Plana_OK()");
		// Dados
		Armadora armadoraNva = new Armadora("Nva","Nueva Armadora","Israel",2);
		
		// Cuando
		Armadora armadoraInsertada = armadoraService.insertarArmadora(armadoraNva);
		Armadora armadoraRecuperada = armadoraService.getArmadoraXID(armadoraNva.getClave());
		
		//Entonces
		assertTrue(armadoraRecuperada.equivaleA(armadoraNva));
		assertTrue(armadoraRecuperada.equivaleA(armadoraInsertada));
		
		bitacora.info(armadoraInsertada.toString());		
	}
	@Test
	void testInsertarArmadora_Plana_FaltanCampos() {
		bitacora.info("testInsertarArmadora_Plana_FaltanCampos()");
		// Dados
		Armadora armadoraNva = new Armadora("ArmErr"," ",null,0);
		
		// Cuando
		Armadora armadoraInsertada = armadoraService.insertarArmadora(armadoraNva);
		Armadora armadoraRecuperada = armadoraService.getArmadoraXID(armadoraNva.getClave());
		
		//Entonces
		assertThat(armadoraInsertada.getClave()).isNotNull()
		                                        .contains("Error");
		assertThat(armadoraRecuperada.getNombre()).isNotNull()
		                                          .contains("NO EXISTE");
		
		bitacora.info("armadoraInsertada = " + armadoraInsertada);		
		bitacora.info("armadoraRecuperada = " + armadoraRecuperada);		
	}
	@Test
	void testInsertarArmadora_Plana_Duplicada() {
		bitacora.info("testInsertarArmadora_Plana_Duplicada()");
		// Dados
		Armadora armadoraNva = new Armadora("ArmDuplicada","Armadora para checar inserción duplicada","México",0);
		
		// Cuando
		Armadora armadoraInsertada = armadoraService.insertarArmadora(armadoraNva);
		Armadora armadoraRecuperada = armadoraService.getArmadoraXID(armadoraNva.getClave());
		Armadora armadoraReinsertada = armadoraService.insertarArmadora(armadoraNva);
		
		// Entonces
		assertThat(armadoraReinsertada.getClave()).isNotNull()
		                                          .contains("Error");
		assertThat(armadoraRecuperada.getNombre()).isEqualTo(armadoraNva.getNombre());
		
		bitacora.info("armadoraInsertada = "   + armadoraInsertada);		
		bitacora.info("armadoraRecuperada = "  + armadoraRecuperada);		
		bitacora.info("armadoraReinsertada = " + armadoraReinsertada);		
	}
	
	@Test
	void testActualizarArmadora_Plana() {
		bitacora.info("testActualizarArmadora_Plana()");
		// Dados
		Armadora armadoraNva = new Armadora("ArmTestAct","Armadora para checar actualización","Singapur",1);
		Armadora armadoraInsertada = armadoraService.insertarArmadora(armadoraNva);
		armadoraNva.setNombre("Armadora ya actualizada");
		armadoraNva.setPaisOrigen("Macedonia del Norte");
		armadoraNva.setnPlantas(0);
		
		//Cuando
		Armadora armadoraModificada = armadoraService.actualizarArmadora(armadoraNva);
		Armadora armadoraRecuperada = armadoraService.getArmadoraXID(armadoraNva.getClave());
		
		//Entonces		
		assertThat(armadoraModificada).isEqualTo(armadoraNva)
		                              .isEqualTo(armadoraRecuperada);
		
		bitacora.info("armadoraInsertada = "  + armadoraInsertada);
		bitacora.info("armadoraNva = "        + armadoraNva);
		bitacora.info("armadoraModificada = " + armadoraModificada);
		bitacora.info("armadoraRecuperada = " + armadoraRecuperada);
	}

}
