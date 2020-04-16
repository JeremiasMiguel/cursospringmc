package com.jeremiasmiguel.cursospringmc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.jeremiasmiguel.cursospringmc.services.DBService;

@Configuration
@Profile("dev")
/* Classe de configuração para o profile de desenvolvimento, presente no application-dev.properties, 
 * com suas específicas funcionalidades
 * 
 * Com isso, os BEANS relacionadas a ele somente serão modificados e usados se o profile
 * ativo for o mesmo, e para verificar isso basta olhar o profile ativo na classe
 * application.properties, onde há a anotação específica.
 */
public class DevConfig {

	@Autowired
	DBService dbService;
	
	// Resgatando um valor do application-dev.properties e atribuindo a uma variável
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	// Método responsável por instanciar o banco de dados no profile de teste
	public boolean instantiateDatabase() throws ParseException {
		/* Verificando se a variável de estratégia de inicialização do banco de dados
		 * tem o valor CREATE, se não for, os dados não serão instanciados pelo método
		 */
		if(!"create".equals(strategy)) {
			return false;
		}
		
		this.dbService.instantiateTestDatabase();
		return true;
	}
	
}
