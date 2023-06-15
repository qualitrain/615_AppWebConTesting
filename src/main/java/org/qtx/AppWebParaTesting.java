package org.qtx;

import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;

@SpringBootApplication
public class AppWebParaTesting {

	private static Logger bitacora = LoggerFactory.getLogger(AppWebParaTesting.class);

	public static void main(String[] args) {
		SpringApplication.run(AppWebParaTesting.class, args);
	}
	
	@Bean(name = "transactionManager")
	public JpaTransactionManager getGestorTransacciones( EntityManagerFactory emf) {
		bitacora.debug("getGestorTransacciones()");
		return new JpaTransactionManager(emf);
	}

}
