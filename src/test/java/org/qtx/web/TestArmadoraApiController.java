package org.qtx.web;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.qtx.entidades.Armadora;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = ArmadoraApiController.class)
public class TestArmadoraApiController {
		
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IArmadoraService servicioArmadoras;
	
	@Test
	public void testGetArmadoras_texto() {
		//Dados
		List<Armadora> armadorasMock = new ArrayList<>();
		armadorasMock.add(new Armadora("BMW","BMW de México SA de CV","Alemania",2));
		armadorasMock.add(new Armadora("KIA","Autos de Corea SA de CV","Corea del Sur",1));
		armadorasMock.add(new Armadora("Mazda","Autos de Oriente SA de CV","Japón",1));
		
		when(servicioArmadoras.getArmadoras_String()).thenReturn(armadorasMock.toString());		
		//Cuando
		
		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/armadoras")
					                              .accept(MediaType.TEXT_PLAIN_VALUE))
        //Entonces
			                                      .andExpect(status().isOk())
			                                      .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8"))
			                                      .andExpect(content().string(armadorasMock.toString())).andReturn();
		} catch (Exception e) {
			fail("Ex:" + e.getClass().getName() + ", Msg:" + e.getMessage());
		}
	}
}
