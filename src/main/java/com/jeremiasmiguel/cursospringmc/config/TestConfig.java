package com.jeremiasmiguel.cursospringmc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.jeremiasmiguel.cursospringmc.services.DBService;
import com.jeremiasmiguel.cursospringmc.services.EmailService;
import com.jeremiasmiguel.cursospringmc.services.MockEmailService;

@Configuration
@Profile("test")
/* Classe de configuração para o profile de teste, presente no application-test.properties, 
 * com suas específicas funcionalidades, como instanciar dados pré-programados para teste
 * 
 * Com isso, os BEANS relacionadas a ele somente serão modificados e usados se o profile
 * ativo for o mesmo, e para verificar isso basta olhar o profile ativo na classe
 * application.properties, onde há a anotação específica.
 */
public class TestConfig {

	@Autowired
	DBService dbService;
	
	@Bean
	// Método responsável por instanciar o banco de dados no profile de teste
	public boolean instantiateDatabase() throws ParseException {
		this.dbService.instantiateTestDatabase();
		return true;
	}
	
	/* Método que instancia a interface EmailService e transforma em um MockEmailService,
	 * com isso, ao injetar um EmailService, como na inserção de um Pedido, há a busca 
	 * por um Bean, que é achado nesse arquivo, e há o polimorfismo
	 */
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
	
}
