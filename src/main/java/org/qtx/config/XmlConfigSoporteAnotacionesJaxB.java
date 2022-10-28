package org.qtx.config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

@Configuration
public class XmlConfigSoporteAnotacionesJaxB {
    @Bean
    Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
    	Jackson2ObjectMapperBuilderCustomizer personalizador = 
    			          (mapperBuilder) -> mapperBuilder.modulesToInstall(new JaxbAnnotationModule());
        return personalizador;
    }
}
