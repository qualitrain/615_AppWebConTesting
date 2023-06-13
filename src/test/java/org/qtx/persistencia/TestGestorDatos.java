package org.qtx.persistencia;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.qtx.entidades.Armadora;
import org.qtx.entidades.ModeloAuto;
import org.qtx.servicios.IGestorDatos;
import org.qtx.servicios.PersistenciaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

@SpringBootTest
public class TestGestorDatos {
	@Autowired
	private IGestorDatos gestorDatos;
	
	private static Logger bitacora = LoggerFactory.getLogger(TestGestorDatos.class);
		
	public TestGestorDatos() {
		bitacora.info("TestgestorDatos()");
	}
	

	@Test
	@Transactional
	@DisplayName(value = "Test Get Armadora X ID usando contexto transaccional")
	public void testGetArmadoraXID() {
		bitacora.debug("TestgestorDatos().testGetArmadoraXID()");

		// Preparacion
		assumeTrue(this.gestorDatos != null);
		if(this.gestorDatos.getArmadoraXID("TestArm")!=null)
			gestorDatos.eliminarArmadora("TestArm");		
		
		// Dados
		Armadora armadora = new Armadora("TestArm","ArmTest AG","Alemania",2);
		gestorDatos.insertarArmadora(armadora);
		
		// Cuando
		Armadora armadoraLeida = gestorDatos.getArmadoraXID(armadora.getClave());
		
		// Entonces
		assertEquals( armadora.getClave(),      armadoraLeida.getClave() );
		assertEquals( armadora.getNombre(),     armadoraLeida.getNombre() );
		assertEquals( armadora.getnPlantas(),   armadoraLeida.getnPlantas() );
		assertEquals( armadora.getPaisOrigen(), armadoraLeida.getPaisOrigen() );		
	}
	
	@Test
	@Transactional
	public void testGetArmadoraXID_Inexistente_returnNull() {
		bitacora.debug("TestgestorDatos().testGetArmadoraXID_Inexistente_returnNull()");
		
		// Preparacion
		assumeTrue(this.gestorDatos != null);
		
		if(gestorDatos.getArmadoraXID("XXXXX") != null)
			gestorDatos.eliminarArmadora("XXXXX");
		
		// Dados
		String cveArmadoraInexistente = "XXXXX";
		
		// Cuando
		Armadora armadoraLeida = gestorDatos.getArmadoraXID(cveArmadoraInexistente);
		
		// Entonces
		assertEquals(armadoraLeida, null);		
	}
	
	@Test
	@Transactional
	public void testInsertarArmadora_Plana() {
		bitacora.debug("TestgestorDatos().testInsertarArmadora_Plana()");
		
		// Preparacion
		assumeTrue(this.gestorDatos != null);
		if(this.gestorDatos.getArmadoraXID("NvaArma") != null)
			this.gestorDatos.eliminarArmadora("NvaArma");
		
		// Dados
		Armadora armadoraNva = new Armadora("NvaArma","Armadora Mexicana SA de CV","Brasil",0);
		
		// Cuando
		Armadora armadoraInsertada = gestorDatos.insertarArmadora(armadoraNva);
		
		// Entonces	
		Armadora armadoraTest = gestorDatos.getArmadoraConModelosXID(armadoraNva.getClave());
		assertTrue(armadoraNva.equivaleA(armadoraTest));
		assertTrue(armadoraNva.equivaleA(armadoraInsertada));		
	}
	
	@Test
	@Transactional
	public void testInsertarArmadora_ConModelos() {
		bitacora.debug("TestgestorDatos().testInsertarArmadora_ConModelos()");
		
		// Preparacion
		assumeTrue(this.gestorDatos != null);
		
		if(this.gestorDatos.getModeloAutoXID("F-45")!=null)
			this.gestorDatos.eliminarModeloAuto("F-45");
		
		if(this.gestorDatos.getModeloAutoXID("F-49")!=null)
			this.gestorDatos.eliminarModeloAuto("F-49");
		
		if(this.gestorDatos.getModeloAutoXID("F-55")!=null)
			this.gestorDatos.eliminarModeloAuto("F-55");
		
		if(this.gestorDatos.getArmadoraXID("Armadora")!=null)
			gestorDatos.eliminarArmadora("Armadora");
		
		// Dados
		Armadora armadoraNva = new Armadora("Armadora","Armadora Agrupada SA de CV","Brasil",0);
		Set<ModeloAuto> modelos = new HashSet<>();
		modelos.add(new ModeloAuto("F-45","FireRabbit",  armadoraNva, "Custom", true));
		modelos.add(new ModeloAuto("F-49","FireRaccoon", armadoraNva, "Austero",true));
		modelos.add(new ModeloAuto("F-55","FireRaccoon", armadoraNva, "Típico", true));		
		armadoraNva.setModelos(modelos);
		
		// Cuando
		Armadora armadoraInsertada = gestorDatos.insertarArmadora(armadoraNva);
		
		// Entonces	
		Armadora armadoraTest = gestorDatos.getArmadoraConModelosXID(armadoraNva.getClave());
		assertTrue(armadoraNva.equivaleA(armadoraTest));
		assertTrue(armadoraNva.equivaleA(armadoraInsertada));		
	}
	
	@Test
	@Transactional
	public void testInsertarArmadora_Duplicada() {
		bitacora.debug("TestgestorDatos().testInsertarArmadora_Duplicada()");
		
		// Preparacion
		assumeTrue(this.gestorDatos != null);
		if(this.gestorDatos.getArmadoraXID("Armadora02")!=null)
			this.gestorDatos.eliminarArmadora("Armadora02");
		
		// Dados
		Armadora armadoraNva = new Armadora("Armadora02", "Armadora X SA de CV", "México", 1);
		gestorDatos.insertarArmadora(armadoraNva);
		
		// Cuando
		// Entonces	
		
		assertThrows( PersistenciaException.class, () -> gestorDatos.insertarArmadora(armadoraNva) );
	}	

	@Test
	@Transactional
	public void testActualizarArmadora_Plana() {
		bitacora.info("testActualizarArmadora_Plana");
		bitacora.info("IGestorDatos:" + this.gestorDatos.getClass().getName());
		// Preparacion
		assumeTrue(this.gestorDatos != null);
		if(this.gestorDatos.getArmadoraXID("Armadora03")!=null)
			this.gestorDatos.eliminarArmadora("Armadora03");
		
		// Dados
		Armadora armadoraNva = new Armadora("Armadora03", "Armadora Z SA de CV", "México", 0);
		Armadora armadoraInsertada = gestorDatos.insertarArmadora(armadoraNva);
		
		armadoraNva.setNombre("Armadora modificada SA de CV");
		armadoraNva.setnPlantas(1);
		armadoraNva.setPaisOrigen("Uruguay");
		
		// Cuando
		Armadora armadoraModificada = gestorDatos.actualizarArmadora(armadoraNva);
		Armadora armadoraRecuperada = gestorDatos.getArmadoraXID(armadoraNva.getClave());
		
		// Entonces
		
		bitacora.info("armadoraInsertada = " + armadoraInsertada);
		bitacora.info("armadoraModificada = " + armadoraModificada);
		bitacora.info("armadoraRecuperada = " + armadoraRecuperada);
		
		assertThat(armadoraRecuperada.getClave()).isEqualTo(armadoraModificada.getClave());
		assertThat(armadoraRecuperada.getNombre()).isEqualTo(armadoraModificada.getNombre());
		assertThat(armadoraRecuperada.getnPlantas()).isEqualTo(armadoraModificada.getnPlantas());
		assertThat(armadoraRecuperada.getPaisOrigen()).isEqualTo(armadoraModificada.getPaisOrigen());
		
	}

	
}
