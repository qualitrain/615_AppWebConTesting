package org.qtx.servicios;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.qtx.entidades.Armadora;
import org.qtx.entidades.ModeloAuto;
import org.qtx.web.IArmadoraService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TestUnitArmadoraService {
	@MockBean
	IGestorDatos gestorDatos;
	
	@Autowired
	IArmadoraService armadoraService;

	private static Logger bitacora = LoggerFactory.getLogger(TestUnitArmadoraService.class);
	
	public TestUnitArmadoraService() {
		bitacora.info("TestArmadoraService()");
	}
	
	@Test
	void testGetArmadoras() {
		bitacora.info("testGetArmadoras()");
		// Dados
		Set<Armadora> armadorasMock = new HashSet<>();
		armadorasMock.add(new Armadora("BMW","BMW de México SA de CV","Alemania",2));
		armadorasMock.add(new Armadora("KIA","Autos de Corea SA de CV","Corea del Sur",1));
		armadorasMock.add(new Armadora("Mazda","Autos de Oriente SA de CV","Japón",1));
		when( gestorDatos.getArmadorasTodas() ).thenReturn(armadorasMock);
 		
		// Cuando
		List<Armadora> listArmadoras = armadoraService.getArmadoras();
		
		// Entonces
		assertThat(listArmadoras).isNotNull()
		                         .asList().containsAll(armadorasMock);
		bitacora.info("testGetArmadoras() caArmadoras = " + listArmadoras.toString());
	}
	
	@Test
	void testGetArmadoraXID_Existe() {
		bitacora.info("testGetArmadoraXID_Existe()");
		// Dados
		String cveArmadora = "KIA";
		Armadora armadoraBuscada = new Armadora(cveArmadora,"Autos de Corea SA de CV","Corea del Sur",1);
		when( gestorDatos.getArmadoraXID(cveArmadora) ).thenReturn( armadoraBuscada );
		
		// Cuando
		Armadora armadora = armadoraService.getArmadoraXID(cveArmadora);
		
		// Entonces
		assertThat(armadora).isEqualTo(armadoraBuscada);
		bitacora.info("testGetArmadoraXID_Existe() armadora=" + armadora);
	}

	@Test
	void testGetArmadoraXID_No_Existe() {
		bitacora.info("testGetArmadoraXID_No_Existe()");
		// Dados
		String cveArmadora = "KIA";
		String label_NO_EXISTE = "NO EXISTE";
		when( gestorDatos.getArmadoraXID(cveArmadora) ).thenReturn( null );
		
		// Cuando
		Armadora armadora = armadoraService.getArmadoraXID(cveArmadora);
		
		// Entonces
		assertThat(armadora).isNotNull()
		                    .hasFieldOrPropertyWithValue("clave", cveArmadora)
		                    .hasFieldOrPropertyWithValue("nombre", label_NO_EXISTE)
		                    .hasFieldOrPropertyWithValue("paisOrigen", label_NO_EXISTE);
		
		bitacora.info("testGetArmadoraXID_No_Existe() armadora= " + armadora);
	}
	
	@Test
	void testGetModelosXarmadora() {
		bitacora.info("testGetModelosXarmadora()");
		// Dados
		String cveArmadoraExistente = "Mazda"; //tiene 2 o más modelos
		
		Armadora mazda = new Armadora("Mazda", "Mazda SA","Japón",1);
		Set<ModeloAuto> modelosMazda = new HashSet<>();
		modelosMazda.add(new ModeloAuto("M-3", "Mazda 3", mazda, "Standard", false));
		modelosMazda.add(new ModeloAuto("M-5", "Mazda 5", mazda, "Luxe", false));
		mazda.setModelos(modelosMazda);
		when( gestorDatos.getArmadoraConModelosXID(cveArmadoraExistente) ).thenReturn( mazda );
		
		// Cuando
		List<ModeloAuto> listaModelos = armadoraService.getModelosXarmadora(cveArmadoraExistente);
		
		// Entonces
		assertThat(listaModelos).isNotNull()
		                        .asList()
		                        .containsAll(modelosMazda);
		
		bitacora.info("testGetModelosXarmadora() modelos= " + listaModelos.toString());
	}
	
}
