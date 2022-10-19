package org.qtx.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.qtx.web.ArmadoraApiRest;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfiguracionJersey extends ResourceConfig {
	public ConfiguracionJersey() {
		register(ArmadoraApiRest.class);
	}
}
